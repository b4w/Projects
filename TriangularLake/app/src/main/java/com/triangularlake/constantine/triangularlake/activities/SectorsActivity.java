package com.triangularlake.constantine.triangularlake.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.j256.ormlite.android.loadercallback.OrmCursorLoaderCallback;
import com.j256.ormlite.stmt.PreparedQuery;
import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.adapters.SectorsListAdapter;
import com.triangularlake.constantine.triangularlake.data.common.CommonDao;
import com.triangularlake.constantine.triangularlake.data.dto.Boulder;
import com.triangularlake.constantine.triangularlake.data.dto.ICommonDtoConstants;
import com.triangularlake.constantine.triangularlake.data.dto.Sector;
import com.triangularlake.constantine.triangularlake.data.helpers.OrmConnect;
import com.triangularlake.constantine.triangularlake.utils.IStringConstants;

import java.sql.SQLException;

public class SectorsActivity extends AppCompatActivity {

    private static final String TAG = SectorsActivity.class.getSimpleName();

    private ListView sectorLayoutListView;
    private SectorsListAdapter sectorListAdapter;
    private Toolbar toolbar;

    private long regionId;
    private String sectorName;

    private CommonDao commonDao;
    private OrmCursorLoaderCallback<Sector, Long> sectorLoaderCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sectors_layout);

        initInputValues();
        initXmlFields();
        initListeners();
        initToolbar();
        initListAdapter();
        initOrmCursorLoader();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_out, R.anim.left_in);
    }

    private void initToolbar() {
        Log.d(TAG, "initToolbar() start");
        toolbar = (Toolbar) findViewById(R.id.sectors_layout_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(sectorName);
//            toolbar.setTitleTextColor(R.color.text_main);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        Log.d(TAG, "initToolbar() done");
    }

    private void initInputValues() {
        Log.d(TAG, "initInputValues() start");
        regionId = getIntent().getExtras().getLong(IStringConstants.REGION_ID);
        sectorName = getIntent().getExtras().getString(IStringConstants.SECTOR_NAME);
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
                // TODO: заменить на singleton (pogo) для того что бы не доставать каждый раз данные из БД
                Sector sector = sectorListAdapter.getTypedItem(position);
                Intent intent = new Intent(getApplicationContext(), SectorActivity.class);
                intent.putExtra(ICommonDtoConstants.SECTOR_ID, sector.getId());
                intent.putExtra(ICommonDtoConstants.BOULDER_NUMBERS, getCollectedBoulderNumbers(sector));
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
        Log.d(TAG, "initListeners() done");
    }

    private long[] getCollectedBoulderNumbers(Sector sector) {
        long[] result = new long[sector.getBoulders().size()];
        for (Boulder boulder : sector.getBoulders()) {
            for (int i = 0; i < result.length; i++) {
                result[i] = boulder.getId();
            }
        }
        return result;
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
            commonDao = OrmConnect.INSTANCE.getDBConnect(getApplicationContext()).getDaoByClass(Sector.class);
            if (commonDao != null) {
                PreparedQuery query = commonDao.queryBuilder().where().eq(IStringConstants.REGION_ID, regionId).prepare();
                sectorLoaderCallback = new OrmCursorLoaderCallback<Sector, Long>(getApplicationContext(),
                        commonDao, query, sectorListAdapter);
                getLoaderManager().initLoader(ICommonDtoConstants.SECTOR_LOADER_NUMBER, null, sectorLoaderCallback);
            }
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        Log.d(TAG, "initOrmCursorLoader() done");
    }
}
