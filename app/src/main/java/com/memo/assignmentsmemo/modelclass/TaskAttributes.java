package com.memo.assignmentsmemo.modelclass;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TaskAttributes implements Serializable {

    String name;
    String country;
    String currency;
    String email;
    String fBaseId;
    String timestamp;
    String orderID;
    String txn_id;
    String taskStatus;
    String remarks;
    int amount,wordCount,invoiceAmt;

    public TaskAttributes(){}

    public TaskAttributes(String name, String country, String currency,
                          String email, String fBaseId, String timestamp,
                          String orderID, String txn_id, String taskStatus, String remarks,
                          int amount, int wordCount, int invoiceAmt) {
        this.name = name;
        this.country = country;
        this.currency = currency;
        this.email = email;
        this.fBaseId = fBaseId;
        this.timestamp = timestamp;
        this.orderID = orderID;
        this.txn_id = txn_id;
        this.taskStatus = taskStatus;
        this.remarks = remarks;
        this.amount = amount;
        this.wordCount = wordCount;
        this.invoiceAmt = invoiceAmt;
    }

    public TaskAttributes(String name, String country, String currency, String email,
                          String fBaseId, String timestamp, String orderID,
                          String taskStatus, String remarks, int amount, int wordCount, int invoiceAmt) {
        this.name = name;
        this.country = country;
        this.currency = currency;
        this.email = email;
        this.fBaseId = fBaseId;
        this.timestamp = timestamp;
        this.orderID = orderID;
        this.taskStatus = taskStatus;
        this.remarks = remarks;
        this.amount = amount;
        this.wordCount = wordCount;
        this.invoiceAmt = invoiceAmt;
    }

    public TaskAttributes(String name, String country, String currency, String taskStatus,
                          String orderID, int amount, int wordCount,
                          int invoiceAmt, String email, String timestamp, String fBaseId
    )
    {
        this.name = name;
        this.country = country;
        this.currency = currency;
        this.orderID = orderID;
        this.amount = amount;
        this.wordCount = wordCount;
        this.invoiceAmt = invoiceAmt;
        this.email = email;
        this.timestamp = timestamp;
        this.fBaseId = fBaseId;
        this.taskStatus = taskStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCurrency() {
        return currency;
    }

    public String getOrderID() {
        return orderID;
    }

    public int getAmount() {
        return amount;
    }

    public int getWordCount() {
        return wordCount;
    }

    public int getInvoiceAmt() {
        return invoiceAmt;
    }

    public String getEmail() {
        return email;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getfBaseId() {
        return fBaseId;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

}
