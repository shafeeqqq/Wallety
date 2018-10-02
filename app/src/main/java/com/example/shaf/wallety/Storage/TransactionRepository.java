package com.example.shaf.wallety.Storage;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.shaf.wallety.Model.Transaction;
import com.example.shaf.wallety.Storage.Dao.TransactionDao;

import java.util.List;

public class TransactionRepository {

    private TransactionDao mTransactionDao;
    private LiveData<List<Transaction>> mAllTransactions;

    public TransactionRepository(Application application) {
        WalletyDatabase db = WalletyDatabase.getDatabase(application);
        mTransactionDao = db.transactionDao();
        mAllTransactions = mTransactionDao.getAllCourses();
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return mAllTransactions;
    }

    public void insert(Transaction transaction) {
        new insertAsyncTask(mTransactionDao).execute(transaction);
    }

    public void delete(Transaction transaction) {
        new deleteAsyncTask(mTransactionDao).execute(transaction);
    }

    public void update(Transaction transaction) {
        new updateAsyncTask(mTransactionDao).execute(transaction);
    }




    private static class insertAsyncTask extends AsyncTask<Transaction, Void, Void> {

        private TransactionDao mAsyncTaskDao;

        insertAsyncTask(TransactionDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Transaction... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends android.os.AsyncTask<Transaction, Void, Void> {

        private TransactionDao mAsyncTaskDao;

        deleteAsyncTask(TransactionDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Transaction... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Transaction, Void, Void> {

        private TransactionDao mAsyncTaskDao;

        updateAsyncTask(TransactionDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Transaction... params) {
            int transactionId = params[0].getId();
            String item = params[0].getItem();
            double amount = params[0].getAmount();
            int month = params[0].getMonth();
            int year = params[0].getYear();
            String unixTime = params[0].getUnixTime();
            int type = params[0].getType();
            int categoryID = 0;
          //  int categoryID = params[0].getCategoryID();
            int accountID = params[0].getAccountID();

            mAsyncTaskDao.updateTransaction(transactionId, item, amount, month, year, unixTime, type, categoryID, accountID);
            return null;
        }
    }
}
