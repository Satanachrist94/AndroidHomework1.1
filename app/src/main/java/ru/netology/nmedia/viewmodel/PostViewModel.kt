package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.PostRepositoryInMemoryImpl

class PostViewModel:ViewModel() {
    private val repository :PostRepository = PostRepositoryInMemoryImpl()

    val data get() = repository.data

    fun onLikeClicked() = repository.like()
    fun onRepostClicked() = repository.repost()



}