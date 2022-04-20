package com.utd.texastechapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LockModesActivity extends AppCompatActivity {
    EditText URIValue;
    EditText emailValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        URIValue = findViewById(R.id.editTextURIValue);
        emailValue = findViewById(R.id.editTextEmail);
        setContentView(R.layout.activity_lock_modes);
    }

    public void onKeypadClick(View view) {
        Intent intent = new Intent(this, SetKeypadActivity.class);
        startActivity(intent);
    }

    public void onSetURIClick(View view) {
        ((Communication) this.getApplication()).SendMessage("Change URI Spotify");
        String response = ((Communication) this.getApplication()).ReceivedMessage();
        ((Communication) this.getApplication()).sendURI(URIValue.getText().toString());
    }

    public void onSetEmailClick(View view) {
        ((Communication) this.getApplication()).SendMessage("Change Email");
        String response = ((Communication) this.getApplication()).ReceivedMessage();
        ((Communication) this.getApplication()).sendEmail(emailValue.getText().toString());
    }
}