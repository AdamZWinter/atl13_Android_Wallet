package com.example.alt_13_android_wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    Button buttonRotateKey;
    Button buttonResetAppData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        buttonRotateKey = findViewById(R.id.buttonSettingsRotateKey);
        buttonResetAppData = findViewById(R.id.buttonSettingsResetAppData);
        buttonResetAppData.setOnClickListener(this::resetAppData);

    }

    public void resetAppData(View view){
        SharedPreferences sharedPreferencesTest = getSharedPreferences(MainActivity.SHAREDPREFS, MODE_PRIVATE);      //Testing only
        SharedPreferences.Editor editor = sharedPreferencesTest.edit();
        editor.clear().apply();
        finish();
    }
}