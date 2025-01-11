package com.example.projectanmp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.projectanmp.model.GameDatabase
import com.example.projectanmp.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = GameDatabase.buildDatabase(application,viewModelScope).userDao()

    val userRegistrationStatus =MutableLiveData<String>()

    fun registerUser(
        firstName: String,
        lastName: String,
        username: String,
        password: String,
        repeatPassword: String,
        teamDescription: String
    ){
        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() ||
            repeatPassword.isEmpty() || teamDescription.isEmpty()
        ) {
            userRegistrationStatus.postValue("Please fill all fields")
            return
        }

        if (password != repeatPassword) {
            userRegistrationStatus.postValue("Passwords do not match")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val existingUser = userDao.getUserByUsername(username)

            if (existingUser != null) {
                userRegistrationStatus.postValue("Username already exists")
            } else {
                val newUser = User(
                    firstName = firstName,
                    lastName = lastName,
                    usrname = username,
                    passwrd = password,
                    desc = teamDescription
                )
                userDao.insertAll(newUser)

                userRegistrationStatus.postValue("User Registered Successfully")
            }
        }

    }
}