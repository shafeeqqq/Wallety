package com.example.shaf.wallety;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.shaf.wallety.Model.Transaction;

import java.text.SimpleDateFormat;
import java.util.List;

public class TransactsAdapter extends RecyclerView.Adapter<TransactsAdapter.TransactsViewHolder> {


    private Context mContext;
    private List<Transaction> mTransactions;

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

            holder.itemView.setText(current.getItem());
            holder.categoryView.setText(String.valueOf(current.getCategoryID()));

            if (current.getAccountID() == TransactEditorActivity.ACCT_CASH)
                holder.acctView.setText(R.string.account_cash);
            else
                holder.acctView.setText(R.string.account_bank);

            SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE, dd MMM");
            String dateToDisplay = dateFormatter.format(Long.valueOf(current.getUnixTime()));
            holder.dateView.setText(dateToDisplay);

            double amount = current.getAmount();
            holder.amountView.setText(String.format("$%.2f", amount));
            if (current.getType() == TransactEditorActivity.TYPE_EXPENSE)
                holder.amountView.setTextColor(mContext.getResources().getColor(R.color.deep_orange));


        } else {
            Toast.makeText(mContext, "Error displaying data", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public int getItemCount() {
        if (mTransactions != null)
            return mTransactions.size();
        return 0;
    }

    void setTransactions(List<Transaction> transactions){
        mTransactions = transactions;
        notifyDataSetChanged();

    }

    public Transaction getTransacation(int position) {
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