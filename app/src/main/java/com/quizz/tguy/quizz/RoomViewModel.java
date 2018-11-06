package com.quizz.tguy.quizz;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;

import java.util.List;

public class RoomViewModel extends AndroidViewModel {
    private RoomRepository mRepository;

    private LiveData<List<RoomQuizz>> allQuizz;

    public RoomViewModel (Application application) {
        super(application);
        mRepository = new RoomRepository(application);
        allQuizz = mRepository.getAllQuizz();
    }

    LiveData<List<RoomQuizz>> getAllQuizz() { return allQuizz; }

    public void insert(RoomQuizz quizz) { mRepository.insert(quizz); }
}
