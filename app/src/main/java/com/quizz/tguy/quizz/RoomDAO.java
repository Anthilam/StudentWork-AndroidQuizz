package com.quizz.tguy.quizz;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RoomDAO {

    @Insert
    void insert(RoomQuizz quizz);

    @Query("DELETE FROM quizz_table")
    void deleteAll();

    @Query("SELECT * from quizz_table ORDER BY quizz_id ASC")
    LiveData<List<RoomQuizz>> getAllQuizz();

    // Rajouter une requete pour avoir un seul quizz
}
