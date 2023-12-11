package com.example.alt_13_android_wallet.models;

public interface ITransactionBody {
    public String getBodyType();
    public void setBodyType(String bodyType);

    public String getAccountId();

    public void setAccountId(String accountId);

    public int getTransactionId();

    public void setTransactionId(int transactionId);

    public String getRecipientId();

    public void setRecipientId(String recipientId);


    public long getuTime();

    public void setuTime(long uTime);

    public String getExtra();

    public void setExtra(String extra);
}
