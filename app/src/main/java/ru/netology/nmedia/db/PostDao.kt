package ru.netology.nmedia.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.netology.nmedia.Post



@Dao
 interface PostDao {

@Query("SELECT * FROM posts ORDER BY id DESC")
    fun getAll()  : LiveData<List<PostEntity>>

    @Query ("UPDATE posts SET repostCount = repostCount + 1 WHERE id =:postId ")
    fun repost (postId: Long)

    @Insert
    fun insert(post: PostEntity)

    @Query("UPDATE posts SET content = :content WHERE id = :id")
    fun updateContentById(id: Long, content: String)

    fun save(post: PostEntity) =
        if (post.id == 0L) insert(post) else updateContentById(post.id, post.content)

    @Query("""
        UPDATE posts SET
        likeCount = likeCount + CASE WHEN liked_by_me THEN -1 ELSE 1 END,
        liked_by_me = CASE WHEN liked_by_me THEN 0 ELSE 1 END
        WHERE id = :id
        """)
    fun likeById(id: Long)

    @Query("DELETE FROM posts WHERE id = :id")
    fun delete(id: Long)
}