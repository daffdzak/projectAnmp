package com.example.projectanmp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.projectanmp.model.Apply
import com.example.projectanmp.model.Game
import com.example.projectanmp.model.GameDatabase
import com.example.projectanmp.model.Team
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class ApplynewViewModel(application: Application) : AndroidViewModel(application) {

    private val gameDao = GameDatabase.buildDatabase(application, viewModelScope).gameDao()
    private val teamDao = GameDatabase.buildDatabase(application, viewModelScope).teamDao()
    private val applyDao = GameDatabase.buildDatabase(application, viewModelScope).appliesDao()

    val gameLD: LiveData<List<Game>> = gameDao.getAllGames()

    fun loadTeams(gameName: String): LiveData<List<Team>> {
        val result = MutableLiveData<List<Team>>()
        viewModelScope.launch(Dispatchers.IO){
            val gameId = gameDao.getGameIdByName(gameName)
            val teams = teamDao.getTeamsByGameIdLive(gameId)
            withContext(Dispatchers.Main){
                teams.observeForever { result.postValue(it) }
            }
        }

        return result
    }

    fun submitProposal(proposal: Apply) {
        viewModelScope.launch(Dispatchers.IO) {
            applyDao.insertProposal(proposal)
        }
    }
}

