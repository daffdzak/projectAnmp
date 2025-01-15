package com.example.projectanmp.view

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.projectanmp.R
import com.example.projectanmp.model.GameDatabase
import com.example.projectanmp.model.Member
import com.example.projectanmp.viewmodel.TeamViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch




class TeamDetailFragment : Fragment() {
    private val teamViewModel: TeamViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_team_detail, container, false)

        val teamNameTextView: TextView = view.findViewById(R.id.teamNameTextView)
        val teamMembersLayout: LinearLayout = view.findViewById(R.id.teamMembersLayout)

        val teamId = arguments?.getInt("teamId") ?: -1

        if (teamId != -1) {
            teamViewModel.loadMembersForTeam(teamId)
        }

        teamViewModel.selectedTeamMembers.observe(viewLifecycleOwner) { members ->
            teamMembersLayout.removeAllViews()

            if (members.isNullOrEmpty()) {
                teamMembersLayout.addView(TextView(requireContext()).apply {
                    text = "No members found for this team."
                    textSize = 18f
                })
            } else {
                members.forEach { member ->
                    val memberTextView = TextView(requireContext()).apply {
                        text = member.name
                        textSize = 18f
                    }
                    teamMembersLayout.addView(memberTextView)
                }
            }
        }

        return view
    }
}


