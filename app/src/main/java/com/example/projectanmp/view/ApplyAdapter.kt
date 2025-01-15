package com.example.projectanmp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectanmp.R
import com.example.projectanmp.databinding.ItemApplyBinding
import com.example.projectanmp.model.Apply

class ApplyAdapter(private val applyList: MutableList<Apply>) :
    RecyclerView.Adapter<ApplyAdapter.ApplyViewHolder>() {

    inner class ApplyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textProposal: TextView = itemView.findViewById(R.id.textProposal)
        private val textStatus: TextView = itemView.findViewById(R.id.textStatus)

        fun bind(apply: Apply) {
            textProposal.text = "${apply.game} - ${apply.team}"
            textStatus.text = apply.status
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_apply, parent, false)
        return ApplyViewHolder(view)
    }

    override fun onBindViewHolder(holder: ApplyViewHolder, position: Int) {
        holder.bind(applyList[position])
    }

    override fun getItemCount(): Int = applyList.size

    fun updateApplies(newApplies: List<Apply>) {
        applyList.clear()
        applyList.addAll(newApplies)
        notifyDataSetChanged()
    }
}



