package ru.otus.example.config.step;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.example.model.dto.BookDto;
import ru.otus.example.model.mongo.BookDoc;
import ru.otus.example.service.BookService;

import java.util.HashMap;

@Configuration
public class BookDocumentStepConfig {

    private JobRepository jobRepository;

    private PlatformTransactionManager platformTransactionManager;

    private final EntityManagerFactory emf;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public BookDocumentStepConfig(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
                                  EntityManagerFactory emf, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.emf = emf;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Bean
    public MongoItemReader<BookDoc> bookDocReader(MongoOperations mongoOperations) {
        return new MongoItemReaderBuilder<BookDoc>()
                .name("bookDocReader")
                .template(mongoOperations)
                .targetType(BookDoc.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public ItemProcessor<BookDoc, BookDto> bookProcessor(BookService bookService) {
        return bookService::transform;
    }

    @Bean
    public JdbcBatchItemWriter<BookDto> bookWriter() {
        return new JdbcBatchItemWriterBuilder<BookDto>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .namedParametersJdbcTemplate(namedParameterJdbcTemplate)
                .sql("insert into Books(title, author_id, genre_id) values (:title, :authorId, :genreId)")
                .build();
    }

    @Bean
    public Step transformBookDocStep(ItemReader<BookDoc> reader, ItemWriter<BookDto> writer,
                                     ItemProcessor<BookDoc, BookDto> bookProcessor) {
        return new StepBuilder("transformBookStep", jobRepository)
                .<BookDoc, BookDto>chunk(5, platformTransactionManager)
                .reader(reader)
                .processor(bookProcessor)
                .writer(writer)
                .build();
    }
}
