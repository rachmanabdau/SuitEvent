package com.example.suitevent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.suitevent.databinding.EventItemBinding
import com.example.suitevent.model.Event

class EventAdapter(
    private val clickListener: (Event) -> Unit
) : ListAdapter<Event, EventViewHolder>(EventDiffUtil) {

    companion object EventDiffUtil : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val viewBinding = EventItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EventViewHolder(viewBinding, clickListener)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class EventViewHolder(
    private val eventBinding: EventItemBinding,
    private val clickListener: (Event) -> Unit
) : RecyclerView.ViewHolder(eventBinding.root) {

    fun bind(data: Event) {
        eventBinding.eventData = data
        eventBinding.eventPicture.load(data.image)
        eventBinding.eventCard.setOnClickListener {
            clickListener.invoke(data)
        }
    }
}