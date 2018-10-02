package com.example.shaf.wallety.Storage;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.AsyncQueryHandler;
import android.os.AsyncTask;
import android.util.Log;

import com.example.shaf.wallety.Model.Category;
import com.example.shaf.wallety.Storage.Dao.CategoryDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CategoryRepository {

    private CategoryDao categoryDao;
    private LiveData<List<Category>> allCategories;

    public CategoryRepository(Application application) {
        WalletyDatabase db = WalletyDatabase.getDatabase(application);
        categoryDao = db.categoryDao();
        allCategories = categoryDao.getAllCategories();
    }

    public LiveData<List<Category>> getAllCategories() {
        return this.allCategories;
    }

    public int getCategoryID(String categoryName) {
        try {
            return new getCategoryIDAsyncTask(categoryDao).execute(categoryName).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void insert(Category category) {
        new insertAsyncTask(categoryDao).execute(category);
    }

    public void delete(Category category) {
        new deleteAsyncTask(categoryDao).execute(category);
    }

    public void update(Category category) {
        new updateAsyncTask(categoryDao).execute(category);
    }






    private static class insertAsyncTask extends AsyncTask<Category, Void, Void> {

        private CategoryDao mAsyncTaskDao;

        insertAsyncTask(CategoryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Category... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Category, Void, Void> {

        private CategoryDao mAsyncTaskDao;

        deleteAsyncTask(CategoryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Category... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Category, Void, Void> {

        private CategoryDao mAsyncTaskDao;

        updateAsyncTask(CategoryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Category... params) {
            int categoryID = params[0].getCategoryID();
            String categoryName = params[0].getCategoryName();
            String categoryColour = params[0].getCategoryColour();
      
            mAsyncTaskDao.updateTransaction(categoryID, categoryName, categoryColour);
            return null;
        }
    }

    private static class getCategoryIDAsyncTask extends AsyncTask<String, Void, Integer> {

        private CategoryDao mAsyncTaskDao;

        getCategoryIDAsyncTask(CategoryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Integer doInBackground(final String... params) {
            String categoryName = params[0];
            int categoryID = mAsyncTaskDao.getCategoryID(categoryName);
            return categoryID;
        }
    }
}
