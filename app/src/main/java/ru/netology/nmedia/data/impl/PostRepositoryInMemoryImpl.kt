package ru.netology.nmedia.data.impl

import android.provider.Settings.Global.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.data.PostRepository

class PostRepositoryInMemoryImpl : PostRepository {
    private var nextID = 1000L


    override val data = MutableLiveData(
        List(100) { index ->
            Post(
                id = index + 1L,
                author = "Нетология.Меняем карьеру через образование",
                content = "Рандомный пост № $index",
                published = "25.06.22",
                likeByMe = false,
                likeCount = 1200,
                repostConut = 0
            )
        })

    private val posts
        get() = checkNotNull(data.value) {

        }


    override fun like(postId: Long) {
        data.value = posts.map {
            if (it.id == postId) it.copy(
                likeByMe = !it.likeByMe
            ) else it


        }
    }

    override fun repost(postId: Long) {

        data.value = posts.map {
            if (it.id == postId) it.copy(
                repostConut = it.repostConut + 1
            )
            else it
        }
    }

    override fun delete(postId: Long) {
        data.value = posts.filter {
            it.id != postId
        }
    }

    override fun save(post: Post) {
        if(post.id ==PostRepository.NEW_POST_ID) add(post) else update(post)
    }

    private fun add(post: Post) {
        data.value = listOf(post.copy(
            id =++nextID

        )) +posts


    }

    private fun update(post: Post) {

        data.value = posts.map {
            if(it.id ==post.id) post else it
        }
    }
}
