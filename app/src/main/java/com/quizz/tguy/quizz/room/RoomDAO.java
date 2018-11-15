package com.quizz.tguy.quizz.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

// RoomDAO : Data Access Object interface for the Room
@Dao
public interface RoomDAO {

    // Insert a quizz in the database
    @Insert
    void insert(RoomQuizz quizz);

    // Delete all the quizz in the database
    @Query("DELETE FROM quizz_table")
    void deleteAll();

    // Get all the quizz from the database, ordered by id
    @Query("SELECT * from quizz_table ORDER BY quizz_id ASC")
    LiveData<List<RoomQuizz>> getAllQuizz();

    // Get a single quizz from the database
    @Query("SELECT * from quizz_table WHERE quizz_id = :id")
    RoomQuizz getQuizzById(int id);

    // Update a quizz in the database
    @Update
    void updateQuizz(RoomQuizz quizz);

    // Delete a quizz in the database
    @Delete
    void delete(RoomQuizz quizz);
}
