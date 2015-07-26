package com.triangularlake.constantine.triangularlake.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.triangularlake.constantine.triangularlake.R;

public class RegionActivity extends Activity {
    private static final String TAG = RegionActivity.class.getSimpleName();

    private RelativeLayout region_layout_lietlahti_rl;
    private RelativeLayout region_layout_triangular_rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.region_layout);

        initXmlFields();

        region_layout_lietlahti_rl = (RelativeLayout) findViewById(R.id.region_layout_lietlahti_rl);
        region_layout_triangular_rl = (RelativeLayout) findViewById(R.id.region_layout_triangular_rl);

        region_layout_lietlahti_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LietlahtiActivity.class);
                startActivity(intent);
            }
        });

        region_layout_triangular_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), "Open TRIANGULAR", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");

        Log.d(TAG, "initXmlFields() done");
    }


}
