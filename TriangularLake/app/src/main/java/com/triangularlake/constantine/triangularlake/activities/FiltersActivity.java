package com.triangularlake.constantine.triangularlake.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.data.dto.ICommonDtoConstants;
import com.triangularlake.constantine.triangularlake.fragments.FavouriteFragment;
import com.triangularlake.constantine.triangularlake.fragments.FiltersFragment;
import com.triangularlake.constantine.triangularlake.fragments.SearchFragment;
import com.triangularlake.constantine.triangularlake.utils.IStringConstants;

import java.util.Locale;

public class FiltersActivity extends AppCompatActivity {
    private static final String TAG = FiltersActivity.class.getSimpleName();
    private static final String RU = "ru";

    private String nameFilterFragment;
    private Toolbar toolbarFilters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filters_layout);

        initToolbarFilters();
        initInputValues();
        initFragment();
    }

    private void initToolbarFilters() {
        Log.d(TAG, "initToolbarFilters() start");
        toolbarFilters = (Toolbar) findViewById(R.id.filters_layout_toolbar_filters);
        if (toolbarFilters != null) {
            setSupportActionBar(toolbarFilters);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("TEST");
            toolbarFilters.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: заменить на переход к главному экрану
                    onBackPressed();
                }
            });
        }
        Log.d(TAG, "initToolbarFilters() done");
    }

    private void initInputValues() {
        Log.d(TAG, "initInputValues() start");
        nameFilterFragment = getIntent().getExtras().getString(IStringConstants.NAME_FILTER_FRAGMENT);
        Log.d(TAG, "initInputValues() done");
    }

    private void initFragment() {
        Log.d(TAG, "initFragment() start");
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = null;

        switch (nameFilterFragment) {
            case IStringConstants.FAVORITES_FRAGMENT:
                fragment = FavouriteFragment.newInstance();
                setTitleToolbar(R.string.favorites_upper);
                break;
            case IStringConstants.FILTERS_FRAGMENT:
                fragment = FiltersFragment.newInstance();
                setTitleToolbar(R.string.filter_routes_upper);
                break;
            case IStringConstants.SEARCH_FRAGMENT:
                setTitleToolbar(R.string.search_upper);
                fragment = SearchFragment.newInstance();
                break;
        }

        fragmentManager.beginTransaction()
                .replace(R.id.filters_layout_container, fragment)
                .commit();
        Log.d(TAG, "initFragment() done");
    }

    private void setTitleToolbar(int idString) {
        if (Locale.ENGLISH.getLanguage().equals(Locale.getDefault().getLanguage())) {
            getSupportActionBar().setTitle(getResources().getString(idString));
        } else if (Locale.getDefault().getLanguage().equals(RU)) {
            getSupportActionBar().setTitle(getResources().getString(idString));
        } else {
            getSupportActionBar().setTitle(getResources().getString(idString));
        }
    }
}
