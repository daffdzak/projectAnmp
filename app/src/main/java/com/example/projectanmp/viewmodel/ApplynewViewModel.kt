package com.example.projectanmp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.projectanmp.model.Apply
import com.example.projectanmp.model.Game
import com.example.projectanmp.model.GameDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ApplynewViewModel(application: Application): AndroidViewModel(application){
    private val database = GameDatabase.buildDatabase(application, viewModelScope)
    private val _gameLD = MutableLiveData<List<Game>>()
    val gameLD: LiveData<List<Game>> = _gameLD

    init {
        loadGames()
    }

    fun loadGames() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val gameList = database.gameDao().getAllGames()
                _gameLD.postValue(gameList)
            } catch (e: Exception) {
                e.printStackTrace()
                _gameLD.postValue(emptyList())
            }
        }
    }

    fun submitProposal(applies: Apply) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                database.appliesDao().insertProposal(applies)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }



}
