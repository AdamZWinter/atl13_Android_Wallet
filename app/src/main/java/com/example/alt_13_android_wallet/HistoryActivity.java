package com.example.alt_13_android_wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alt_13_android_wallet.adapters.HistoryCustomAdapter;
import com.example.alt_13_android_wallet.models.Account;
import com.example.alt_13_android_wallet.models.DisplayTransaction;
import com.example.alt_13_android_wallet.utils.JsonFetcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        getTransactions();

    }

    public void getTransactions(){
        //String url = "http://sdev372gcvm.topsecondhost.com/api/v1/transactions";
        String url = "http://192.168.0.23:8080/api/v1/transactions";
        Log.v("MyTag", "URL:  " + url);
        JsonFetcher.fetchJsonData(url, new JsonFetcher.JsonCallback() {
            @Override
            public void onSuccess(String jsonData) {
                // Handle successful JSON response
                Log.d("MyTag", "JSON data: " + jsonData);
                // Update UI with jsonData
                JSONArray jsonArray = null;
                try {
                    Log.v("MyTag", "In the try block");
                    jsonArray = new JSONArray(jsonData);
                    Log.v("MyTag", "JSON Array index 0: " + jsonArray.get(0));

                } catch (JSONException e) {
                    //throw new RuntimeException(e);
                    Log.v("MyTag", "failed to make jsonObject");
                    Toast.makeText(getApplicationContext(), "Error: unable to GET account", Toast.LENGTH_LONG).show();
                }

                ArrayList<DisplayTransaction> transactionArrayList = new ArrayList<>();
                if(jsonArray != null){
                    Log.v("MyTag", "starting for loop");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            //Log.v("MyTag", "getting object");
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);


                            //Log.v("MyTag", "jsonObject: " + jsonObject);

                            //JSONObject body = new JSONObject(jsonObject.get("body").toString());
                            JSONObject body = jsonObject.getJSONObject("body");

                            //Log.v("MyTag", "body: " + body);
                            //Log.v("MyTag", "accountId: " + body.getString("accountId"));


                            DisplayTransaction transaction = new DisplayTransaction();
                            transaction.setAccountId(body.getString("accountId"));
                            transaction.setRecipientId(body.getString("recipientId"));
                            transaction.setAmount(body.getDouble("amount"));
                            transaction.setTransactionId(body.getInt("transactionId"));
                            transaction.setuTime(body.getLong("uTime"));
                            transaction.setExtra(body.getString("extra"));
                            Log.v("MyTag", "Transaction: " + transaction);
                            transactionArrayList.add(transaction);
                        } catch (JSONException e) {
                            //ignore this one
                        }
                    }
                }
                Log.v("MyTag", "ArrayList: " + transactionArrayList);
                listView = findViewById(R.id.listViewHistory);
                HistoryCustomAdapter historyCustomAdapter = new HistoryCustomAdapter(getApplicationContext(), transactionArrayList);
                listView.setAdapter(historyCustomAdapter);

            }

            @Override
            public void onError(String errorMessage) {
                // Handle error case
                Log.e("MyTag", "onError: " + errorMessage);
                // Display error message to the user
                Toast.makeText(getApplicationContext(), "onError: unable to GET account", Toast.LENGTH_LONG).show();
            }
        });
    }
}