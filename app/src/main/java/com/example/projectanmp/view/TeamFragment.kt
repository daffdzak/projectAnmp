package com.example.projectanmp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.activityViewModels
import com.example.projectanmp.R
import com.example.projectanmp.model.Team
import com.example.projectanmp.viewmodel.TeamViewModel


class TeamFragment : Fragment() {
    private val teamViewModel: TeamViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_team, container, false)

        // Hardcode daftar tim
        val teamList = listOf(
            Team("TEAM A", listOf("Member 1", "Member 2", "Member 3", "Member 4", "Member 5")),
            Team("TEAM B", listOf("Member 1", "Member 2", "Member 3", "Member 4", "Member 5")),
            Team("TEAM C", listOf("Member 1", "Member 2", "Member 3", "Member 4", "Member 5"))
        )

        val listView: ListView = view.findViewById(R.id.teamListView)

        // Adapter untuk menampilkan daftar tim
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, teamList.map { it.name })
        listView.adapter = adapter

        // Set on item click listener
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedTeam = teamList[position]
            teamViewModel.selectTeam(selectedTeam)

            // Ganti fragment ke TeamDetailFragment
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.hostFragment, TeamDetailFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}

