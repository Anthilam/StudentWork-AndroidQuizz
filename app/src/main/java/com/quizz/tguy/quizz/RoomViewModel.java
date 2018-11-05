package com.quizz.tguy.quizz;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class RoomViewModel extends AndroidViewModel {
    private RoomRepository mRepository;

    private LiveData<List<RoomWord>> mAllWords;

    public RoomViewModel (Application application) {
        super(application);
        mRepository = new RoomRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    LiveData<List<RoomWord>> getAllWords() { return mAllWords; }

    public void insert(RoomWord word) { mRepository.insert(word); }
}
