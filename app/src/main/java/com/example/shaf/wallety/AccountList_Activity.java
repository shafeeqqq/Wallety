package com.example.shaf.wallety;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.shaf.wallety.Model.Account;
import com.example.shaf.wallety.Storage.ViewModel.AccountViewModel;

import java.util.List;
import java.util.Objects;

public class AccountList_Activity extends AppCompatActivity {

    public static final int REQUEST_NEW_ACCOUNT_DATA = 1024;
    private AccountViewModel mAccountViewModel;
    private AccountList_Adapter adapter_global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account_list);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        setTitle("Account List");

        RecyclerView mRecyclerView = findViewById(R.id.account_recycler_view);

        final AccountList_Adapter mAdapter = new AccountList_Adapter(AccountList_Activity.this);

        mAccountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        mAccountViewModel.getAllAccounts().observe(this, new Observer<List<Account>>() {
            @Override
            public void onChanged(@Nullable final List<Account> accountList) {
                // Update the cached copy of the words in the adapter.
                mAdapter.setAccountList(accountList);
                adapter_global = mAdapter;
            }
        });


        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemCustomListener(this,
                mRecyclerView, new RecyclerItemCustomListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // loadTransactionEditor(position);

            }

            @Override
            public void onLongItemClick(View view, int position) {
                showDeleteDialogBox(position);
            }
        }));


        FloatingActionButton fab = findViewById(R.id.account_list_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountList_Activity.this, AccountAdderActivity.class);

                startActivityForResult(intent, REQUEST_NEW_ACCOUNT_DATA);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && resultCode == RESULT_OK && (requestCode == REQUEST_NEW_ACCOUNT_DATA)) {

            String accountName = data.getStringExtra("accountName");
            double accountBalance = data.getDoubleExtra("accountBalance", 0);

//            int update = data.getIntExtra("update",0);
//            int position = data.getIntExtra("position", -401);

//            if (update == 0) {
            Account account = new Account(accountName, accountBalance);
            mAccountViewModel.insert(account);

//            } else if (update == 1 && position != -401 ) {
//                int transactionID = adapter_global.getTransacation(position).getId();
//                Transaction transaction = new Transaction(transactionID, item, amount, month, year,
//                        unixTime, type, categoryID, accountID);
//                mTransactsViewModel.update(transaction);
//            }

            Toast.makeText(this, "Account Saved.", Toast.LENGTH_LONG).show();

        }


        else {
            Toast.makeText(
                    getApplicationContext(),
                    "Unable to save data",
                    Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDeleteDialogBox(int position) {
        final int id = position;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete entry?");

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteEntry(id);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteEntry(int position){
        mAccountViewModel.delete(adapter_global.getAccount(position));
        Toast.makeText(this, R.string.deletion_success, Toast.LENGTH_LONG).show();
    }

}
