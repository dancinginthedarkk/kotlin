package com.example.firstapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var counter = MutableLiveData<ArrayList<Int>>()
    private var list = ArrayList<Int>()

    fun getCount() : LiveData<ArrayList<Int>> {
        return counter
    }

    fun increment() {
        list.add(list.size)
        counter.value = list
    }
}