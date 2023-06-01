package com.example.nanopost.presentation.images

import android.view.ViewGroup
import com.example.nanopost.domen.models.Image
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.nanopost.R
import com.example.nanopost.databinding.ImageItemBinding
import com.example.nanopost.presentation.inflate

class ImagesAdapter : PagingDataAdapter<Image, ImagesAdapter.ViewHolder>(DiffCallback) {

    var onItemClick: (String) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class ViewHolder(
        parent: ViewGroup,
        var onItemClick: (String) -> Unit = {},
    ) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.image_item),
    ) {
        private val binding by viewBinding(ImageItemBinding::bind)
        fun bind(item: Image) = with(binding) {
            image.load(item.sizes[1].url)
            root.setOnClickListener {
                onItemClick(item.id)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Image>() {

        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }
    }
}

