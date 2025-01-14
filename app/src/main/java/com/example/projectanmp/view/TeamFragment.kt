package com.example.projectanmp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectanmp.R
import com.example.projectanmp.model.Team
import com.example.projectanmp.viewmodel.TeamViewModel


class TeamFragment : Fragment() {

    private var gameId: Int = -1
    private lateinit var listView: ListView
    private lateinit var teamAdapter: ArrayAdapter<String>
    private lateinit var viewModel: TeamViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            gameId = it.getInt("gameId", -1) // Ambil gameId dari Safe Args
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_team, container, false)

        // Setup ListView
        listView = view.findViewById(R.id.teamListView)
        teamAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, mutableListOf())
        listView.adapter = teamAdapter

        // Setup ViewModel
        viewModel = ViewModelProvider(this).get(TeamViewModel::class.java)

        // Observe data tim dari ViewModel
        viewModel.getTeamsForGame(gameId).observe(viewLifecycleOwner) { teams ->
            val teamNames = teams.map { it.teamName } // Ubah ke daftar nama tim
            teamAdapter.clear()
            teamAdapter.addAll(teamNames) // Perbarui ListView dengan nama tim
        }

        return view
    }
}




