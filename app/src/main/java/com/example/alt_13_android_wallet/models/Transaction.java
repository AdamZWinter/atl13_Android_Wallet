package com.example.alt_13_android_wallet.models;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Transaction implements ITransaction
 * This will be the parent class for all transactions
 * This format will allow for many different types of transactions
 * All the details of the transaction, other than blockId and signature
 * will be contained within the body
 */
public class Transaction implements ITransaction{

    private String transactionType;
    private ITransactionBody body;
    private int blockId;
    private String hashAlgorithm;
    private String signature;
    private String pki;


    /**
     * Constructor:  The blockId will be added
     * after the transaction has been added to the chain
     * @param body this should be a JSON formated String will all the details
     * @param signature the signature of the sender on the transaction
     */
    public Transaction(ITransactionBody body, String signature) {
        this.body = body;
        this.signature = signature;
        this.blockId = 0;
    }

    /**
     * NoArgs constructor
     */
    public Transaction() {
        this.hashAlgorithm = "SHA-256";
        this.pki = "TBD";
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionType='" + transactionType + '\'' +
                ", body=" + body +
                ", blockId=" + blockId +
                ", hashAlgorithm='" + hashAlgorithm + '\'' +
                ", signature='" + signature + '\'' +
                ", pki='" + pki + '\'' +
                '}';
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public byte[] getBodyHash() {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(this.hashAlgorithm);
            messageDigest.update(body.toString().getBytes(StandardCharsets.UTF_8));
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String getBodyHashString(){
        byte[] bytes = this.getBodyHash();
        Base64.Encoder base64encdoer = Base64.getEncoder();
        return base64encdoer.encodeToString(bytes);
    }

    @Override
    public int getBlockId() {
        return blockId;
    }

    @Override
    public void setBody(ITransactionBody body) {
        this.body = body;
    }

    @Override
    public ITransactionBody getBody() {
        return this.body;
    }

    @Override
    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }

    public String getHashAlgorithm() {
        return hashAlgorithm;
    }

    public void setHashType(String hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }

    @Override
    public String getSignature() {
        return signature;
    }

    @Override
    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPki() {
        return pki;
    }

    public void setPki(String pki) {
        this.pki = pki;
    }
}
