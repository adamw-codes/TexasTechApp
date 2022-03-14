package com.utd.texastechapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LockModesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_modes);
    }

    public void onKeypadClick(View view) {
        Intent intent = new Intent(this, SetKeypadActivity.class);
        startActivity(intent);
    }
}