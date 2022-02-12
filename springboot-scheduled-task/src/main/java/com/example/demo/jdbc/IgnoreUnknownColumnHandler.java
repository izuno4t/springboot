package com.example.demo.jdbc;

import org.seasar.doma.jdbc.UnknownColumnHandler;
import org.seasar.doma.jdbc.entity.EntityType;
import org.seasar.doma.jdbc.query.Query;

public class IgnoreUnknownColumnHandler implements UnknownColumnHandler {

    @Override
    public void handle(Query query, EntityType<?> entityType, String unknownColumnName) {
        // do nothing;
    }
}
