package com.example.shaf.wallety;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shaf.wallety.Model.Account;
import com.example.shaf.wallety.Model.Category;
import com.example.shaf.wallety.Model.Transaction;

import java.text.SimpleDateFormat;
import java.util.List;

public class TransactsAdapter extends RecyclerView.Adapter<TransactsAdapter.TransactsViewHolder> {


    private Context mContext;

    private List<Transaction> mTransactions;
    private List<Category> mCategoryList;
    private List<Account> mAccountList;

    public TransactsAdapter(Context context) {
        mContext = context;
    }


    @NonNull
    @Override
    public TransactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.list_item, parent, false);


        return new TransactsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactsViewHolder holder, int position) {

        if (mTransactions != null) {
            Transaction current = mTransactions.get(position);
            Category currentCategory = getCategory(current.getCategoryID());
            Account currentAccount = getAccount(current.getAccountID());

            holder.itemView.setText(current.getItem());

            Log.e("cate_a", String.valueOf(currentCategory == null));

            if (currentCategory != null) {
                Log.e("cate_an", String.valueOf(currentCategory.getCategoryName()));
                holder.categoryView.setText(currentCategory.getCategoryName());
                holder.categoryView.setTextColor(Color.parseColor(currentCategory.getCategoryColour()));
            }

            if (currentAccount != null) {
                holder.acctView.setText(currentAccount.getAccountName());
            }


            SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE, dd MMM");
            String dateToDisplay = dateFormatter.format(Long.valueOf(current.getUnixTime()));
            holder.dateView.setText(dateToDisplay);

            double amount = current.getAmount();
            holder.amountView.setText(String.format("$%.2f", amount));
            if (current.getType() == TransactEditorActivity.TYPE_EXPENSE)
                holder.amountView.setTextColor(mContext.getResources().getColor(R.color.deep_orange));
            else
                holder.amountView.setTextColor(mContext.getResources().getColor(R.color.deep_green));

        } else {
            Toast.makeText(mContext, "Error displaying data", Toast.LENGTH_LONG).show();
        }
    }

    private Account getAccount(int accountID) {
        for (Account item: mAccountList) {
            if (item.getAccountID() == accountID)
                return item;
        }
        return null;
    }

    private Category getCategory(int categoryID) {
        for (Category item: mCategoryList) {

            if (item.getCategoryID() == categoryID) {
                Log.e("cate_loop", "enter if");
                return item;
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        if (mTransactions != null)
            return mTransactions.size();
        return 0;
    }

    public void setTransactions(List<Transaction> transactions){
        mTransactions = transactions;
        notifyDataSetChanged();

    }

    public void setCategoryList(List<Category> categoryList) {
        mCategoryList = categoryList;
        notifyDataSetChanged();
    }

    public void setAccountList(List<Account> accountList) {
        mAccountList = accountList;
        notifyDataSetChanged();
    }

    public Transaction getTransaction(int position) {
        return mTransactions.get(position);
    }


    public class TransactsViewHolder extends RecyclerView.ViewHolder {

        private TextView itemView;
        private TextView categoryView;
        private TextView dateView;
        private TextView acctView;
        private TextView amountView;


        public TransactsViewHolder(final View listItemView) {
            super(listItemView);

            itemView = listItemView.findViewById(R.id.item_text_view);
            categoryView = listItemView.findViewById(R.id.category_text_view);
            dateView = listItemView.findViewById(R.id.date_text_view);
            acctView = listItemView.findViewById(R.id.acct_text_view);
            amountView = listItemView.findViewById(R.id.amount_text_view);


        }
    }




}
