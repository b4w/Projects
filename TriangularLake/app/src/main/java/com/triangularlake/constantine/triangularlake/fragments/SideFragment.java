package com.triangularlake.constantine.triangularlake.fragments;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
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

import com.bumptech.glide.Glide;
import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.adapters.ProblemsExpListAdapter;
import com.triangularlake.constantine.triangularlake.data.common.CommonDao;
import com.triangularlake.constantine.triangularlake.data.dto.ICommonDtoConstants;
import com.triangularlake.constantine.triangularlake.data.dto.Photo;
import com.triangularlake.constantine.triangularlake.data.dto.Problem;
import com.triangularlake.constantine.triangularlake.data.dto.Side;
import com.triangularlake.constantine.triangularlake.data.helpers.OrmConnect;
import com.triangularlake.constantine.triangularlake.utils.DisplayUtils;
import com.triangularlake.constantine.triangularlake.utils.MyExpandableListView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SideFragment extends Fragment {
    private static final String TAG = SideFragment.class.getSimpleName();

    private long sideId;
    private long[] problemNumbers;

    private ImageView fragmentSideImageSide;
    private MyExpandableListView fragmentSideExlistView;
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
        initXmlFields(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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

    private void initXmlFields(View view) {
        Log.d(TAG, "initXmlFields() start");
//        fragmentSideImageSide = (ImageView) view.findViewById(R.id.fragment_side_image_side);
        fragmentSideExlistView = (MyExpandableListView) view.findViewById(R.id.fragment_side_exlist_view);
        fragmentSideLinearLayout = (LinearLayout) view.findViewById(R.id.fragment_side_linear_layout);
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
        // загрузка фотографий
        new AsyncLoadSidePhoto().execute();
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

    class AsyncLoadSidePhoto extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... voids) {
            Bitmap resultBitmap = null;
            try {
                commonDao = OrmConnect.INSTANCE.getDBConnect(getActivity()).getDaoByClass(Side.class);

                // разрешение экрана
                final DisplayUtils displayUtils = DisplayUtils.getInstance();
                displayUtils.updateDisplaySizes(getActivity());

                final List<Side> sides = commonDao.queryForEq(ICommonDtoConstants.ID, sideId);

                // добавляем бэкграунд изображение
                final Bitmap backgroundBitmap = Glide.with(getActivity())
                        .load(sides.get(0).getSidePhoto())
                        .asBitmap()
//                    .override(displayUtils.getDisplayWidth(), displayUtils.getDisplayHeight())
//                        .centerCrop()
                        .into(displayUtils.getDisplayWidth(), displayUtils.getDisplayHeight())
                        .get();

                resultBitmap = Bitmap.createBitmap(backgroundBitmap.getWidth(),
                        backgroundBitmap.getHeight(),
                        backgroundBitmap.getConfig());

                Canvas canvas = new Canvas(resultBitmap);
                canvas.drawBitmap(backgroundBitmap, new Matrix(), null);

                // добавление фотографий линий на изображение
                commonDao = OrmConnect.INSTANCE.getDBConnect(getActivity()).getDaoByClass(Photo.class);
                final List<Photo> photos = new ArrayList<>();
                for (int i = 0; i < problemNumbers.length; i++) {
                    photos.add((Photo) commonDao.queryForId(problemNumbers[i]));
                }
                for (Photo item : photos) {
                    final Bitmap bitmap = BitmapFactory.decodeByteArray(item.getPhotoData(), 0, item.getPhotoData().length);
                    canvas.drawBitmap(bitmap, 0, 0, null);
                }

            } catch (SQLException | InterruptedException | ExecutionException e) {
                Log.e(TAG, "SideFragment loadDataFromDB() Error! " + e.getMessage());
            }
            return resultBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView backgroundImageView = new ImageView(getActivity());
            backgroundImageView.setImageBitmap(bitmap);
            fragmentSideLinearLayout.addView(backgroundImageView);
        }
    }
}
