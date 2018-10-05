package com.example.shaf.wallety;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.shaf.wallety.Model.Account;
import com.example.shaf.wallety.Model.Category;
import com.example.shaf.wallety.Storage.ViewModel.AccountViewModel;
import com.example.shaf.wallety.Storage.ViewModel.CategoryViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TransactEditorActivity extends AppCompatActivity {


    public static final int TYPE_EXPENSE = 0;
    public static final int TYPE_INCOME = 1;
    public static final int NOT_UPDATE = -8;

    private List<String> categoryNameList;
    private List<String> accountNameList;
    private ArrayAdapter<String> accountSpinnerAdapter;
    private int mPosition;

    private static final String UNKNOWN = "Unknown";

    TextView mDateTextView;
    Intent mIntent;
    Context mContext = this;
    private final String LOG_TAG = mContext.getClass().getSimpleName();
    private CategoryViewModel mCategoryViewModel;
    private AccountViewModel mAccountViewModel;
    private List<Category> mCategoryList = new ArrayList<Category>();
    private List<Account> mAccountList = new ArrayList<Account>();
    private int mAccountID;
    private int mTType = TYPE_EXPENSE;
    private int mCategoryID;
    private String mUnixTime;
    private int year;
    private int month;

    private TextInputEditText mItemEditText;
    private TextInputEditText mAmountEditText;
    private Spinner mAccountSpinner;
    private Spinner mTTypeSpinner;
    private Spinner mCategorySpinner;





    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transact_editor);

        setTitle("New Entry");
        getSupportActionBar().setElevation(0);

        mIntent = getIntent();
        mPosition = mIntent.getIntExtra("position", NOT_UPDATE);      // for updates


        TextView categoryTextView = findViewById(R.id.category_header);
        categoryTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent = new Intent(TransactEditorActivity.this, CategoryListActivity.class);
                startActivity(intent);
                return false;
            }
        });

        Date now = new Date();
        mUnixTime = String.valueOf(now.getTime());

        mItemEditText = findViewById(R.id.addItem);
        mAmountEditText = findViewById(R.id.addAmount);
        mDateTextView = findViewById(R.id.addDate);

        mDateTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");

                return false;
            }
        });


        mAccountSpinner = findViewById(R.id.accountSpinner);
        getAccountListData();
        setupAccountSpinner();

        mTTypeSpinner = findViewById(R.id.TTypeSpinner);
        setupTTypeSpinner();

        mCategorySpinner = findViewById(R.id.categorySpinner);
        setupCategorySpinner();

        mCategoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
         mCategoryViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable final List<Category> categoryList) {
                // Update the cached copy of the words in the adapter.
                new GetCategoryListTask().execute(categoryList);
//
            }
        });


        if (mPosition == NOT_UPDATE) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM, yyyy hh:mm");
            String dateToDisplay = dateFormatter.format(now.getTime());
            mDateTextView.setText(dateToDisplay);
        }



//        mAccountViewModel= ViewModelProviders.of(this).get(AccountViewModel.class);
//        mAccountViewModel.getAllAccounts().observe(this, new Observer<List<Account>>() {
//            @Override
//            public void onChanged(@Nullable final List<Account> accountList) {
//                // Update the cached copy of the words in the adapter.
//                updateAccountList(accountList);
//                setupAccountSpinner();
//            }
//        });
    }

    private void updateAccountList(List<Account> accountList) {
        mAccountList.clear();
        mAccountList.addAll(accountList);
    }

    private void updateCategoryList(List<Category> categoryList) {
        mCategoryList.clear();
        mCategoryList.addAll(categoryList);
    }

    private void getAccountListData() {

        mAccountViewModel= ViewModelProviders.of(this).get(AccountViewModel.class);
        mAccountViewModel.getAllAccounts().observe(this, new Observer<List<Account>>() {
            @Override
            public void onChanged(@Nullable final List<Account> accountList) {
                // Update the cached copy of the words in the adapter.
//                mAccountList = accountList;
                updateAccountList(accountList);
                setupAccountSpinner();
//                accountNameList = getAccountNameList();
//                accountSpinnerAdapter.notifyDataSetChanged();
//                mAccountSpinner.setAdapter(accountSpinnerAdapter);
            }
        });
    }

    private void setupTTypeSpinner() {
        // Create adapter for spinner. The list options are from the String array
        ArrayAdapter transactionTypeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_transaction_type_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style
        transactionTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mTTypeSpinner.setAdapter(transactionTypeSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mTTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.type_expense)))
                        mTType = TYPE_EXPENSE; // Expense
                    else
                        mTType = TYPE_INCOME; // Income
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mTType = TYPE_EXPENSE;
            }
        });
    }


    private void setupAccountSpinner() {
        accountNameList = getAccountNameList();

        accountSpinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, accountNameList);

        // Specify dropdown layout style
        accountSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mAccountSpinner.setAdapter(accountSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mAccountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);

                if (!TextUtils.isEmpty(selection))
                    mAccountID = mAccountViewModel.getAccountID(selection);
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mCategoryID = -1;
            }
        });
    }

    private List<String> getAccountNameList() {
        List<String> accountNameList = new ArrayList<String>();
        for (int i=0; i<mAccountList.size(); i++)
            accountNameList.add(mAccountList.get(i).getAccountName());
        return accountNameList;
    }


    private void setupCategorySpinner() {
        categoryNameList = getCategoryNameList();
        Log.e("spinner-size", categoryNameList.size() + "");

        final ArrayAdapter<String> categorySpinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categoryNameList);

        // Specify dropdown layout style
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);


        // methods on spinner must be run on the Ui thread; not async task
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Apply the adapter to the spinner
                mCategorySpinner.setAdapter(categorySpinnerAdapter);

                // Set the integer mSelected to the constant values
                mCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selection = (String) parent.getItemAtPosition(position);

                        if (!TextUtils.isEmpty(selection))
                            mCategoryID = mCategoryViewModel.getCategoryID(selection);
                    }

                    // Because AdapterView is an abstract class, onNothingSelected must be defined
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        mCategoryID = -1;
                    }
                });

            }
        });
    }


    private List getCategoryNameList() {
        List<String> categoryNameList = new ArrayList<>();
        for (int i=0; i<mCategoryList.size(); i++)
            categoryNameList.add(mCategoryList.get(i).getCategoryName());

        return categoryNameList;
    }

    private void loadData() {

        String item = mIntent.getStringExtra("item");
        double amount = mIntent.getDoubleExtra("amount", -1);
        int type = mIntent.getIntExtra("type", TransactEditorActivity.TYPE_EXPENSE);
        int accountID = mIntent.getIntExtra("accountID", -1);
        int categoryID = mIntent.getIntExtra("categoryID", -1);
        String unixTime = mIntent.getStringExtra("unixTime");

        if (unixTime != null) {
            Date dateObj = new Date(Long.valueOf(unixTime));
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM, yyyy hh:mm");
            String dateToDisplay = dateFormatter.format(dateObj);
            mDateTextView.setText(dateToDisplay);
            mUnixTime = String.valueOf(dateObj.getTime());
        }

        mItemEditText.setText(item);
        mAmountEditText.setText(String.valueOf(amount));
        mTTypeSpinner.setSelection(type);

        mAccountSpinner.setSelection(accountID-1);
        mCategorySpinner.setSelection(categoryID-1);

    }

    private void saveData() {

        Intent replyIntent = new Intent();
        setResult(RESULT_CANCELED, replyIntent);
        double amount = 0.00;

        String item = mItemEditText.getText().toString().trim();
        String amountText = mAmountEditText.getText().toString().trim();
        if (!amountText.isEmpty()) {
            amount = Double.valueOf(amountText);
        }
        else {
            showDraftDialog();
        }

        parseDate();       // update mUnixTime variable, assign month and year


        replyIntent.putExtra("item", item);
        replyIntent.putExtra("amount", amount);
        replyIntent.putExtra("month", month);
        replyIntent.putExtra("year", year);
        replyIntent.putExtra("unixTime", mUnixTime);
        replyIntent.putExtra("categoryID", mCategoryID);
        replyIntent.putExtra("type", mTType);
        replyIntent.putExtra("accountID", mAccountID);

        boolean update = false;
        if (mPosition == NOT_UPDATE)
            replyIntent.putExtra("update", update);

        else {
            update = true;
            replyIntent.putExtra("update", update);
            replyIntent.putExtra("position", mPosition);
        }

        if (!amountText.isEmpty()) {
            setResult(RESULT_OK, replyIntent);
            finish();
        }

    }

    private void showDraftDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete Draft?");

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) dialog.dismiss();
                setResult(RESULT_CANCELED);
                finish();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.transact_editor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.done) {
            Log.e("FAIL", "entered menu done");
            saveData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void parseDate() {
        SimpleDateFormat getDate = new SimpleDateFormat("dd MMM, yyyy");

        Calendar cal = Calendar.getInstance();

        Date date_to_save = new Date();
        try {
            // get String data from dateTextView
            date_to_save = getDate.parse(mDateTextView.getText().toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        // convert string to long
        long dateToSaveLong = date_to_save.getTime();
        cal.setTime(date_to_save);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
    }

    private  class GetCategoryListTask extends AsyncTask<List<Category>, Void, Void> {


        public GetCategoryListTask() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getAccountListData();

        }

        @Override
        protected Void doInBackground(List<Category>... lists) {
            updateCategoryList(lists[0]);
            Log.e("spinner-ld", String.valueOf(lists[0].size()));
            setupCategorySpinner();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (mPosition != NOT_UPDATE)
                loadData();
        }
    }

}
