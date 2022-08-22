package ru.netology.nmedia.viewmodel

import SingleLiveEvent
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.netology.nmedia.Post
import ru.netology.nmedia.adapter.PostListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.PostRepositoryImpl
import ru.netology.nmedia.db.AppDb


class PostViewModel(application: Application) : AndroidViewModel(application){
    private val repository: PostRepository =PostRepositoryImpl(
        dao = AppDb.getInstance(
            context = application
        ).postDao
    )


    val data by repository :: data
    val navigateToPostContent = SingleLiveEvent<String>()
    val sharePostContent = SingleLiveEvent<String>()
    val currentPost = SingleLiveEvent<Post?>()
    val playVideoContent = SingleLiveEvent<String>()

    val navigateToPostDetails = SingleLiveEvent<Long>()


    fun onLikeClicked(post: Post) = repository.like(post.id)
    fun onRepostClicked(post: Post) = repository.repost(post.id)
    fun onDeleteClicked(post: Post) = repository.delete(post.id)




    fun onEditClicked(post: Post) {
        currentPost.value = post //отображение корректируемого поста на экране
        navigateToPostContent.value = post.content

    }

    fun onVideoClicked(post: Post) {
        post.videoUrl?.let {
            playVideoContent.value = it
        }
    }


    fun onSaveButtomClicked(content: String) {
        if (content.isBlank()) return
        val newPost = currentPost.value?.copy(
            content = content
        ) ?: Post(
            id = 1,
            author = "Me",
            content = content,
            published = "Today",
            likeByMe = false,
            likeCount = 0L,
            repostCount = 0L
        )
        repository.save(newPost)
        currentPost.value = null


    }


}