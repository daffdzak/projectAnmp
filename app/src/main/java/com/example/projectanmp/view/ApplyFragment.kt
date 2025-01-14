package com.example.projectanmp.view

import android.content.Context
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
    private lateinit var adapterApply: ApplyAdapter

    private fun getUsername(): String {
        return requireContext().getSharedPreferences("UserSession", Context.MODE_PRIVATE)
            .getString("username", "") ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentApplyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ApplyViewModel::class.java)
        adapterApply = ApplyAdapter(listOf())

        binding.proposalRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterApply
        }

        viewModel.applyLD.observe(viewLifecycleOwner) { applies ->
            if (applies.isNotEmpty()) {
                adapterApply.updateProposal(applies)
                binding.noDataTextView.visibility = View.GONE
            } else {
                binding.noDataTextView.visibility = View.VISIBLE
            }
        }

         binding.floatingActionButton.setOnClickListener {
            navigateToApplyNew()
        }
    }
    override fun onResume() {
        super.onResume()
        viewModel.refresh(getUsername())
    }

    private fun navigateToApplyNew() {
        findNavController().navigate(R.id.action_itemApply_to_itemApplyNew)
    }

}




