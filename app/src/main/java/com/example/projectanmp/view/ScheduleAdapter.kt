package com.example.projectanmp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectanmp.databinding.ItemScheduleBinding
import com.example.projectanmp.model.UpcomingEvent
import com.example.projectanmp.model.UpcomingEventEntity

class ScheduleAdapter(
    private val eventList: MutableList<UpcomingEventEntity>,
    private val onItemClick: (UpcomingEventEntity) -> Unit
) : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    inner class ScheduleViewHolder(val binding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: UpcomingEventEntity) {
            binding.txtTGL.text = event.day.toString()
            binding.txtBLN.text = event.month
            binding.txtNama.text = event.eventName

            binding.root.setOnClickListener {
                onItemClick(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val binding = ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bind(eventList[position])
    }

    override fun getItemCount(): Int = eventList.size

    fun updateEvents(newEvents: List<UpcomingEventEntity>) {
        eventList.clear()
        eventList.addAll(newEvents)
        notifyDataSetChanged()
    }
}





