package com.example.projectanmp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.projectanmp.R
import com.example.projectanmp.viewmodel.TeamViewModel


class TeamDetailFragment : Fragment() {
    private val teamViewModel: TeamViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_team_detail, container, false)


        val teamNameTextView: TextView = view.findViewById(R.id.teamNameTextView)
        val teamMembersLayout: LinearLayout = view.findViewById(R.id.teamMembersLayout)


        teamViewModel.selectedTeam.observe(viewLifecycleOwner) { Team ->
            teamNameTextView.text = Team?.name ?: "No team selected"

            teamMembersLayout.removeAllViews()

            Team?.members?.forEach { member: String ->
                val memberTextView = TextView(requireContext()).apply {
                    text = member
                    textSize = 18f
                }
                teamMembersLayout.addView(memberTextView)
            }
        }

        return view
    }
}

