package com.triangularlake.constantine.triangularlake.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.facebook.stetho.Stetho;
import com.j256.ormlite.android.loadercallback.OrmCursorLoaderCallback;
import com.j256.ormlite.stmt.PreparedQuery;
import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.adapters.RegionsListAdapter;
import com.triangularlake.constantine.triangularlake.data.common.CommonDao;
import com.triangularlake.constantine.triangularlake.data.dto.ICommonDtoConstants;
import com.triangularlake.constantine.triangularlake.data.dto.Region;
import com.triangularlake.constantine.triangularlake.data.helpers.OrmConnect;
import com.triangularlake.constantine.triangularlake.utils.IStringConstants;

import java.sql.SQLException;

public class RegionsActivity extends Activity {
    private static final String TAG = RegionsActivity.class.getSimpleName();

    private ListView regionsLayoutListView;
    private RegionsListAdapter regionsListAdapter;

    private CommonDao commonDao;
    private OrmCursorLoaderCallback<Region, Long> regionLoaderCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regions_layout);

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
                Region region = regionsListAdapter.getTypedItem(position);
                Intent intent = null;

                switch (region.getRegionName()) {
                    case IStringConstants.REGION_LIETLAHTI:
                        intent = new Intent(getApplicationContext(), SectorsActivity.class);
                        intent.putExtra(IStringConstants.SECTOR_NAME, getString(R.string.lietlahti));
                        break;
                    case IStringConstants.REGION_TRIANGULAR_LAKE:
                        intent = new Intent(getApplicationContext(), SectorsActivity.class);
                        intent.putExtra(IStringConstants.SECTOR_NAME, getString(R.string.triangular_lake));
                        break;
                }

                intent.putExtra(IStringConstants.REGION_ID, region.getId());
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
        Log.d(TAG, "initListeners() done");
    }

    private void initListAdapter() {
        Log.d(TAG, "initListAdapter() start");
        regionsListAdapter = new RegionsListAdapter(getApplicationContext());
        Log.d(TAG, "initListAdapter() done");
    }

    private void initOrmCursorLoader() {
        Log.d(TAG, "initOrmCursorLoader() start");
        regionsLayoutListView.setAdapter(regionsListAdapter);
        try {
            commonDao = OrmConnect.INSTANCE.getDBConnect(getApplicationContext()).getDaoByClass(Region.class);
            if (commonDao != null) {
                PreparedQuery query = commonDao.queryBuilder().prepare();
                regionLoaderCallback = new OrmCursorLoaderCallback<Region, Long>(getApplicationContext(),
                        commonDao, query, regionsListAdapter);
                getLoaderManager().initLoader(ICommonDtoConstants.REGION_LOADER_NUMBER, null, regionLoaderCallback);
            } else {
                Log.e(TAG, "Error when load Regions");
            }
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        Log.d(TAG, "initOrmCursorLoader() done");
    }
}
