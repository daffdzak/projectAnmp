package com.example.projectanmp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projectanmp.R
import com.example.projectanmp.databinding.FragmentScheduleDetailBinding
import com.squareup.picasso.Picasso


class ScheduleDetailFragment : Fragment() {

    private lateinit var binding: FragmentScheduleDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleDetailBinding.inflate(inflater, container, false)

        val args = ScheduleDetailFragmentArgs.fromBundle(requireArguments())
        val eventImage = args.eventImage
        val eventDescription = args.eventDescription

        binding.eventDescription.text = eventDescription
        Picasso.get().load(eventImage).into(binding.eventImage)

        return binding.root
    }
}
