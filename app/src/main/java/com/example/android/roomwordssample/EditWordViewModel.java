package com.example.android.roomwordssample;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EditWordViewModel extends AndroidViewModel {
    private String TAG = this.getClass().getSimpleName();
    private WordDao noteDao;
    private WordRoomDatabase db;

    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    public EditWordViewModel( @NonNull Application application) {
        super(application);
        Log.i(TAG, "Edit ViewModel");
        db = WordRoomDatabase.getDatabase(application);
        noteDao=db.wordDao();

    }
    LiveData<Word>getWord(String wordId) { return noteDao.getWord(wordId);
    }

}
