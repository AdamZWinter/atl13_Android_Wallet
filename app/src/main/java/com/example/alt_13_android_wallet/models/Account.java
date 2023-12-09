package com.example.alt_13_android_wallet.models;

import android.content.SharedPreferences;

import com.example.alt_13_android_wallet.MainActivity;

import org.json.JSONStringer;

import lombok.*;

import java.util.Objects;


/**
 * Model for user data
 * @author Adam Winter
 * @version see version control
 */
@Data
public class Account implements IAccount{
    private long id;
    private String email;
    private String publicKey;
    private double balance;

    public Account(long id, String email, String publicKey, double balance) {
        this.id = id;
        this.email = email;
        this.publicKey = publicKey;
        this.balance = balance;
    }

    public Account() {
    }

    /**
     * Represents a user account
     * @param email an email address for the user
     */
    public Account(String email, String publicKey) {
        this.email = email;
        this.publicKey = publicKey;
        balance = 100.00;
    }

    public static Account fromSharedPreferences(SharedPreferences sharedPreferences){
        Account account = new Account();
        account.setId(sharedPreferences.getLong(MainActivity.ACCOUNT_ID_KEY, 0));
        account.setEmail(sharedPreferences.getString(MainActivity.EMAIL_KEY, "error"));
        //double??
        account.setPublicKey(sharedPreferences.getString(MainActivity.PUBLIC_KEY_KEY, "error"));
        return account;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        return this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getPublicKey(), getBalance());
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPublicKey() {
        return publicKey;
    }

    @Override
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void setBalance(double balance) {
        this.balance = balance;
    }
}