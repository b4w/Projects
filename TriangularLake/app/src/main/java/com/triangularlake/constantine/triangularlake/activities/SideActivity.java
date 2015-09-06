package com.triangularlake.constantine.triangularlake.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.data.common.CommonDao;
import com.triangularlake.constantine.triangularlake.data.dto.ICommonDtoConstants;
import com.triangularlake.constantine.triangularlake.data.dto.Problem;
import com.triangularlake.constantine.triangularlake.data.helpers.OrmConnect;
import com.triangularlake.constantine.triangularlake.fragments.SideFragment;
import com.triangularlake.constantine.triangularlake.utils.IStringConstants;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SideActivity extends AppCompatActivity {
    private static final String TAG = SideActivity.class.getSimpleName();

    private Toolbar toolbar;
    private Drawer navigationDrawer;

    private long boulderId;
    private String boulderName;

    private Map<Long, List<Long>> sideProblemsMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.side_layout);

        initInputValues();
        initXmlFields();
        initToolbar();
        loadData();
        initFragment();
        initNavigationDrawer();
        initListeners();
    }

    private void initInputValues() {
        Log.d(TAG, "initInputValues() start");
        boulderId = getIntent().getLongExtra(ICommonDtoConstants.BOULDER_ID, 0);
        boulderName = getIntent().getStringExtra(ICommonDtoConstants.BOULDER_NAME);
        Log.d(TAG, "initInputValues() done");
    }

    private void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");
        try {
            CommonDao commonDao = OrmConnect.INSTANCE.getDBConnect(getApplicationContext()).getDaoByClass(Problem.class);
            if (commonDao != null) {
                final List<Problem> problems = commonDao.queryForEq(ICommonDtoConstants.BOULDER_ID, boulderId);
                sideProblemsMap = new HashMap<>();
                List<Long> listProblems;
                for (Problem problem : problems) {
                    if (!sideProblemsMap.containsKey(problem.getSide().getId())) {
                        listProblems = new ArrayList<>();
                        listProblems.add(problem.getId());
                        sideProblemsMap.put(problem.getSide().getId(), listProblems);
                    } else {
                        listProblems = sideProblemsMap.get(problem.getSide().getId());
                        listProblems.add(problem.getId());
                    }
                }
            }
        } catch (SQLException e) {
            Log.e(TAG, "SideActivity initXmlFields() Error! " + e.getMessage());
        }
        Log.d(TAG, "initXmlFields() done");
    }

    private void initToolbar() {
        Log.d(TAG, "initToolbar() start");
        toolbar = (Toolbar) findViewById(R.id.side_layout_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(boulderName);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        Log.d(TAG, "initToolbar() done");
    }

    private void loadData() {
        Log.d(TAG, "loadData() start");


        Log.d(TAG, "loadData() done");
    }

    private void initFragment() {
        Log.d(TAG, "initFragment() start");
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = SideFragment.newInstance();
        Bundle bundle = new Bundle();

        //TODO: подумать и заменить на pojo???
        for (Long id : sideProblemsMap.keySet()) {
            bundle.putLong(ICommonDtoConstants.SIDE_ID, id);

            List<Long> longs = sideProblemsMap.get(id);
            long[] array = new long[longs.size()];
            for (int i = 0; i < longs.size(); i++) {
                array[i] = longs.get(i);
            }
            bundle.putLongArray(ICommonDtoConstants.PROBLEM_NUMBERS, array);
        }
        fragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .add(R.id.side_layout_container, fragment)
                .commit();
        Log.d(TAG, "initFragment() done");
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

        Log.d(TAG, "initListeners() done");
    }
}
