package com.example.alt_13_android_wallet.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.alt_13_android_wallet.models.Account;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AccountsPoster {

    // Executor to perform network operations in a separate thread
    private static final Executor executor = Executors.newSingleThreadExecutor();

    // Handler to update UI from the background thread
    private static final Handler handler = new Handler(Looper.getMainLooper());

    // Method to perform the POST request and handle the JSON response
    public static void postAccount(Account account, String urlString, final JsonCallback callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    ObjectMapper mapper = new ObjectMapper();
                    //Converting the Object to JSONString
                    String postData = mapper.writeValueAsString(account);
                    Log.v("MyTag", "postData: " + postData);


                    //JSONObject jsonObject = new JSONObject(account);
                    //String postData = jsonObject.toString();

                    try {
                        // Set up the connection for a POST request
                        urlConnection.setRequestMethod("POST");
                        urlConnection.setDoOutput(true);
                        urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

                        // Write the data to the connection's output stream
                        OutputStream outputStream = urlConnection.getOutputStream();
                        outputStream.write(postData.getBytes());
                        outputStream.flush();
                        outputStream.close();

                        // Check the HTTP response code
                        int responseCode = urlConnection.getResponseCode();

                        if (responseCode == HttpURLConnection.HTTP_CREATED) {
                            // Read the JSON response
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                            StringBuilder stringBuilder = new StringBuilder();

                            String line;
                            while ((line = bufferedReader.readLine()) != null) {
                                stringBuilder.append(line).append("\n");
                            }
                            bufferedReader.close();

                            final String jsonResponse = stringBuilder.toString();
                            Log.v("MyTag", "jsonResponse: " + jsonResponse);


                            // Notify the success with JSON response on the main/UI thread
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onSuccess(jsonResponse);
                                }
                            });
                        } else {
                            // Notify the error on the main/UI thread
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onError("Error in POST request, HTTP response code: " + responseCode);
                                }
                            });
                        }//end else
                    } finally {
                        Log.v("MyTag", "Disconnecting");
                        urlConnection.disconnect();
                    }
                } catch (Exception e) {
                    Log.e("MyTag", "Error posting Account", e);

                    // Use the handler to update UI from the background thread
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError("Error posting Account");
                        }
                    });
                }
            }
        });
    }

    // Callback interface to handle success and error cases
    public interface JsonCallback {
        void onSuccess(String jsonData);

        void onError(String errorMessage);
    }
}
