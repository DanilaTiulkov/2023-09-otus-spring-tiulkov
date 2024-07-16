package ru.otus.example.shell;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class BatchCommands {

    private Job insertBookJob;

    private JobLauncher jobLauncher;


    public BatchCommands(Job insertBookJob, JobLauncher jobLauncher) {
        this.insertBookJob = insertBookJob;
        this.jobLauncher = jobLauncher;
    }

    @ShellMethod(value = "book-migration", key = "mig-books")
    public void bookMigration() throws Exception {
        JobExecution execution = jobLauncher.run(insertBookJob, new JobParameters());
    }
}
