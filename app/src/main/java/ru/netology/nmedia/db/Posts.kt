package ru.netology.nmedia.db

import android.database.Cursor
import androidx.room.util.TableInfo
import ru.netology.nmedia.Post

 internal fun PostEntity.toModel() = Post(
    id = id,
            author = author,
    content = content,
    published = published,
    likeByMe = likeByMe  ,
    likeCount = likeCount,
    repostCount = repostCount,
    videoUrl = videoUrl

)
 fun Post.toEntity() = PostEntity(
    id = id,
    author = author,
    content = content,
    published = published,
    likeByMe = likeByMe  ,
    likeCount = likeCount,
    repostCount = repostCount,
    videoUrl = videoUrl

)
