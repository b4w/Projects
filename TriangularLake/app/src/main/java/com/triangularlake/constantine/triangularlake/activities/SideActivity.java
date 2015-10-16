package com.triangularlake.constantine.triangularlake.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.adapters.ProblemsPagerAdapter;
import com.triangularlake.constantine.triangularlake.data.common.CommonDao;
import com.triangularlake.constantine.triangularlake.data.dto.ICommonDtoConstants;
import com.triangularlake.constantine.triangularlake.data.dto.Problem;
import com.triangularlake.constantine.triangularlake.data.helpers.OrmConnect;
import com.triangularlake.constantine.triangularlake.utils.IStringConstants;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SideActivity extends AppCompatActivity {
    private static final String TAG = SideActivity.class.getSimpleName();

    private Toolbar toolbar;
    private ViewPager sideLayoutContainerViewPager;

    private PagerAdapter problemsPagerAdapter;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private ImageButton buttonMenu;

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
        sideLayoutContainerViewPager = (ViewPager) findViewById(R.id.side_layout_container_view_pager);
        buttonMenu = (ImageButton) findViewById(R.id.side_layout_button_menu);
        // инициализация бокового меню
        drawerLayout = (DrawerLayout) findViewById(R.id.side_layout_drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, null, R.string.navigation_menu_open, R.string.navigation_menu_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.side_layout_navigation_view);
        navigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.text_main)));
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
        try {
            final CommonDao commonDao = OrmConnect.INSTANCE.getDBConnect(getApplicationContext()).getDaoByClass(Problem.class);
            if (commonDao != null) {
                final List<Problem> problems = commonDao.queryForEq(ICommonDtoConstants.BOULDER_ID, boulderId);
                sideProblemsMap = new LinkedHashMap<>();
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

        problemsPagerAdapter = new ProblemsPagerAdapter(getSupportFragmentManager(), sideProblemsMap);
        sideLayoutContainerViewPager.setAdapter(problemsPagerAdapter);
        Log.d(TAG, "loadData() done");
    }

    private void initListeners() {
        Log.d(TAG, "initListeners() start");
        // add look at map click();
        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout != null && navigationView != null) {
                    drawerLayout.openDrawer(navigationView);
                }
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
            overridePendingTransition(R.anim.right_out, R.anim.left_in);
        }
    }
}
