package com.example.shaf.wallety.Storage;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.database.sqlite.SQLiteConstraintException;
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

    public boolean insert(Category category) {
        boolean success=false;
        try {
            success = new insertAsyncTask(categoryDao).execute(category).get();

        } catch (ExecutionException e) {
            e.printStackTrace();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return success;
    }

    public void delete(Category category) {
        new deleteAsyncTask(categoryDao).execute(category);
    }

    public void update(Category category) {
        Log.e("cate_repo", String.valueOf(category.getCategoryID()));
        new updateAsyncTask(categoryDao).execute(category);
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


    public String getCategoryName(int categoryID) {
        try {
            return new getCategoryNameAsyncTask(categoryDao).execute(categoryID).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Unknown";
    }


    private static class insertAsyncTask extends AsyncTask<Category, Void, Boolean> {

        private CategoryDao mAsyncTaskDao;

        insertAsyncTask(CategoryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Boolean doInBackground(final Category... params) {
            boolean success;
            try {
                mAsyncTaskDao.insert(params[0]);
                success = true;

            } catch (SQLiteConstraintException uniqueNameException){
                Log.e("Exceptioncatching", "enter");
                success = false;
                Log.e("Exceptioncatching-catch", String.valueOf(success));
            }
            Log.e("Exceptioncatching-catch", String.valueOf(success));
            return success;
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

            Log.e("cate_stored",String.valueOf(categoryID));
            mAsyncTaskDao.updateCategory(categoryID, categoryName, categoryColour);
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


    private static class getCategoryNameAsyncTask extends AsyncTask<Integer, Void, String> {

        private CategoryDao mAsyncTaskDao;

        getCategoryNameAsyncTask(CategoryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected String doInBackground(final Integer... params) {
            int categoryID = params[0];
            String categoryName = mAsyncTaskDao.getCategoryName(categoryID);
            return categoryName;
        }
    }
}
