package com.triangularlake.constantine.triangularlake.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.triangularlake.constantine.triangularlake.R;

public class RegionActivity extends Activity {


    private RelativeLayout region_layout_lietlahti_rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.region_layout);


        region_layout_lietlahti_rl = (RelativeLayout) findViewById(R.id.region_layout_lietlahti_rl);
        region_layout_lietlahti_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplication(), "Open LIETLAHTI", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
