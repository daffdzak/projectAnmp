package com.example.projectanmp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.projectanmp.model.EsportGame
import com.example.projectanmp.model.Game
import com.example.projectanmp.model.GameDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val db = GameDatabase.buildDatabase(application, viewModelScope)
    val gamesLD: LiveData<List<Game>> = db.gameDao().getAllGameLiveData()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorState = MutableLiveData<Boolean>()
    val errorState: LiveData<Boolean> get() = _errorState

    init {
        refresh()
    }

    fun refresh() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val games = db.gameDao().getAllGameLiveData().value
                _errorState.postValue(false)
                games?.let {
                    viewModelScope.launch(Dispatchers.Main) {
                        (gamesLD as MutableLiveData).postValue(it)
                    }
                }
            } catch (exception: Exception) {
                _errorState.postValue(true)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}
