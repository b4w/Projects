package com.triangularlake.constantine.triangularlake.data.helpers;

import android.content.Context;

import com.triangularlake.constantine.triangularlake.data.dto.ICommonDtoConstants;

public enum OrmConnect {

    INSTANCE;

    private OrmHelper ormHelper;

    public OrmHelper getDBConnect(Context context) {
        if (ormHelper == null) {
            ormHelper = new OrmHelper(context, ICommonDtoConstants.TRIANGULAR_LAKE_DB,
                    ICommonDtoConstants.TRIANGULAR_LAKE_DB_VERSION);
        }
        return ormHelper;
    }
}
