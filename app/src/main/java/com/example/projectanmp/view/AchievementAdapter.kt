package com.example.projectanmp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectanmp.databinding.ItemAchievementBinding
import com.example.projectanmp.model.Achievement

class AchievementAdapter(private val achievementList: MutableList<Achievement>) :
    RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder>() {

    inner class AchievementViewHolder(val binding: ItemAchievementBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(achievement: Achievement) {
            val formattedText = "${achievement.eventName} - ${achievement.team} (${achievement.year})"
            binding.txtAchievement.text = formattedText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        val binding = ItemAchievementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AchievementViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        holder.bind(achievementList[position])
    }

    fun updateAchievements(newAchievements: List<Achievement>) {
        achievementList.clear()
        achievementList.addAll(newAchievements)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = achievementList.size
}
