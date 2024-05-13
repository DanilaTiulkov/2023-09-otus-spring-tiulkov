package ru.otus.example.actuator.healtcheck;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class TimeHealthIndicator implements HealthIndicator {


    @Override
    public Health health() {
        var currentDate = new Date();
        var beginTechnicalWork = getBeginTechnicalWorkDate();
        var endTechnicalWork = getEndTechnicalWorkDate();
        if (currentDate.after(beginTechnicalWork) && currentDate.before(endTechnicalWork)) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Technical work is underway")
                    .build();
        } else {
            return Health.up().build();
        }
    }

    private Date getBeginTechnicalWorkDate() {
        var year = 2024;
        var month = Calendar.MAY;
        var date = 13;
        var hour = 22;
        var minute = 50;
        var calendar = Calendar.getInstance();
        calendar.set(year, month, date, hour, minute);
        return calendar.getTime();
    }

    private Date getEndTechnicalWorkDate() {
        var year = 2024;
        var month = Calendar.MAY;
        var date = 13;
        var hour = 23;
        var minute = 50;
        var calendar = Calendar.getInstance();
        calendar.set(year, month, date, hour, minute);
        return calendar.getTime();
    }
}
