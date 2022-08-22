package ru.netology.nmedia.data.impl

import androidx.lifecycle.Transformations
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.db.PostDao
import ru.netology.nmedia.db.PostEntity
import ru.netology.nmedia.db.toEntity
import ru.netology.nmedia.db.toModel

class PostRepositoryImpl( private val dao: PostDao) : PostRepository {
    override val data = Transformations.map(dao.getAll()) {list ->
        list.map {it.toModel()
        }
    }
    override fun like(postId: Long) {
        dao.likeById(postId)

    }

    override fun repost(postId: Long) {
        dao.repost(postId)


    }

    override fun delete(postId: Long) {
        dao.delete(postId)


    }

    override fun save(post: Post) {
        dao.save(post.toEntity())
       // if( post.id ==0L) dao.insert(post.toEntity())
     //   else dao.updateContentById(post.id, post.content)

        //dao.insert(post.toEntity())

    }



}








