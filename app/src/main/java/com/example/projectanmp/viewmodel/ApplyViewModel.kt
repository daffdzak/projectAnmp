package com.example.projectanmp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.projectanmp.model.Apply
import com.example.projectanmp.model.GameDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ApplyViewModel (application: Application) : AndroidViewModel(application) {

    private val database = GameDatabase.buildDatabase(application, viewModelScope)
    private val applies = MutableLiveData<List<Apply>>()
    val applyLD: LiveData<List<Apply>> get() = applies

    fun refresh(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val proposalList = database.appliesDao().getApplyByUsernameSync(username)
                applies.postValue(proposalList)
            } catch (e: Exception) {
                e.printStackTrace()
                applies.postValue(emptyList())
            }
        }
    }
}