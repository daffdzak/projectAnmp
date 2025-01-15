package com.example.projectanmp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectanmp.databinding.FragmentAchievementBinding
import com.example.projectanmp.model.Achievement
import com.example.projectanmp.viewmodel.AchievementViewModel
import com.example.projectanmp.viewmodel.GameViewModel
import android.widget.ArrayAdapter


class AchievementFragment : Fragment() {

    private lateinit var binding: FragmentAchievementBinding
    private lateinit var viewModel: AchievementViewModel
    private lateinit var achievementAdapter: AchievementAdapter

    private var gameId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            gameId = it.getInt("gameId", -1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAchievementBinding.inflate(inflater, container, false)

        binding.recAchievement.layoutManager = LinearLayoutManager(context)
        achievementAdapter = AchievementAdapter(mutableListOf())
        binding.recAchievement.adapter = achievementAdapter

        viewModel = ViewModelProvider(this).get(AchievementViewModel::class.java)
        observeViewModel()

        viewModel.getAchievementsByGameId(gameId)

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.achievementLD.observe(viewLifecycleOwner) { achievements ->
            achievementAdapter.updateAchievements(achievements)
        }
    }
}


