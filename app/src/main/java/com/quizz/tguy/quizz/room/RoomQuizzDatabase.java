package com.quizz.tguy.quizz.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

// RoomQuizzDatabase : Room database creation
@Database(entities = {RoomQuizz.class}, version = 5, exportSchema = false)
public abstract class RoomQuizzDatabase extends RoomDatabase {

    public abstract RoomDAO dao(); // DAO

    private static volatile RoomQuizzDatabase INSTANCE; // Instance of the database

    // getDatabase : get the database
    static RoomQuizzDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomQuizzDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomQuizzDatabase.class, "quizz_database")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // RoomDatabase.Callback : callback used when we create the database
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            new PopulateDbAsync(INSTANCE).execute(); // Pre-populate the database
        }
    };

    // PopulateDbAsync : pre-populate the database in an AsyncTask
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final RoomDAO mDao; // DAO

        // Constructor
        PopulateDbAsync(RoomQuizzDatabase db) {
            mDao = db.dao();
        }

        // doInBackground
        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();                   // Delete the database
            RoomQuizz quizz = new RoomQuizz();  // Create a new default quizz
            quizz.setTitle("Bienvenue!");
            mDao.insert(quizz);
            return null;
        }
    }
}