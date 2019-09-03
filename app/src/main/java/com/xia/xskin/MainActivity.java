package com.xia.xskin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xia.xskin.manager.SkinManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SkinManager.init(getApplication());
    }

    public void turn(View view) {
        startActivity(new Intent(this,SkinActivity.class));
    }
}
