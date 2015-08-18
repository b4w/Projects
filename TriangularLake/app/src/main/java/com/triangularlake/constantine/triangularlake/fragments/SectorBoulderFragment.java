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
import android.widget.Toast;

import com.j256.ormlite.android.loadercallback.OrmCursorLoaderCallback;
import com.j256.ormlite.stmt.PreparedQuery;
import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.adapters.SectorBouldersListAdapter;
import com.triangularlake.constantine.triangularlake.data.common.CommonDao;
import com.triangularlake.constantine.triangularlake.data.dto.ICommonDtoConstants;
import com.triangularlake.constantine.triangularlake.data.dto.Photo;
import com.triangularlake.constantine.triangularlake.data.dto.Problem;
import com.triangularlake.constantine.triangularlake.data.helpers.OrmConnect;
import com.triangularlake.constantine.triangularlake.data.helpers.OrmHelper;

import java.sql.SQLException;
import java.util.List;

public class SectorBoulderFragment extends Fragment {
    private static final String TAG = SectorBoulderFragment.class.getSimpleName();

    private ImageView fragmentSectorBoulderPhoto;
    private ListView fragmentSectorBoulderProblemList;

    private CommonDao photoDao;
    private CommonDao problemDao;
    private SectorBouldersListAdapter sectorBouldersListAdapter;
    private OrmCursorLoaderCallback<Problem, Long> sectorBouldersLoaderCallback;

    private long boulderId;

    public static SectorBoulderFragment newInstance() {
        SectorBoulderFragment fragment = new SectorBoulderFragment();
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
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
        Log.d(TAG, "initInputValues() done");
    }

    private void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");
        fragmentSectorBoulderPhoto = (ImageView) getActivity().findViewById(R.id.fragment_sector_boulder_photo);
        fragmentSectorBoulderProblemList = (ListView) getActivity().findViewById(R.id.fragment_sector_boulder_problem_list);
        Log.d(TAG, "initXmlFields() done");
    }

    private void initListeners() {
        Log.d(TAG, "initListeners() start");
        fragmentSectorBoulderPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Open boulder id = " + boulderId, Toast.LENGTH_SHORT).show();
            }
        });
        Log.d(TAG, "initListeners() done");
    }

    private void loadDataFromDB() {
        Log.d(TAG, "loadDataFromDB() start");
        try {
            photoDao = OrmConnect.INSTANCE.getDBConnect(getActivity()).getDaoByClass(Photo.class);
            List<Photo> photos = photoDao.queryForEq(ICommonDtoConstants.BOULDER_ID, boulderId);
            Bitmap bitmap = BitmapFactory.decodeByteArray(photos.get(0).getPhotoData(), 0, photos.get(0).getPhotoData().length);
            fragmentSectorBoulderPhoto.setImageBitmap(bitmap);
        } catch (SQLException e) {

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
        try {
            problemDao = OrmConnect.INSTANCE.getDBConnect(getActivity()).getDaoByClass(Problem.class);
            if (problemDao != null) {
                PreparedQuery query = problemDao.queryBuilder().where().eq(ICommonDtoConstants.BOULDER_ID, boulderId).prepare();
                sectorBouldersLoaderCallback = new OrmCursorLoaderCallback<Problem, Long>(getActivity(),
                        problemDao, query, sectorBouldersListAdapter);
                getLoaderManager().initLoader(ICommonDtoConstants.PROBLEM_LOADER_NUMBER, null, sectorBouldersLoaderCallback);
            }
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        Log.d(TAG, "initOrmCursorLoader() done");
    }
}
