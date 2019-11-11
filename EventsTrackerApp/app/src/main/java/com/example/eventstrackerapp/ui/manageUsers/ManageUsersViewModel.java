package com.example.eventstrackerapp.ui.manageUsers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ManageUsersViewModel extends ViewModel{

    private MutableLiveData<String> mText;

    public ManageUsersViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("This is manage users fragment");
    }

    public LiveData<String> getText(){
        return mText;
    }
}