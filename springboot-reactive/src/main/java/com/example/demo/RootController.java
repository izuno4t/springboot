package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class RootController {

    private static final Logger logger = LoggerFactory.getLogger(RootController.class);

    private static final Map<String, String> MAP = new HashMap<>();


    @GetMapping(value = "/**", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> index() {
        Optional<String> jsonText;
        if (MAP.containsKey("json")) {
            logger.info("response from resource cache");
            jsonText = Optional.of(MAP.get("json"));
        } else {
            logger.info("response from resource file");
            jsonText = getJson();
            jsonText.ifPresent(it -> MAP.put("json", it));
        }
        return Mono.just(jsonText.orElse(""));
    }

    @PostMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> json(@RequestBody String body) {
        MAP.put("json", body);
        return Mono.just(body);
    }


    private Optional<String> getJson() {
        var url = Thread.currentThread().getContextClassLoader().getResource("example.json");
        if (Objects.isNull(url)) {
            return Optional.empty();
        }
        var sb = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(Path.of(url.toURI()), StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.of(sb.toString());
    }
}
