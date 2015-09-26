package com.triangularlake.constantine.triangularlake.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.adapters.RegionsAdapter;
import com.triangularlake.constantine.triangularlake.data.common.CommonDao;
import com.triangularlake.constantine.triangularlake.data.dto.Region;
import com.triangularlake.constantine.triangularlake.data.helpers.OrmConnect;
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
    private Drawer navigationDrawer;

    private Button buttonMap;
    private ImageButton buttonMenu;

    private RegionsAdapter regionsAdapter;
    private RegionsAdapter.IRegionsAdapterCallback regionsAdapterCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regions_layout);

        // кеширование секторов
        if (!SectorsCache.isCached) {
            new SectorsCache.AsyncLoadSectors(getApplicationContext()).execute();
        }

        initXmlFields();
        initListeners();
        initNavigationDrawer();
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
        Log.d(TAG, "initXmlFields() done");
    }

    /**
     * Инициализация слушателей событий.
     */
    private void initListeners() {
        Log.d(TAG, "initListeners() start");
        regionsAdapterCallback = new RegionsAdapter.IRegionsAdapterCallback() {
            @Override
            public void openRegionOnId(long regionId) {
                Log.d(TAG, "openChosenRegionOnId() start");
                Intent intent = null;
                switch ((int)regionId) {
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
                if (navigationDrawer != null) {
                    navigationDrawer.openDrawer();
                }
            }
        });
        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Show map", Toast.LENGTH_SHORT).show();
            }
        });
        Log.d(TAG, "initListeners() done");
    }

    /**
     * Добавляем боковое меню (navigationDrawer).
     */
    private void initNavigationDrawer() {
        Log.d(TAG, "initNavigationDrawer() start");
        navigationDrawer = new DrawerBuilder()
                .withActivity(this)
                .withDrawerGravity(Gravity.RIGHT)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(getAccountHeader())
                .withSliderBackgroundColorRes(R.color.background) // цвет фона
                .addDrawerItems(initDrawerItems())
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
                        Bundle nameFragment = new Bundle();
                        Intent intent = null;
                        switch (i) {
                            case 1:
                                nameFragment.putString(IStringConstants.NAME_FILTER_FRAGMENT, IStringConstants.FAVORITES_FRAGMENT);
                                intent = new Intent(getApplicationContext(), FiltersActivity.class);
                                intent.putExtras(nameFragment);
                                break;
                            case 2:
                                nameFragment.putString(IStringConstants.NAME_FILTER_FRAGMENT, IStringConstants.FILTERS_FRAGMENT);
                                intent = new Intent(getApplicationContext(), FiltersActivity.class);
                                intent.putExtras(nameFragment);
                                break;
                            case 3:
                                nameFragment.putString(IStringConstants.NAME_FILTER_FRAGMENT, IStringConstants.SEARCH_FRAGMENT);
                                intent = new Intent(getApplicationContext(), FiltersActivity.class);
                                intent.putExtras(nameFragment);
                                break;
                            case 4:
                                intent = new Intent(getApplicationContext(), InformationActivity.class);
                                break;
                        }
                        if (navigationDrawer.isDrawerOpen()) {
                            navigationDrawer.closeDrawer();
                        }
                        startActivity(intent);
                        // TODO: поменять анимацию
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        return true;
                    }
                })
                .build();
        Log.d(TAG, "initNavigationDrawer() done");
    }

    /**
     * Добавляем элементы меню в боковое меню.
     *
     * @return массив элементов меню.
     */
    private IDrawerItem[] initDrawerItems() {
        Log.d(TAG, "initDrawerItems()");
        PrimaryDrawerItem favoritesItem = new PrimaryDrawerItem()
                .withName(getResources().getString(R.string.favorites_upper))
                .withSelectedColorRes(R.color.background) // цвет выделенного пункта из ресурсов
                .withSelectedTextColorRes(R.color.text_main) // цвет текста выделенного пункта из ресурсов
                .withTextColor(Color.WHITE)
                .withIdentifier(1);

        PrimaryDrawerItem filterRoutesItem = new PrimaryDrawerItem()
                .withName(getResources().getString(R.string.filter_routes_upper))
                .withSelectedColorRes(R.color.background) // цвет выделенного пункта из ресурсов
                .withSelectedTextColorRes(R.color.text_main) // цвет текста выделенного пункта из ресурсов
                .withTextColor(Color.WHITE)
                .withIdentifier(1);

        PrimaryDrawerItem searchItem = new PrimaryDrawerItem()
                .withName(getResources().getString(R.string.search_upper))
                .withSelectedColorRes(R.color.background) // цвет выделенного пункта из ресурсов
                .withSelectedTextColorRes(R.color.text_main) // цвет текста выделенного пункта из ресурсов
                .withTextColor(Color.WHITE)
                .withIdentifier(1);

        PrimaryDrawerItem informationItem = new PrimaryDrawerItem()
                .withName(getResources().getString(R.string.information))
                .withSelectedColorRes(R.color.background) // цвет выделенного пункта из ресурсов
                .withSelectedTextColorRes(R.color.text_main) // цвет текста выделенного пункта из ресурсов
                .withTextColor(Color.WHITE)
                .withIdentifier(1);

        return new IDrawerItem[]{favoritesItem, filterRoutesItem, searchItem, informationItem};
    }

    /**
     * Заголовок в боковом меню.
     *
     * @return объект класса заголовка меню.
     */
    private AccountHeader getAccountHeader() {
        Log.d(TAG, "getAccountHeader() start");
        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .build();
        Log.d(TAG, "getAccountHeader() done");
        return accountHeader;
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
