package com.example.alt_13_android_wallet.models;

public interface ITransaction {
    void setBlockId(int blockId);
    int getBlockId();

    void setBody(String body);
    String getBody();

    void setSignature(String signature);
    String getSignature();

    //long getUTime();
}
