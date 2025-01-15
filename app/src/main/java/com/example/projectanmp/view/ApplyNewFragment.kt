package com.example.projectanmp.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.projectanmp.R
import com.example.projectanmp.databinding.FragmentApplyNewBinding
import com.example.projectanmp.model.Apply
import com.example.projectanmp.viewmodel.ApplynewViewModel


class ApplyNewFragment : Fragment() {

    private lateinit var binding: FragmentApplyNewBinding
    private lateinit var viewModel: ApplynewViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentApplyNewBinding.inflate(inflater, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ApplynewViewModel::class.java)

        setupGameSpinner()
        setupSubmitButton()
    }

    private fun setupGameSpinner() {
        viewModel.gameLD.observe(viewLifecycleOwner) { games ->
            if (!games.isNullOrEmpty()) {
                val gameAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    games.map { it.gameName }
                )
                gameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerGame.adapter = gameAdapter

                // Update teams when a game is selected
                binding.spinnerGame.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>) {}

                    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                        val selectedGame = games[position].gameName
                        loadTeamsForGame(selectedGame)
                    }
                }
            } else {
                Toast.makeText(requireContext(), "No games available", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadTeamsForGame(gameName: String) {
        viewModel.loadTeams(gameName).observe(viewLifecycleOwner) { teams ->
            if (!teams.isNullOrEmpty()) {
                val teamAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    teams.map { it.teamName }
                )
                teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerTeam.adapter = teamAdapter
            } else {
                Toast.makeText(requireContext(), "No teams available for this game", Toast.LENGTH_SHORT).show()
                binding.spinnerTeam.adapter = null
            }
        }
    }

    private fun setupSubmitButton() {
        binding.buttonSubmit.setOnClickListener {
            val game = binding.spinnerGame.selectedItem?.toString() ?: ""
            val team = binding.spinnerTeam.selectedItem?.toString() ?: ""
            val description = binding.editTextTextMultiLine.text.toString()
            val username = sharedPreferences.getString("username", "UnknownUser") ?: "UnknownUser"

            if (game.isEmpty() || team.isEmpty() || description.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val proposal = Apply(
                    game = game,
                    team = team,
                    description = description,
                    status = "WAITING",
                    username = username
                )
                viewModel.submitProposal(proposal)
                Toast.makeText(requireContext(), "Proposal submitted successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_itemApplyNew_to_itemApply)
            }
        }
    }
}
