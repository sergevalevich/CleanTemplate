package com.valevich.umora.domain.repository.specification;


import android.os.Parcelable;

import com.squareup.sqldelight.RowMapper;
import com.squareup.sqldelight.SqlDelightStatement;

public interface SqlDelightSpecification<T> extends Parcelable{
    SqlDelightStatement getStatement();
    RowMapper<T> getMapper();
}
