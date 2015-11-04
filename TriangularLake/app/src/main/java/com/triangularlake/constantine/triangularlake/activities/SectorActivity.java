package com.triangularlake.constantine.triangularlake.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.adapters.SectorBouldersAdapter;
import com.triangularlake.constantine.triangularlake.data.common.CommonDao;
import com.triangularlake.constantine.triangularlake.data.dto.Boulder;
import com.triangularlake.constantine.triangularlake.data.dto.ICommonDtoConstants;
import com.triangularlake.constantine.triangularlake.data.dto.Sector;
import com.triangularlake.constantine.triangularlake.data.helpers.OrmConnect;
import com.triangularlake.constantine.triangularlake.pojo.BoulderProblems;
import com.triangularlake.constantine.triangularlake.utils.IStringConstants;

import org.solovyev.android.views.llm.DividerItemDecoration;
import org.solovyev.android.views.llm.LinearLayoutManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SectorActivity extends AppCompatActivity implements BoulderProblems.IBoulderProblemsLoaderCallBack {
    private static final String TAG = SectorActivity.class.getSimpleName();

    private static final String LEFT_QUOTES = "<<";
    private static final String RIGHT_QUOTES = ">>";

    private ImageView sectorPhoto;
    private TextView sectorName;
    private TextView description;
    private RecyclerView sectorLayoutBouldersRecycleView;
    private Toolbar toolbar;
    private Button buttonMap;
    private ImageButton buttonMenu;

    private Long sectorId;
    private String labelSectorName;
    private String sectorDescription;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private SectorBouldersAdapter sectorBouldersAdapter;
    private BoulderProblems.IBoulderProblemsLoaderCallBack callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sector_layout);

        initInputValues();
        initToolbar();
        initXmlFields();
        loadData();
        initListeners();
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
        labelSectorName = getIntent().getExtras().getString(ICommonDtoConstants.SECTOR_NAME);
        sectorDescription = getIntent().getExtras().getString(ICommonDtoConstants.SECTOR_DESCRIPTION);
        Log.d(TAG, "initInputValues() done");
    }

    private void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");
        sectorPhoto = (ImageView) findViewById(R.id.sector_layout_sector_photo);
        sectorName = (TextView) findViewById(R.id.sector_layout_sector_name);
        description = (TextView) findViewById(R.id.sector_layout_description);
        buttonMenu = (ImageButton) findViewById(R.id.button_menu);
        sectorLayoutBouldersRecycleView = (RecyclerView) findViewById(R.id.sector_layout_boulders_recycle_view);
        // инициализация бокового меню
        drawerLayout = (DrawerLayout) findViewById(R.id.sector_layout_drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, null, R.string.navigation_menu_open, R.string.navigation_menu_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.sector_layout_navigation_view);
        navigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.text_main)));
        Log.d(TAG, "initXmlFields() done");
    }

    private void loadData() {
        Log.d(TAG, "loadData() start");
        // загрузка списка камней и проблем
        callback = this;
        // TODO: переделать загрузку?
//        new BoulderProblems.BoulderProblemsAsyncLoader(sectorId, getApplicationContext(), callback).execute();

        List<BoulderProblems> boulderProblemsList = new ArrayList<>();
        byte[] sectorPhoto;

        try {
            final CommonDao commonDao = OrmConnect.INSTANCE.getDBConnect(getApplicationContext()).getDaoByClass(Sector.class);
            final Sector sector = (Sector) commonDao.queryForEq("_id", sectorId).get(0);
            if (sector != null) {
                sectorPhoto = sector.getSectorPhoto();
                for (Boulder boulder : sector.getBoulders()) {
                    boulderProblemsList.add(new BoulderProblems(
                            boulder.getId(),
                            boulder.getPhoto().getPhotoData(),
                            boulder.getBoulderName(),
                            boulder.getBoulderNameRu(),
                            boulder.getProblems()));
                }
                // фотография сектора
                final Bitmap bitmap = BitmapFactory.decodeByteArray(sectorPhoto, 0, sectorPhoto.length);
                this.sectorPhoto.setImageBitmap(bitmap);
            }
        } catch (SQLException e) {
            Log.e(TAG, "BoulderProblemsAsyncLoader Error! " + e.getMessage());
        }
        sectorBouldersAdapter = new SectorBouldersAdapter(boulderProblemsList);
        sectorLayoutBouldersRecycleView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new org.solovyev.android.views.llm.LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        sectorLayoutBouldersRecycleView.addItemDecoration(new DividerItemDecoration(this, null));
        sectorLayoutBouldersRecycleView.setLayoutManager(layoutManager);
        sectorLayoutBouldersRecycleView.setAdapter(sectorBouldersAdapter);

        // название сектора
        final String sectorLabel = LEFT_QUOTES + labelSectorName + RIGHT_QUOTES;
        sectorName.setText(sectorLabel);
        // описание сектора
        description.setText(sectorDescription);
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
    public void callback(List<BoulderProblems> boulderProblemsList, byte[] sectorPhoto) {
        // список камней и проблем после загрузки из БД
        sectorBouldersAdapter = new SectorBouldersAdapter(boulderProblemsList);
        sectorLayoutBouldersRecycleView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new org.solovyev.android.views.llm.LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        sectorLayoutBouldersRecycleView.addItemDecoration(new DividerItemDecoration(this, null));
        sectorLayoutBouldersRecycleView.setLayoutManager(layoutManager);
        sectorLayoutBouldersRecycleView.setAdapter(sectorBouldersAdapter);

        // фотография сектора
        final Bitmap bitmap = BitmapFactory.decodeByteArray(sectorPhoto, 0, sectorPhoto.length);
        this.sectorPhoto.setImageBitmap(bitmap);
    }
}
