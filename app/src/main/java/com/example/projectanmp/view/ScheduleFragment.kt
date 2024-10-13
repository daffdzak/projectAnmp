package com.example.projectanmp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectanmp.databinding.FragmentScheduleBinding
import com.example.projectanmp.viewmodel.ScheduleViewModel
import com.example.projectanmp.adapter.ScheduleAdapter

class ScheduleFragment : Fragment() {
    private lateinit var binding: FragmentScheduleBinding
    private lateinit var viewModel: ScheduleViewModel
    private lateinit var scheduleAdapter: ScheduleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set layout manager and adapter for RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        scheduleAdapter = ScheduleAdapter(listOf()) // Pass an empty list initially
        binding.recyclerView.adapter = scheduleAdapter

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(ScheduleViewModel::class.java)

        // Observe ViewModel to update UI with data
        observeViewModel()

        // Call refresh to load the data
        viewModel.refresh()
    }

    private fun observeViewModel() {
        // Observe LiveData for upcoming events
        viewModel.upcomingEventsLD.observe(viewLifecycleOwner, Observer { events ->
            events?.let {
                scheduleAdapter.updateEvents(it)
                binding.recyclerView.visibility = View.VISIBLE
                Log.d("ScheduleFragment", "Event list size: ${it.size}")
            }
        })

        // Observe LiveData for errors
        viewModel.scheduleLoadErrorLD.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                if (it) {
                    Log.e("ScheduleFragment", "Error loading schedule data")
                }
            }
        })

        // Observe LiveData for loading state
        viewModel.loadingLD.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                binding.recyclerView.visibility = if (it) View.GONE else View.VISIBLE
            }
        })
    }
}
