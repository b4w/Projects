package com.triangularlake.constantine.triangularlake.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.triangularlake.constantine.triangularlake.R;

public class InformationFragment extends Fragment {
    private static final String TAG = InformationFragment.class.getSimpleName();
    private static final String ARG_SECTION_NUMBER = "section_number";

    private int sectionNumber;

    private TextView fragmentInformationTextView;

    public static InformationFragment newInstance(int sectionNumber) {
        InformationFragment fragment = new InformationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public InformationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInputValues();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        initXmlFields(view);
        return view;
    }

    private void initInputValues() {
        Log.d(TAG, "initInputValues() start");
        Bundle bundle = getArguments();
        sectionNumber = bundle.getInt(ARG_SECTION_NUMBER);
        Log.d(TAG, "initInputValues() done");
    }

    private void initXmlFields(View view) {
        Log.d(TAG, "initXmlFields() start");
        fragmentInformationTextView = (TextView) view.findViewById(R.id.fragment_information_text_view);
        fragmentInformationTextView.setText("Position fragment - " + sectionNumber);
        Log.d(TAG, "initXmlFields() done");
    }
}
