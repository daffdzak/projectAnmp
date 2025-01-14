package com.example.projectanmp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectanmp.databinding.ItemApplyBinding
import com.example.projectanmp.model.Apply

class ApplyAdapter(private var applies: List<Apply>) : RecyclerView.Adapter<ApplyAdapter.ApplyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplyViewHolder {
        val binding = ItemApplyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApplyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ApplyViewHolder, position: Int) {
        val apply = applies[position]
        holder.binding.textProposal.text = "${apply.game} - ${apply.team}"
        holder.binding.textStatus.text = apply.status
    }

    override fun getItemCount(): Int = applies.size

    fun updateProposal(newProposal: List<Apply>) {
        applies = newProposal
        notifyDataSetChanged()
    }
    class ApplyViewHolder(val binding:ItemApplyBinding ) : RecyclerView.ViewHolder(binding.root)
}