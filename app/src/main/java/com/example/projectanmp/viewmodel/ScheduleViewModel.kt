package com.example.projectanmp.view

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
import com.example.projectanmp.model.GameDatabase
import com.example.projectanmp.model.UpcomingEvent
import com.example.projectanmp.model.UpcomingEventEntity
import org.json.JSONObject

class ScheduleViewModel(application: Application) : AndroidViewModel(application) {

    private val upcomingEventDao = GameDatabase.buildDatabase(application, viewModelScope).upcomingEventDao()

    val upcomingEvents: LiveData<List<UpcomingEventEntity>> = upcomingEventDao.getAllUpcomingEvents()
}

