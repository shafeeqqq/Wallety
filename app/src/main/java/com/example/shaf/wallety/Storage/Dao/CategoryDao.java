package com.example.shaf.wallety.Storage.Dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.shaf.wallety.Model.Category;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert
    void insert(Category category);

    @Query("SELECT * from category_table ORDER BY categoryID ASC")
    LiveData<List<Category>> getAllCategories();

    @Delete
    void delete(Category category);

    @Update
    void update(Category category);

    @Query("DELETE FROM category_table")
    void deleteAll();

    @Query("UPDATE category_table SET categoryName=:c_Name, categoryColour=:c_Colour WHERE categoryID=:c_ID")
    void updateCategory(int c_ID, String c_Name, String c_Colour);

    @Query("SELECT categoryID from category_table WHERE categoryName=:categoryName")
    int getCategoryID(String categoryName);

    @Query("SELECT categoryName from category_table WHERE categoryID=:categoryID")
    String getCategoryName(int categoryID);


}
