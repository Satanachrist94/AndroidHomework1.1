package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val post = Post(
            1L,
            "Нетология.Меняем карьеру через образование",
            "Записывайтесь на видеолекцию «Маркетплейсы: " +
                    "как выбрать площадку для размещения товаров» С " +
                    "конца 2021 года в онлайн-ритейле стали говорить о" +
                    "второй волне маркетплейсов в России. В таком многообразии" +
                    " бывает сложно выбрать оптимальную платформу для продажи " +
                    "своих товаров. Как выбрать подходящий маркетплейс для вашего" +
                    " интернет-магазина — разберёмся на занятии. Вы узнаете, что из" +
                    " себя представляют маркетплейсы, чем они отличаются от агрегаторов " +
                    "и зачем нужно сейчас занять эту нишу. Выбирайте маркетплейс, который вам подходит:",
        "25.06.22")
        binding.render(post)

        binding.likeButton.setOnClickListener{
            post.likeByMe= !post.likeByMe
            binding.likeButton.setImageResource(getLikeResId(post.likeByMe))
            binding.likes.setText(getCountLike(post.likeByMe,post))
        }



    }
    private fun ActivityMainBinding.render (post: Post) {
        authorName.text=post.author
        postContent.text = post.content
        data.text=post.published
        likes.text=post.likeCount.toString()
        likeButton.setImageResource(getLikeResId(post.likeByMe))
    }
    private fun getLikeResId (liked:Boolean) =
        if(liked) R.drawable.ic_liked_24dp
        else R.drawable.ic_like_24dp

    private fun getCountLike (likeByMe:Boolean,post: Post) :String {
        val formatThousand = 1_000
        val formatMillion = 1_000_000

        val likeFormatString =
            when(val liked = if(likeByMe) post.likeCount+1 else post.likeCount) {
                in 1_000..999_999 -> "${liked/formatThousand}"
                in 1_000_000..999_999_999 -> "${liked/formatMillion}"
                else-> liked
            }
            return likeFormatString.toString()
    }
}

