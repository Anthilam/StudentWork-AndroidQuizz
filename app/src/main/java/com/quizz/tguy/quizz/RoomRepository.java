package com.quizz.tguy.quizz;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;

import java.util.List;

// RoomRepository : Repository for the Room
public class RoomRepository {

    private RoomDAO dao;
    private LiveData<List<RoomQuizz>> allQuizz;

    RoomRepository(Application application) {
        RoomQuizzDatabase db = RoomQuizzDatabase.getDatabase(application);
        dao = db.dao();
        allQuizz = dao.getAllQuizz();
    }

    LiveData<List<RoomQuizz>> getAllQuizz() {
        return allQuizz;
    }

    public void insert(RoomQuizz quizz) {
        new insertAsyncTask(dao).execute(quizz);
    }

    public RoomQuizz getQuizzById(int id) {
        return dao.getQuizzById(id);
    }

    private static class insertAsyncTask extends AsyncTask<RoomQuizz, Void, Void> {

        private RoomDAO mAsyncTaskDao;

        insertAsyncTask(RoomDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final RoomQuizz... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    void updateQuizz(RoomQuizz quizz) {
        dao.updateQuizz(quizz);
    }

    void updateQuizzList(List<RoomQuizz> quizz_list) {
        dao.udpateQuizzList(quizz_list);
    }

    void delete(RoomQuizz quizz) {
        dao.delete(quizz);
    }
}
