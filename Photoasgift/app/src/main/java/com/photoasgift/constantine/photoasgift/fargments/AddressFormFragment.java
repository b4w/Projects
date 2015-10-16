package com.photoasgift.constantine.photoasgift.fargments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.photoasgift.constantine.photoasgift.R;

public class AddressFormFragment extends Fragment {
    private static final String TAG = AddressFormFragment.class.getSimpleName();

    private TextView name;
    private TextView surname;
    private TextView middleName;
    private TextView country;
    private TextView city;
    private TextView street;
    private TextView house;
    private TextView building;
    private TextView structure;
    private TextView index;
    private TextView from;

    public static AddressFormFragment newInstance() {
        AddressFormFragment fragment = new AddressFormFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.address_form_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initXmlFields();
    }

    private void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");
        name = (TextView)getActivity().findViewById(R.id.address_form_fragment_name);
        surname = (TextView)getActivity().findViewById(R.id.address_form_fragment_surname);
        middleName = (TextView)getActivity().findViewById(R.id.address_form_fragment_middle_name);
        country = (TextView)getActivity().findViewById(R.id.address_form_fragment_country);
        city = (TextView)getActivity().findViewById(R.id.address_form_fragment_city);
        street = (TextView)getActivity().findViewById(R.id.address_form_fragment_street);
        house = (TextView)getActivity().findViewById(R.id.address_form_fragment_house);
        building = (TextView)getActivity().findViewById(R.id.address_form_fragment_building);
        structure = (TextView)getActivity().findViewById(R.id.address_form_fragment_structure);
        index = (TextView)getActivity().findViewById(R.id.address_form_fragment_index);
        from = (TextView)getActivity().findViewById(R.id.address_form_fragment_from);
        Log.d(TAG, "initXmlFields() done");
    }
}
