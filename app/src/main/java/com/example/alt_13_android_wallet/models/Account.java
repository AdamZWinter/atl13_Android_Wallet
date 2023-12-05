package com.example.alt_13_android_wallet.models;

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

    @Setter
    @Getter
    private int id;

    @Setter
    @Getter
    private String email;

    @Setter
    @Getter
    private String publicKey;

    @Setter
    @Getter
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
}