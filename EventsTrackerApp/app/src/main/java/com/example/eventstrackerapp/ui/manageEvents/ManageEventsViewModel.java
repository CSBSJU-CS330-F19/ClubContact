package com.example.eventstrackerapp.ui.manageEvents;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ManageEventsViewModel extends ViewModel{

    private MutableLiveData<String> mText;

    public ManageEventsViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("This is manage events fragment");
    }

    public LiveData<String> getText(){
        return mText;
    }
}