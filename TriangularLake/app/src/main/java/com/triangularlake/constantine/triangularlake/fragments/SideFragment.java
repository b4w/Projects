package com.triangularlake.constantine.triangularlake.fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.adapters.ProblemsExpListAdapter;
import com.triangularlake.constantine.triangularlake.data.common.CommonDao;
import com.triangularlake.constantine.triangularlake.data.dto.ICommonDtoConstants;
import com.triangularlake.constantine.triangularlake.data.dto.Photo;
import com.triangularlake.constantine.triangularlake.data.dto.Problem;
import com.triangularlake.constantine.triangularlake.data.dto.Side;
import com.triangularlake.constantine.triangularlake.data.helpers.OrmConnect;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SideFragment extends Fragment {
    private static final String TAG = SideFragment.class.getSimpleName();

    private long sideId;
    private long[] problemNumbers;

    private ImageView fragmentSideImageSide;
    private ExpandableListView fragmentSideExlistView;
    private ProblemsExpListAdapter problemsExpListAdapter;
    private LinearLayout fragmentSideLinearLayout;

    private CommonDao commonDao;
    private List<List<Problem>> problems;

    public static SideFragment newInstance() {
        SideFragment fragment = new SideFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInputValues();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_side, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initXmlFields();
        initListeners();
        loadDataFromDB();
        updateProblems();
    }

    private void initInputValues() {
        Log.d(TAG, "initInputValues() start");
        Bundle bundle = this.getArguments();
        sideId = bundle.getLong(ICommonDtoConstants.SIDE_ID, 0);
        problemNumbers = bundle.getLongArray(ICommonDtoConstants.PROBLEM_NUMBERS);
        Log.d(TAG, "initInputValues() done");
    }

    private void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");
//        fragmentSideImageSide = (ImageView) getActivity().findViewById(R.id.fragment_side_image_side);
        fragmentSideExlistView = (ExpandableListView) getActivity().findViewById(R.id.fragment_side_exlist_view);
        fragmentSideLinearLayout = (LinearLayout) getActivity().findViewById(R.id.fragment_side_linear_layout);
        Log.d(TAG, "initXmlFields() done");
    }

    private void initListeners() {
        Log.d(TAG, "initListeners() start");
        fragmentSideExlistView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {
                Problem problem = (Problem) parent.getExpandableListAdapter().getChild(groupPosition, childPosition);
                return false;
            }
        });

        Log.d(TAG, "initListeners() done");
    }

    private void loadDataFromDB() {
        Log.d(TAG, "loadDataFromDB() start");
        try {
            commonDao = OrmConnect.INSTANCE.getDBConnect(getActivity()).getDaoByClass(Side.class);

            // добавляем бэкграунд
            List<Side> sides = commonDao.queryForEq(ICommonDtoConstants.ID, sideId);
            ImageView backgroundImageView = new ImageView(getActivity());
            Bitmap bitmap = BitmapFactory.decodeByteArray(sides.get(0).getSidePhoto(), 0, sides.get(0).getSidePhoto().length);
            backgroundImageView.setImageBitmap(bitmap);
            fragmentSideLinearLayout.addView(backgroundImageView);

            // добавление фотографий линий на изображение
            commonDao = OrmConnect.INSTANCE.getDBConnect(getActivity()).getDaoByClass(Photo.class);
            List<Photo> photos = new ArrayList<>();
            for (int i = 0; i < problemNumbers.length; i++) {
                photos.add((Photo)commonDao.queryForId(problemNumbers[i]));
            }
            for (Photo item : photos) {
                ImageView imagePhoto = new ImageView(getActivity());
                bitmap = BitmapFactory.decodeByteArray(item.getPhotoData(), 0, item.getPhotoData().length);
                imagePhoto.setImageBitmap(bitmap);
            }
        } catch (SQLException e) {
            Log.e(TAG, "SideFragment loadDataFromDB() Error! " + e.getMessage());
        }
        Log.d(TAG, "loadDataFromDB() done");
    }

    private void updateProblems() {
        Log.d(TAG, "updateProblems() start");
        problems = new ArrayList<>();
        List<Problem> listProblems = new ArrayList<>();

        // TODO: может надо перенести в отдельный метод
        try {
            commonDao = OrmConnect.INSTANCE.getDBConnect(getActivity()).getDaoByClass(Problem.class);
            listProblems = commonDao.queryForEq(ICommonDtoConstants.SIDE_ID, sideId);
        } catch (SQLException e) {
            Log.e(TAG, "Side Fragment updateProblems() Error! " + e.getMessage());
        }

        /**
         * Обновляем раскрывающийся список с трассами
         */
        if (listProblems != null && !listProblems.isEmpty()) {
            List<Problem> child;
            for (Problem problem : listProblems) {
                child = new ArrayList<>();
                child.add(problem);
                problems.add(child);
            }
        }

        problemsExpListAdapter = new ProblemsExpListAdapter(getActivity(), problems);
        fragmentSideExlistView.setAdapter(problemsExpListAdapter);
        Log.d(TAG, "updateProblems() done");
    }
}
