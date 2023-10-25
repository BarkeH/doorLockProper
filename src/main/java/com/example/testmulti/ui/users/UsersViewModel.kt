package com.example.testmulti.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UsersViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is the users section"
    }
    val text: LiveData<String> = _text
}