package com.triangularlake.constantine.triangularlake.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.adapters.FavouriteProblemsAdapter;
import com.triangularlake.constantine.triangularlake.data.helpers.SQLiteHelper;
import com.triangularlake.constantine.triangularlake.data.pojo.Problem;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment {
    private static final String TAG = FavouriteFragment.class.getSimpleName();

    private RecyclerView fragmentFavoritesProblems;
    private FavouriteProblemsAdapter favouriteProblemsAdapter;

    public static FavouriteFragment newInstance() {
        FavouriteFragment fragment = new FavouriteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initXmlFields();
        initListeners();
        initListAdapter();
    }

    private void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");
        fragmentFavoritesProblems = (RecyclerView) getActivity().findViewById(R.id.fragment_favorites_problems);
        Log.d(TAG, "initXmlFields() done");
    }

    private void initListeners() {
        Log.d(TAG, "initListeners() start");
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        fragmentFavoritesProblems.setLayoutManager(linearLayoutManager);
        fragmentFavoritesProblems.setAdapter(favouriteProblemsAdapter);
        Log.d(TAG, "initListeners() done");
    }

    private void loadData() {
        Log.d(TAG, "loadData() start");
        final SQLiteDatabase db = new SQLiteHelper(getActivity().getApplicationContext()).getReadableDatabase();
        final Cursor problemCursor = db.rawQuery("select * from PROBLEMS where favourite is 1", null);
        final List<Problem> problems = new ArrayList<>();
        problemCursor.moveToFirst();
        while (!problemCursor.isAfterLast()) {
            problems.add(new Problem(problemCursor.getInt(0),
                    problemCursor.getInt(1) > 0,
                    problemCursor.getInt(2),
                    problemCursor.getString(3) != null ? problemCursor.getString(3) : "",
                    problemCursor.getString(4) != null ? problemCursor.getString(4) : "",
                    problemCursor.getString(5),
                    problemCursor.getString(6),
                    problemCursor.getString(7),
                    problemCursor.getString(8),
                    problemCursor.getInt(9),
                    problemCursor.getInt(10),
                    problemCursor.getInt(11),
                    problemCursor.getInt(12),
                    problemCursor.getInt(13) > 0));
            problemCursor.moveToNext();
        }
        problemCursor.close();
        // TODO: разобраться с открытием и закрытием БД
        if (db.isOpen()) {
            db.close();
        }
        favouriteProblemsAdapter = new FavouriteProblemsAdapter(problems);
        Log.d(TAG, "loadData() done");
    }

    private void initListAdapter() {
        Log.d(TAG, "initListAdapter() start");
        // добавление данных в адаптер и RecyclerView
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        fragmentFavoritesProblems.setLayoutManager(layoutManager);
        fragmentFavoritesProblems.setAdapter(favouriteProblemsAdapter);
        Log.d(TAG, "initListAdapter() done");
    }
}
