package ru.otus.example.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.*;
import org.springframework.messaging.MessageChannel;
import ru.otus.example.model.Caterpillar;
import ru.otus.example.model.Cocoon;
import ru.otus.example.model.enums.CaterpillarStatus;
import ru.otus.example.model.enums.CocoonStatus;
import ru.otus.example.service.ButterflyService;
import ru.otus.example.service.CaterpillarService;
import ru.otus.example.service.CocoonService;

import java.util.List;


@Configuration
public class IntegrationConfig {

    @Bean
    public MessageChannel caterpillarChannel() {
        return MessageChannels.queue(10).getObject();
    }

    @Bean
    public MessageChannel butterflyChannel() {
        return MessageChannels.publishSubscribe().getObject();
    }

    @Bean
    public IntegrationFlow flow(CocoonService cocoonService, CaterpillarService caterpillarService,
                                ButterflyService butterflyService) {
        return IntegrationFlow.from(caterpillarChannel())
                .handle(caterpillarService, "filter")
                .<List<Caterpillar>>filter(source ->
                        source.stream().allMatch(caterpillar ->
                                caterpillar.getStatus().equals(CaterpillarStatus.ALIVE)))
                .split()
                .handle(caterpillarService, "checkStatus")
                .<Caterpillar, Cocoon>transform(caterpillar -> new Cocoon(CocoonStatus.FILLED, caterpillar.getColor()))
                .handle(cocoonService, "situation")
                .aggregate()
                .handle(cocoonService, "filter")
                .<List<Cocoon>>filter(source ->
                        source.stream().allMatch(cocoon ->
                                cocoon.getStatus().equals(CocoonStatus.FILLED)))
                .handle(butterflyService, "transform")
                .channel(butterflyChannel())
                .get();
    }
}
