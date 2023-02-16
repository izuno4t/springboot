package com.example.demo.exec1;

import com.example.demo.FooResolver;
import com.example.demo.domain.FooId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foo")
public class FooController {
    private static Logger log = LoggerFactory.getLogger(FooController.class);

    @GetMapping(path = "/{fooId}")
    @FooResolver(name = "FooControllerPathParameterNameResolver")
    public ResponseEntity<String> path(@PathVariable FooId fooId) {
        log.info("fooId={}", fooId.getValue());
        return ResponseEntity.status(HttpStatus.CREATED).body("HTTP Status will be CREATED (CODE 201)\n");

    }

}
