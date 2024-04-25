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
import ru.otus.example.model.h2.Genre;
import ru.otus.example.model.mongo.GenreDoc;
import ru.otus.example.service.GenreService;

import java.util.HashMap;

@Configuration
public class GenreDocumentStepConfig {

    private JobRepository jobRepository;

    private PlatformTransactionManager platformTransactionManager;

    private final EntityManagerFactory entityManagerFactory;

    private final JdbcTemplate jdbcTemplate;

    private final String CREATE_TABLE_SQL = "create table Genres_temp(" +
            "id bigint auto_increment PRIMARY KEY, " +
            "genre_doc_id varchar(255)," +
            "genre_id bigint);";

    private final String DROP_TABLE_SQL = "drop table Genres_temp";


    public GenreDocumentStepConfig(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
                                   EntityManagerFactory entityManagerFactory, JdbcTemplate jdbcTemplate) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.entityManagerFactory = entityManagerFactory;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean
    public MongoItemReader<GenreDoc> genreDocReader(MongoOperations mongoOperations) {
        return new MongoItemReaderBuilder<GenreDoc>()
                .name("genreDocReader")
                .template(mongoOperations)
                .targetType(GenreDoc.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public JpaItemWriter<Genre> genreWriter() {
        return new JpaItemWriterBuilder<Genre>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public ItemProcessor<GenreDoc, Genre> genreProcessor(GenreService genreService) {
        return genreService::transform;
    }

    @Bean
    public Step transformGenreDocStep(ItemReader<GenreDoc> reader, ItemWriter<Genre> writer,
                                      ItemProcessor<GenreDoc, Genre> processor) {
        var genreTempId = 1;
        return new StepBuilder("transformGenresStep", jobRepository)
                .<GenreDoc, Genre>chunk(5, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new ItemReadListener<GenreDoc>() {
                    @Override
                    public void afterRead(GenreDoc item) {
                        insertGenresDoc(item);
                    }
                })
                .listener(new ItemWriteListener<Genre>() {

                    @Override
                    public void afterWrite(Chunk<? extends Genre> items) {
                        insertGenres(items, genreTempId);
                    }
                })
                .build();
    }

    @Bean
    public Step createTableGenresTemp() {
        return new StepBuilder("createTableGenresTemp", jobRepository)
                .tasklet(createTableGenresTempTasklet(), platformTransactionManager)
                .build();
    }

    @Bean
    public Tasklet createTableGenresTempTasklet() {
        return (contribution, chunkContext) -> {
            jdbcTemplate.execute(CREATE_TABLE_SQL);
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Step dropTableGenresTemp() {
        return new StepBuilder("dropTableGenresTemp", jobRepository)
                .tasklet(dropTableGenresTempTasklet(), platformTransactionManager)
                .build();
    }

    @Bean
    public Tasklet dropTableGenresTempTasklet() {
        return (contribution, chunkContext) -> {
            jdbcTemplate.execute(DROP_TABLE_SQL);
            return RepeatStatus.FINISHED;
        };
    }

    private void insertGenres(Chunk<? extends Genre> items, int genreTempId) {
        for (Genre genre: items) {
            jdbcTemplate.execute("update Genres_temp set genre_id = %d where id = %d"
                    .formatted(genre.getGenreId(), genreTempId));
            ++genreTempId;
        }
    }

    private void insertGenresDoc(GenreDoc item) {
        jdbcTemplate.execute("insert into Genres_temp(genre_doc_id) values ('%s')".formatted(item.getGenreId()));
    }
}
