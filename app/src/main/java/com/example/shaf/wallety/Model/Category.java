package com.example.shaf.wallety.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "category_table")
public class Category {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "categoryID")
    private int categoryID;

    @ColumnInfo(name = "categoryName")
    private String categoryName;

    @ColumnInfo(name = "categoryColour")
    private String categoryColour;


    @Ignore
    public Category(String categoryName, String colour) {
        this.categoryName = categoryName;
        this.categoryColour = colour;

    }

    public Category(int categoryID, String categoryName, String categoryColour) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.categoryColour = categoryColour;

    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryColour() {
        return categoryColour;
    }

    @NonNull
    public int getCategoryID() {
        return categoryID;
    }
}
