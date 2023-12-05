package com.example.alt_13_android_wallet.models;

/**
 * The interface for an account
 */
public interface IAccount {
    void setId(int id);
    int getId();
    void setEmail(String email);
    String getEmail();
    void setPublicKey(String publicKey);

    /**
     * Get the public key of the account
     * @return String a base64encoded public key
     */
    String getPublicKey();

    void setBalance(double balance);
    double getBalance();
}