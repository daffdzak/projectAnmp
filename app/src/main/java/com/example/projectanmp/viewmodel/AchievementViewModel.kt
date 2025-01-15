package com.example.projectanmp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.projectanmp.model.Achievement
import com.example.projectanmp.model.EsportGame
import com.example.projectanmp.model.GameDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class AchievementViewModel(application: Application) : AndroidViewModel(application) {

    private val achievementDao = GameDatabase.buildDatabase(application, viewModelScope).achievementDao()

    val achievementLD = MutableLiveData<List<Achievement>>()

    fun getAchievementsByGameId(gameId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val achievements = achievementDao.getAchievementsForGame(gameId)
            achievementLD.postValue(achievements)
        }
    }
}

