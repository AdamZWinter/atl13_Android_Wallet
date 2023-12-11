package com.example.alt_13_android_wallet.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleTransactionBody implements ITransactionBody {
    private String bodyType;
    private String accountId;
    private int transactionId;
    private String recipientId;
    private double amount;
    private long uTime;
    private String extra;

    SimpleTransactionBody(String accountId, int transactionId, String recipientId, double amount, long uTime, String extra){
        this.bodyType = "SIMPLE";
        this.accountId = accountId;
        this.transactionId = transactionId;
        this.recipientId = recipientId;
        this.amount = amount;
        this.uTime = uTime;
        this.extra = extra;
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
//        return "{" +
//                "accountId='" + accountId + '\'' +
//                ", transactionId=" + transactionId +
//                ", recipientId='" + recipientId + '\'' +
//                ", amount=" + amount +
//                ", uTime=" + uTime +
//                ", extra='" + extra + '\'' +
//                '}';
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getuTime() {
        return uTime;
    }

    public void setuTime(long uTime) {
        this.uTime = uTime;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
