package com.triangularlake.constantine.triangularlake.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.adapters.SectorBouldersAdapter;
import com.triangularlake.constantine.triangularlake.data.dto.ICommonDtoConstants;
import com.triangularlake.constantine.triangularlake.data.helpers.SQLiteHelper;
import com.triangularlake.constantine.triangularlake.data.pojo.Boulder;
import com.triangularlake.constantine.triangularlake.data.pojo.BoulderProblems;
import com.triangularlake.constantine.triangularlake.data.pojo.PojosKt;
import com.triangularlake.constantine.triangularlake.data.pojo.Problem;
import com.triangularlake.constantine.triangularlake.utils.IStringConstants;

import org.solovyev.android.views.llm.DividerItemDecoration;
import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SectorActivity extends AppCompatActivity {
    private static final String TAG = SectorActivity.class.getSimpleName();

    private static final String LEFT_QUOTES = "<<";
    private static final String RIGHT_QUOTES = ">>";

    private ImageView sectorPhoto;
    private TextView sectorName;
    private TextView description;
    private RecyclerView sectorLayoutBouldersRecycleView;
    private Toolbar toolbar;
    private ImageButton buttonMenu;

    private int sectorId;
    private String labelSectorName;
    private String sectorDescription;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private SectorBouldersAdapter sectorBouldersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sector_layout);

        initInputValues();
        initToolbar();
        initXmlFields();
        loadData();
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
        sectorId = getIntent().getExtras().getInt(ICommonDtoConstants.SECTOR_ID);
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

        final List<BoulderProblems> boulderProblemsList = new ArrayList<>();
        final SQLiteDatabase db = new SQLiteHelper(getApplicationContext()).getReadableDatabase();

        // фотография выбранного сектора
        final Cursor sectorCursor = db.rawQuery("select sector_photo from SECTORS where _id = ?", new String[]{sectorId + ""});
        sectorCursor.moveToFirst();
        while (!sectorCursor.isAfterLast()) {
            final byte[] sectorPhoto = sectorCursor.getBlob(0);
            final Bitmap bitmap = BitmapFactory.decodeByteArray(sectorPhoto, 0, sectorPhoto.length);
            this.sectorPhoto.setImageBitmap(bitmap);
            sectorCursor.moveToNext();
        }
        sectorCursor.close();

        // список фотографий
        // что бы не делать из цикла запрос, вытаскиваем сразу все фото для боулдерингов
        final Map<Integer, byte[]> photosMap = new HashMap<>();
        final Cursor photoCursor = db.rawQuery("select boulder_id, photo_data from PHOTOS where boulder_id is not null", null);
        photoCursor.moveToFirst();
        while (!photoCursor.isAfterLast()) {
            photosMap.put(photoCursor.getInt(0), photoCursor.getBlob(1));
            photoCursor.moveToNext();
        }
        photoCursor.close();

        // список боулдерингов
        final List<Boulder> boulders = new ArrayList<>();
        final Cursor boulderCursor = db.rawQuery("select * from BOULDERS where sector_id = ?", new String[]{sectorId + ""});
        boulderCursor.moveToFirst();
        while (!boulderCursor.isAfterLast()) {
            boulders.add(PojosKt.getNewBoulderFromCursor(boulderCursor));
            boulderCursor.moveToNext();
        }
        boulderCursor.close();

        for (Boulder boulder : boulders) {
            boulderProblemsList.add(new BoulderProblems(
                    boulder.getId(),
                    photosMap.get(boulder.getId()),
                    boulder.getBoulderName(),
                    boulder.getBoulderNameRu(),
                    getProblemsByBoulderId(db, boulder.getId())));
        }

        // TODO: разобраться с открытием и закрытием БД
        if (db.isOpen()) {
            db.close();
        }
        sectorBouldersAdapter = new SectorBouldersAdapter(boulderProblemsList);

        // название сектора
        final String sectorLabel = LEFT_QUOTES + labelSectorName + RIGHT_QUOTES;
        sectorName.setText(sectorLabel);
        // описание сектора
        description.setText(sectorDescription);
        Log.d(TAG, "loadData() done");
    }

    private List<Problem> getProblemsByBoulderId(SQLiteDatabase db, int boulderId) {
        Log.d(TAG, "getProblemsByBoulderId() start");
        final List<Problem> result = new ArrayList<>();
        final Cursor problemCursor = db.rawQuery("select * from PROBLEMS where boulder_id = ?", new String[]{boulderId + ""});
        problemCursor.moveToFirst();
        while (!problemCursor.isAfterLast()) {
            result.add(PojosKt.getNewProblemFromCursor(problemCursor));
            problemCursor.moveToNext();
        }
        problemCursor.close();
        Log.d(TAG, "getProblemsByBoulderId() done");
        return result;
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

    private void initListAdapter() {
        Log.d(TAG, "initListAdapter() start");
        sectorLayoutBouldersRecycleView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new org.solovyev.android.views.llm.LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        sectorLayoutBouldersRecycleView.addItemDecoration(new DividerItemDecoration(this, null));
        sectorLayoutBouldersRecycleView.setLayoutManager(layoutManager);
        sectorLayoutBouldersRecycleView.setAdapter(sectorBouldersAdapter);
        Log.d(TAG, "initListAdapter() done");
    }
}
