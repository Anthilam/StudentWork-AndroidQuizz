package com.quizz.tguy.quizz;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

// RoomViewModel : View Model for the Room
public class RoomViewModel extends AndroidViewModel {
    private RoomRepository mRepository; // Room repository

    private LiveData<List<RoomQuizz>> allQuizz; // List of quizz

    // Constructor
    public RoomViewModel (Application application) {
        super(application);
        mRepository = new RoomRepository(application); // Init the repository
        allQuizz = mRepository.getAllQuizz(); // Get all the quizz
    }

    // getAllQuizz : get all the quizz
    LiveData<List<RoomQuizz>> getAllQuizz() {
        return allQuizz;
    }

    // insert : insert a quizz in the database
    public void insert(RoomQuizz quizz) {
        mRepository.insert(quizz);
    }

    // getQuizzById : get a single quizz in the database
    public RoomQuizz getQuizzById(int id) {
        return mRepository.getQuizzById(id);
    }

    // updateQuizz : update a quizz in the database
    void updateQuizz(RoomQuizz quizz) {
        mRepository.updateQuizz(quizz);
    }

    // delete : delete a quizz in the database
    void delete(RoomQuizz rq) {
        mRepository.delete(rq);
    }
}
