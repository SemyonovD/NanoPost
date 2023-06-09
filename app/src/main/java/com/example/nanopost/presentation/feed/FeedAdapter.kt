package com.example.nanopost.presentation.feed

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nanopost.R
import com.example.nanopost.domen.models.Post
import coil.load
import com.example.nanopost.databinding.PostItemBinding
import com.example.nanopost.presentation.inflate


class FeedAdapter : PagingDataAdapter<Post, FeedAdapter.ViewHolder>(DiffCallback) {

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
        parent.inflate(R.layout.post_item),
    ) {


        private val binding by viewBinding(PostItemBinding::bind)
        fun bind(item: Post) = with(binding) {
            val urll = item.images.getOrNull(0)?.sizes?.getOrNull(1)?.url
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
                onItemClick(item.id)
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
