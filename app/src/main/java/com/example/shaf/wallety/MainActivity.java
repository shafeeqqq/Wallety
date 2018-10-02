package com.example.shaf.wallety;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.shaf.wallety.Model.Transaction;
import com.example.shaf.wallety.Storage.ViewModel.TransactsViewModel;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private RecyclerView mRecyclerView;
    private TransactsViewModel mTransactsViewModel;
    private TransactsAdapter adapter_global;
    private ConstraintLayout graph;

    private static final int REQUEST_TRANSACTION_DATA = 100;
    private static final int REQUEST_UPDATE_DATA = 101;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setVisibility(View.GONE);
                    graph.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    return true;

                case R.id.navigation_dashboard:
                    mRecyclerView.setVisibility(View.GONE);
                    mTextMessage.setVisibility(View.VISIBLE);
                    mTextMessage.setText(R.string.title_dashboard);
                    graph.setVisibility(View.VISIBLE);
                    return true;

                case R.id.navigation_notifications:
                    mRecyclerView.setVisibility(View.GONE);
                    mTextMessage.setVisibility(View.VISIBLE);
                    mTextMessage.setText(R.string.title_settings);
                    return true;
            }
            return false;
        }
    };

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().collapseActionView();
        mTextMessage = (TextView) findViewById(R.id.message);
        mRecyclerView = findViewById(R.id.recycler_view);
        graph = findViewById(R.id.graph);


        final TransactsAdapter mAdapter = new TransactsAdapter(MainActivity.this);
        adapter_global = mAdapter;

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_dashboard);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TransactEditorActivity.class);
                startActivityForResult(intent, REQUEST_TRANSACTION_DATA);
            }
        });

        mTransactsViewModel = ViewModelProviders.of(this).get(TransactsViewModel.class);

        mTransactsViewModel.getAllTransactions().observe(this, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(@Nullable final List<Transaction> transactions) {
                // Update the cached copy of the words in the adapter.
                mAdapter.setTransactions(transactions);
                adapter_global = mAdapter;
            }
        });


        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemCustomListener(this,
                mRecyclerView, new RecyclerItemCustomListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                loadTransactionEditor(position);

            }

            @Override
            public void onLongItemClick(View view, int position) {
                showDeleteDialogBox(position);
            }
        }));



        GraphView graphView = (GraphView) findViewById(R.id.graphView);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3)
        });
        graphView.addSeries(series);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && resultCode == RESULT_OK && (requestCode == REQUEST_TRANSACTION_DATA ||
                requestCode == REQUEST_UPDATE_DATA)) {

            String item = data.getStringExtra("item");
            double amount = data.getDoubleExtra("amount", -1);
            int month = data.getIntExtra("month", -1);
            int year = data.getIntExtra("year", -1);
            String unixTime = data.getStringExtra("unixTime");
            int type = data.getIntExtra("type", TransactEditorActivity.TYPE_EXPENSE);
            int accountID = data.getIntExtra("accountID", TransactEditorActivity.ACCT_CASH);
            int categoryID = data.getIntExtra("categoryID", -1);

            int update = data.getIntExtra("update",0);
            int position = data.getIntExtra("position", -401);

            if (update == 0) {
                Transaction transaction = new Transaction(item, amount, month, year, unixTime,
                        type, categoryID, accountID);

                mTransactsViewModel.insert(transaction);

            } else if (update == 1 && position != -401 ) {
                int transactionID = adapter_global.getTransacation(position).getId();
                Transaction transaction = new Transaction(transactionID, item, amount, month, year,
                        unixTime, type, categoryID, accountID);
                mTransactsViewModel.update(transaction);
            }

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

        }


        else {
            Log.e("FAIL", String.valueOf(resultCode) + String.valueOf(data == null));
            Toast.makeText(
                    getApplicationContext(),
                    "Unable to save data",
                    Toast.LENGTH_LONG).show();
        }
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
        mTransactsViewModel.delete(adapter_global.getTransacation(position));
        Toast.makeText(this, "Deleted entry.", Toast.LENGTH_SHORT).show();
    }



    private void loadTransactionEditor(int position) {
        Intent intent = new Intent(MainActivity.this, TransactEditorActivity.class);
        intent.putExtra("position", position);

        Transaction transaction = adapter_global.getTransacation(position);

        intent.putExtra("item", transaction.getItem());
        intent.putExtra("amount", transaction.getAmount());
        intent.putExtra("unixTime", transaction.getUnixTime());
        intent.putExtra("categoryID", transaction.getCategoryID());
        intent.putExtra("type", transaction.getType());
        intent.putExtra("accountID", transaction.getAccountID());

        startActivityForResult(intent, REQUEST_UPDATE_DATA);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


}
