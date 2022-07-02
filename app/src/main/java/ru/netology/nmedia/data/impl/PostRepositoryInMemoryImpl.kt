package ru.netology.nmedia.data.impl

import android.provider.Settings.Global.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.data.PostRepository

class PostRepositoryInMemoryImpl : PostRepository {
    private var post = Post(
        id = 1L,
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
        likeCount = 999,
        repostConut = 0

    )
    override val data = MutableLiveData(post)

    override fun like() {
        post = post.copy(
            likeByMe = !post.likeByMe
        )
        data.value = post
    }


    override fun repost() {
        post = post.copy(
            repostConut = post.repostConut + 1
        )
        data.value = post
    }

}