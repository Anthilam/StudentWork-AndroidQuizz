package com.quizz.tguy.quizz.room;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

// RoomRepository : Repository for the Room
public class RoomRepository {
    private RoomDAO dao; // DAO
    private LiveData<List<RoomQuizz>> allQuizz; // List of quizz

    // Constructor
    RoomRepository(Application application) {
        RoomQuizzDatabase db = RoomQuizzDatabase.getDatabase(application); // Get the database
        dao = db.dao(); // Init the DAO
        allQuizz = dao.getAllQuizz(); // Get all the quizz
    }

    // getAllQuizz : get all the quizz in the database
    LiveData<List<RoomQuizz>> getAllQuizz() {
        return allQuizz;
    }

    // insert : insert a quizz in the database
    public void insert(RoomQuizz quizz) {
        new insertAsyncTask(dao).execute(quizz);
    }

    // getQuizzById : get a single quizz in the database
    public RoomQuizz getQuizzById(int id) {
        return dao.getQuizzById(id);
    }

    // insertAsyncTask : insert a quizz in the database asynchronously
    private static class insertAsyncTask extends AsyncTask<RoomQuizz, Void, Void> {
        private RoomDAO mAsyncTaskDao; // DAO

        insertAsyncTask(RoomDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final RoomQuizz... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    // updateQuizz : update a quizz in the database
    void updateQuizz(RoomQuizz quizz) {
        dao.updateQuizz(quizz);
    }

    // delete : delete a quizz in the database
    void delete(RoomQuizz quizz) {
        dao.delete(quizz);
    }
}
