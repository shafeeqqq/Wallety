package com.example.shaf.wallety.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "category_table",
        indices = {@Index(value = {"categoryName"}, unique = true)})
public class Category {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "categoryID")
    private int categoryID;

    @NonNull
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



    public enum Category_init_data {
        FOOD        ("#FF5722"),
        GROCERIES   ("#2F5722"),
        TRANSPORT   ("#294823"),
        EDUCATION   ("#234322"),
        PHONE       ("#281f20"),
        ENTERTAINMENT       ("#2f4322"),
        HEALTH      ("#ac1020"),
        EXTRACURRICULAR    ("#29ed23"),
        GIFT        ("#8fe823"),
        RENT        ("#9f2823"),
        UTILITIES   ("#294ee3"),
        OTHER       ("#978293");

        private final String categoryColour;

        Category_init_data(String colour) {
            this.categoryColour = colour;
        }

        public String categoryColour() {
            return categoryColour;
        }
    }

}
