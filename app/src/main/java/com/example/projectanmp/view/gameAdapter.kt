package com.example.projectanmp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.projectanmp.R
import com.example.projectanmp.databinding.ItemGamesBinding
import com.example.projectanmp.model.EsportGame
import com.squareup.picasso.Picasso

class GameAdapter(private val gameList: List<EsportGame>) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    class GameViewHolder(val binding: ItemGamesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = ItemGamesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val currentItem = gameList[position]
        holder.binding.txtNama.text = currentItem.game_title
        holder.binding.txtDeskripsi.text = currentItem.description

        holder.binding.btnAchievement.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToAchievementFragment()
            Navigation.findNavController(it).navigate(action)
        }


        Picasso.get()
            .load(currentItem.image)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.placeholder_image) // Gambar error jika URL gagal dimuat
            .into(holder.binding.imgGame) // Menggunakan binding untuk mengambil ImageView
    }

    override fun getItemCount(): Int = gameList.size
}
