package com.example.projectanmp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.projectanmp.R
import com.example.projectanmp.databinding.FragmentLoginBinding
import com.example.projectanmp.viewmodel.LoginViewModel


class LoginFragment : Fragment() {
    private lateinit var viewModel:LoginViewModel
    private lateinit var binding:FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                // Memanggil ViewModel untuk memverifikasi login
                viewModel.login(usrname, password)
            } else {
                Toast.makeText(context, "Please enter username and password", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }
}