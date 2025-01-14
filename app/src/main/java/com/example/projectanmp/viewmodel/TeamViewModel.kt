package com.example.projectanmp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectanmp.model.GameDatabase
import com.example.projectanmp.model.Team
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamViewModel(application: Application) : AndroidViewModel(application) {

    private val teamDao = GameDatabase.buildDatabase(application, viewModelScope).teamDao()

    fun getTeamsForGame(gameId: Int): LiveData<List<Team>> {
        val teamsLiveData = MutableLiveData<List<Team>>()
        viewModelScope.launch(Dispatchers.IO) {
            val teams = teamDao.getTeamsByGameId(gameId)
            teamsLiveData.postValue(teams)
        }
        return teamsLiveData
    }
}

