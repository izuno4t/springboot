package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Component
public class RootService {

    private static final Logger logger = LoggerFactory.getLogger(RootService.class);

    @Cacheable("json")
    public Optional<String> get(LocalDateTime localDateTime) {
        logger.info("localDateTime :{}", localDateTime);
        var jsonText = getJson();
        logger.info("response from resource file :{}", jsonText);
        return jsonText;
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
