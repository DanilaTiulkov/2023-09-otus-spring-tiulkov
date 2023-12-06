package otus.ru.example.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import otus.ru.example.service.TestRunnerService;

@ShellComponent
public class ApplicationTestComands {

    private final TestRunnerService testRunnerService;

    @Autowired
    public ApplicationTestComands(TestRunnerService testRunnerService) {
        this.testRunnerService = testRunnerService;
    }

    @ShellMethod(value = "Start test", key = {"start"})
    public void startTest () {
        testRunnerService.run();
    }
}
