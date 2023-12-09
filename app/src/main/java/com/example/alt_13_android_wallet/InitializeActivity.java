package com.example.alt_13_android_wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.AccountsException;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.alt_13_android_wallet.models.Account;
import com.example.alt_13_android_wallet.utils.AccountsPoster;

import org.json.JSONException;
import org.json.JSONObject;

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

        //String url = "http://sdev372gcvm.topsecondhost.com/api/v1/transactions";
        String url = "http://192.168.0.23:8080/api/v1/accounts";

        Account account = new Account(email, "placeholderfromAndroid");
        AccountsPoster.postAccount(account, url, new AccountsPoster.JsonCallback() {
            @Override
            public void onSuccess(String jsonData) {
                Log.v("MyTag", "Account POST success, jsonData: " + jsonData);

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(jsonData);
                    Log.v("MyTag", "Posted Account Object:  " + jsonObject);
                    SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHAREDPREFS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong(MainActivity.ACCOUNT_ID_KEY, jsonObject.getLong("id"));
                    editor.putString(MainActivity.EMAIL_KEY, jsonObject.getString(MainActivity.EMAIL_KEY));
                    editor.putString(MainActivity.PUBLIC_KEY_KEY, jsonObject.getString(MainActivity.PUBLIC_KEY_KEY));
                    editor.putString(MainActivity.BALANCE_KEY, jsonObject.getString(MainActivity.BALANCE_KEY));
                    editor.apply();
                    editor.commit();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                finish();
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("MyTag", "Failed Account POST: " + errorMessage);
            }
        });

    }

}