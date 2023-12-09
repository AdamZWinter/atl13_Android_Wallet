package com.example.alt_13_android_wallet.models;

import org.json.JSONStringer;

import lombok.*;

import java.util.Objects;


/**
 * Model for user data
 * @author Adam Winter
 * @version see version control
 */
@Data
@NoArgsConstructor
public class Account implements IAccount{
    private int id;
    private String email;
    private String publicKey;
    private double balance;

    public Account(int id, String email, String publicKey, double balance) {
        this.id = id;
        this.email = email;
        this.publicKey = publicKey;
        this.balance = balance;
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
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
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