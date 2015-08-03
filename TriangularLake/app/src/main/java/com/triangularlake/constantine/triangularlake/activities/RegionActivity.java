package com.triangularlake.constantine.triangularlake.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.j256.ormlite.android.loadercallback.OrmCursorLoaderCallback;
import com.j256.ormlite.stmt.PreparedQuery;
import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.adapters.RegionsListAdapter;
import com.triangularlake.constantine.triangularlake.data.common.CommonDao;
import com.triangularlake.constantine.triangularlake.data.dto.ICommonDtoConstants;
import com.triangularlake.constantine.triangularlake.data.dto.Region;
import com.triangularlake.constantine.triangularlake.data.helpers.OrmHelper;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class RegionActivity extends Activity {
    private static final String TAG = RegionActivity.class.getSimpleName();

    private ListView regionsLayoutListView;
    private RegionsListAdapter regionsListAdapter;
    private List<Region> regions;

    private CommonDao commonDao;
    private OrmHelper ormHelper;
    private OrmCursorLoaderCallback<Region, Long> regionLoaderCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.region_layout);

        // библиотека для работы с БД в браузере
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());

        initXmlFields();
        initListeners();
        initListAdapter();
        updateEntities();
        initOrmCursorLoader();
    }

    private void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");
        regionsLayoutListView = (ListView) findViewById(R.id.regions_layout_list_view);
        Log.d(TAG, "initXmlFields() done");
    }

    private void initListeners() {
        Log.d(TAG, "initListeners() start");
        regionsLayoutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "CLICK", Toast.LENGTH_SHORT).show();
            }
        });
        Log.d(TAG, "initListeners() done");
    }

    private void initListAdapter() {
        Log.d(TAG, "initListAdapter() start");
        regionsListAdapter = new RegionsListAdapter(getApplicationContext());
        Log.d(TAG, "initListAdapter() done");
    }

    private void updateEntities() {
        Log.d(TAG, "updateEntities() start");
        List<Region> result = null;
        try {
            commonDao = getOrmHelper().getDaoByClass(Region.class);
            result = commonDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "updateEntities() done");
        regions = result != null ? result : Collections.<Region>emptyList();
        Log.d(TAG, "updateEntities() done");
    }

    public OrmHelper getOrmHelper() {
        Log.d(TAG, "getOrmHelper() start");
        if (ormHelper == null) {
            ormHelper = new OrmHelper(getApplicationContext(), ICommonDtoConstants.TRIANGULAR_LAKE_DB,
                    ICommonDtoConstants.TRIANGULAR_LAKE_DB_VERSION);
//            ormHelper.createAll();
        }
        Log.d(TAG, "getOrmHelper() done");
        return ormHelper;
    }

    private void initOrmCursorLoader() {
        Log.d(TAG, "initOrmCursorLoader() start");
        regionsLayoutListView.setAdapter(regionsListAdapter);
        try {
            PreparedQuery query = commonDao.queryBuilder().prepare();
            regionLoaderCallback = new OrmCursorLoaderCallback<Region, Long>(getApplicationContext(),
                    commonDao, query, regionsListAdapter);
            getLoaderManager().initLoader(ICommonDtoConstants.REGION_LOADER_NUMBER, null, regionLoaderCallback);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        Log.d(TAG, "initOrmCursorLoader() done");
    }
}
