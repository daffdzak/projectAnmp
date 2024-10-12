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

class GameViewModel(application: Application) : AndroidViewModel(application) {
    val GamesLD = MutableLiveData<ArrayList<EsportGame>>()
    val gameLoadErrorLD = MutableLiveData<Boolean>()
    val LoadingLD = MutableLiveData<Boolean>()

    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun refresh() {
        LoadingLD.value = true
        gameLoadErrorLD.value = false

        // Inisialisasi RequestQueue
        queue = Volley.newRequestQueue(getApplication())
        val url = "https://www.jsonkeeper.com/b/ENFP"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                // Parsing JSON ke dalam model EsportGame
                val sType = object : TypeToken<List<EsportGame>>() {}.type
                val result = Gson().fromJson<List<EsportGame>>(response, sType)

                // Update LiveData dengan hasil parsing
                GamesLD.value = ArrayList(result)

                // Log hasil parsing
                Log.d("showvolley", result.toString())

                // Set loading status
                LoadingLD.value = false
            },
            { error ->
                Log.d("showvoley", error.toString())
                gameLoadErrorLD.value = true  // Set error status
                LoadingLD.value = false        // Set loading status
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
