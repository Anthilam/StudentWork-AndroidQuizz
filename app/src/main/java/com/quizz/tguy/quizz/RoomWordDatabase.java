package com.quizz.tguy.quizz;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {RoomWord.class}, version = 1)
public abstract class RoomWordDatabase extends RoomDatabase {
    public abstract RoomDAO wordDao();

    private static volatile RoomWordDatabase INSTANCE;

    static RoomWordDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomWordDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomWordDatabase.class, "word_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final RoomDAO mDao;

        PopulateDbAsync(RoomWordDatabase db) {
            mDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            RoomWord word = new RoomWord("Hello");
            mDao.insert(word);
            word = new RoomWord("World");
            mDao.insert(word);
            return null;
        }
    }
}