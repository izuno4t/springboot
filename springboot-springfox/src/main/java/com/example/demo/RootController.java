package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@RestController
@RequestMapping("/")
public class RootController {

    private static final Logger logger = LoggerFactory.getLogger(RootController.class);


    @GetMapping(value = "/**", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> index(HttpServletRequest request) throws IOException {
        var context = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        var jsonText = getJson(context);
        if (Objects.nonNull(jsonText) && !Objects.equals("", jsonText)) {
            logger.info("response from cache");
            return ResponseEntity.ok(jsonText);
        }

        try (var is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("example.json")) {
            jsonText = StreamUtils.copyToString(is, StandardCharsets.UTF_8);
            logger.info("response from resource file");
            setJson(context, jsonText);
            return ResponseEntity.ok(jsonText);
        }
    }


    @PostMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> json(HttpServletRequest request) throws IOException {
        var context = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        try (var is = new ServletServerHttpRequest(request).getBody()) {
            var jsonText = StreamUtils.copyToString(is, StandardCharsets.UTF_8);
            setJson(context, jsonText);
            return ResponseEntity.ok(jsonText);
        }
    }


    @Nullable
    private String getJson(WebApplicationContext context) {
        if (Objects.isNull(context)) {
            return null;
        }
        var servletContext = context.getServletContext();
        if (Objects.isNull(servletContext)) {
            return null;
        }
        return (String) servletContext.getAttribute("json");
    }

    private void setJson(WebApplicationContext context, String json) {
        if (Objects.isNull(context)) {
            return;
        }
        var servletContext = context.getServletContext();
        if (Objects.isNull(servletContext)) {
            return;
        }
        servletContext.setAttribute("json", json);
    }


}
