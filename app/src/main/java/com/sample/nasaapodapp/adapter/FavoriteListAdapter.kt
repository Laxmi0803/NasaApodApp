package com.sample.nasaapodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sample.nasaapodapp.databinding.ItemFavoriteListBinding
import com.sample.nasaapodapp.model.ApodDTO

class FavoriteListAdapter(private val listener: OnItemClickListener) :  ListAdapter<ApodDTO, FavoriteListAdapter.FavoriteViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteListBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class FavoriteViewHolder(private val binding: ItemFavoriteListBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if(position != RecyclerView.NO_POSITION){
                        val apod = getItem(position)
                        listener.onItemClick(apod)
                    }
                }
            }
        }

        fun bind(apod: ApodDTO){
            binding.apply {
                Glide.with(itemView)
                    .load(apod.url)
                    .into(ivApodImage)
                tvDescription.text = apod.explanation
                tvTitle.text = apod.title
                tvDate.text = apod.date

            }
        }
    }
    interface OnItemClickListener{
        fun onItemClick(apod: ApodDTO)
    }

    class DiffCallback : DiffUtil.ItemCallback<ApodDTO>(){
        override fun areItemsTheSame(oldItem: ApodDTO, newItem: ApodDTO): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: ApodDTO, newItem: ApodDTO): Boolean {
            return oldItem == newItem
        }

    }
}