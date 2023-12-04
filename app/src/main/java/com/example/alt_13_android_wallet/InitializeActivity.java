package com.example.alt_13_android_wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InitializeActivity extends AppCompatActivity {

    EditText editTextNewAccountEmail;
    Button newAccountButton;
    Button importAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize);

        editTextNewAccountEmail = findViewById(R.id.editTextNewAccountEmail);
        newAccountButton = findViewById(R.id.buttonNewAccount);
        importAccountButton = findViewById(R.id.buttonImportAccount);

        newAccountButton.setOnClickListener(this::initializeNewAccount);
    }

    private void initializeNewAccount(View view) {
        String email = editTextNewAccountEmail.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHAREDPREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MainActivity.EMAIL_KEY, email);
        editor.apply();
        editor.commit();
        finish();
    }

}