package com.example.demo;

import com.example.demo.dao.EntryDao;
import com.example.demo.entity.Entry;
import com.example.demo.enums.EntryStatus;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
class Setup {

    private static final Logger logger = LoggerFactory.getLogger(Setup.class);

    @Autowired
    EntryDao dao;


    @Test
    void setup() {
        var list = IntStream.rangeClosed(1, 100000).mapToObj(it -> {
            var entity = new Entry();
            entity.setItem(RandomStringUtils.randomAlphanumeric(14));
            entity.setName(RandomStringUtils.randomAlphanumeric(12));
            entity.setStatus(EntryStatus.QUEUED);
            entity.setExecutedAt(null);
            entity.setExecutedBy(null);
            return entity;
        }).collect(Collectors.toList());
        dao.insert(list);

    }
}
