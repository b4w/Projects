package com.triangularlake.constantine.triangularlake.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.data.common.CommonDao;
import com.triangularlake.constantine.triangularlake.data.dto.ICommonDtoConstants;
import com.triangularlake.constantine.triangularlake.data.dto.Sector;
import com.triangularlake.constantine.triangularlake.data.helpers.OrmConnect;
import com.triangularlake.constantine.triangularlake.fragments.SectorBoulderFragment;
import com.triangularlake.constantine.triangularlake.utils.IStringConstants;

import java.sql.SQLException;
import java.util.List;

public class SectorActivity extends AppCompatActivity implements SectorBoulderFragment.ISectorBoulderFragmentCallBack {
    private static final String TAG = SectorActivity.class.getSimpleName();

    private ImageView sectorPhoto;
    private TextView sectorName;
    private TextView description;

    private long sectorId;
    private long[] boulderNumbers;
    private String[] boulderNames;

    private Toolbar toolbar;
    private String labelSectorName;

    private Drawer navigationDrawer;

    private Button buttonMap;
    private ImageButton buttonMenu;

    private SectorBoulderFragment sectorBoulderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sector_layout);

        initInputValues();
        initXmlFields();
        initSectorStoneFragments();
        initNavigationDrawer();
        loadData();
        initToolbar();
        initListeners();
//        initListAdapter();
//        initOrmCursorLoader();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_out, R.anim.left_in);
    }

    private void initToolbar() {
        Log.d(TAG, "initToolbar() start");
        toolbar = (Toolbar) findViewById(R.id.sector_layout_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(labelSectorName);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        Log.d(TAG, "initToolbar() done");
    }

    private void initInputValues() {
        Log.d(TAG, "initInputValues() start");
        sectorId = getIntent().getExtras().getLong(ICommonDtoConstants.SECTOR_ID);
        boulderNumbers = getIntent().getExtras().getLongArray(ICommonDtoConstants.BOULDER_NUMBERS);
        boulderNames = getIntent().getExtras().getStringArray(ICommonDtoConstants.BOULDER_NAMES);
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
                labelSectorName = sector.getSectorName();
                description.setText(sector.getSectorDesc());
            }
        } catch (SQLException e) {

        }
        Log.d(TAG, "loadData() done");
    }

    /**
     * Инициализация фрагментов камней сектора.
     */
    private void initSectorStoneFragments() {
        Log.d(TAG, "initSectorStoneFragments() start");
        FragmentManager fragmentManager;
//        TODO: подумать как упростить
        for (long boulderId : boulderNumbers) {
            fragmentManager = getFragmentManager();
            sectorBoulderFragment = SectorBoulderFragment.newInstance();

            Bundle bundle = new Bundle();
            bundle.putLong(ICommonDtoConstants.BOULDER_ID, boulderId);
            bundle.putString(ICommonDtoConstants.BOULDER_NAME, boulderNames[0]);
            sectorBoulderFragment.setArguments(bundle);

            fragmentManager.beginTransaction()
                    .add(R.id.sector_layout_sector_container, sectorBoulderFragment, "sectorFragmentId" + boulderId)
                    .commit();
        }
        Log.d(TAG, "initSectorStoneFragments() done");
    }

    private void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");
        sectorPhoto = (ImageView) findViewById(R.id.sector_layout_sector_photo);
        sectorName = (TextView) findViewById(R.id.sector_layout_sector_name);
        description = (TextView) findViewById(R.id.sector_layout_description);
        buttonMenu = (ImageButton) findViewById(R.id.button_menu);
        Log.d(TAG, "initXmlFields() done");
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

    private AccountHeader getAccountHeader() {
        Log.d(TAG, "getAccountHeader() start");
        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .build();
        Log.d(TAG, "getAccountHeader() done");
        return accountHeader;
    }

    private void initListeners() {
        Log.d(TAG, "initListeners() start");
        // add look at map click();
        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (navigationDrawer != null) {
                    navigationDrawer.openDrawer();
                }
            }
        });
        Log.d(TAG, "initListeners() done");
    }

    /**
     * Открываем выбранный камень с трассами
     *
     * @param boulderId
     */
    @Override
    public void openChosenBoulder(long boulderId, String boulderName) {
        Log.d(TAG, "openChosenBoulder() start");
        Intent intent = new Intent(getApplicationContext(), SideActivity.class);
        intent.putExtra(ICommonDtoConstants.BOULDER_ID, boulderId);
        intent.putExtra(ICommonDtoConstants.BOULDER_NAME, boulderName);
        startActivity(intent);
        Log.d(TAG, "openChosenBoulder() done");
    }
}
