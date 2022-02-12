package com.example.demo.task;

import com.example.demo.dao.DeliveryDao;
import com.example.demo.dao.EntryDao;
import com.example.demo.entity.Entry;
import com.example.demo.enums.EntryStatus;
import org.apache.commons.collections4.ListUtils;
import org.seasar.doma.jdbc.SelectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final int ROWS_PER_PROCESS_UNIT = 100;

    private final EntryDao entryDao;

    private final DeliveryDao deliveryDao;

    private final TaskExecutor taskExecutor;


    public ScheduledTasks(EntryDao entryDao, DeliveryDao deliveryDao, TaskExecutor taskExecutor) {
        this.entryDao = entryDao;
        this.deliveryDao = deliveryDao;
        this.taskExecutor = taskExecutor;
    }


    @Scheduled(fixedRate = 1000)
    public void task1() {
        createExecutor("foo");
    }

    @Scheduled(fixedRate = 1000)
    public void task2() {
        createExecutor("bar");
    }

    @Scheduled(fixedRate = 1000)
    public void task3() {
        createExecutor("baz");
    }

    @Scheduled(fixedRate = 1000)
    public void task4() {
        createExecutor("qux");
    }

    @Scheduled(fixedRate = 1000)
    public void task5() {
        createExecutor("quux");
    }

    @Scheduled(fixedRate = 1000)
    public void task6() {
        createExecutor("corge");
    }

    @Scheduled(fixedRate = 1000)
    public void task7() {
        createExecutor("grault");
    }

    public void createExecutor(String name) {
        var options = SelectOptions.get();
        options.limit(ROWS_PER_PROCESS_UNIT);
        var list = entryDao.selectWaiting(options);
        if (list.isEmpty()) {
            logger.info("対象データがありませんでした。name={}", name);
        }

        var now = LocalDateTime.now();
        list.forEach(it -> {
            it.setExecutedBy(name);
            it.setExecutedAt(now);
            it.setStatus(EntryStatus.EXECUTING);
        });
        updateStatus(list);

        var ids = list.stream().map(Entry::getId).toList();
        ListUtils.partition(ids, 10).forEach(it -> {
            logger.info("TaskExecutorを実行しました。");
            taskExecutor.execute(new FooTask(name, entryDao, deliveryDao, it));
        });
    }

    @Transactional
    public void updateStatus(List<Entry> entities) {
        entryDao.updateStatus(entities);
    }


}

