package com.example.projectanmp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projectanmp.R
import com.example.projectanmp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Menginisialisasi binding
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        // Mengubah teks pada TextView dengan ID descriptionText
        binding.descriptionText.text = "Kami adalah tim yang berfokus pada dunia Esport."

        // Mengembalikan root view dari binding
        return binding.root
    }
}
