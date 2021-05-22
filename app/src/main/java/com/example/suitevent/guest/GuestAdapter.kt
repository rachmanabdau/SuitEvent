package com.example.mymoviddb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.suitevent.R
import com.example.suitevent.databinding.GuestItemBinding
import com.example.suitevent.model.GuestResponse
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class GuestAdapter @Inject constructor() :
    PagingDataAdapter<GuestResponse.Result, GuestViewHolder>(DiffUtilCallback) {

    private var _actionDetail: (GuestResponse.Result) -> Unit = {}

    companion object DiffUtilCallback : DiffUtil.ItemCallback<GuestResponse.Result>() {
        override fun areItemsTheSame(
            oldItem: GuestResponse.Result,
            newItem: GuestResponse.Result
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GuestResponse.Result,
            newItem: GuestResponse.Result
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data, _actionDetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        val view =
            GuestItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GuestViewHolder(view)
    }

    fun setItemClick(itemClick: (GuestResponse.Result) -> Unit) {
        _actionDetail = itemClick
    }
}

class GuestViewHolder(private val binding: GuestItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: GuestResponse.Result?, actionDetail: (GuestResponse.Result) -> Unit) {
        data?.apply {
            binding.guestData = data
            binding.fullName = data.firstName + " " + data.lastName
            binding.guestAvatar.load(data.avatar) {
                placeholder(R.drawable.loading_img)
                error(R.drawable.ic_broken_image)
                transformations(CircleCropTransformation())
            }
            binding.guestConstraint.setOnClickListener {
                actionDetail(data)
            }
        }
    }
}