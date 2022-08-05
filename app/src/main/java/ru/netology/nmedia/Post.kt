package ru.netology.nmedia

data class Post(
    val id :Long,
    val author :String,
    val content :String,
    val published :String,
    var likeByMe :Boolean ,
    var likeCount:Long,
    var repostConut :Long,
    var videoUrl :String = "https://www.youtube.com/watch?v=WhWc3b3KhnY"

)
