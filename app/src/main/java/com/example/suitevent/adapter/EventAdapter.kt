package com.example.suitevent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.suitevent.databinding.EventItemBinding
import com.example.suitevent.databinding.EventItemPagerBinding
import com.example.suitevent.model.Event

class EventAdapter(
    private val eventList: List<Event>,
    private val clickListener: (Event) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isForMaps = false

    override fun getItemCount(): Int {
        return eventList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (isForMaps) {
            val viewBinding = EventItemPagerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return EventMapViewHolder(viewBinding, clickListener)
        }
        val viewBinding = EventItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EventViewHolder(viewBinding, clickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is EventViewHolder) {
            holder.bind(eventList[position])
        } else {
            (holder as EventMapViewHolder).bind(eventList[position])
        }
    }

    fun setAdapterForMaps(): EventAdapter {
        isForMaps = true
        return this
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

class EventMapViewHolder(
    private val eventBinding: EventItemPagerBinding,
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