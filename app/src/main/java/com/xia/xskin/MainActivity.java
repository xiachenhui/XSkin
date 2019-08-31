package com.xia.xskin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xia.xskin.manager.SkinManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SkinManager.init(getApplication());
    }
}
