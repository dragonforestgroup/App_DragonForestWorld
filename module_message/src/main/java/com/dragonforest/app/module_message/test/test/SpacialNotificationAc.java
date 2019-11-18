package com.dragonforest.app.module_message.test.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dragonforest.app.module_message.R;

public class SpacialNotificationAc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("SpacialNotificationAc", "SpacialNotificationAc#onCreate() ");
        setContentView(R.layout.activity_spacial_notification);
    }
}
