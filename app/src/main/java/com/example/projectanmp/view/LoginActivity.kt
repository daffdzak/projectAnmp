package com.example.projectanmp.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projectanmp.R
import com.example.projectanmp.databinding.ActivityLoginBinding
import com.example.projectanmp.model.GameDatabase
import com.example.projectanmp.viewmodel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var loginInViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("User Session", MODE_PRIVATE)

        val userDao = GameDatabase.buildDatabase(this, CoroutineScope(Dispatchers.Main)).userDao()
        loginInViewModel = LoginViewModel(userDao)

        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            navigateToMainActivity()
        }

        setupObservers()

        binding.loginButton.setOnClickListener{
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            loginInViewModel.loginUser(username, password)
        }

        binding.registerTextView.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


//        enableEdgeToEdge()
//        setContentView(R.layout.activity_login)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }

    private fun setupObservers() {
        loginInViewModel.loginResult.observe(this) { isSuccess ->
            if (isSuccess) {
                saveSession(loginInViewModel.user.value?.usrname ?: "")
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                navigateToMainActivity()
            } else {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
            }
        }

        loginInViewModel.errorMessage.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                loginInViewModel.resetErrorMessage()
            }
        }
    }

    private fun saveSession(username: String) {
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.putBoolean("isLoggedIn", true)
        editor.apply()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}