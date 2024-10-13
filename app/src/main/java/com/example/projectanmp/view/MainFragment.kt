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
import com.example.projectanmp.viewmodel.GameViewModel

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: GameViewModel
    private lateinit var gameAdapter: GameAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        gameAdapter = GameAdapter(listOf())
        binding.recyclerView.adapter = gameAdapter

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        observeViewModel()

        binding.refreshLayout.setOnRefreshListener {
            binding.recyclerView.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            viewModel.refresh()
            binding.refreshLayout.isRefreshing = false
        }
        binding.progressBar.visibility = View.VISIBLE
        viewModel.refresh()
    }

    private fun observeViewModel() {
        viewModel.GamesLD.observe(viewLifecycleOwner, Observer { games ->
            games?.let {
                gameAdapter.updateGames(it)
                binding.recyclerView.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                Log.d("MainFragment", "Game list size: ${it.size}")
            }
        })

        viewModel.gameLoadErrorLD.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                if (it) {
                    Log.e("MainFragment", "Error loading data")
                }
            }
        })

        viewModel.LoadingLD.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                if (it) {
                    binding.recyclerView.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                }
            }
        })
    }
}
