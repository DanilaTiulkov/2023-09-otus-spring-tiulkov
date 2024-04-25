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
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.example.model.h2.Author;
import ru.otus.example.model.mongo.AuthorDoc;
import ru.otus.example.service.AuthorService;

import java.util.HashMap;

@Configuration
public class AuthorDocumentStepConfig {


    private JobRepository jobRepository;

    private PlatformTransactionManager platformTransactionManager;

    private final EntityManagerFactory emf;

    private final JdbcTemplate jdbcTemplate;

    private final String CREATE_TABLE_SQL = "create table Authors_temp(" +
                                "id bigint auto_increment PRIMARY KEY, " +
                                "author_doc_id varchar(255)," +
                                "author_id bigint);";

    private final String DROP_TABLE_SQL = "drop table Authors_temp";

    public AuthorDocumentStepConfig(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
                                   EntityManagerFactory emf, JdbcTemplate jdbcTemplate) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.emf = emf;
        this.jdbcTemplate = jdbcTemplate;
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
    public JpaItemWriter<Author> authorWriter() {
        return new JpaItemWriterBuilder<Author>()
                .entityManagerFactory(emf)
                .build();
    }

    @Bean
    public ItemProcessor<AuthorDoc, Author> processor(AuthorService authorService) {
        return authorService::transform;
    }

    @Bean
    public Step transformAuthorDocStep(ItemReader<AuthorDoc> reader, ItemWriter<Author> writer,
                                       ItemProcessor<AuthorDoc, Author> processor) {
        var authorTempId = 1;
        return new StepBuilder("transformAuthorsStep", jobRepository)
                .<AuthorDoc, Author>chunk(5, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new ItemReadListener<AuthorDoc>() {
                    @Override
                    public void afterRead(AuthorDoc item) {
                        insertAuthorsDoc(item);
                    }
                })
                .listener(new ItemWriteListener<Author>() {
                    @Override
                    public void afterWrite(Chunk<? extends Author> items) {
                        insertAuthors(items, authorTempId);
                    }
                })
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
