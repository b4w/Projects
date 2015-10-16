package com.photoasgift.constantine.photoasgift.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.photoasgift.constantine.photoasgift.R;
import com.photoasgift.constantine.photoasgift.adapters.MainAdapter;
import com.photoasgift.constantine.photoasgift.database.MainMenu;

import java.util.ArrayList;
import java.util.List;

public class CostActivity extends AppCompatActivity {
    private final static String TAG = CostActivity.class.getSimpleName();

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;


    private RecyclerView mainLayoutRecyclerView;
    private MainAdapter mainAdapter;
    private MainAdapter.IMainAdapterCallback iMainAdapterCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cost_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar11);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("TEST");

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Suleiman Ali Shakir");

//        ImageView header = (ImageView) findViewById(R.id.header);

        initAdapterAndRecyclerView();
    }

    private void initAdapterAndRecyclerView() {
        Log.d(TAG, "initAdapterAndRecyclerView() start");
        mainLayoutRecyclerView = (RecyclerView) findViewById(R.id.scrollableview);
        mainAdapter = new MainAdapter(getTestDataMainMenu(), iMainAdapterCallback);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mainLayoutRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mainLayoutRecyclerView.setAdapter(mainAdapter);
        Log.d(TAG, "initAdapterAndRecyclerView() done");
    }

    // TODO: после добавления в БД - удалить.
    private List<MainMenu> getTestDataMainMenu() {
        List<MainMenu> mainMenuList = new ArrayList<>();
        mainMenuList.add(new MainMenu("1", "Give gift", "Сделать подарок", ""));
        mainMenuList.add(new MainMenu("2", "Cost", "Стоимость", ""));
        mainMenuList.add(new MainMenu("3", "My orders", "Мои заказы", ""));
        mainMenuList.add(new MainMenu("4", "Feedback", "Обратная связь", ""));
        mainMenuList.add(new MainMenu("5", "Settings", "Настройки", ""));
        mainMenuList.add(new MainMenu("6", "Give gift", "Сделать подарок", ""));
        mainMenuList.add(new MainMenu("7", "Cost", "Стоимость", ""));
        mainMenuList.add(new MainMenu("8", "My orders", "Мои заказы", ""));
        mainMenuList.add(new MainMenu("9", "Feedback", "Обратная связь", ""));
        mainMenuList.add(new MainMenu("10", "Settings", "Настройки", ""));
        mainMenuList.add(new MainMenu("11", "Settings", "Настройки", ""));
        mainMenuList.add(new MainMenu("12", "Give gift", "Сделать подарок", ""));
        mainMenuList.add(new MainMenu("13", "Cost", "Стоимость", ""));
        mainMenuList.add(new MainMenu("14", "My orders", "Мои заказы", ""));
        mainMenuList.add(new MainMenu("15", "Feedback", "Обратная связь", ""));
        mainMenuList.add(new MainMenu("16", "Settings", "Настройки", ""));
        return mainMenuList;
    }
}
