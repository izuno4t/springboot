package com.example.demo.dao;

import com.example.demo.entity.Entry;
import org.seasar.doma.BatchInsert;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;
import java.util.Optional;

@ConfigAutowireable
@Dao
public interface EntryDao {

    @Select
    Optional<Entry> selectById(Integer id);

    @Select
    List<Entry> selectAll();

    @Insert
    int insert(Entry entity);

    @BatchInsert
    int[] insert(List<Entry> entities);
}
