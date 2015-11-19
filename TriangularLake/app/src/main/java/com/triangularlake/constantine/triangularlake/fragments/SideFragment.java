package com.triangularlake.constantine.triangularlake.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.v4.app.Fragment;
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
import com.triangularlake.constantine.triangularlake.data.dto.ICommonDtoConstants;
import com.triangularlake.constantine.triangularlake.data.helpers.SQLiteHelper;
import com.triangularlake.constantine.triangularlake.data.pojo.Photo;
import com.triangularlake.constantine.triangularlake.data.pojo.PojosKt;
import com.triangularlake.constantine.triangularlake.data.pojo.Problem;
import com.triangularlake.constantine.triangularlake.data.pojo.Side;
import com.triangularlake.constantine.triangularlake.utils.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

public class SideFragment extends Fragment {
    private static final String TAG = SideFragment.class.getSimpleName();

    private int sideId;
    private int[] problemNumbers;

    private ImageView fragmentSideImageSide;
    private ExpandableListView fragmentSideExlistView;
    private ProblemsExpListAdapter problemsExpListAdapter;
    private LinearLayout fragmentSideLinearLayout;

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
        loadData();
        updateProblems();
    }

    private void initInputValues() {
        Log.d(TAG, "initInputValues() start");
        Bundle bundle = this.getArguments();
        sideId = bundle.getInt(ICommonDtoConstants.SIDE_ID, 0);
        problemNumbers = bundle.getIntArray(ICommonDtoConstants.PROBLEM_NUMBERS);
        Log.d(TAG, "initInputValues() done");
    }

    private void initXmlFields(View view) {
        Log.d(TAG, "initXmlFields() start");
//        fragmentSideImageSide = (ImageView) view.findViewById(R.id.fragment_side_image_side);
        fragmentSideExlistView = (ExpandableListView) view.findViewById(R.id.fragment_side_exlist_view);
//        fragmentSideLinearLayout = (LinearLayout) view.findViewById(R.id.fragment_side_linear_layout);
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

    private void loadData() {
        Log.d(TAG, "loadData() start");
        Bitmap resultBitmap = null;
        Bitmap backgroundBitmap = null;

        // разрешение экрана
        final DisplayUtils displayUtils = DisplayUtils.getInstance();
        displayUtils.updateDisplaySizes(getActivity());

        // загружаем Side
        final SQLiteDatabase db = new SQLiteHelper(getContext()).getReadableDatabase();
        final Cursor sidesCursor = db.rawQuery("select * from SIDES where _id = ?", new String[]{sideId + ""});
        sidesCursor.moveToFirst();
        Side side = null;
        while (!sidesCursor.isAfterLast()) {
            side = PojosKt.getNewSideFromCursor(sidesCursor);
            sidesCursor.moveToNext();
        }
        sidesCursor.close();

        if (side != null) {
            backgroundBitmap = BitmapFactory.decodeByteArray(side.getSidePhoto(), 0, side.getSidePhoto().length);
        }

        // добавление изображения камня
        Canvas canvas = null;
        if (backgroundBitmap != null) {
            resultBitmap = Bitmap.createBitmap(backgroundBitmap.getWidth(),
                    backgroundBitmap.getHeight(),
                    backgroundBitmap.getConfig());

            canvas = new Canvas(resultBitmap);
            canvas.drawBitmap(backgroundBitmap, new Matrix(), null);
        }

        // загружаем фотографии из БД по _id проблемы
        StringBuffer sb = new StringBuffer("select * from PHOTOS where problem_id in (")
                .append(getStringOfNumbers(problemNumbers))
                .append(")");
        final Cursor photoCursor = db.rawQuery(sb.toString(), new String[]{});
        List<Photo> photos = new ArrayList<>();
        photoCursor.moveToFirst();
        while (!photoCursor.isAfterLast()) {
            photos.add(PojosKt.getNewPhotoFromCursor(photoCursor));
            photoCursor.moveToNext();
        }
        photoCursor.close();

        // добавление фотографий линий на изображение
        if (canvas != null) {
            for (Photo item : photos) {
                final Bitmap bitmap = BitmapFactory.decodeByteArray(item.getPhotoData(), 0, item.getPhotoData().length);
                canvas.drawBitmap(bitmap, 0, 0, null);
            }
        }

        // TODO: Проблема. Если добавляем перед списком List с изображениями линий, то не работает scroll.
        // Поэтому временное решение - изображения добавлять в виде Header-а для списка трасс.

        // добавляем изображение на view
        ImageView backgroundImageView = new ImageView(getActivity());
        backgroundImageView.setImageBitmap(resultBitmap);

        // к существующему списку трасс, добавляем header в виде изображений трасс
        fragmentSideExlistView.addHeaderView(backgroundImageView);

        // к существующему списку трасс, добавляем footer
//        fragmentSideExlistView.addFooterView(getFooterView());

        // TODO: разобраться с открытием и закрытием БД
        if (db.isOpen()) {
            db.close();
        }
        Log.d(TAG, "loadData() done");
    }

    /**
     * Возвращает FooterView для списка трасс.
     * @return view.
     */
    private View getFooterView(){
        View view = getActivity().getLayoutInflater().inflate(R.layout.side_footer_layout, null);
        // TODO: Добавить значения.
        return view;
    }


    /**
     * Возвращает строку из номеров проблем для запроса в БД.
     *
     * @param problemNumbers - номера проблем.
     * @return строку номеров проблем.
     */
    private String getStringOfNumbers(int[] problemNumbers) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(problemNumbers[0]);
        for (int i = 1; i < problemNumbers.length; i++) {
            stringBuffer.append(", ");
            stringBuffer.append(problemNumbers[i]);
        }
        return stringBuffer.toString();
    }

    /**
     * Обновление списка проблем (под фотографией).
     */
    private void updateProblems() {
        Log.d(TAG, "updateProblems() start");
        problems = new ArrayList<>();
        List<Problem> listProblems = new ArrayList<>();

        // загружаем список проблем по _id Side.
        final SQLiteDatabase db = new SQLiteHelper(getContext()).getReadableDatabase();
        final Cursor problemCursor = db.rawQuery("select * from PROBLEMS where side_id = ?", new String[]{sideId + ""});
        problemCursor.moveToFirst();
        while (!problemCursor.isAfterLast()) {
            listProblems.add(PojosKt.getNewProblemFromCursor(problemCursor));
            problemCursor.moveToNext();
        }
        problemCursor.close();
        db.close();

        // Обновляем раскрывающийся список с трассами.
        if (!listProblems.isEmpty()) {
            List<Problem> child;
            for (Problem problem : listProblems) {
                child = new ArrayList<>();
                child.add(problem);
                problems.add(child);
            }
        }

        // добавление списка проблем в адаптер.
        problemsExpListAdapter = new ProblemsExpListAdapter(getActivity(), problems);
        fragmentSideExlistView.setAdapter(problemsExpListAdapter);
        Log.d(TAG, "updateProblems() done");
    }
}
