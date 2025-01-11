package com.example.projectanmp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.projectanmp.R
import com.example.projectanmp.databinding.ActivityRegisterBinding
import com.example.projectanmp.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        binding.btnRegister.isEnabled = false

        binding.termsCheck.setOnCheckedChangeListener { _, isChecked ->
            binding.btnRegister.isEnabled = isChecked
        }

        registerViewModel.userRegistrationStatus.observe(this) { status ->
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show()

            if (status == "User Registered Successfully") {
                val intent = Intent(this@RegisterActivity, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.btnRegister.setOnClickListener {
            val firstName = binding.firstNameEditText.text.toString().trim()
            val lastName = binding.lastNameEditText.text.toString().trim()
            val username = binding.usernameEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val repeatPassword = binding.editTextRepeat.text.toString().trim()
            val teamDescription = binding.descEditText.text.toString().trim()

            registerViewModel.registerUser(
                firstName,
                lastName,
                username,
                password,
                repeatPassword,
                teamDescription
            )
        }

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


    }
}