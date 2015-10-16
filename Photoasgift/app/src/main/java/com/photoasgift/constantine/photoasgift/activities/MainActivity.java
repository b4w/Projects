package com.photoasgift.constantine.photoasgift.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.photoasgift.constantine.photoasgift.R;
import com.photoasgift.constantine.photoasgift.adapters.MainAdapter;
import com.photoasgift.constantine.photoasgift.database.MainMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mainLayoutRecyclerView;
    private MainAdapter mainAdapter;
    private MainAdapter.IMainAdapterCallback iMainAdapterCallback;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        initXmlFields();
        initListeners();
        initAdapterAndRecyclerView();
        initToolbar();

    }

    private void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");
        mainLayoutRecyclerView = (RecyclerView) findViewById(R.id.main_layout_recycler_view);
        Log.d(TAG, "initXmlFields() done");
    }

    private void initListeners() {
        Log.d(TAG, "initListeners() start");
        iMainAdapterCallback = new MainAdapter.IMainAdapterCallback() {
            @Override
            public void openChosenActivity(String mainMenuItemId) {
                Intent intent = null;
                switch (mainMenuItemId) {
                    case "1":
                        intent = new Intent(getApplicationContext(), OrderActivity.class);
                        break;
                    case "2":
                        intent = new Intent(getApplicationContext(), CostActivity.class);
                        break;
                    case "3":
                        Toast.makeText(getApplicationContext(), "My orders", Toast.LENGTH_SHORT).show();
                        break;
                    case "4":
                        Toast.makeText(getApplicationContext(), "Feedback", Toast.LENGTH_SHORT).show();
                        break;
                    case "5":
                        Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
                        break;
                }
                if (intent != null) {
                    startActivity(intent);
                }
            }
        };
        Log.d(TAG, "initListeners() done");
    }

    private void initAdapterAndRecyclerView() {
        Log.d(TAG, "initAdapterAndRecyclerView() start");
        mainAdapter = new MainAdapter(getTestDataMainMenu(), iMainAdapterCallback);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mainLayoutRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mainLayoutRecyclerView.setAdapter(mainAdapter);
        Log.d(TAG, "initAdapterAndRecyclerView() done");
    }

    private void initToolbar() {
        Log.d(TAG, "initToolbar() start");
        toolbar = (Toolbar) findViewById(R.id.main_layout_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("test");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        Log.d(TAG, "initToolbar() done");
    }

    // TODO: после добавления в БД - удалить.
    private List<MainMenu> getTestDataMainMenu() {
        List<MainMenu> mainMenuList = new ArrayList<>();
        mainMenuList.add(new MainMenu("1", "Give gift", "Сделать подарок", ""));
        mainMenuList.add(new MainMenu("2", "Cost", "Стоимость", ""));
        mainMenuList.add(new MainMenu("3", "My orders", "Мои заказы", ""));
        mainMenuList.add(new MainMenu("4", "Feedback", "Обратная связь", ""));
        mainMenuList.add(new MainMenu("5", "Settings", "Настройки", ""));
        return mainMenuList;
    }
}
