package com.example.galeria.ui.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galeria.R
import com.example.galeria.databinding.ImageListBinding
import com.example.galeria.models.randomImageModel.Urls

class ImageUrlsAdapter : ListAdapter<Urls, ImageUrlsAdapter.ImageViewHolder>(DiffCallback()) {

    class ImageViewHolder(private val binding: ImageListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(urls: Urls){
            binding.apply {
                Glide.with(binding.root).load(urls.regular).centerInside().placeholder(R.drawable.ic_baseline_favorite_24).into(binding.singleImage)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ImageListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }
    class DiffCallback:DiffUtil.ItemCallback<Urls>(){
        override fun areItemsTheSame(oldItem: Urls, newItem: Urls) =
            oldItem.likedImageId == newItem.likedImageId

        override fun areContentsTheSame(oldItem: Urls, newItem: Urls) =
            oldItem == newItem
    }
}