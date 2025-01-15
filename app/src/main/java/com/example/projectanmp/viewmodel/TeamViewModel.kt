package com.example.projectanmp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectanmp.model.GameDatabase
import com.example.projectanmp.model.Member
import com.example.projectanmp.model.Team
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamViewModel(application: Application) : AndroidViewModel(application) {

    private val teamDao = GameDatabase.buildDatabase(application, viewModelScope).teamDao()
    private val memberDao = GameDatabase.buildDatabase(application, viewModelScope).memberDao()

    private val _selectedTeamMembers = MutableLiveData<List<Member>>()
    val selectedTeamMembers: LiveData<List<Member>> get() = _selectedTeamMembers



    fun getTeamsForGame(gameId: Int): LiveData<List<Team>> {
        val teamsLiveData = MutableLiveData<List<Team>>()
        viewModelScope.launch(Dispatchers.IO) {
            val teams = teamDao.getTeamsByGameId(gameId)
            teamsLiveData.postValue(teams)
        }
        return teamsLiveData
    }

    fun loadMembersForTeam(teamId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val members = memberDao.getMembersByTeamId(teamId)
            _selectedTeamMembers.postValue(members)
        }
    }

}

