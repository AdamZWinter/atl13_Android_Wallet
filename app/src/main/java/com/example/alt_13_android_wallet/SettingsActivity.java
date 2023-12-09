package com.example.alt_13_android_wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    Button buttonRotateKey;
    Button buttonResetAppData;
    TextView textViewPublicKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        buttonRotateKey = findViewById(R.id.buttonSettingsRotateKey);
        buttonResetAppData = findViewById(R.id.buttonSettingsResetAppData);
        buttonResetAppData.setOnClickListener(this::resetAppData);
        textViewPublicKey = findViewById(R.id.textViewCurrentPublicKey);

        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHAREDPREFS, MODE_PRIVATE);
        textViewPublicKey.setText(sharedPreferences.getString(MainActivity.PUBLIC_KEY_KEY, "error"));

    }

    public void resetAppData(View view){
        SharedPreferences sharedPreferencesTest = getSharedPreferences(MainActivity.SHAREDPREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesTest.edit();
        editor.clear().apply();
        finish();
    }
}