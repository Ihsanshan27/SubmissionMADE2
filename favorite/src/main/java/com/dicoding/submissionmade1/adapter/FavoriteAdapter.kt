package com.dicoding.submissionmade1.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submissionmade1.core.domain.model.Favorite
import com.dicoding.submissionmade1.databinding.ItemVerticalBinding
import com.dicoding.submissionmade1.ui.detail.DetailActivity

class FavoriteAdapter : ListAdapter<Favorite, FavoriteAdapter.ViewHolder>(DIFF_CALLBACK) {
    class ViewHolder(private val binding: ItemVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Favorite) {
            binding.tvItemNameHomeVertical.text = item.name
            binding.tvCityName.text = item.cityName
            Glide.with(itemView.context)
                .load(item.mediaCover)
                .into(binding.imgItemCoverHomeVertical)

            itemView.setOnClickListener {
                val goDetail = Intent(itemView.context, DetailActivity::class.java)
                goDetail.putExtra(DetailActivity.EXTRA_ID, item.id.toInt())
                itemView.context.startActivity(goDetail)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        val binding =
            ItemVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.ViewHolder, position: Int) {
        val events = getItem(position)
        holder.bind(events)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Favorite>() {
            override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem == newItem
            }
        }
    }
}