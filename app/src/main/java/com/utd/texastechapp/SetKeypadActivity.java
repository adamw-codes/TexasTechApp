package com.utd.texastechapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

public class SetKeypadActivity extends AppCompatActivity {
    CheckBox expiryCheckBox;
    SeekBar expiryTimeSeekbar;
    TextView keypadValueText;
    boolean keypadEntered = false;
    boolean maxKeyLimit = false;
    String keypadValueFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_keypad);
        expiryCheckBox = findViewById(R.id.expiryCheckBox);
        expiryTimeSeekbar = findViewById(R.id.expirySeekBar);
        keypadValueText = findViewById(R.id.keypadValueText);
    }


    public void onExpiryCheckClick(View view) {
        if(!expiryCheckBox.isChecked()){
            expiryTimeSeekbar.setVisibility(View.VISIBLE);
        }
        else{
            expiryTimeSeekbar.setVisibility(View.INVISIBLE);
        }
    }

    public void onNumberClick(View view) {
        if(!maxKeyLimit) {
            Button pressedButton = (Button) view;
            String digit = (String) pressedButton.getText();
            String keypadValue = "";
            if (!keypadEntered) {
                keypadEntered = true;
            } else {
                keypadValue = (String) keypadValueText.getText();
            }
            keypadValue = keypadValue + digit;
            keypadValueText.setText(keypadValue);
            if (keypadValue.length() == 4){
                maxKeyLimit = true;
            }
        }
    }

    public void onSaveClick(View view) {
        keypadValueFinal = (String) keypadValueText.getText();
        ((Communication) this.getApplicationContext()).SendMessage("Set the Keypad");
        ((Communication) this.getApplicationContext()).ReceivedMessage();
        ((Communication) this.getApplicationContext()).updateKeypad(keypadValueFinal);
        Intent intent = new Intent(this, LockModesActivity.class);
        startActivity(intent);
    }

    public void onClearClick(View view) {
        keypadEntered = false;
        maxKeyLimit = false;
        keypadValueText.setText("Enter a 6-digit pin");
    }
}