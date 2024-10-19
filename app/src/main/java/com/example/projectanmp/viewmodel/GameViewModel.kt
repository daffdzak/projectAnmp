package com.example.projectanmp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.projectanmp.model.EsportGame
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class GameViewModel(application: Application) : AndroidViewModel(application) {
    val GamesLD = MutableLiveData<ArrayList<EsportGame>>()
    val gameLoadErrorLD = MutableLiveData<Boolean>()
    val LoadingLD = MutableLiveData<Boolean>()

    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun refresh() {
        LoadingLD.value = true
        gameLoadErrorLD.value = false

        queue = Volley.newRequestQueue(getApplication())
        val url = "https://www.jsonkeeper.com/b/9KFN"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("API Response", response)
                try {
                    val jsonObject = JSONObject(response)
                    val gamesArray = jsonObject.getJSONArray("esport_games")

                    val sType = object : TypeToken<List<EsportGame>>() {}.type
                    val result = Gson().fromJson<List<EsportGame>>(gamesArray.toString(), sType)
                    GamesLD.value = ArrayList(result)
                    Log.d("showvolley", result.toString())

                }
                catch (e: Exception){
                    Log.e("Parsing error",e.toString())
                    gameLoadErrorLD.value = true
                }finally {
                    LoadingLD.value = false
                }

            },
            { error ->
                Log.d("showvoley", error.toString())
                gameLoadErrorLD.value = true
                LoadingLD.value = false
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
