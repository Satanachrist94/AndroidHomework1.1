package ru.netology.nmedia

class Post(
    val id :Long,
    val author :String,
    val content :String,
    val published :String,
    var likeByMe :Boolean = false,
    var likeCount:Long = 0,
    var repostConut :Long =0
)
