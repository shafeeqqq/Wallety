package com.example.shaf.wallety.Storage.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.shaf.wallety.Model.Account;
import com.example.shaf.wallety.Storage.AccountRepository;

import java.util.List;

public class AccountViewModel extends AndroidViewModel {

    private AccountRepository mRepository;

    private LiveData<List<Account>> mAllAccounts;


    public AccountViewModel(@NonNull Application application) {
        super(application);

        mRepository = new AccountRepository(application);
        mAllAccounts = mRepository.getAllAccounts();
    }

    public LiveData<List<Account>> getAllAccounts() {
        return mAllAccounts;
    }

    public void insert(Account account) {
        mRepository.insert(account);
    }

    public void delete(Account account) {
        mRepository.delete(account);
    }

    public void update(Account account) {
        mRepository.update(account);
    }

}
