package com.example.demo.dao;

import com.example.demo.entity.Entry;
import org.seasar.doma.BatchInsert;
import org.seasar.doma.BatchUpdate;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import java.util.List;
import java.util.Optional;

@ConfigAutowireable
@Dao
public interface EntryDao {

    @Select
    Optional<Entry> selectById(Long id);

    @Select
    List<Entry> selectAll();

    @Insert
    int insert(Entry entity);

    @BatchInsert
    int[] insert(List<Entry> entities);

    @Update(include = {"status", "executedBy", "executedAt"})
    int updateStatus(Entry entity);

    @BatchUpdate(include = {"status", "executedBy", "executedAt"})
    int[] updateStatus(List<Entry> list);

    @Select
    List<Entry> selectWaiting(SelectOptions options);

}
