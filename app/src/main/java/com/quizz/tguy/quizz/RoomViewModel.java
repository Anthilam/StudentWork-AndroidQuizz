package com.quizz.tguy.quizz;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

// RoomViewModel : View Model for the Room
public class RoomViewModel extends AndroidViewModel {
    private RoomRepository mRepository;

    private LiveData<List<RoomQuizz>> allQuizz;

    public RoomViewModel (Application application) {
        super(application);
        mRepository = new RoomRepository(application);
        allQuizz = mRepository.getAllQuizz();
    }

    LiveData<List<RoomQuizz>> getAllQuizz() {
        return allQuizz;
    }

    public void insert(RoomQuizz quizz) {
        mRepository.insert(quizz);
    }

    public RoomQuizz getQuizzById(int id) {
        return mRepository.getQuizzById(id);
    }

    void updateQuizz(RoomQuizz quizz) {
        mRepository.updateQuizz(quizz);
    }

    void updateQuizzList(List<RoomQuizz> quizzList) {
        mRepository.updateQuizzList(quizzList);
    }

    void delete(int index) {
        mRepository.delete(allQuizz.getValue().get(index));
    }
}
