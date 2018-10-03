package com.example.shaf.wallety;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class CategoryAdderActivity extends AppCompatActivity {

    Context mContext = this;

    TextInputEditText mCatNameEditText;
    TextInputEditText mCatColourEditText;

    private int mPosition=0;

    private final String LOG_TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_adder);

        mCatNameEditText = findViewById(R.id.cat_add_name);
        mCatColourEditText = findViewById(R.id.cat_add_colour);
        Button addCategoryButton = findViewById(R.id.newCategory_button);

        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });


    }


    private void saveData() {

        String categoryName = mCatNameEditText.getText().toString().trim();
        String categoryColour = mCatColourEditText.getText().toString().trim();

        Intent replyIntent = new Intent();
        replyIntent.putExtra("categoryName", categoryName);
        replyIntent.putExtra("categoryColour", categoryColour);


//        if (mPosition == -100)
//            replyIntent.putExtra("update", 0);
//
//        else {
//            replyIntent.putExtra("update", 1);
//            replyIntent.putExtra("position", mPosition);
//        }
        setResult(RESULT_OK, replyIntent);
        finish();

    }


//    // save a new category
//    private void saveNewCategory() {
//
//        SharedPreferences.Editor editor = mSharedPreferences.edit();
//        String categoryNameString = mCategoryName.getText().toString();
//
//        if (!mSharedPreferences.contains(categoryNameString)) {
//
//            Map temp = mSharedPreferences.getAll();
//
//            int value = temp.size() + 1;
//
//            editor.putString(String.valueOf(value), categoryNameString);
//
//            // handles in background as opposed ot .commit()
//            editor.apply();
//
//            Toast.makeText(mContext, "Category saved.", Toast.LENGTH_SHORT).show();
//            finish();
//
//        }
//
//        else {
//            Toast.makeText(mContext, "Category already exists.", Toast.LENGTH_SHORT).show();
//        }
//    }



}
