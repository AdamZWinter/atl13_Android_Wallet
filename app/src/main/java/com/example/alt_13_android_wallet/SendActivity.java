package com.example.alt_13_android_wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.alt_13_android_wallet.models.Account;
import com.example.alt_13_android_wallet.models.DisplayTransaction;
import com.example.alt_13_android_wallet.models.SimpleTransaction;

import java.time.Instant;

public class SendActivity extends AppCompatActivity {

    Account thisAccount;
    EditText editTextRecipient;
    EditText editTextAmount;

    Button buttonSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        thisAccount = Account.fromSharedPreferences(getSharedPreferences("ANDROIDCLASS", MODE_PRIVATE));
        editTextRecipient = findViewById(R.id.editTextSendRecipient);
        editTextAmount = findViewById(R.id.editTextSendAmount);
        buttonSend = findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(this::postTransaction);

    }

    private void postTransaction(View view){
        String recipientEmail = editTextRecipient.getText().toString();
        Double amount = Double.parseDouble(editTextAmount.getText().toString());
        Log.v("SendActivity", "recipient: " + recipientEmail + "  amount: " + amount);
        SimpleTransaction simpleTransaction = new SimpleTransaction(
                this.thisAccount.getEmail(),
                1,                                  //ToDo: Implement this
                recipientEmail,
                amount,
                Instant.now().getEpochSecond(),
                "jsonEncodedStringExtra"
        );
        String hash = simpleTransaction.getBodyHashString();    //Todo: Do not use this
        simpleTransaction.setSignature(hash);                   //Todo:  Put actual signature here
        Log.v("SendActivity", "SimpleTransaction: " + simpleTransaction);


    }
}