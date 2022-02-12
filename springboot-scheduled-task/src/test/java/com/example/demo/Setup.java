package com.example.demo;

import com.example.demo.dao.EntryDao;
import com.example.demo.entity.Entry;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
class Setup {

    @Autowired
    EntryDao dao;


    @Test
    void setup() {
        var list = IntStream.rangeClosed(1, 100).mapToObj(it -> {
            var entity = new Entry();
            entity.setItem(RandomStringUtils.random(20));
            entity.setName(RandomStringUtils.random(10));
            entity.setExecutedAt(null);
            entity.setExecutedBy(null);
            return entity;
        }).collect(Collectors.toList());
        dao.insert(list);

    }
}
