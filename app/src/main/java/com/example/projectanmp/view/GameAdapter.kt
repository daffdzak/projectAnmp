package com.example.projectanmp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.projectanmp.R
import com.example.projectanmp.databinding.ItemGamesBinding
import com.example.projectanmp.model.EsportGame
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class GameAdapter(private var gameList: List<EsportGame>) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    class GameViewHolder(val binding: ItemGamesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = ItemGamesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)


    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val currentItem = gameList[position]
        holder.binding.txtNama.text = currentItem.game_title
        holder.binding.txtDeskripsi.text = currentItem.description

//        holder.binding.btnAchievement.setOnClickListener {
//            val achievementsAsStrings = currentItem.achievements.map {
//               "${it.event_name};${it.team};${it.year}"
//            }.toTypedArray()
//            val action = MainFragmentDirections.actionMainFragmentToAchievementFragment(achievementsAsStrings)
//            holder.itemView.findNavController().navigate(action)
//        }

        holder.binding.btnTeams.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToTeamFragment()
            holder.itemView.findNavController().navigate(action)
        }

        Picasso.get()
            .load(currentItem.image)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.placeholder_image)
            .into(holder.binding.imgGame, object : Callback {
                override fun onSuccess() {
                }

                override fun onError(e: Exception?) {
                    e?.printStackTrace()
                }
            })


    }

    override fun getItemCount(): Int = gameList.size

    fun updateGames(newGameList: List<EsportGame>) {
        gameList = newGameList
        notifyDataSetChanged()
    }
}
