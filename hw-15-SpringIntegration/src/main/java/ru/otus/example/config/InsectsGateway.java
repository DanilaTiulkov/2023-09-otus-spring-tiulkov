package ru.otus.example.config;

import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.example.model.Butterfly;
import ru.otus.example.model.Caterpillar;

import java.util.List;

@MessagingGateway
public interface InsectsGateway {

    @org.springframework.integration.annotation.Gateway(
            requestChannel = "caterpillarChannel", replyChannel = "butterflyChannel")
    List<Butterfly> process(List<Caterpillar> caterpillars);
}
