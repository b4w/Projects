package com.triangularlake.constantine.triangularlake.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.j256.ormlite.android.loadercallback.OrmCursorLoaderCallback;
import com.j256.ormlite.stmt.PreparedQuery;
import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.adapters.SectorsListAdapter;
import com.triangularlake.constantine.triangularlake.data.common.CommonDao;
import com.triangularlake.constantine.triangularlake.data.dto.ICommonDtoConstants;
import com.triangularlake.constantine.triangularlake.data.dto.Sector;
import com.triangularlake.constantine.triangularlake.data.helpers.OrmHelper;

import java.sql.SQLException;

public class SectorsActivity extends Activity {

    private static final String TAG = SectorsActivity.class.getSimpleName();

    private ListView sectorLayoutListView;
    private SectorsListAdapter sectorListAdapter;

    private long regionId;

    private CommonDao commonDao;
    private OrmHelper ormHelper;
    private OrmCursorLoaderCallback<Sector, Long> sectorLoaderCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sectors_layout);

        initInputValues();
        initXmlFields();
        initListeners();
        initListAdapter();
        initOrmCursorLoader();
    }

    private void initInputValues() {
        Log.d(TAG, "initInputValues() start");
        regionId = getIntent().getExtras().getLong(RegionsActivity.REGION_ID);
        Log.d(TAG, "initInputValues() done");
    }

    private void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");
        sectorLayoutListView = (ListView) findViewById(R.id.sectors_layout_list_view);
        Log.d(TAG, "initXmlFields() done");
    }

    private void initListeners() {
        Log.d(TAG, "initListeners() start");
        sectorLayoutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Sector sector = sectorListAdapter.getTypedItem(position);
                Intent intent = new Intent(getApplicationContext(), SectorActivity.class);
                startActivity(intent);
            }
        });
        Log.d(TAG, "initListeners() done");
    }

    private void initListAdapter() {
        Log.d(TAG, "initListAdapter() start");
        sectorListAdapter = new SectorsListAdapter(getApplicationContext());
        Log.d(TAG, "initListAdapter() done");
    }

    private void initOrmCursorLoader() {
        Log.d(TAG, "initOrmCursorLoader() start");
        sectorLayoutListView.setAdapter(sectorListAdapter);
        try {
            commonDao = getOrmHelper().getDaoByClass(Sector.class);
            if (commonDao != null) {
                PreparedQuery query = commonDao.queryBuilder().where().eq(RegionsActivity.REGION_ID, regionId).prepare();
                sectorLoaderCallback = new OrmCursorLoaderCallback<Sector, Long>(getApplicationContext(),
                        commonDao, query, sectorListAdapter);
                getLoaderManager().initLoader(ICommonDtoConstants.SECTOR_LOADER_NUMBER, null, sectorLoaderCallback);
            }
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        Log.d(TAG, "initOrmCursorLoader() done");
    }

    public OrmHelper getOrmHelper() {
        Log.d(TAG, "getOrmHelper() start");
        if (ormHelper == null) {
            ormHelper = new OrmHelper(getApplicationContext(), ICommonDtoConstants.TRIANGULAR_LAKE_DB,
                    ICommonDtoConstants.TRIANGULAR_LAKE_DB_VERSION);
        }
        Log.d(TAG, "getOrmHelper() done");
        return ormHelper;
    }

}
