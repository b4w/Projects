package com.triangularlake.constantine.triangularlake.data.common;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class CommonDao<T, ID> extends BaseDaoImpl<T, ID> {

    public CommonDao(ConnectionSource connectionSource, Class<T> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

}
