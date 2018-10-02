package com.example.shaf.wallety;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.shaf.wallety.Model.Category;
import com.example.shaf.wallety.Model.Transaction;
import com.example.shaf.wallety.Storage.ViewModel.CategoryViewModel;
import com.example.shaf.wallety.Storage.ViewModel.TransactsViewModel;

import java.util.List;

public class CategoryListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CategoryViewModel mCategoryViewModel;
    private CategoryListAdapter adapter_global;
    public static final int REQUEST_NEW_CATEGORY_DATA = 1024;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setElevation(0);

        setTitle("Category List");
        mRecyclerView = findViewById(R.id.cat_recycler_view);

        final CategoryListAdapter mAdapter = new CategoryListAdapter(CategoryListActivity.this);
        adapter_global = mAdapter;

        mCategoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

        mCategoryViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable final List<Category> categoryList) {
                // Update the cached copy of the words in the adapter.
                mAdapter.setCategoryList(categoryList);
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
               // showDeleteDialogBox(position);
            }
        }));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryListActivity.this, CategoryAdderActivity.class);

                startActivityForResult(intent, REQUEST_NEW_CATEGORY_DATA);
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

        if (data != null && resultCode == RESULT_OK && (requestCode == REQUEST_NEW_CATEGORY_DATA)) {

            String categoryName = data.getStringExtra("categoryName");
            String categoryColour = data.getStringExtra("categoryColour");

 //           int update = data.getIntExtra("update",0);
//            int position = data.getIntExtra("position", -401);

    //        if (update == 0) {
            Category category = new Category(categoryName, categoryColour);
            mCategoryViewModel.insert(category);

//            } else if (update == 1 && position != -401 ) {
//                int transactionID = adapter_global.getTransacation(position).getId();
//                Transaction transaction = new Transaction(transactionID, item, amount, month, year,
//                        unixTime, type, categoryID, accountID);
//                mTransactsViewModel.update(transaction);
//            }

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

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
//                NavUtils.navigateUpFromSameTask(this)     // does not work!!!
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
