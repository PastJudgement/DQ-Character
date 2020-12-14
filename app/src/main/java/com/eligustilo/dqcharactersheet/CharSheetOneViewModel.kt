package com.eligustilo.dqcharactersheet

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class CharSheetOneViewModel(application: Application): AndroidViewModel(application) {
    var mutableCharacterName = MutableLiveData<String>()

}