package com.example.alt_13_android_wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alt_13_android_wallet.models.Account;
import com.example.alt_13_android_wallet.models.IAccount;

import java.util.Locale;
import com.example.alt_13_android_wallet.utils.JsonFetcher;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static final String SHAREDPREFS = "ANDROIDCLASS";
    public static final String EMAIL_KEY = "email";
    public static final Locale locale = Locale.US;
    private Account account;
    private TextView textViewBalance;
    private Button buttonSend;
    private Button buttonReceive;
    private Button buttonGift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewBalance = findViewById(R.id.textViewMainBalanceAmount);
        buttonSend = findViewById(R.id.buttonMainSend);
        buttonReceive = findViewById(R.id.buttonMainReceive);
        buttonGift = findViewById(R.id.buttonMainGift);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(SHAREDPREFS, MODE_PRIVATE);
        String email = sharedPreferences.getString(EMAIL_KEY, null);
        if(email == null || email == ""){
            Log.v("MyTag", "OnResume Email is null or empty.");
            Intent intent = new Intent(getApplicationContext(), InitializeActivity.class);
            //if needed to pass values in the bundle
            //otherwise not used
            Bundle bundle = new Bundle();
            startActivity(intent, bundle);
        }else{
            Log.v("MyTag", "OnResume: " + email);
            getAccountByEmail(email);
            Log.v("MyTag", "Account result: " + account.toString());
            //textViewBalance.setText(Double.toString(account.getBalance()));
            textViewBalance.setText(String.format(locale, "%.2f", account.getBalance()));
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int itemID = item.getItemId();
        if(itemID == R.id.menuItemSettings){
            Toast.makeText(getApplicationContext(), "Settings button is clicked", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        }else if(itemID == R.id.menuItemHistory){
            Toast.makeText(getApplicationContext(), "History button is clicked", Toast.LENGTH_LONG).show();
        }else if(itemID == R.id.menuItemRefresh){
            getAccountByEmail(account.getEmail());
        }
        return true;
    }

    public Account getAccountByEmail(String email){
        String url = "http://sdev372gcvm.topsecondhost.com/api/v1/accounts/"+email;
        this.account = new Account(0, "error", "error", 0.0);
        JsonFetcher.fetchJsonData(url, new JsonFetcher.JsonCallback() {
            @Override
            public void onSuccess(String jsonData) {
                // Handle successful JSON response
                Log.d("MyTag", "JSON data: " + jsonData);
                // Update UI with jsonData
                try {
                    Log.v("MyTag", "In the try block");
                    JSONObject jsonObject = new JSONObject(jsonData.substring(7));
                    Log.v("MyTag", "JSON object: " + jsonObject);
                    account.setId(jsonObject.getInt("id"));
                    account.setEmail(email);
                    account.setPublicKey(jsonObject.getString("publicKey"));
                    account.setBalance(jsonObject.getDouble("balance"));
                    textViewBalance.setText("♪"+Double.toString(account.getBalance()));
                } catch (JSONException e) {
                    //throw new RuntimeException(e);
                    Log.v("MyTag", "failed to make jsonObject");
                    Toast.makeText(getApplicationContext(), "Error: unable to GET account", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(String errorMessage) {
                // Handle error case
                Log.e("MyTag", "Error: " + errorMessage);
                // Display error message to the user
            }
        });

        return account;
    }
}