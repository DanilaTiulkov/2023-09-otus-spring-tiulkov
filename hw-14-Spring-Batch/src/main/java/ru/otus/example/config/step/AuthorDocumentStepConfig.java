package ru.otus.example.config.step;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.example.model.dto.AuthorDto;
import ru.otus.example.model.h2.Author;
import ru.otus.example.model.h2.temp.AuthorTemp;
import ru.otus.example.model.mongo.AuthorDoc;
import ru.otus.example.service.AuthorService;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;

@Configuration
public class AuthorDocumentStepConfig {


    private JobRepository jobRepository;

    private PlatformTransactionManager platformTransactionManager;

    private final EntityManagerFactory emf;

    private final JdbcTemplate jdbcTemplate;

    private final DataSource dataSource;

    private final String CREATE_TABLE_SQL = "create table Authors_temp(" +
                                "id bigint auto_increment PRIMARY KEY, " +
                                "author_doc_id varchar(255)," +
                                "author_id bigint);";

    private final String DROP_TABLE_SQL = "drop table Authors_temp";

    public AuthorDocumentStepConfig(EntityManagerFactory emf, JdbcTemplate jdbcTemplate, DataSource dataSource,
                                    PlatformTransactionManager platformTransactionManager, JobRepository jobRepository) {
        this.emf = emf;
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
        this.platformTransactionManager = platformTransactionManager;
        this.jobRepository = jobRepository;
    }

    @Bean
    public MongoItemReader<AuthorDoc> authorDocReader(MongoOperations mongoOperations) {
        return new MongoItemReaderBuilder<AuthorDoc>()
                .name("authorDocReader")
                .template(mongoOperations)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .targetType(AuthorDoc.class)
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<AuthorDto> authorWriter() {
        return new JdbcBatchItemWriterBuilder<AuthorDto>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("insert into Authors(author_id, full_Name) values(select author_id from Authors_temp where author_doc_id = :authorDocId, :fullName)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<AuthorDto> authorTempWriter() {
        return new JdbcBatchItemWriterBuilder<AuthorDto>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("insert into Authors_temp(author_doc_id, author_id) values(:authorDocId, nextval('authors_id_seq'))")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public CompositeItemWriter<AuthorDto> authorCompositeItemWriter(
            JdbcBatchItemWriter<AuthorDto> authorTempWriter,
            JdbcBatchItemWriter<AuthorDto> authorWriter) {
        CompositeItemWriter<AuthorDto> writer = new CompositeItemWriter<>();
        writer.setDelegates(List.of(authorTempWriter, authorWriter));
        return writer;
    }

    @Bean
    public ItemProcessor<AuthorDoc, AuthorDto> processor(AuthorService authorService) {
        return authorService::transform;
    }

    @Bean
    public Step transformAuthorDocStep(ItemReader<AuthorDoc> reader, CompositeItemWriter<AuthorDto> authorCompositeItemWriter,
                                       ItemProcessor<AuthorDoc, AuthorDto> processor) {
        var authorTempId = 1;
        return new StepBuilder("transformAuthorsStep", jobRepository)
                .<AuthorDoc, AuthorDto>chunk(5, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(authorCompositeItemWriter)
//                .listener(new ItemReadListener<AuthorDoc>() {
//                    @Override
//                    public void afterRead(AuthorDoc item) {
//                        insertAuthorsDoc(item);
//                    }
//                })
//                .listener(new ItemWriteListener<Author>() {
//                    @Override
//                    public void afterWrite(Chunk<? extends Author> items) {
//                        insertAuthors(items, authorTempId);
//                    }
//                })
                .build();
    }

    @Bean
    public Step createTableAuthorsTemp() {
        return new StepBuilder("createTableAuthorTemp", jobRepository)
                .tasklet(createTableAuthorsTempTasklet(), platformTransactionManager)
                .build();
    }

    @Bean
    public Tasklet createTableAuthorsTempTasklet() {
        return (contribution, chunkContext) -> {
            jdbcTemplate.execute(CREATE_TABLE_SQL);
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Step dropTableAuthorsTemp() {
        return new StepBuilder("dropTableAuthorTemp", jobRepository)
                .tasklet(dropTableAuthorsTempTasklet(), platformTransactionManager)
                .build();
    }

    @Bean
    public Tasklet dropTableAuthorsTempTasklet() {
        return (contribution, chunkContext) -> {
            jdbcTemplate.execute(DROP_TABLE_SQL);
            return RepeatStatus.FINISHED;
        };
    }

    private void insertAuthors(Chunk<? extends Author> items, int authorTempId) {
        for (Author author: items) {
            jdbcTemplate.execute("update Authors_temp set author_id = %d where id = %d"
                    .formatted(author.getAuthorId(), authorTempId));
            ++authorTempId;
        }
    }

    private void insertAuthorsDoc(AuthorDoc item) {
        jdbcTemplate.execute("insert into Authors_temp(author_doc_id) values ('%s')".formatted(item.getAuthorId()));
    }
}
