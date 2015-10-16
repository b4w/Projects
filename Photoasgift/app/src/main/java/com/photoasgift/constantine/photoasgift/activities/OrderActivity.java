package com.photoasgift.constantine.photoasgift.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.photoasgift.constantine.photoasgift.R;
import com.photoasgift.constantine.photoasgift.fargments.AddressFormFragment;
import com.photoasgift.constantine.photoasgift.fargments.LoadImageFragment;

public class OrderActivity extends Activity {
    private final static String TAG = OrderActivity.class.getSimpleName();

    private FrameLayout imageContainer;
    private Button sendButton;
    private Button cancelButton;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_layout);

        initXmlFields();
        initListeners();
        initFragments();
    }

    private void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");
        imageContainer = (FrameLayout) findViewById(R.id.order_layout_image_container);
        sendButton = (Button) findViewById(R.id.order_layout_send_button);
        cancelButton = (Button) findViewById(R.id.order_layout_cancel_button);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.order_layout_coordinator_layout);
        Log.d(TAG, "initXmlFields() done");
    }

    private void initListeners() {
        Log.d(TAG, "initListeners() start");
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendOrderEmail();
//                Snackbar snackbar = Snackbar.make(coordinatorLayout, "Hello!", Snackbar.LENGTH_LONG);
//                snackbar.show();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Log.d(TAG, "initListeners() done");
    }

    private void initFragments() {
        Log.d(TAG, "initFragments() start");
        final LoadImageFragment loadImageFragment = LoadImageFragment.newInstance();
        final AddressFormFragment addressFormFragment = AddressFormFragment.newInstance();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.order_layout_image_container, loadImageFragment)
                .replace(R.id.order_layout_address_container, addressFormFragment)
                .commit();
        Log.d(TAG, "initFragments() done");
    }

    // TODO: тестовая отправка email сообщений. В дальнейшем заменить на сервер.
    private void sendOrderEmail() {
        Log.d(TAG, "sendOrderEmail() start");
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plan/text");
        // Кому
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String [] {"dzirtdurden@gmail.com"});
        // Тема
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, new String [] {"test"});
        // Тело сообщения
        emailIntent.putExtra(Intent.EXTRA_TEXT, new String [] {"test TExt message"});
        startActivity(Intent.createChooser(emailIntent, "Sending email ..."));
        Log.d(TAG, "sendOrderEmail() done");
    }
}
