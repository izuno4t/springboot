package com.example.demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/")
public class RootController {

    private final RootService service;

    public RootController(RootService service) {
        this.service = service;
    }

    @GetMapping(value = "/**", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> index() {
        var dt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        var jsonText = service.get(dt);
        return Mono.just(jsonText.orElse(""));
    }

}
