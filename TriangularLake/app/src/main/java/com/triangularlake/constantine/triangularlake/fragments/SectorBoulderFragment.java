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

import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.data.common.CommonDao;
import com.triangularlake.constantine.triangularlake.data.dto.Boulder;
import com.triangularlake.constantine.triangularlake.data.dto.ICommonDtoConstants;
import com.triangularlake.constantine.triangularlake.data.dto.Photo;
import com.triangularlake.constantine.triangularlake.data.helpers.OrmHelper;

import java.sql.SQLException;
import java.util.List;

public class SectorBoulderFragment extends Fragment {
    private static final String TAG = SectorBoulderFragment.class.getSimpleName();

    private ImageView fragmentSectorBoulderPhoto;
    private CommonDao commonDao;
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
        initDB();
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
        loadDataFromDB();
    }

    private void initInputValues() {
        Log.d(TAG, "initInputValues() start");
        Bundle bundle = this.getArguments();
        boulderId = bundle.getLong("boulderId", 0);
        Log.d(TAG, "initInputValues() done");
    }

    private void initDB() {
        Log.d(TAG, "initDB() start");
        OrmHelper ormHelper = new OrmHelper(getActivity(), ICommonDtoConstants.TRIANGULAR_LAKE_DB,
                ICommonDtoConstants.TRIANGULAR_LAKE_DB_VERSION);
        try {
//            commonDao = ormHelper.getDaoByClass(Boulder.class);
            commonDao = ormHelper.getDaoByClass(Photo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "initDB() done");
    }

    private void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");
        fragmentSectorBoulderPhoto = (ImageView)getActivity().findViewById(R.id.fragment_sector_boulder_photo);
        Log.d(TAG, "initXmlFields() done");
    }

    private void loadDataFromDB() {
        Log.d(TAG, "loadDataFromDB() start");
        try {
            List <Photo> photos = commonDao.queryForEq("boulder_id", boulderId);
            Bitmap bitmap = BitmapFactory.decodeByteArray(photos.get(0).getPhotoData(), 0, photos.get(0).getPhotoData().length);
            fragmentSectorBoulderPhoto.setImageBitmap(bitmap);
        } catch (SQLException e) {

        }
        Log.d(TAG, "loadDataFromDB() done");
    }
}
