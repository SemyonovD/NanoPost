package com.example.nanopost.presentation.profile

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nanopost.R
import com.example.nanopost.databinding.BioProfileBinding
import com.example.nanopost.domen.models.Profile
import com.example.nanopost.presentation.inflate

class HeaderAdapter(private val item: Profile, private val ourId: String?): RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder>() {

    var onEditClick: (List<String?>) -> Unit = {}
    var onSubscribeClick: (String) -> Unit = {}
    var onUnsubscribeClick: (String) -> Unit = {}
    var onImagesClick: (String) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        return HeaderViewHolder(parent, onEditClick, onSubscribeClick, onUnsubscribeClick, onImagesClick, ourId)
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return 1
    }

    class HeaderViewHolder(
        parent: ViewGroup,
        var onEditClick: (List<String?>) -> Unit = {},
        var onSubscribeClick: (String) -> Unit = {},
        var onUnsubscribeClick: (String) -> Unit = {},
        var onImagesClick: (String) -> Unit = {},
        private val ourId: String?
    ) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.bio_profile)
    ) {
        private val binding by viewBinding(BioProfileBinding::bind)

        fun bind(item: Profile) = with(binding) {
            editButton.visibility = View.GONE
            saveChangesButton.visibility = View.GONE
            subscribeButton.visibility = View.GONE
            unsubscribeButton.visibility = View.GONE

            enterDisplayName.visibility = View.GONE
            displayNameField.visibility = View.VISIBLE
            enterBioField.visibility = View.GONE
            bioField.visibility = View.VISIBLE

            if (item.avatarId == null) {
                item.displayName?.get(0).let {
                    letterAvatar.text = it.toString()
                }
            }
            displayNameField.text = item.displayName
            bioField.text = item.bio
            imagesCountField.text = item.imagesCount.toString()
            subscribersCountField.text = item.subscribersCount.toString()
            postsCountField.text = item.postsCount.toString()

            if (item.subscribed) {
                editButton.visibility = View.GONE
                subscribeButton.visibility = View.GONE
                unsubscribeButton.visibility = View.VISIBLE
            } else if (ourId == item.id) {
                editButton.visibility = View.VISIBLE
                subscribeButton.visibility = View.GONE
                unsubscribeButton.visibility = View.GONE
            } else {
                editButton.visibility = View.GONE
                subscribeButton.visibility = View.VISIBLE
                unsubscribeButton.visibility = View.GONE
            }

            unsubscribeButton.setOnClickListener {
                onUnsubscribeClick(item.id)
            }
            editButton.setOnClickListener {
                enterDisplayName.visibility = View.VISIBLE
                displayNameField.visibility = View.GONE
                enterBioField.visibility = View.VISIBLE
                bioField.visibility = View.GONE
                saveChangesButton.visibility = View.VISIBLE

                enterDisplayName.setText(item.displayName)
                enterBioField.setText(item.bio)
            }
            saveChangesButton.setOnClickListener{
                enterDisplayName.visibility = View.GONE
                displayNameField.visibility = View.VISIBLE
                enterBioField.visibility = View.GONE
                bioField.visibility = View.VISIBLE
                onEditClick(
                    listOf
                        (
                        item.id,
                        enterDisplayName.text.toString(),
                        enterBioField.text.toString(),
                        null
                    )

                )
            }
            subscribeButton.setOnClickListener {
                onSubscribeClick(item.id)
            }
            chevronRight.setOnClickListener {
                onImagesClick(item.id)
            }
        }
    }

}