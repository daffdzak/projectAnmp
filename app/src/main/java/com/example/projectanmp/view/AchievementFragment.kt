//package com.example.projectanmp.view
//
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.AdapterView
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.projectanmp.databinding.FragmentAchievementBinding
//import com.example.projectanmp.model.Achievement
//import com.example.projectanmp.viewmodel.AchievementViewModel
//import com.example.projectanmp.viewmodel.GameViewModel
//import android.widget.ArrayAdapter
//
//
//class AchievementFragment : Fragment() {
//
//    private lateinit var binding: FragmentAchievementBinding
//    private lateinit var viewModel: AchievementViewModel
//    private lateinit var achievementAdapter: AchievementAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentAchievementBinding.inflate(inflater, container, false)
//
//        // Setup RecyclerView
//        binding.recAchievement.layoutManager = LinearLayoutManager(context)
//        achievementAdapter = AchievementAdapter(mutableListOf())
//        binding.recAchievement.adapter = achievementAdapter
//
//        // Ambil data achievements dari argument yang dikirim dari GameAdapter
//        val achievementsAsStrings = arguments?.getStringArray("achievements") ?: emptyArray()
//
//        // Konversi data string ke dalam list Achievement
//        val achievements = achievementsAsStrings.map { achievementString ->
//            val parts = achievementString.split(";")
//            Achievement(
//                eventName = parts[0],
//                team = parts[1],
//                year = parts[2].toIntOrNull() ?: 0 // Pastikan year bisa dikonversi ke Int
//            )
//        }
//
//        // Update RecyclerView dengan data achievement
//        achievementAdapter.updateAchievements(achievements)
//
//        val years = listOf(2022, 2023).map {it.toString()}
//        binding.spinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, years)
//
//        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
//                val selectedYear = years[position].toInt() // Mengambil tahun yang dipilih
//                filterAchievementsByYear(selectedYear) // Memanggil fungsi untuk memfilter data
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                // Tidak ada tindakan diperlukan di sini
//            }
//        }
//
//
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val args = AchievementFragmentArgs.fromBundle(requireArguments())
//        val achievements = args.achievement
//
//        val achievementList = achievements.map {
//            val parts = it.split(";")
//            Achievement(parts[0], parts[1], parts[2].toInt())
//        }
//
//        viewModel = ViewModelProvider(this).get(AchievementViewModel::class.java)
//
//        observeViewModel()
//
//        viewModel.refresh()
//    }
//
//    private fun filterAchievementsByYear(year: Int) {
//        val filteredAchievements = viewModel.achievementLD.value?.filter { achievement ->
//            achievement.year == year
//        } ?: emptyList() // Jika achievementLD adalah null, gunakan daftar kosong
//
//        achievementAdapter.updateAchievements(filteredAchievements) // Memperbarui adapter dengan data yang difilter
//    }
//
//    private fun observeViewModel() {
//        viewModel.achievementLD.observe(viewLifecycleOwner, Observer { achievementList ->
//            achievementList?.let {
//               // achievementAdapter = AchievementAdapter(it)
//                //binding.recAchievement.adapter = achievementAdapter
//                achievementAdapter.updateAchievements(it)
//            }
//        })
//
//        viewModel.achievementLoadErrorLD.observe(viewLifecycleOwner, Observer { isError ->
//            isError?.let {
//                if (it) {
//                    Log.e("AchievementFragment", "Error loading achievement data")
//                }
//            }
//        })
//
//        viewModel.loadingLD.observe(viewLifecycleOwner, Observer { isLoading ->
//            isLoading?.let {
//                binding.recAchievement.visibility = if (it) View.GONE else View.VISIBLE
//            }
//        })
//    }
//}
//
