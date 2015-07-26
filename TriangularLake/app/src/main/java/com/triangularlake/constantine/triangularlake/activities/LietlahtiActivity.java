package com.triangularlake.constantine.triangularlake.activities;

import android.app.Activity;
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
import com.triangularlake.constantine.triangularlake.data.helpers.ICommonOrmHelper;
import com.triangularlake.constantine.triangularlake.data.helpers.OrmHelper;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class LietlahtiActivity extends Activity {

    private static final String TAG = LietlahtiActivity.class.getSimpleName();

    private ListView lietlahtiLayoutListView;
    private SectorsListAdapter lietlahtiListAdapter;
    private List<Sector> sectors;

    private CommonDao commonDao;
    private OrmHelper ormHelper;
    private OrmCursorLoaderCallback<Sector, Integer> sectorLoaderCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lietlahti_layout);

        initXmlFields();
        initListeners();
        initListAdapter();
        updateEntities();
        initOrmCursorLoader();
    }

    private void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");
        lietlahtiLayoutListView = (ListView) findViewById(R.id.lietlahti_layout_list_view);
        Log.d(TAG, "initXmlFields() done");
    }

    private void initListeners() {
        Log.d(TAG, "initListeners() start");
        lietlahtiLayoutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "CLICK", Toast.LENGTH_SHORT).show();
            }
        });
        Log.d(TAG, "initListeners() done");
    }

    private void initListAdapter() {
        Log.d(TAG, "initListAdapter() start");
        lietlahtiListAdapter = new SectorsListAdapter(getApplicationContext());
        Log.d(TAG, "initListAdapter() done");
    }

    private void updateEntities() {
        Log.d(TAG, "updateEntities() start");
        List<Sector> result = null;
        try {
            commonDao = getOrmHelper().getDaoByClass(Sector.class);
            result = commonDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "updateEntities() done");
        sectors = result != null ? result : Collections.<Sector>emptyList();
        Log.d(TAG, "updateEntities() done");
    }

    private void initOrmCursorLoader() {
        Log.d(TAG, "initOrmCursorLoader() start");
        lietlahtiLayoutListView.setAdapter(lietlahtiListAdapter);
        try {
            PreparedQuery query = commonDao.queryBuilder().prepare();
            sectorLoaderCallback = new OrmCursorLoaderCallback<Sector, Integer>(getApplicationContext(),
                    commonDao, query, lietlahtiListAdapter);
            getLoaderManager().initLoader(ICommonOrmHelper.LIETLAHTI_DAO_NUMBER, null, sectorLoaderCallback);
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
