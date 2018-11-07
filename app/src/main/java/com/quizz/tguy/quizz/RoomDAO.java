package com.quizz.tguy.quizz;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

// RoomDAO : DAO interface for the Room

@Dao
public interface RoomDAO {

    @Insert
    void insert(RoomQuizz quizz);

    @Query("DELETE FROM quizz_table")
    void deleteAll();

    @Query("SELECT * from quizz_table ORDER BY quizz_id ASC")
    LiveData<List<RoomQuizz>> getAllQuizz();

    @Query("SELECT * from quizz_table WHERE quizz_id = :id")
    RoomQuizz getQuizzById(int id);
}
