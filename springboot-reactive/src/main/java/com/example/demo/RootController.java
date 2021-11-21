package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/")
public class RootController {

    private static final Logger logger = LoggerFactory.getLogger(RootController.class);


    @GetMapping(value = "/**", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> index() throws IOException {
        try (var is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("example.json")) {
            var jsonText = StreamUtils.copyToString(is, StandardCharsets.UTF_8);
            logger.info("response from resource file");
            return Mono.just(jsonText);
        }
    }


}
