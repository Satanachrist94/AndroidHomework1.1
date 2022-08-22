package ru.netology.nmedia.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
 data class PostEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id :Long,
    val author :String,
    val content :String,
    val published :String,
    @ColumnInfo(name = "liked_by_me")
    var likeByMe :Boolean ,
    var likeCount:Long,
    var repostCount :Long,
    var videoUrl :String = "https://www.youtube.com/watch?v=WhWc3b3KhnY")
