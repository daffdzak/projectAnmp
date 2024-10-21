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
    private var i = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.descriptionText.text = "Dika Gaming adalah tim yang berfokus pada dunia Esport."

        binding.btnLike.text = i.toString()

        binding.btnLike.setOnClickListener {
            i++
            binding.btnLike.text = i.toString()
            binding.btnLike.isEnabled = false
        }

        return binding.root
    }
}
