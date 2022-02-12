package com.example.demo.dao;

import com.example.demo.entity.Delivery;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.boot.ConfigAutowireable;

@ConfigAutowireable
@Dao
public interface DeliveryDao {


    @Insert
    int insert(Delivery entity);
    
}
