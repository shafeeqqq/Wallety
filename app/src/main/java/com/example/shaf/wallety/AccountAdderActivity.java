package com.example.shaf.wallety;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AccountAdderActivity extends AppCompatActivity {

    Context mContext = this;

    TextInputEditText mAcc_Name_EditText;
    TextInputEditText mAcc_Bal_EditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_adder);
        getSupportActionBar().setElevation(0);
        setTitle("Create Account");

        mAcc_Name_EditText = findViewById(R.id.account_add_name);
        mAcc_Bal_EditText = findViewById(R.id.account_add_balance);

        Button addAccountButton = findViewById(R.id.new_account_button);
        addAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }


    private void saveData() {

        String accountName = mAcc_Name_EditText.getText().toString().trim();
        double accountBalance = Double.parseDouble(mAcc_Bal_EditText.getText().toString().trim());

        Intent replyIntent = new Intent();
        replyIntent.putExtra("accountName", accountName);
        replyIntent.putExtra("accountBalance", accountBalance);

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




}
