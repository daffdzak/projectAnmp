package com.example.projectanmp.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectanmp.R
import com.example.projectanmp.databinding.FragmentApplyBinding
import com.example.projectanmp.viewmodel.ApplyViewModel


class ApplyFragment : Fragment() {

    private lateinit var binding: FragmentApplyBinding
    private lateinit var viewModel: ApplyViewModel
    private lateinit var applyAdapter: ApplyAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentApplyBinding.inflate(inflater, container, false)

        binding.proposalRecyclerView.layoutManager = LinearLayoutManager(context)
        applyAdapter = ApplyAdapter(mutableListOf())
        binding.proposalRecyclerView.adapter = applyAdapter

        viewModel = ViewModelProvider(this).get(ApplyViewModel::class.java)

        sharedPreferences = requireActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", null) ?: ""

        observeViewModel()
        viewModel.refresh(username)

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_itemApply_to_itemApplyNew)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ApplyViewModel::class.java)

        val username = sharedPreferences.getString("username", null) ?: ""

        viewModel.refresh(username)
    }


    private fun observeViewModel() {
        viewModel.applyLD.observe(viewLifecycleOwner) { applies ->
            if (applies.isNullOrEmpty()) {
                binding.noDataTextView.visibility = View.VISIBLE
                binding.proposalRecyclerView.visibility = View.GONE
            } else {
                binding.noDataTextView.visibility = View.GONE
                binding.proposalRecyclerView.visibility = View.VISIBLE
                applyAdapter.updateApplies(applies)
            }
        }
    }
}









