package com.triangularlake.constantine.triangularlake.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.adapters.FavouriteProblemsAdapter;

public class SearchFragment extends Fragment {
    private static final String TAG = SearchFragment.class.getSimpleName();

    private static final String PROBLEM_NAME = "problem_name";
    private static final String PROBLEM_NAME_RU = "problem_name_ru";
    private static final String RU = "ru";

    private EditText inputSearch;
    private RecyclerView fragmentSearchProblems;
    private FavouriteProblemsAdapter favouriteProblemsAdapter;

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initXmlFields();
        initListeners();
    }

    private void loadData() {
        Log.d(TAG, "loadData() start");
//        try {
//            final CommonDao commonDao = OrmConnect.INSTANCE.getDBConnect(getActivity()).getDaoByClass(Problem.class);
//            @SuppressWarnings("unchecked")
//            final List<Problem> problems = commonDao.queryForAll();
//            if (problems != null) {
//                favouriteProblemsAdapter = new FavouriteProblemsAdapter(problems);
//            }
//        } catch (SQLException e) {
//
//        }
        Log.d(TAG, "loadData() done");
    }

    private void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");
        inputSearch = (EditText) getActivity().findViewById(R.id.fragment_search_edit_text);
        fragmentSearchProblems = (RecyclerView) getActivity().findViewById(R.id.fragment_search_problems);
        Log.d(TAG, "initXmlFields() done");
    }

    private void initListeners() {
        Log.d(TAG, "initListeners() start");
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        fragmentSearchProblems.setLayoutManager(linearLayoutManager);
        fragmentSearchProblems.setAdapter(favouriteProblemsAdapter);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                try {
//                    final CommonDao commonDao = OrmConnect.INSTANCE.getDBConnect(getActivity()).getDaoByClass(Problem.class);
//                    if (commonDao != null) {
//                        String searchField = charSequence.toString();
//                        // TODO: Ошибка по SIDES! =(((
//                        @SuppressWarnings("unchecked")
//                        final QueryBuilder<Problem, Long> qb = commonDao.queryBuilder();
//                        if (Locale.ENGLISH.getLanguage().equals(Locale.getDefault().getLanguage())) {
//                            qb.where().like(PROBLEM_NAME, '%' + searchField + '%');
//                        } else if (Locale.getDefault().getLanguage().equals(RU)) {
//                            qb.where().like(PROBLEM_NAME_RU, '%' + searchField + '%');
//                        } else {
//                            qb.where().like(PROBLEM_NAME, '%' + searchField + '%');
//                        }
//                        final PreparedQuery<Problem> pq = qb.prepare();
//                        @SuppressWarnings("unchecked")
//                        List<Problem> problems = commonDao.query(pq);
//                        favouriteProblemsAdapter.setProblems(problems);
//                        favouriteProblemsAdapter.notifyDataSetChanged();
//                    }
//                } catch (SQLException e) {
//                    Log.e("FavouriteProblemsCache!", "FavouriteProblemsCache doInBackground() Error! " + e.getMessage());
//                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Log.d(TAG, "initListeners() done");
    }
}
