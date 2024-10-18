package com.example.projectanmp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectanmp.databinding.FragmentAchievementBinding
import com.example.projectanmp.model.Achievement
import com.example.projectanmp.viewmodel.AchievementViewModel
import com.example.projectanmp.viewmodel.GameViewModel

class AchievementFragment : Fragment() {

    private lateinit var binding: FragmentAchievementBinding
    private lateinit var viewModel: AchievementViewModel
    private lateinit var achievementAdapter: AchievementAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAchievementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(AchievementViewModel::class.java)

        binding.recAchievement.layoutManager = LinearLayoutManager(context)
        achievementAdapter = AchievementAdapter(arrayListOf())
        binding.recAchievement.adapter = achievementAdapter

        observeViewModel()

        viewModel.refresh()
    }

    private fun observeViewModel() {
        viewModel.achievementLD.observe(viewLifecycleOwner, Observer { achievementList ->
            achievementList?.let {
                achievementAdapter = AchievementAdapter(it)
                binding.recAchievement.adapter = achievementAdapter
            }
        })

        viewModel.achievementLoadErrorLD.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                if (it) {
                    Log.e("ScheduleFragment", "Error loading schedule data")
                }
            }
        })

        viewModel.loadingLD.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                binding.recAchievement.visibility = if (it) View.GONE else View.VISIBLE
            }
        })
    }
}
