package com.example.nanopost.presentation.profile

import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.nanopost.R
import com.example.nanopost.databinding.BioProfileBinding
import com.example.nanopost.domen.models.Profile
import com.example.nanopost.presentation.inflate

class HeaderAdapter(
    private val item: Profile,
    private val ourId: String?
    ): RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder>() {

    var onEditClick: (List<String?>) -> Unit = {}
    var onSubscribeClick: (String) -> Unit = {}
    var onUnsubscribeClick: (String) -> Unit = {}
    var onImagesClick: (String) -> Unit = {}
    var onAvatarClick: () -> Unit  = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        return HeaderViewHolder(
            parent,
            onEditClick,
            onSubscribeClick,
            onUnsubscribeClick,
            onImagesClick,
            onAvatarClick,
            ourId
        )
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
        private var onAvatarClick: () -> Unit = {},
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

            placeholder00.visibility = View.GONE
            placeholder01.visibility = View.GONE
            placeholder02.visibility = View.GONE
            placeholder03.visibility = View.GONE
            textNoImages.visibility = View.VISIBLE

            if (item.avatarId == null) {
                item.displayName?.get(0).let {
                    letterAvatar.text = it.toString()
                    letterAvatar.visibility = View.VISIBLE
                }
            } else {
                avatar.load(item.avatarLarge)
                letterAvatar.visibility = View.GONE
            }
            displayNameField.text = item.displayName
            bioField.text = item.bio
            imagesCountField.text = item.imagesCount.toString()
            subscribersCountField.text = item.subscribersCount.toString()
            postsCountField.text = item.postsCount.toString()

            item.images.getOrNull(0)?.sizes?.getOrNull(0)?.url?.let {
                placeholder00.load(it)
                placeholder00.visibility = View.VISIBLE
                textNoImages.visibility = View.GONE
            }
            item.images.getOrNull(1)?.sizes?.getOrNull(0)?.url?.let {
                placeholder01.load(it)
                placeholder01.visibility = View.VISIBLE
            }
            item.images.getOrNull(2)?.sizes?.getOrNull(0)?.url?.let {
                placeholder02.load(it)
                placeholder02.visibility = View.VISIBLE
            }
            item.images.getOrNull(3)?.sizes?.getOrNull(0)?.url?.let {
                placeholder03.load(it)
                placeholder03.visibility = View.VISIBLE
            }

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
                it.visibility = View.GONE
                saveChangesButton.visibility = View.VISIBLE

                enterDisplayName.visibility = View.VISIBLE
                displayNameField.visibility = View.GONE
                enterBioField.visibility = View.VISIBLE
                bioField.visibility = View.GONE

                enterDisplayName.setText(item.displayName)
                enterBioField.setText(item.bio)

                avatar.setOnClickListener {
                    onAvatarClick()
                }
            }
            saveChangesButton.setOnClickListener{
                it.visibility = View.GONE
                editButton.visibility = View.VISIBLE

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