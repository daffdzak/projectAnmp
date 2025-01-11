//package com.example.projectanmp.viewmodel
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.example.projectanmp.model.Team
//
//class TeamViewModel : ViewModel() {
//    private val _selectedTeam = MutableLiveData<Team>()
//    val selectedTeam: LiveData<Team> get() = _selectedTeam
//
//    fun selectTeam(team: Team) {
//        _selectedTeam.value = team
//    }
//}