package com.example.galeria.ui.dashboard

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galeria.R
import com.example.galeria.databinding.ImageListBinding
import com.example.galeria.models.pageOfImagesModel.Result

class ImagesPageAdapter(private val onClickListener: OnClickListener): ListAdapter<Result,ImagesPageAdapter.ImageViewHolder>(DiffCallback()) {

    inner class ImageViewHolder(private val binding: ImageListBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(result: Result){
            binding.apply {
                Glide.with(binding.root).load(result.urls.regular).
                centerInside().placeholder(R.drawable.ic_baseline_favorite_24).
                into(binding.singleImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ImageViewHolder {
        val binding = ImageListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.itemView.setOnClickListener{onClickListener.onClick(currentItem)}
        holder.bind(currentItem)

    }

    class DiffCallback: DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Result, newItem: Result) =
            oldItem == newItem
    }

    class OnClickListener(val clickListener: (result: Result) -> Unit) {
        fun onClick(result: Result) = clickListener(result)
    }
}