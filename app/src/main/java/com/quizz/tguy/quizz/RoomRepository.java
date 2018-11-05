package com.quizz.tguy.quizz;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class RoomRepository {

    private RoomDAO mWordDao;
    private LiveData<List<RoomWord>> mAllWords;

    RoomRepository(Application application) {
        RoomWordDatabase db = RoomWordDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();
    }

    LiveData<List<RoomWord>> getAllWords() {
        return mAllWords;
    }


    public void insert (RoomWord word) {
        new insertAsyncTask(mWordDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<RoomWord, Void, Void> {

        private RoomDAO mAsyncTaskDao;

        insertAsyncTask(RoomDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final RoomWord... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
