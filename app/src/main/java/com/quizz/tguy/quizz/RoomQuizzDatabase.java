package com.quizz.tguy.quizz;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {RoomQuizz.class}, version = 3, exportSchema = false)
public abstract class RoomQuizzDatabase extends RoomDatabase {
    public abstract RoomDAO dao();

    private static volatile RoomQuizzDatabase INSTANCE;

    static RoomQuizzDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomQuizzDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomQuizzDatabase.class, "word_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final RoomDAO mDao;

        PopulateDbAsync(RoomQuizzDatabase db) {
            mDao = db.dao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            RoomQuizz quizz = new RoomQuizz(1);
            mDao.insert(quizz);
            quizz = new RoomQuizz(2);
            mDao.insert(quizz);
            return null;
        }
    }
}