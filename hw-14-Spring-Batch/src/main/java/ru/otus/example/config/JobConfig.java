package ru.otus.example.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfig {

    private JobRepository jobRepository;

    public JobConfig(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Bean
    public Job insertBookJob(Step transformBookDocStep, Step transformAuthorDocStep, Step createTableAuthorsTemp,
                             Step transformGenreDocStep, Step createTableGenresTemp, Step dropTableAuthorsTemp,
                             Step dropTableGenresTemp) {
        return new JobBuilder("importBookJobBuilder", jobRepository)
                .start(createTableAuthorsTemp)
                .next(createTableGenresTemp)
                .next(transformAuthorDocStep)
                .next(transformGenreDocStep)
                .next(transformBookDocStep)
//                .next(dropTableAuthorsTemp)
//                .next(dropTableGenresTemp)
                .build();
    }
}
