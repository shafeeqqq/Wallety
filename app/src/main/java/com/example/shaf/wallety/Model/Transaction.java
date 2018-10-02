package com.example.shaf.wallety.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "transactions_table")
//        , foreignKeys = {@ForeignKey(entity = Category.class, parentColumns = "categoryID", childColumns = "categoryID"),
//                @ForeignKey(entity = Account.class, parentColumns = "accountID", childColumns = "accountID")
//        })

public class Transaction {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "item")
    private String item;

    @ColumnInfo(name = "amount")
    private double amount;

    @ColumnInfo(name = "month")
    private int month;

    @ColumnInfo(name = "year")
    private int year;

    @ColumnInfo(name = "unixTime")
    private String unixTime;

    @ColumnInfo(name = "type")      //income or expense
    private int type;

    @ColumnInfo(name = "categoryID")
    private int categoryID;

    @ColumnInfo(name = "accountID")
    private int accountID;


    @Ignore
    public Transaction(String item, double amount, int month, int year, String unixTime, int type, int categoryID, int accountID) {
        this.item = item;
        this.amount = amount;
        this.month = month;
        this.year = year;
        this.unixTime = unixTime;
        this.type = type;
        this.categoryID = categoryID;
        this.accountID = accountID;
    }


    public Transaction(@NonNull int id, String item, double amount, int month, int year, String unixTime, int type, int categoryID, int accountID) {
        this.id = id;
        this.item = item;
        this.amount = amount;
        this.month = month;
        this.year = year;
        this.unixTime = unixTime;
        this.type = type;
        this.categoryID = categoryID;
        this.accountID = accountID;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public String getItem() {
        return item;
    }

    public double getAmount() {
        return amount;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getUnixTime() {
        return unixTime;
    }

    public int getType() {
        return type;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public int getAccountID() {
        return accountID;
    }
}
