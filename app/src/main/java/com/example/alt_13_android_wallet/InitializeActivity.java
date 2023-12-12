package com.example.alt_13_android_wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.AccountsException;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.alt_13_android_wallet.models.Account;
import com.example.alt_13_android_wallet.utils.AccountsPoster;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.DSAKeyPairGenerator;
import java.security.interfaces.DSAParams;

public class InitializeActivity extends AppCompatActivity {

    EditText editTextNewAccountEmail;
    Button newAccountButton;
    Button importAccountButton;

    PublicKey publicKey;
    String publicKeyString;

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

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore");
            keyPairGenerator.initialize(new KeyGenParameterSpec.Builder(
                    MainActivity.KEYSTORE_ALIAS,
                    KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY)
                    .setDigests(KeyProperties.DIGEST_SHA256,
                            KeyProperties.DIGEST_SHA512)
                    .build());
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            //PrivateKey privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
            byte[] publicKeyBytes = publicKey.getEncoded();
            publicKeyString = Base64.encodeToString(publicKeyBytes, Base64.DEFAULT);
            Log.v("Crypto", "publicKeyString: " + publicKeyString);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }

        Account account = new Account(email, publicKeyString);
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
                    editor.putString(MainActivity.PUBLIC_KEY_KEY, publicKeyString);
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