package ru.otus.example.config.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoPagingItemReader;
import org.springframework.batch.item.data.builder.MongoPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.example.cache.GenreCache;
import ru.otus.example.config.writer.GenreWriter;
import ru.otus.example.model.dto.GenreDto;
import ru.otus.example.model.mongo.GenreDoc;
import ru.otus.example.service.GenreService;

import java.util.HashMap;

@Configuration
public class GenreDocumentStepConfig {

    private JobRepository jobRepository;

    private PlatformTransactionManager platformTransactionManager;

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    private final GenreService genreService;


    public GenreDocumentStepConfig(NamedParameterJdbcOperations namedParameterJdbcOperations, GenreService genreService,
                                   PlatformTransactionManager platformTransactionManager, JobRepository jobRepository) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.genreService = genreService;
        this.platformTransactionManager = platformTransactionManager;
        this.jobRepository = jobRepository;
    }

    @Bean
    public MongoPagingItemReader<GenreDoc> genreDocReader(MongoOperations mongoOperations) {
        return new MongoPagingItemReaderBuilder<GenreDoc>()
                .name("genreDocReader")
                .template(mongoOperations)
                .targetType(GenreDoc.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public ItemProcessor<GenreDoc, GenreDto> genreProcessor(GenreService genreService) {
        return genreService::transform;
    }

    @Bean
    public ItemWriter<GenreDto> genreWriter(GenreCache genreCache) {
        return new GenreWriter(namedParameterJdbcOperations, genreCache);
    }

    @Bean
    public Step transformGenreDocStep(ItemReader<GenreDoc> reader, ItemWriter<GenreDto> writer,
                                      ItemProcessor<GenreDoc, GenreDto> processor) {
        return new StepBuilder("transformGenresStep", jobRepository)
                .<GenreDoc, GenreDto>chunk(5, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
