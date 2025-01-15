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


import java.util.Calendar

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

        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val years = (currentYear downTo (currentYear - 4)).toList()
        val yearAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, years)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = yearAdapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedYear = years[position]
                filterAchievementsByYear(selectedYear)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        return binding.root
    }

    private fun filterAchievementsByYear(year: Int) {
        val filteredAchievements = viewModel.achievementLD.value?.filter { achievement ->
            achievement.year == year
        } ?: emptyList()

        achievementAdapter.updateAchievements(filteredAchievements)
    }

    private fun observeViewModel() {
        viewModel.achievementLD.observe(viewLifecycleOwner) { achievements ->
            achievementAdapter.updateAchievements(achievements)
        }
    }
}





