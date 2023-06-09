package com.example.nanopost.presentation.profile

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.nanopost.R
import com.example.nanopost.databinding.PostItemBinding
import com.example.nanopost.domen.models.Post
import com.example.nanopost.presentation.inflate

class PostAdapter : PagingDataAdapter<Post, PostAdapter.ViewHolder>(DiffCallback) {

    var onPostClick: (String) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        return ViewHolder(parent, onPostClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {holder.bind(it)}
    }

    class ViewHolder(
        parent: ViewGroup,
        var onPostClick: (String) -> Unit = {},
    ) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.post_item),
    ) {
        private val binding by viewBinding(PostItemBinding::bind)

        fun bind(item: Post) = with(binding) {
            val urll = item.images.getOrNull(0)?.sizes?.getOrNull(0)?.url
            if (urll != null) {
                placeholder1.load(urll)
            } else {
                placeholder1.visibility = View.GONE
            }
            displayNameField.text = item.owner.displayName
            if(item.text == null) {
                postTextField.visibility = View.GONE
            } else {
                postTextField.text = item.text
            }
            avatar.load(item.owner.avatarUrl)
            if (item.owner.avatarUrl == null) {
                item.owner.displayName?.get(0).let {
                    binding.letterAvatar.text = it.toString()
                }
            }
            dateTimeField.text = item.dateCreated
            if (item.likes.liked) {
                likeButton.visibility = View.GONE
                likedButton.visibility = View.VISIBLE
                likedButton.text = item.likes.likesCount.toString()
            } else {
                likeButton.visibility = View.VISIBLE
                likedButton.visibility = View.GONE
                likeButton.text = item.likes.likesCount.toString()
            }
            root.setOnClickListener {
                onPostClick(item.id)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Post>() {

        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }
}