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

        val teamList = listOf(
            Team("TEAM A", listOf("Faker", "Chovy", "Keria", "Gumayusi", "Stelle")),
            Team("TEAM B", listOf("Moy", "HellMoura", "Kenz", "DivineAngle", "Ruler")),
            Team("TEAM C", listOf("Oura", "Brusko", "Irrad", "AudyTzy", "Clover"))
        )

        val listView: ListView = view.findViewById(R.id.teamListView)

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, teamList.map { it.name })
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedTeam = teamList[position]
            teamViewModel.selectTeam(selectedTeam)

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.hostFragment, TeamDetailFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}

