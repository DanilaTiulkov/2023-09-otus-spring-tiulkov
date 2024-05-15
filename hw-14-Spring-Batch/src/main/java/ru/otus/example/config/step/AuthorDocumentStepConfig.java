package ru.otus.example.config.step;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.example.config.writer.AuthorWriter;
import ru.otus.example.model.dto.AuthorDto;
import ru.otus.example.model.mongo.AuthorDoc;
import ru.otus.example.service.AuthorService;

import java.util.HashMap;



@Configuration
public class AuthorDocumentStepConfig {


    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    private final AuthorService authorService;


    @Autowired
    public AuthorDocumentStepConfig(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
                                    NamedParameterJdbcOperations namedParameterJdbcOperations,
                                    AuthorService authorService) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.authorService = authorService;
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
    public ItemWriter<AuthorDto> authorWriter() {
        return new AuthorWriter(namedParameterJdbcOperations);
    }

    @Bean
    public ItemProcessor<AuthorDoc, AuthorDto> processor() {
        return authorService::transform;
    }

    @Bean
    public Step transformAuthorDocStep(ItemReader<AuthorDoc> reader, ItemWriter<AuthorDto> authorWriter,
                                       ItemProcessor<AuthorDoc, AuthorDto> processor) {
        return new StepBuilder("transformAuthorsStep", jobRepository)
                .<AuthorDoc, AuthorDto>chunk(5, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(authorWriter)
                .listener(new ItemWriteListener<AuthorDto>() {
                    @Override
                    public void afterWrite(Chunk<? extends AuthorDto> items) {
                        items.forEach(authorDto -> authorService.findAllAuthorsIds()
                                .put(authorDto.getAuthorDocId(), authorDto.getAuthorId()));
                    }
                })
                .build();
    }
}
