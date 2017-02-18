package com.valevich.clean.repository.specifications;

import com.squareup.sqldelight.RowMapper;
import com.squareup.sqldelight.SqlDelightStatement;

public interface SqlDelightSpecification<T> {
    SqlDelightStatement getStatement();
    RowMapper<T> getMapper();
}
