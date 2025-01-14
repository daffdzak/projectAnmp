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
import com.example.projectanmp.R
import com.example.projectanmp.databinding.FragmentApplyNewBinding
import com.example.projectanmp.viewmodel.ApplynewViewModel


class ApplyNewFragment : Fragment() {

    private lateinit var binding: FragmentApplyNewBinding
    private lateinit var viewModel: ApplynewViewModel
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentApplyNewBinding.inflate(inflater, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ApplynewViewModel::class.java)

        val spinnerGame = binding.spinnerGame
        val spinnerTeam = binding.spinnerTeam
        val editTextDescription = binding.editTextTextMultiLine
        val buttonSubmit = binding.buttonSubmit

        viewModel.gameLD.observe(viewLifecycleOwner) { games ->
            if (!games.isNullOrEmpty()) {
                val gameAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    games.map { it.gameName }
                )
                gameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerGame.adapter = gameAdapter
            } else {
                Toast.makeText(requireContext(), "No competitions available", Toast.LENGTH_SHORT)
                    .show()
            }
        }

//        spinnerGame.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(parentView: AdapterView<*>) {}
//
//            override fun onItemSelected(
//                parentView: AdapterView<*>,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                val selectedGameName = spinnerGame.selectedItem.toString()
//                viewModel.loadTeams(selectedGameName).observe(viewLifecycleOwner) { teams ->
//                    if (teams != null && teams.isNotEmpty()) {
//                        val teamAdapter = ArrayAdapter(
//                            requireContext(),
//                            android.R.layout.simple_spinner_item,
//                            teams.map { it.teamName }
//                        )
//                        teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                        spinnerTeam.adapter = teamAdapter
//                    } else {
//                        spinnerTeam.adapter = null
//                    }
//                }
//            }
//        }

    }


}