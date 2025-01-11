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
import com.example.projectanmp.databinding.FragmentMainBinding
import com.example.projectanmp.model.Game
import com.example.projectanmp.viewmodel.GameViewModel

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: GameViewModel
    private val gameAdapter =GameAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        setupRecyclerView()
        setupSwipeRefreshLayout()
        observeViewModel()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = gameAdapter
        }
    }

    private fun setupSwipeRefreshLayout() {
        binding.refreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }



    private fun observeViewModel() {
        viewModel.gamesLD.observe(viewLifecycleOwner) { games ->
            games?. let {
                gameAdapter.updateGamesList(it as ArrayList<Game>)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.refreshLayout.isRefreshing = isLoading
        }

        viewModel.errorState.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                binding.progressBar.visibility = View.GONE
            } else {
            }
        }
    }
}
