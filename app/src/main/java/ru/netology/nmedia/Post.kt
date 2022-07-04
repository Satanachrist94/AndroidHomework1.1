package ru.netology.nmedia

data class Post(
    val id :Long,
    val author :String,
    val content :String,
    val published :String,
    var likeByMe :Boolean ,
    var likeCount:Long,
    var repostConut :Long

)
