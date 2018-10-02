package com.example.shaf.wallety.Storage.Dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.shaf.wallety.Model.Account;

import java.util.List;

@Dao
public interface AccountDao {

    @Insert
    void insert(Account account);

    @Query("SELECT * from account_table ORDER BY accountID ASC")
    LiveData<List<Account>> getAllAccounts();

    @Delete
    void delete(Account account);

    @Update
    void update(Account account);

    @Query("DELETE FROM account_table")
    void deleteAll();

    @Query("UPDATE account_table SET accountName=:a_Name, accountBalance=:a_balance WHERE accountID=:a_ID")
    void updateTransaction(int a_ID, String a_Name, double a_balance);

    /**
     * create an Observer of the data in the onCreate() method of MainActivity
     * override the observer's onChanged() method.
     *
     * When the LiveData changes, the observer is notified and
     * onChanged() is executed. You will then update the cached data in the adapter, and the
     * adapter will update what the user sees.
     */
}
