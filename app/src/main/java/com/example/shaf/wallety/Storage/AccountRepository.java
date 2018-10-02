package com.example.shaf.wallety.Storage;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.shaf.wallety.Model.Account;
import com.example.shaf.wallety.Storage.Dao.AccountDao;

import java.util.List;

public class AccountRepository {

    private AccountDao accountDao;
    private LiveData<List<Account>> allAccounts;

    public AccountRepository(Application application) {
        WalletyDatabase db = WalletyDatabase.getDatabase(application);
        accountDao = db.accountDao();
        allAccounts = accountDao.getAllAccounts();
    }

    public LiveData<List<Account>> getAllAccounts() {
        return allAccounts;
    }

    public void insert(Account account) {
        new insertAsyncTask(accountDao).execute(account);
    }

    public void delete(Account account) {
        new deleteAsyncTask(accountDao).execute(account);
    }

    public void update(Account account) {
        new updateAsyncTask(accountDao).execute(account);
    }






    private static class insertAsyncTask extends AsyncTask<Account, Void, Void> {

        private AccountDao mAsyncTaskDao;

        insertAsyncTask(AccountDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Account... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends android.os.AsyncTask<Account, Void, Void> {

        private AccountDao mAsyncTaskDao;

        deleteAsyncTask(AccountDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Account... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Account, Void, Void> {

        private AccountDao mAsyncTaskDao;

        updateAsyncTask(AccountDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Account... params) {
            int accountID = params[0].getAccountID();
            String accountName = params[0].getAccountName();
            double accountBalance = params[0].getAccountBalance();

            mAsyncTaskDao.updateTransaction(accountID, accountName, accountBalance);
            return null;
        }
    }
}
