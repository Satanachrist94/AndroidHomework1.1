package ru.netology.nmedia.data.impl

import android.provider.Settings.Global.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.data.PostRepository

class PostRepositoryInMemoryImpl : PostRepository {

    override val data = MutableLiveData(
        List(100) { index ->
            Post(
                id = index + 1L,
                author = "Нетология.Меняем карьеру через образование",
                content = "Записывайтесь на видеолекцию «Маркетплейсы: " +
                        "как выбрать площадку для размещения товаров» С " +
                        "конца 2021 года в онлайн-ритейле стали говорить о" +
                        "второй волне маркетплейсов в России. В таком многообразии" +
                        " бывает сложно выбрать оптимальную платформу для продажи " +
                        "своих товаров. Как выбрать подходящий маркетплейс для вашего" +
                        " интернет-магазина — разберёмся на занятии. Вы узнаете, что из" +
                        " себя представляют маркетплейсы, чем они отличаются от агрегаторов " +
                        "и зачем нужно сейчас занять эту нишу. Выбирайте маркетплейс, который вам подходит:",
                published = "25.06.22",
                likeByMe = false,
                likeCount = 1200,
                repostConut = 0
            )
        })
    private val posts
        get() = checkNotNull(data.value) {

        }


    override fun like(postId: Long) {
        data.value = posts.map {
            if (it.id == postId) it.copy(
                likeByMe = !it.likeByMe
            ) else it


        }
    }

    override fun repost(postId: Long) {

        data.value = posts.map {
            if (it.id == postId) it.copy(
                repostConut = it.repostConut + 1
            )
            else it
        }
    }
}
