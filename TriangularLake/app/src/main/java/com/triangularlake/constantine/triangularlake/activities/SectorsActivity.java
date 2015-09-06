package com.triangularlake.constantine.triangularlake.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.adapters.SectorsListAdapter;
import com.triangularlake.constantine.triangularlake.data.common.CommonDao;
import com.triangularlake.constantine.triangularlake.data.dto.Boulder;
import com.triangularlake.constantine.triangularlake.data.dto.ICommonDtoConstants;
import com.triangularlake.constantine.triangularlake.data.dto.Sector;
import com.triangularlake.constantine.triangularlake.pojo.SectorsCache;
import com.triangularlake.constantine.triangularlake.utils.IStringConstants;

public class SectorsActivity extends AppCompatActivity {

    private static final String TAG = SectorsActivity.class.getSimpleName();

    private ListView sectorLayoutListView;
    private Toolbar toolbar;
    private Drawer navigationDrawer;

    private long regionId;
    private String sectorName;

    private Button buttonMap;
    private ImageButton buttonMenu;

    private long[] boulderNumbers;
    private String[] boulderNames;

    private SectorsListAdapter sectorsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sectors_layout);

        initInputValues();
        initXmlFields();
        initListeners();
        initNavigationDrawer();
        initToolbar();
        initListAdapter();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_out, R.anim.left_in);
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
        sectorLayoutListView = (ListView) findViewById(R.id.sectors_layout_list_view);
        buttonMap = (Button) findViewById(R.id.button_map);
        buttonMenu = (ImageButton) findViewById(R.id.button_menu);
        Log.d(TAG, "initXmlFields() done");
    }

    /**
     * Инициализация слушателей событий.
     */
    private void initListeners() {
        Log.d(TAG, "initListeners() start");
        sectorLayoutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: заменить на singleton (pogo) для того что бы не доставать каждый раз данные из БД. Несколько раз вытаскиваю boulders!
                // переписать на HashMap<id, names> и закешировать?
                Sector sector = (Sector) sectorsListAdapter.getItem(position);
                Intent intent = new Intent(getApplicationContext(), SectorActivity.class);
                intent.putExtra(ICommonDtoConstants.SECTOR_ID, sector.getId());
                updateBoulderNumbersAndNames(sector);
                intent.putExtra(ICommonDtoConstants.BOULDER_NUMBERS, boulderNumbers);
                intent.putExtra(ICommonDtoConstants.BOULDER_NAMES, boulderNames);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
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

    private void updateBoulderNumbersAndNames(Sector sector) {
        boulderNumbers = new long[sector.getBoulders().size()];
        boulderNames = new String[sector.getBoulders().size()];
        int i = 0;
        for (Boulder boulder : sector.getBoulders()) {
            boulderNumbers[i] = boulder.getId();
            //TODO: добавлять в зависимости от локали
            boulderNames[i] = boulder.getBoulderName();
            i++;
        }
    }

    private void initListAdapter() {
        Log.d(TAG, "initListAdapter() start");
        if (regionId == 1) {
            sectorsListAdapter = new SectorsListAdapter(getApplicationContext(), SectorsCache.getInstance().getLietlahtiSectors());
        } else {
            sectorsListAdapter = new SectorsListAdapter(getApplicationContext(), SectorsCache.getInstance().getTriangularSectors());
        }
        sectorLayoutListView.setAdapter(sectorsListAdapter);
        Log.d(TAG, "initListAdapter() done");
    }
}
