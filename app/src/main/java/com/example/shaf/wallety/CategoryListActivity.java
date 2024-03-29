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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.shaf.wallety.Model.Category;
import com.example.shaf.wallety.Storage.ViewModel.CategoryViewModel;

import java.util.List;

public class CategoryListActivity extends AppCompatActivity {

    CategoryListAdapter adapter_global;
    private CategoryViewModel mCategoryViewModel;
    public static final int REQUEST_NEW_CATEGORY_DATA = 1024;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        getSupportActionBar().setElevation(0);

        setTitle(getString(R.string.title_category_list_activity));
        RecyclerView mRecyclerView = findViewById(R.id.cat_recycler_view);

        final CategoryListAdapter mAdapter = new CategoryListAdapter(CategoryListActivity.this);

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
               showDeleteDialogBox(position);
            }
        }));

        FloatingActionButton fab = findViewById(R.id.fab);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && resultCode == RESULT_OK && (requestCode == REQUEST_NEW_CATEGORY_DATA)) {

            String categoryName = data.getStringExtra(getString(R.string.intent_category_name));
            String categoryColour = data.getStringExtra(getString(R.string.intent_category_colour));

 //           int update = data.getIntExtra("update",0);
//            int position = data.getIntExtra("position", -401);

    //        if (update == 0) {
            Category category = new Category(categoryName, categoryColour);

            boolean insertion_success = mCategoryViewModel.insert(category);


//            } else if (update == 1 && position != -401 ) {
//                int transactionID = adapter_global.getTransacation(position).getId();
//                Transaction transaction = new Transaction(transactionID, item, amount, month, year,
//                        unixTime, type, categoryID, accountID);
//                mTransactsViewModel.update(transaction);
//            }
            Log.e("Exceptioncatc-activity", String.valueOf(insertion_success));
            if (insertion_success)
                Toast.makeText(this, R.string.saved_toast, Toast.LENGTH_LONG).show();

            else
                Toast.makeText(this, R.string.cat_InsertionException, Toast.LENGTH_LONG).show();
        } 
        
        else {
            Toast.makeText( getApplicationContext(), R.string.save_error, Toast.LENGTH_SHORT).show();
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

    private void showDeleteDialogBox(int position) {
        final int id = position;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_alertMsg);

        builder.setPositiveButton(R.string.delete_dialog_option, new DialogInterface.OnClickListener() {
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
        mCategoryViewModel.delete(adapter_global.getCategory(position));
        Toast.makeText(this, "Deleted Entry", Toast.LENGTH_SHORT).show();
    }

}
