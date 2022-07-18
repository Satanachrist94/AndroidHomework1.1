package ru.netology.nmedia.adapter

import android.view.View
import android.view.ViewGroup
import ru.netology.nmedia.Post
import java.security.acl.Group

interface PostListener {
    fun onLikeClicked(post: Post)
    fun onRepostClicked(post: Post)
    fun onDeleteClicked(post: Post)
    fun onUpdateClicked(post: Post)
    fun onCancelClicked()




}