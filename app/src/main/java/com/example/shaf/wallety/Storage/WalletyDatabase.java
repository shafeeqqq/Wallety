package com.example.shaf.wallety.Storage;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.shaf.wallety.Model.Account;
import com.example.shaf.wallety.Model.Account.Account_init_data;
import com.example.shaf.wallety.Model.Category;
import com.example.shaf.wallety.Model.Category.Category_init_data;
import com.example.shaf.wallety.Model.Transaction;
import com.example.shaf.wallety.R;
import com.example.shaf.wallety.Storage.Dao.AccountDao;
import com.example.shaf.wallety.Storage.Dao.CategoryDao;
import com.example.shaf.wallety.Storage.Dao.TransactionDao;


@Database(entities = {Transaction.class, Account.class, Category.class}, version = 1)
public abstract class WalletyDatabase extends RoomDatabase {

    public abstract TransactionDao transactionDao();
    public abstract AccountDao accountDao();
    public abstract CategoryDao categoryDao();

    private static WalletyDatabase INSTANCE;

    private static RoomDatabase.Callback roomDbCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onCreate (@NonNull SupportSQLiteDatabase db){
                    super.onCreate(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    static WalletyDatabase getDatabase(final Context context) {

        if (INSTANCE == null) {
            synchronized (WalletyDatabase.class) {

                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WalletyDatabase.class, context.getString(R.string.sqlDbName))
                            .addCallback(roomDbCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private static final double ZERO_BALANCE = 0.00;
        private final CategoryDao categoryDao;
        private final AccountDao accountDao;

        PopulateDbAsync(WalletyDatabase db) {
            categoryDao = db.categoryDao();
            accountDao = db.accountDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // initialise category table
            for (Category_init_data item: Category_init_data.values())
                categoryDao.insert(new Category(capitaliseWord(item.toString()), item.categoryColour()));

            // initialise account table
            for (Account_init_data item: Account_init_data.values())
                accountDao.insert(new Account(capitaliseWord(item.toString()), ZERO_BALANCE));

            return null;
        }

        private String capitaliseWord(String word) {
            String lowercase = word.toLowerCase();
            if (lowercase.contains("_"))
                lowercase = lowercase.replace("_", " ");
            return lowercase.substring(0,1).toUpperCase() + lowercase.substring(1);
        }
    }


}
