package com.triangularlake.constantine.triangularlake.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.triangularlake.constantine.triangularlake.R;

public class SectorActivity extends Activity {
    private static final String TAG = SectorActivity.class.getSimpleName();

    private ImageView sectorPhoto;
    private TextView sectorName;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sector_layout);

//        initInputValues();
        initXmlFields();
        initListeners();
//        initListAdapter();
//        initOrmCursorLoader();
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
