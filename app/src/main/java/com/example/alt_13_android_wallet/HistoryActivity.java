package com.example.alt_13_android_wallet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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
    ArrayList<DisplayTransaction> transactionArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listView = findViewById(R.id.listViewHistory);

        transactionArrayList = new ArrayList<>();
        getTransactions();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showTransactionDialog(position);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showTransactionDialog(position);
            }
        });

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

    private void showTransactionDialog(int position){
        DisplayTransaction transaction = transactionArrayList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
        builder.setTitle("Transaction Details");
        TextView textViewTransactionDetails = new TextView(HistoryActivity.this);

        String transactionDetails = "Sender: \t" + transaction.getAccountId() + "\n" +
                "Recipient: \t" + transaction.getRecipientId() + "\n" +
                "Amount: \t ♪" + transaction.getAmount() + "\n" +
                "Transaction ID: \t" + transaction.getTransactionId() + "\n" +
                "Unix Time: \t" + transaction.getuTime() + "\n" +
                "Additional Info: " + transaction.getExtra() + "\n";

        textViewTransactionDetails.setText(transactionDetails);
        textViewTransactionDetails.setPadding(40, 10, 10, 10);
        builder.setView(textViewTransactionDetails);
//        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String text = userInput.getText().toString();
//                colors.add(text);
//                //Toast.makeText(getApplicationContext(), "This is positive response." + text, Toast.LENGTH_LONG).show();
//            }
//        });
//        builder.setNegativeButton("Remove", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String text = userInput.getText().toString();
//                colors.remove(text);
//                //Toast.makeText(getApplicationContext(), "This is negative response.", Toast.LENGTH_LONG).show();
//            }
//        });
        builder.setNeutralButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }
}