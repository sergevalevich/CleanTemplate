package com.valevich.clean.repository.specifications;

import com.squareup.sqldelight.RowMapper;

public interface SqlDelightSpecification<T> {
    String getQuery();
    String [] getArgs();
    RowMapper<T> getMapper();
}
