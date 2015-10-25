package com.triangularlake.constantine.triangularlake.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.adapters.RegionsAdapter;
import com.triangularlake.constantine.triangularlake.data.common.CommonDao;
import com.triangularlake.constantine.triangularlake.data.dto.Region;
import com.triangularlake.constantine.triangularlake.data.helpers.OrmConnect;
import com.triangularlake.constantine.triangularlake.pojo.FavouriteProblemsCache;
import com.triangularlake.constantine.triangularlake.pojo.SectorsCache;
import com.triangularlake.constantine.triangularlake.utils.IStringConstants;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegionsActivity extends Activity {
    private final static String TAG = RegionsActivity.class.getSimpleName();
    private final static int REGION_LIETLAHTI_ID = 1;
    private final static int REGION_TRIANGULAR_LAKE_ID = 2;

    private RecyclerView regionsLayoutRecyclerView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private Button buttonMap;
    private ImageButton buttonMenu;

    private RegionsAdapter regionsAdapter;
    private RegionsAdapter.IRegionsAdapterCallback regionsAdapterCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regions_layout);

        // кеширование секторов в отдельном потоке
        if (!SectorsCache.isCached) {
            new SectorsCache.AsyncLoadSectors(getApplicationContext()).execute();
        }

//        // кеширование избранных проблем в отдельном потоке
//        if (!FavouriteProblemsCache.isCached) {
//            new FavouriteProblemsCache.AsyncLoaderFavouriteRoutes(getApplicationContext()).execute();
//        }

        initXmlFields();
        initListeners();
        loadData();
    }

    /**
     * Инициализация полей в разметке xml файла.
     */
    private void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");
        regionsLayoutRecyclerView = (RecyclerView) findViewById(R.id.regions_layout_recycler_view);
        buttonMap = (Button) findViewById(R.id.button_map);
        buttonMenu = (ImageButton) findViewById(R.id.button_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.regions_layout_drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, null, R.string.navigation_menu_open, R.string.navigation_menu_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.regions_layout_navigation_view);
        navigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.text_main)));
        Log.d(TAG, "initXmlFields() done");
    }

    /**
     * Инициализация слушателей событий.
     */
    private void initListeners() {
        Log.d(TAG, "initListeners() start");
        // открываем выбранный регион
        regionsAdapterCallback = new RegionsAdapter.IRegionsAdapterCallback() {
            @Override
            public void openRegionOnId(long regionId) {
                Log.d(TAG, "openChosenRegionOnId() start");
                Intent intent = null;
                switch ((int) regionId) {
                    case REGION_LIETLAHTI_ID:
                        intent = new Intent(getApplicationContext(), SectorsActivity.class);
                        intent.putExtra(IStringConstants.SECTOR_NAME, getString(R.string.lietlahti));
                        break;
                    case REGION_TRIANGULAR_LAKE_ID:
                        intent = new Intent(getApplicationContext(), SectorsActivity.class);
                        intent.putExtra(IStringConstants.SECTOR_NAME, getString(R.string.triangular_lake));
                        break;
                }
                if (intent != null) {
                    intent.putExtra(IStringConstants.REGION_ID, regionId);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
                Log.d(TAG, "openChosenRegionOnId() done");
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
        Log.d(TAG, "initListeners() done");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (drawerLayout != null && navigationView != null && drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawer(navigationView);
        } else {
            // TODO: выход из приложения?
        }
    }

    /**
     * Загрузка данных по регионам из БД в AsyncTask
     */
    private void loadData() {
        Log.d(TAG, "loadData() start");
        new AsyncLoadDataFromDB().execute();
        Log.d(TAG, "loadData() done");
    }

    /**
     * Асинхронная загрузка Регионов из БД.
     * Инициализация адаптера.
     */
    class AsyncLoadDataFromDB extends AsyncTask<Void, Void, List<Region>> {

        @Override
        protected List<Region> doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground() start");
            List<Region> regions = new ArrayList<>();
            try {
                final CommonDao commonDao = OrmConnect.INSTANCE.getDBConnect(getApplicationContext()).getDaoByClass(Region.class);
                if (commonDao != null) {
                    regions = commonDao.queryForAll();
                } else {
                    Log.e(TAG, "Error when load Regions");
                }
            } catch (SQLException e) {
                Log.e(TAG, e.getMessage());
            }
            Log.d(TAG, "doInBackground() done");
            return regions;
        }

        @Override
        protected void onPostExecute(List<Region> regions) {
            super.onPostExecute(regions);
            Log.d(TAG, "onPostExecute() start");
            // инициализация адаптера + добаление слушателя события
            regionsAdapter = new RegionsAdapter(regions, regionsAdapterCallback);
            // инициализация RecyclerView
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            regionsLayoutRecyclerView.setLayoutManager(linearLayoutManager);
            regionsLayoutRecyclerView.setAdapter(regionsAdapter);
            Log.d(TAG, "onPostExecute() done");
        }
    }
}
