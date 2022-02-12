package com.example.demo.task;

import com.example.demo.dao.DeliveryDao;
import com.example.demo.dao.EntryDao;
import com.example.demo.entity.Delivery;
import com.example.demo.enums.EntryStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public class FooTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(FooTask.class);

    private final String name;

    private final EntryDao entryDao;

    private final DeliveryDao deliveryDao;

    private final List<Long> entryIds;

    public FooTask(String name, EntryDao entryDao, DeliveryDao deliveryDao, List<Long> entryIds) {
        this.name = name;
        this.entryDao = entryDao;
        this.deliveryDao = deliveryDao;
        this.entryIds = entryIds;
//        logger.info("{} processes the following {}", name, entryIds);
    }

    @Override
    @Transactional
    public void run() {
        entryIds.forEach(it -> {
            logger.info("{} execute entry {}", name, it);
            var entity = entryDao.selectById(it).orElseThrow();
            if (entity.getStatus() == EntryStatus.EXECUTING) {
                entity.setStatus(EntryStatus.SUCCESSES);
                entryDao.updateStatus(entity);

                var delivery = new Delivery();
                delivery.setId(entity.getId());
                delivery.setItem(entity.getItem());
                delivery.setName(entity.getName());
                delivery.setCreatedBy(name);
                delivery.setCreatedAt(LocalDateTime.now());
                try {
                    deliveryDao.insert(delivery);
                } catch (Exception e) {
                    logger.warn(e.getMessage());
                }
            }
        });
    }
}
