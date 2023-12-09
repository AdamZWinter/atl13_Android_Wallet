package com.example.alt_13_android_wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.alt_13_android_wallet.models.Account;

public class SendActivity extends AppCompatActivity {

    Account thisAccount;
    EditText editTextRecipient;
    EditText editTextAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        thisAccount = Account.fromSharedPreferences(getSharedPreferences("ANDROIDCLASS", MODE_PRIVATE));
        editTextRecipient = findViewById(R.id.editTextSendRecipient);
        editTextAmount = findViewById(R.id.editTextSendAmount);



    }
}