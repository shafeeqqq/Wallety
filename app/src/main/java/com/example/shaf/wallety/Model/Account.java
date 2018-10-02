package com.example.shaf.wallety.Model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "account_table")
public class Account {


    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "accountID")
    private int accountID;


    @ColumnInfo(name = "accountName")
    private String accountName;

    @ColumnInfo(name = "accountBalance")
    private double accountBalance;


    @Ignore
    public Account(String accountName, double accountBalance) {
        this.accountName = accountName;
        this.accountBalance= accountBalance;
    }

    public Account(int accountID, String accountName, double accountBalance) {
        this.accountID = accountID;
        this.accountName = accountName;
        this.accountBalance= accountBalance;
    }



    public String getAccountName() {
        return accountName;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }

    @NonNull
    public int getAccountID() {
        return accountID;
    }
}
