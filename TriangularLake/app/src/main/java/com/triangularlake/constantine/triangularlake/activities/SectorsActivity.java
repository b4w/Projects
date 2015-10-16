package com.triangularlake.constantine.triangularlake.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.adapters.SectorsAdapter;
import com.triangularlake.constantine.triangularlake.data.dto.ICommonDtoConstants;
import com.triangularlake.constantine.triangularlake.pojo.SectorsCache;
import com.triangularlake.constantine.triangularlake.utils.IStringConstants;

public class SectorsActivity extends AppCompatActivity {

    private static final String TAG = SectorsActivity.class.getSimpleName();

    private RecyclerView sectorsLayoutRecyclerView;
    private Toolbar toolbar;

    private long regionId;
    private String sectorName;

    private Button buttonMap;
    private ImageButton buttonMenu;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private SectorsAdapter sectorsAdapter;
    private SectorsAdapter.ISectorsAdapterCallback sectorsAdapterCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sectors_layout);

        initInputValues();
        initToolbar();
        initXmlFields();
        initListeners();
        initListAdapter();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (drawerLayout != null && navigationView != null && drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawer(navigationView);
        } else {
            overridePendingTransition(R.anim.right_out, R.anim.left_in);
        }
    }

    /**
     * Инициализация тулбара.
     */
    private void initToolbar() {
        Log.d(TAG, "initToolbar() start");
        toolbar = (Toolbar) findViewById(R.id.sectors_layout_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(sectorName);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        Log.d(TAG, "initToolbar() done");
    }

    /**
     * Инициализация входных значений.
     */
    private void initInputValues() {
        Log.d(TAG, "initInputValues() start");
        regionId = getIntent().getExtras().getLong(IStringConstants.REGION_ID);
        sectorName = getIntent().getExtras().getString(IStringConstants.SECTOR_NAME);
        Log.d(TAG, "initInputValues() done");
    }

    /**
     * Инициализация разметки xml файла.
     */
    private void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");
        sectorsLayoutRecyclerView = (RecyclerView) findViewById(R.id.sectors_layout_recycler_view);
        buttonMap = (Button) findViewById(R.id.button_map);
        buttonMenu = (ImageButton) findViewById(R.id.button_menu);
        // инициализация бокового меню
        drawerLayout = (DrawerLayout) findViewById(R.id.sectors_layout_drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, null, R.string.navigation_menu_open, R.string.navigation_menu_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.sectors_layout_navigation_view);
        navigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.text_main)));
        Log.d(TAG, "initXmlFields() done");
    }

    /**
     * Инициализация слушателей событий.
     */
    private void initListeners() {
        Log.d(TAG, "initListeners() start");
        sectorsAdapterCallback = new SectorsAdapter.ISectorsAdapterCallback() {
            @Override
            public void openChosenSector(String sectorName, String sectorDescription, long sectorId) {
                Intent intent = new Intent(getApplicationContext(), SectorActivity.class);
                intent.putExtra(ICommonDtoConstants.SECTOR_ID, sectorId);
                intent.putExtra(ICommonDtoConstants.SECTOR_NAME, sectorName);
                intent.putExtra(ICommonDtoConstants.SECTOR_DESCRIPTION, sectorDescription);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        };
        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout != null && navigationView != null) {
                    drawerLayout.openDrawer(navigationView);
                }
            }
        });
        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Show map", Toast.LENGTH_SHORT).show();
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                Bundle nameFragment = new Bundle();
                Intent intent = null;
                switch (id) {
                    case (R.id.favourite):
                        nameFragment.putString(IStringConstants.NAME_FILTER_FRAGMENT, IStringConstants.FAVORITES_FRAGMENT);
                        intent = new Intent(getApplicationContext(), FiltersActivity.class);
                        intent.putExtras(nameFragment);
                        break;
                    case (R.id.filter_routes):
                        nameFragment.putString(IStringConstants.NAME_FILTER_FRAGMENT, IStringConstants.FILTERS_FRAGMENT);
                        intent = new Intent(getApplicationContext(), FiltersActivity.class);
                        intent.putExtras(nameFragment);
                        break;
                    case (R.id.search):
                        nameFragment.putString(IStringConstants.NAME_FILTER_FRAGMENT, IStringConstants.SEARCH_FRAGMENT);
                        intent = new Intent(getApplicationContext(), FiltersActivity.class);
                        intent.putExtras(nameFragment);
                        break;
                    case (R.id.information):
                        intent = new Intent(getApplicationContext(), InformationActivity.class);
                        break;
                }
                if (drawerLayout.isDrawerOpen(navigationView)) {
                    drawerLayout.closeDrawer(navigationView);
                }
                startActivity(intent);
                // TODO: поменять анимацию
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            }
        });
        /*
        Скрытие и отображение кнопки "Карта"
         */
//        sectorLayoutListView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView absListView, int i) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if (absListView.getId() == absListView.getId()) {
//
//                    Log.i(TAG, "firstVisibleItem = " + firstVisibleItem);
//                    Log.i(TAG, "visibleItemCount = " + visibleItemCount);
//                    Log.i(TAG, "totalItemCount = " + totalItemCount);
//
//                    final int currentFirstVisibleItem = mGrid.getFirstVisiblePosition();
//                    if (currentFirstVisibleItem > mLastFirstVisibleItem) {
//                        Log.i("a", "scrolling down...");
//                        if (mIsScrollingUp) {
//                            mIsScrollingUp = false;
//                            bottomView.setTranslationY(bottomHeight);
//                        }
//                    } else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
//                        Log.i("a", "scrolling up...");
//                        if (!mIsScrollingUp) {
//                            mIsScrollingUp = true;
//                            bottomView.setTranslationY(0);
//                        }
//                    }
//
//                    mLastFirstVisibleItem = currentFirstVisibleItem;
//                }
//            }
//        });
        Log.d(TAG, "initListeners() done");
    }

    private void initListAdapter() {
        Log.d(TAG, "initListAdapter() start");
        if (regionId == 1) {
            sectorsAdapter = new SectorsAdapter(SectorsCache.getInstance().getLietlahtiSectors(), sectorsAdapterCallback);
        } else if (regionId == 2) {
            sectorsAdapter = new SectorsAdapter(SectorsCache.getInstance().getTriangularSectors(), sectorsAdapterCallback);
        }
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        sectorsLayoutRecyclerView.setLayoutManager(linearLayoutManager);
        sectorsLayoutRecyclerView.setAdapter(sectorsAdapter);
        Log.d(TAG, "initListAdapter() done");
    }
}
