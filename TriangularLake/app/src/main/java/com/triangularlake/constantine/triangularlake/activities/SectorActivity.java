package com.triangularlake.constantine.triangularlake.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.data.common.CommonDao;
import com.triangularlake.constantine.triangularlake.data.dto.ICommonDtoConstants;
import com.triangularlake.constantine.triangularlake.data.dto.Sector;
import com.triangularlake.constantine.triangularlake.data.helpers.OrmConnect;
import com.triangularlake.constantine.triangularlake.fragments.SectorBoulderFragment;

import java.sql.SQLException;
import java.util.List;

public class SectorActivity extends Activity {
    private static final String TAG = SectorActivity.class.getSimpleName();

    private ImageView sectorPhoto;
    private TextView sectorName;
    private TextView description;

    private long sectorId;
    private long[] boulderNumbers;

    private SectorBoulderFragment sectorBoulderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sector_layout);

        initInputValues();
        initSectorStoneFragments();
        initXmlFields();
        loadData();
        initListeners();
//        initListAdapter();
//        initOrmCursorLoader();
    }

    private void initInputValues() {
        Log.d(TAG, "initInputValues() start");
        sectorId = getIntent().getExtras().getLong(ICommonDtoConstants.SECTOR_ID);
        boulderNumbers = getIntent().getExtras().getLongArray(ICommonDtoConstants.BOULDER_NUMBERS);
        Log.d(TAG, "initInputValues() done");
    }

    private void loadData() {
        Log.d(TAG, "loadData() start");
        // TODO: обращение к БД в отдельном потоке?
        try {
            CommonDao commonDao = OrmConnect.INSTANCE.getDBConnect(getApplicationContext()).getDaoByClass(Sector.class);
            if (commonDao != null) {
                List<Sector> sectors = commonDao.queryForEq(ICommonDtoConstants.ID, sectorId);
                Sector sector = sectors.get(0);
                sectorPhoto.setImageBitmap(BitmapFactory.decodeByteArray(sector.getSectorPhoto(), 0, sector.getSectorPhoto().length));
                // TODO: добавить поддержку локали
                sectorName.setText("<<" + sector.getSectorName() + ">>");
                description.setText(sector.getSectorDesc());
            }
        } catch (SQLException e) {

        }
        Log.d(TAG, "loadData() done");
    }

    private void initSectorStoneFragments() {
        Log.d(TAG, "initSectorStoneFragments() start");
        Bundle bundle = new Bundle();
//        TODO: подумать как упростить
        for (long boulderId : boulderNumbers) {
            FragmentManager fragmentManager = getFragmentManager();
            sectorBoulderFragment = SectorBoulderFragment.newInstance();
            bundle.putLong(ICommonDtoConstants.BOULDER_ID, boulderId);
            sectorBoulderFragment.setArguments(bundle);

            fragmentManager.beginTransaction()
                    .add(R.id.sector_layout_sector_container, sectorBoulderFragment)
                    .commit();
        }

//        .addToBackStack(ExercisesFragment.class.getSimpleName())

        Log.d(TAG, "initSectorStoneFragments() done");
    }

    private void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");
        sectorPhoto = (ImageView) findViewById(R.id.sector_layout_sector_photo);
        sectorName = (TextView) findViewById(R.id.sector_layout_sector_name);
        description = (TextView) findViewById(R.id.sector_layout_description);
        Log.d(TAG, "initXmlFields() done");
    }

    private void initListeners() {
        Log.d(TAG, "initListeners() start");
        // add look at map click();
        Log.d(TAG, "initListeners() done");
    }
}
