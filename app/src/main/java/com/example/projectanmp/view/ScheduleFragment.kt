package com.example.projectanmp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectanmp.databinding.FragmentScheduleBinding
import com.example.projectanmp.adapter.ScheduleAdapter

class ScheduleFragment : Fragment() {

    private lateinit var binding: FragmentScheduleBinding
    private lateinit var viewModel: ScheduleViewModel
    private lateinit var scheduleAdapter: ScheduleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        scheduleAdapter = ScheduleAdapter(mutableListOf()) { event ->
            val action = ScheduleFragmentDirections.actionScheduleFragmentToScheduleDetailFragment(
                eventImage = event.eventImage,
                eventDescription = event.description
            )
            view?.findNavController()?.navigate(action)
        }
        binding.recyclerView.adapter = scheduleAdapter

        viewModel = ViewModelProvider(this).get(ScheduleViewModel::class.java)
        observeViewModel()

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.upcomingEvents.observe(viewLifecycleOwner) { events ->
            scheduleAdapter.updateEvents(events)
        }
    }
}



