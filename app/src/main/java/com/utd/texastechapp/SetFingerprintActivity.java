package com.utd.texastechapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class SetFingerprintActivity extends AppCompatActivity {
    ArrayList<String> userList = new ArrayList<String>();
    CheckBox topCheck;
    CheckBox bottomCheck;
    LinearLayout popUpWindow;
    Communication comm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_fingerprint);
        topCheck = findViewById(R.id.fingerprintTopCheckBox);
        bottomCheck = findViewById(R.id.fingerprintBottomCheckBox);
        popUpWindow = findViewById(R.id.popUpWindowFP);
    }

    private void checkFingerprints() {
        new Thread(new Runnable() {
            @Override
            public void run() {
               while(true){
                   String response = getResponse();
                   if (response.equals("RGF1")) {
                       topCheck.toggle();
                       sendResponse("Fingerprint 1");
                   } else if (response.equals("RGF2")) {
                       bottomCheck.toggle();
                       sendResponse("Fingerprint 2");
                       response = getResponse();
                       break;
                   } else {
                       if (topCheck.isChecked()) {
                           topCheck.toggle();
                       }
                   }
               }
               checkCheckBoxes();
            }
        }).start();
    }

    private String getResponse() {
        return ((Communication) this.getApplication()).ReceivedMessage();
    }

    private void sendResponse(String response){
        ((Communication) this.getApplication()).SendMessage(response);
    }


    public void checkCheckBoxes() {
        Intent intent = new Intent(this, SetFacialRecognitionActivity.class);
        intent.putExtra("userList", userList);
        startActivity(intent);
    }

    public void onPopUpDismissClick(View view) {
        popUpWindow.setVisibility(View.INVISIBLE);
        ((Communication) this.getApplication()).SendMessage("Begin Registering");
        checkFingerprints();
    }
}