package com.example.projectanmp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.projectanmp.model.Login
import com.example.projectanmp.model.LoginDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginViewModel(application: Application)
    :AndroidViewModel(application), CoroutineScope {

    val loginLD = MutableLiveData<List<Login>>()
    val loginLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun refresh() {
        loadingLD.value = true
        loginLoadErrorLD.value = false
        launch {
            val db = LoginDatabase.buildDatabase(
                getApplication()
            )

            loginLD.postValue(db.todoDao().selectAllTodo())
            loadingLD.postValue(false)
        }
    }

    fun clearTask(login: Login) {
        launch {
            val db = LoginDatabase.buildDatabase(
                getApplication()
            )
            db.todoDao().deleteTodo(login)

            loginLD.postValue(db.todoDao().selectAllTodo())
        }
    }


}