package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.db.PostEntity

interface PostRepository {
    val data: LiveData<List<Post>>
    fun like(postId: Long)
    fun repost(postId: Long)
    fun delete ( postId: Long)
    fun save (post :Post)

}