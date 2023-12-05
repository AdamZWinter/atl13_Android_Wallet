package com.example.alt_13_android_wallet.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class JsonFetcher {

    // Executor to perform network operations in a separate thread
    private static final Executor executor = Executors.newSingleThreadExecutor();

    // Handler to update UI from the background thread
    private static final Handler handler = new Handler(Looper.getMainLooper());

    // URL to fetch JSON data from
    //private static final String JSON_URL = "https://example.com/api/data";

    // Method to perform the GET request and handle the JSON response
    public static void fetchJsonData(String urlString, final JsonCallback callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();

                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }

                        // Parse JSON or perform other operations as needed
                        final String jsonData = stringBuilder.toString();

                        // Use the handler to update UI from the background thread
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onSuccess(jsonData);
                            }
                        });
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (Exception e) {
                    Log.e("JsonFetcher", "Error fetching JSON", e);

                    // Use the handler to update UI from the background thread
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError("Error fetching JSON");
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