package com.example.projectanmp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.projectanmp.model.UpcomingEvent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class ScheduleViewModel(application: Application) : AndroidViewModel(application) {
    val upcomingEventsLD = MutableLiveData<ArrayList<UpcomingEvent>>()
    val scheduleLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun refresh() {
        loadingLD.value = true
        scheduleLoadErrorLD.value = false

        queue = Volley.newRequestQueue(getApplication())
        val url = "https://www.jsonkeeper.com/b/SLXN" // Ganti dengan URL yang sesuai

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("API Response", response)
                try {
                    val jsonObject = JSONObject(response)
                    val gamesArray = jsonObject.getJSONArray("esport_games") // Pastikan menggunakan nama yang sesuai

                    val upcomingEvents = mutableListOf<UpcomingEvent>()

                    // Loop through each game and extract upcoming events
                    for (i in 0 until gamesArray.length()) {
                        val game = gamesArray.getJSONObject(i)
                        val gameTitle = game.getString("game_title")
                        val eventsArray = game.getJSONArray("upcoming_events")

                        for (j in 0 until eventsArray.length()) {
                            val event = eventsArray.getJSONObject(j)
                            val upcomingEvent = UpcomingEvent(
                                event_name = event.getString("event_name"),
                                year = event.getInt("year"),
                                month = event.getString("month"),
                                day = event.getInt("day"),
                                time = event.getString("time"),
                                game = gameTitle,
                                description = event.getString("description"),
                                event_image = event.getString("event_image")
                            )
                            upcomingEvents.add(upcomingEvent)
                        }
                    }

                    // Set hasil langsung ke LiveData
                    upcomingEventsLD.value = ArrayList(upcomingEvents)
                    Log.d("showvolley", upcomingEvents.toString())

                } catch (e: Exception) {
                    Log.e("Parsing error", e.toString())
                    scheduleLoadErrorLD.value = true
                } finally {
                    loadingLD.value = false
                }

            },
            { error ->
                Log.d("showvolley", error.toString())
                scheduleLoadErrorLD.value = true
                loadingLD.value = false
            }
        )

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}
