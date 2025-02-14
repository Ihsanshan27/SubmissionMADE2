package com.dicoding.submissionmade1.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submissionmade1.core.domain.model.Events
import com.dicoding.submissionmade1.databinding.ItemVerticalBinding
import com.dicoding.submissionmade1.ui.detail.DetailActivity

class EventsAdapter : ListAdapter<Events, EventsAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(private val binding: ItemVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Events) {
            binding.tvItemNameHomeVertical.text = item.name
            binding.tvCityName.text = item.cityName
            Glide.with(itemView.context)
                .load(item.mediaCover)
                .into(binding.imgItemCoverHomeVertical)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID, item.id)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding =
            ItemVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val events = getItem(position)
        holder.bind(events)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Events>() {
            override fun areItemsTheSame(oldItem: Events, newItem: Events): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Events, newItem: Events): Boolean {
                return oldItem == newItem
            }
        }
    }
}