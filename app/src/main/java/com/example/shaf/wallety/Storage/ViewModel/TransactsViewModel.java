package com.example.shaf.wallety.Storage.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;


import com.example.shaf.wallety.Model.Transaction;
import com.example.shaf.wallety.Storage.TransactionRepository;

import java.util.List;

public class TransactsViewModel extends AndroidViewModel {

    private TransactionRepository mRepository;

    private LiveData<List<Transaction>> mAllTransactions;


    public TransactsViewModel(@NonNull Application application) {
        super(application);

        mRepository = new TransactionRepository(application);
        mAllTransactions = mRepository.getAllTransactions();
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return mAllTransactions;
    }

    public void insert(Transaction transaction) {
        mRepository.insert(transaction);
    }

    public void delete(Transaction transaction) {
        mRepository.delete(transaction);
    }

    public void update(Transaction transaction) {
        Log.e("tran_viewmodel", String.valueOf(transaction.getCategoryID()));
        mRepository.update(transaction);
    }

}
