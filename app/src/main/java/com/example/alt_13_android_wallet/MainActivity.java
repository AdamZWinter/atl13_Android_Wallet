package com.example.alt_13_android_wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String SHAREDPREFS = "ANDROIDCLASS";
    public static final String EMAIL_KEY = "email";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        SharedPreferences sharedPreferences = getSharedPreferences(SHAREDPREFS, MODE_PRIVATE);
//        String email = sharedPreferences.getString(EMAIL_KEY, null);
//        if(email == null){
//            Log.v("MyTag", "OnCreate Email is null.");
//            Intent intent = new Intent(getApplicationContext(), InitializeActivity.class);
//            //if needed to pass values in the bundle
//            //otherwise not used
//            Bundle bundle = new Bundle();
//            startActivity(intent, bundle);
//        }else{
//            Log.v("MyTag", "OnCreate: " + email);
//        }
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
        }
        return true;
    }

    public void getAccountByEmail(){

    }
}