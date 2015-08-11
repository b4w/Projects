package com.triangularlake.constantine.triangularlake.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.fragments.SectorBoulderFragment;

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
        initListeners();
//        initListAdapter();
//        initOrmCursorLoader();
    }

    private void initInputValues() {
        Log.d(TAG, "initInputValues() start");
        sectorId = getIntent().getExtras().getLong("sectorId");
        boulderNumbers = getIntent().getExtras().getLongArray("boulderNumbers");
        Log.d(TAG, "initInputValues() done");
    }

    private void initSectorStoneFragments() {
        Log.d(TAG, "initSectorStoneFragments() start");
        Bundle bundle = new Bundle();
//        TODO: подумать как упростить
        for (long boulderId : boulderNumbers) {
            FragmentManager fragmentManager = getFragmentManager();
            sectorBoulderFragment = SectorBoulderFragment.newInstance();
            bundle.putLong("boulderId", boulderId);
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
