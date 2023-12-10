package com.example.alt_13_android_wallet.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

/**
 * SimpleTransaction extends Transaction, which implement ITransaction
 * This is a basic transaction that transfers notes from one account to another
 */
public class SimpleTransaction extends Transaction{

    /**
     * Constructor
     * @param accountId  the email address of the sender
     * @param transactionId  Like a check number
     * @param recipientId  the email address of the recipient
     * @param amount  the amount to send
     * @param uTime  a time stamp set by the sender
     * @param extra  additional information to include
     */
    public SimpleTransaction(String accountId, int transactionId, String recipientId, double amount, long uTime, String extra) {
        super();
        Body body = new Body(accountId, transactionId, recipientId, amount, uTime, extra);
        ObjectMapper objectMapper = new ObjectMapper();
        String bodyString;
        try {
            bodyString = objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        super.setBodyString(bodyString);
        //super.setSignature(base64encodedSignature);
    }

    private class Body{
        @Getter
        @Setter
        private String accountId;
        @Getter
        @Setter
        private int transactionId;
        @Getter
        @Setter
        private String recipientId;
        @Getter
        @Setter
        private double amount;
        @Getter
        @Setter
        private long uTime;
        @Getter
        @Setter
        private String extra;

        Body(String accountId, int transactionId, String recipientId, double amount, long uTime, String extra){
            this.accountId = accountId;
            this.transactionId = transactionId;
            this.recipientId = recipientId;
            this.amount = amount;
            this.uTime = uTime;
            this.extra = extra;
        }

        @Override
        public String toString() {
            return "{" +
                    "accountId='" + accountId + '\'' +
                    ", transactionId=" + transactionId +
                    ", recipientId='" + recipientId + '\'' +
                    ", amount=" + amount +
                    ", uTime=" + uTime +
                    ", extra='" + extra + '\'' +
                    '}';
        }
    }

}
