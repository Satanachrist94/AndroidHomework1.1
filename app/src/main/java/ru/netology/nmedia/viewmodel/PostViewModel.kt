package ru.netology.nmedia.viewmodel

import PostReposytoryFileImpl
import SingleLiveEvent
import android.app.Application
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.Group
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Post
import ru.netology.nmedia.adapter.PostListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.PostRepositoryInMemoryImpl


class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostReposytoryFileImpl(application)


    val data get() = repository.data
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
            id = PostRepository.NEW_POST_ID,
            author = "Me",
            content = content,
            published = "Today",
            likeByMe = false,
            likeCount = 0L,
            repostConut = 0L
        )
        repository.save(newPost)
        currentPost.value = null


    }


}