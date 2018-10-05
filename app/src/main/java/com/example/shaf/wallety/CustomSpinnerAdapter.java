package com.example.shaf.wallety;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.example.shaf.wallety.Model.Account;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {


    public CustomSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
    }


    public void setDataSet(List<Account> objectList){
        notifyDataSetChanged();

    }
}
