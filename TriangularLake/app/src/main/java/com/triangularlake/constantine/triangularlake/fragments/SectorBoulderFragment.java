package com.triangularlake.constantine.triangularlake.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.android.loadercallback.OrmCursorLoaderCallback;
import com.j256.ormlite.stmt.PreparedQuery;
import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.adapters.SectorBouldersListAdapter;
import com.triangularlake.constantine.triangularlake.data.common.CommonDao;
import com.triangularlake.constantine.triangularlake.data.dto.ICommonDtoConstants;
import com.triangularlake.constantine.triangularlake.data.dto.Photo;
import com.triangularlake.constantine.triangularlake.data.dto.Problem;
import com.triangularlake.constantine.triangularlake.data.helpers.OrmConnect;

import java.sql.SQLException;
import java.util.List;

public class SectorBoulderFragment extends Fragment {
    private static final String TAG = SectorBoulderFragment.class.getSimpleName();

    private ImageView fragmentSectorBoulderPhoto;
    private ListView fragmentSectorBoulderProblemList;
    private TextView fragmentSectorBoulderName;

    private CommonDao commonDao;
    private SectorBouldersListAdapter sectorBouldersListAdapter;
    private OrmCursorLoaderCallback<Problem, Long> sectorBouldersLoaderCallback;

    private long boulderId;
    private String boulderName;

    private ISectorBoulderFragmentCallBack callBack;

    public static SectorBoulderFragment newInstance() {
        SectorBoulderFragment fragment = new SectorBoulderFragment();
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callBack = (ISectorBoulderFragmentCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement ISectorBoulderFragmentCallBack.");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInputValues();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sector_boulder, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initXmlFields();
        initListeners();
        loadDataFromDB();
        initListAdapter();
        initOrmCursorLoader();
    }

    private void initInputValues() {
        Log.d(TAG, "initInputValues() start");
        Bundle bundle = this.getArguments();
        boulderId = bundle.getLong(ICommonDtoConstants.BOULDER_ID, 0);
        boulderName = bundle.getString(ICommonDtoConstants.BOULDER_NAME, "");
        Log.d(TAG, "initInputValues() done");
    }

    private void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");
        fragmentSectorBoulderPhoto = (ImageView) getActivity().findViewById(R.id.fragment_sector_boulder_photo);
        fragmentSectorBoulderProblemList = (ListView) getActivity().findViewById(R.id.fragment_sector_boulder_problem_list);
        fragmentSectorBoulderName = (TextView) getActivity().findViewById(R.id.fragment_sector_boulder_name);
        Log.d(TAG, "initXmlFields() done");
    }

    private void initListeners() {
        Log.d(TAG, "initListeners() start");
        fragmentSectorBoulderPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.openChosenBoulder(boulderId, boulderName);
            }
        });
        Log.d(TAG, "initListeners() done");
    }

    private void loadDataFromDB() {
        Log.d(TAG, "loadDataFromDB() start");
        fragmentSectorBoulderName.setText(boulderName);
        try {
            commonDao = OrmConnect.INSTANCE.getDBConnect(getActivity()).getDaoByClass(Photo.class);
            List<Photo> photos = commonDao.queryForEq(ICommonDtoConstants.BOULDER_ID, boulderId);
            Bitmap bitmap = BitmapFactory.decodeByteArray(photos.get(0).getPhotoData(), 0, photos.get(0).getPhotoData().length);
            fragmentSectorBoulderPhoto.setImageBitmap(bitmap);
        } catch (SQLException e) {
            Log.e(TAG, "SectorBoulderFragment loadDataFromDB() Error! " + e.getMessage());
        }
        Log.d(TAG, "loadDataFromDB() done");
    }

    private void initListAdapter() {
        Log.d(TAG, "initListAdapter() start");
        sectorBouldersListAdapter = new SectorBouldersListAdapter(getActivity());
        Log.d(TAG, "initListAdapter() done");
    }

    private void initOrmCursorLoader() {
        Log.d(TAG, "initOrmCursorLoader() start");
        fragmentSectorBoulderProblemList.setAdapter(sectorBouldersListAdapter);
        fragmentSectorBoulderProblemList.setScrollContainer(false);
        try {
            commonDao = OrmConnect.INSTANCE.getDBConnect(getActivity()).getDaoByClass(Problem.class);
            if (commonDao != null) {
                PreparedQuery query = commonDao.queryBuilder().where().eq(ICommonDtoConstants.BOULDER_ID, boulderId).prepare();
                sectorBouldersLoaderCallback = new OrmCursorLoaderCallback<Problem, Long>(getActivity(),
                        commonDao, query, sectorBouldersListAdapter);
                getLoaderManager().initLoader(ICommonDtoConstants.PROBLEM_LOADER_NUMBER, null, sectorBouldersLoaderCallback);
            }
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        Log.d(TAG, "initOrmCursorLoader() done");
    }

    public interface ISectorBoulderFragmentCallBack {
        void openChosenBoulder(long boulderId, String boulderName);
    }
}
