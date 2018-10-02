package com.example.shaf.wallety.Storage.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.shaf.wallety.Model.Category;
import com.example.shaf.wallety.Storage.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private CategoryRepository mRepository;

    private LiveData<List<Category>> mAllCategories;


    public CategoryViewModel(@NonNull Application application) {
        super(application);

        mRepository = new CategoryRepository(application);
        mAllCategories = mRepository.getAllCategories();
    }

    public LiveData<List<Category>> getAllCategories() {
        return mAllCategories;
    }

    public void insert(Category category) {
        mRepository.insert(category);
    }

    public void delete(Category category) {
        mRepository.delete(category);
    }

    public void update(Category category) {
        mRepository.update(category);
    }

    public int getCategoryID(String categoryName) {
        return mRepository.getCategoryID(categoryName);
    }

}
