package com.example.projectanmp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectanmp.databinding.ItemScheduleBinding
import com.example.projectanmp.model.UpcomingEvent

class ScheduleAdapter(private var events: List<UpcomingEvent>) : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    class ScheduleViewHolder(val binding: ItemScheduleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemScheduleBinding.inflate(inflater, parent, false)
        return ScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val event = events[position]
        holder.binding.apply {
            txtTGL.text = event.day.toString()
            txtBLN.text = event.month
            txtNama.text = event.event_name
        }
    }

    override fun getItemCount() = events.size

    fun updateEvents(newEvents: List<UpcomingEvent>) {
        events = newEvents
        notifyDataSetChanged()
    }
}
