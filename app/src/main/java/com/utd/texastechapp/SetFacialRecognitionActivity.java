package com.utd.texastechapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.ArrayList;

public class SetFacialRecognitionActivity extends AppCompatActivity {
    ArrayList<String> userList = new ArrayList<String>();
    EditText userName;
    ImageView cameraImage;
    LinearLayout popUpWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_facial_recognition);
        userName = findViewById(R.id.editTextUserName);
        cameraImage = findViewById(R.id.cameraView);
        popUpWindow = findViewById(R.id.popUpWindowFP);
    }


    public void onSaveUserClick(View view) {
        String savedUser = userName.getText().toString();
        if(!savedUser.equals("")) {
            ((Communication) this.getApplication()).sendUserName(savedUser);
            String response = ((Communication) this.getApplication()).ReceivedMessage();
            ((Communication) this.getApplication()).SendMessage("Registering Done");
            Intent intent = new Intent(this, UserListActivity.class);
            intent.putExtra("userList", userList);
            startActivity(intent);
        }
    }

    public void onPopUpDismissClick(View view) throws IOException {
        popUpWindow.setVisibility(View.INVISIBLE);
        ((Communication) this.getApplication()).SendMessage("Facial Recognition Start");
        startFacialRecognition();
    }

    private void startFacialRecognition() throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String response = getResponse();
                    Bitmap imageIn = getImageResponse();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            cameraImage.setImageBitmap(imageIn);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Bitmap getImageResponse() throws IOException {
        return ((Communication) this.getApplication()).imageReceive();
    }

    private String getResponse() {
        return ((Communication) this.getApplication()).ReceivedMessage();
    }

    public void onRetryClick(View view) throws IOException {
        ((Communication) this.getApplication()).SendMessage("Face Denied");
        startFacialRecognition();
    }
}