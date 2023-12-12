package com.example.alt_13_android_wallet.models;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class DisplayTransaction{

    private String accountId;
    private int transactionId;
    private String recipientId;
    private double amount;
    private long uTime;
    private String extra;
    private String signature;

    /**
     * Constructor
     * @param accountId  the email address of the sender
     * @param transactionId  Like a check number
     * @param recipientId  the email address of the recipient
     * @param amount  the amount to send
     * @param uTime  a time stamp set by the sender
     * @param extra  additional information to include
     */
    public DisplayTransaction(String accountId, int transactionId, String recipientId, double amount, long uTime, String extra, String signature) {
        this.accountId = accountId;
        this.transactionId = transactionId;
        this.recipientId = recipientId;
        this.amount = amount;
        this.uTime = uTime;
        this.extra = extra;
        this.signature = signature;
    }

    public DisplayTransaction() {
        //no args constructor
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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignatureAbbreviated(){
        if(signature.length() > 48){
            String start = signature.substring(0, 16);
            String end = signature.substring(signature.length()-16);
            return start + "....." + end;
        }else{
            return signature;
        }
    }

    @Override
    public String toString() {
        return "DisplayTransaction{" +
                "accountId='" + accountId + '\'' +
                ", transactionId=" + transactionId +
                ", recipientId='" + recipientId + '\'' +
                ", amount=" + amount +
                ", uTime=" + uTime +
                ", extra='" + extra + '\'' +
                ", signature='" + getSignatureAbbreviated() + '\'' +
                '}';
    }
}
