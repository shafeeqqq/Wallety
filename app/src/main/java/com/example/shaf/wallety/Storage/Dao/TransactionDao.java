package com.example.shaf.wallety.Storage.Dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.example.shaf.wallety.Model.Transaction;

import java.util.List;

@Dao
public interface TransactionDao {

    @Insert
    void insert(Transaction transaction);

    @Query("SELECT * from transactions_table ORDER BY unixTime DESC")
    LiveData<List<Transaction>> getAllCourses();

    @Delete
    void delete(Transaction transaction);

    @Update
    void update(Transaction transaction);

    @Query("DELETE FROM transactions_table")
    void deleteAll();

    @Query("UPDATE transactions_table SET item=:tItem, amount=:tAmount, month=:tMonth, year=:tYear , unixTime=:tUnixTime, type=:tType," +
            " categoryID=:t_categoryID, accountID=:t_accountID WHERE id=:tId")

    void updateTransaction(int tId, String tItem, double tAmount, int tMonth, int tYear, String tUnixTime, int tType,
                           int t_categoryID, int t_accountID);




    /**
     * create an Observer of the data in the onCreate() method of MainActivity
     * override the observer's onChanged() method.
     *
     * When the LiveData changes, the observer is notified and
     * onChanged() is executed. You will then update the cached data in the adapter, and the
     * adapter will update what the user sees.
     */
}
