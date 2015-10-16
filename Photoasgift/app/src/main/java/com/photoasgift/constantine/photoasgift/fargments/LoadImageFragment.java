package com.photoasgift.constantine.photoasgift.fargments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.photoasgift.constantine.photoasgift.R;

import java.io.FileNotFoundException;
import java.io.IOException;

public class LoadImageFragment extends Fragment {
    private final static String TAG = LoadImageFragment.class.getSimpleName();
    private final int GALLERY_REQUEST = 1;

    private ImageView orderLayoutImage;

    public static LoadImageFragment newInstance() {
        LoadImageFragment fragment = new LoadImageFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.load_image_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initXmlFields();
        initListeners();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap galleryPic = null;
        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == getActivity().RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        galleryPic = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    orderLayoutImage.setImageBitmap(galleryPic);
                }
        }
    }

    private void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");
        orderLayoutImage = (ImageView) getActivity().findViewById(R.id.order_layout_image);
        Log.d(TAG, "initXmlFields() done");
    }

    private void initListeners() {
        Log.d(TAG, "initListeners() start");
        orderLayoutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImageFromGallery();
            }
        });
        Log.d(TAG, "initListeners() done");
    }

    private void loadImageFromGallery() {
        Log.d(TAG, "loadImageFromGallery() start");
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
        Log.d(TAG, "loadImageFromGallery() done");
    }
}
