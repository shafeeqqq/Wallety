package com.example.shaf.wallety;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shaf.wallety.Model.Account;

import java.util.List;

public class AccountList_Adapter extends RecyclerView.Adapter<AccountList_Adapter.AccountViewHolder> {


    private Context mContext;
    private List<Account> mAccountList;

    public AccountList_Adapter(Context context) {
        mContext = context;
    }


    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.account_list_item, parent, false);

        return new AccountViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {

        if (mAccountList != null) {
            Account current = mAccountList.get(position);
            holder.accountNameView.setText(current.getAccountName());
            holder.accountBalanceView.setText(String.valueOf(current.getAccountBalance()));

        } else {
            Toast.makeText(mContext, "Error displaying data", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public int getItemCount() {
        if (mAccountList != null)
            return mAccountList.size();
        return 0;
    }

    void setAccountList(List<Account> accountList){
        mAccountList = accountList;
        notifyDataSetChanged();
    }

    public Account getAccount(int position) {
        return mAccountList.get(position);
    }


    public class AccountViewHolder extends RecyclerView.ViewHolder {

        private TextView accountNameView;
        private TextView accountBalanceView;


        public AccountViewHolder(final View listItemView) {
            super(listItemView);

            accountNameView = listItemView.findViewById(R.id.account_name_view);
            accountBalanceView = listItemView.findViewById(R.id.account_balance_view);
        }
    }




}
