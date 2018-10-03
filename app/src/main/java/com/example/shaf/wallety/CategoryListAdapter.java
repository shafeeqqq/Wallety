package com.example.shaf.wallety;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shaf.wallety.Model.Category;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> {


    private Context mContext;
    private List<Category> mCategoryList;

    public CategoryListAdapter(Context context) {
        mContext = context;
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.category_list_item, parent, false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {


        if (mCategoryList != null) {
            Category current = mCategoryList.get(position);
            holder.categoryNameView.setText(current.getCategoryName());
            GradientDrawable colourIcon = (GradientDrawable) holder.categoryColourView.getBackground();
            colourIcon.setColor(Color.parseColor(current.getCategoryColour()));
//            holder.categoryCardView.setCardBackgroundColor(Color.parseColor(current.getCategoryColour()));

        } else {
            Toast.makeText(mContext, "Error displaying data", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public int getItemCount() {
        if (mCategoryList != null)
            return mCategoryList.size();
        return 0;
    }

    void setCategoryList(List<Category> categoryList){
        mCategoryList = categoryList;
        notifyDataSetChanged();
    }

    public Category getCategory(int position) {
        return mCategoryList.get(position);
    }


    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView categoryNameView;
        private ImageView categoryColourView;
        private CardView categoryCardView;


        public CategoryViewHolder(final View listItemView) {
            super(listItemView);

            categoryNameView = listItemView.findViewById(R.id.cat_name_text_view);
            categoryColourView = listItemView.findViewById(R.id.cat_colour_view);
            categoryCardView = listItemView.findViewById(R.id.cat_card);
        }
    }




}
