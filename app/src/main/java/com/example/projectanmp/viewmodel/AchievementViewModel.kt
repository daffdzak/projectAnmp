package com.example.projectanmp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.projectanmp.model.Achievement
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class AchievementViewModel(application: Application) : AndroidViewModel(application) {
    val achievementLD = MutableLiveData<List<Achievement>>()
    val achievementLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    private var queue: RequestQueue? = null
    private val url = "https://www.jsonkeeper.com/b/SLXN"

    fun refresh() {
        loadingLD.value = true
        achievementLoadErrorLD.value = false

        queue = Volley.newRequestQueue(getApplication())

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("API Response", response)
                try {
                    val jsonObject = JSONObject(response)
                    val esportGamesArray = jsonObject.getJSONArray("esport_games")

                    val allAchievements = mutableListOf<Achievement>()

                    for (i in 0 until esportGamesArray.length()) {
                        val gameObject = esportGamesArray.getJSONObject(i)
                        val achievementsArray = gameObject.getJSONArray("achievements")

                        val sType = object : TypeToken<List<Achievement>>() {}.type
                        val achievements: List<Achievement> = Gson().fromJson(achievementsArray.toString(), sType)

                        allAchievements.addAll(achievements)
                    }

                    achievementLD.value = allAchievements
                } catch (e: Exception) {
                    Log.e("Parsing error", e.toString())
                    achievementLoadErrorLD.value = true
                } finally {
                    loadingLD.value = false
                }
            },
            { error ->
                Log.d("Volley Error", error.toString())
                achievementLoadErrorLD.value = true
                loadingLD.value = false
            }
        )

        queue?.add(stringRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll("TAG")
    }
}
