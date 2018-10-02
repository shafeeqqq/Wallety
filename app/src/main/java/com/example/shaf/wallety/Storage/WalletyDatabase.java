package com.example.shaf.wallety.Storage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.shaf.wallety.Model.Account;
import com.example.shaf.wallety.Model.Category;
import com.example.shaf.wallety.Model.Transaction;
import com.example.shaf.wallety.Storage.Dao.AccountDao;
import com.example.shaf.wallety.Storage.Dao.CategoryDao;
import com.example.shaf.wallety.Storage.Dao.TransactionDao;


@Database(entities = {Transaction.class, Account.class, Category.class}, version = 1)
public abstract class WalletyDatabase extends RoomDatabase {

    public abstract TransactionDao transactionDao();
    public abstract AccountDao accountDao();
    public abstract CategoryDao categoryDao();

    private static WalletyDatabase INSTANCE;

    static WalletyDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WalletyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WalletyDatabase.class, "wallety_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
