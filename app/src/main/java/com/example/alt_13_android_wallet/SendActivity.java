package com.example.alt_13_android_wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alt_13_android_wallet.models.Account;
import com.example.alt_13_android_wallet.models.SimpleTransaction;
import com.example.alt_13_android_wallet.utils.TransactionPoster;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

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
        ObjectMapper mapper = new ObjectMapper();
        try {
            Log.v("SendActivity", mapper.writeValueAsString(simpleTransaction));
        } catch (JsonProcessingException e) {
            Log.v("SendActivity", "Not able to write object with mapper.");
        }
        String url = "http://192.168.0.23:8080/api/v1/transactions";
        TransactionPoster.postTransaction(simpleTransaction, url, new TransactionPoster.JsonCallback() {
            @Override
            public void onSuccess(String jsonData) {
                Log.v("SendActivity", "Transaction POST success, jsonData: " + jsonData);
                //Boolean success = Boolean.parseBoolean(jsonData.trim());
                if(Boolean.parseBoolean(jsonData.trim())){
                    Toast.makeText(getApplicationContext(), "Sent.", Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Error.  See Logs.", Toast.LENGTH_LONG).show();
                    Log.e("SendActivity", "Send response jsonData: " + jsonData);
                }

            }

            @Override
            public void onError(String errorMessage) {
                Log.e("SendActivity", "Failed Transaction POST: " + errorMessage);
            }
        });

    }
}