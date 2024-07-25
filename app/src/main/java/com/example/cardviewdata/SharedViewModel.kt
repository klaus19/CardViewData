package com.example.cardviewdata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _textCount = MutableLiveData<Int>(0)
    val textCount: LiveData<Int> get() = _textCount

    fun updateTextCount(newCount: Int) {
        _textCount.value = newCount
    }
}
