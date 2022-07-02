package ru.netology.nmedia

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.lifecycle.map
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {
    private val FORMAT_THOUSAND = 1_000
    private val FORMAT_MILLION = 1_000_000
    private val viewModel: PostViewModel by viewModels()

    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.likeButton.setOnClickListener {
            viewModel.onLikeClicked()

        }
        viewModel.data.observe(this) { post ->
            with(binding) {
                authorName.text = post.author
                postContent.text = post.content
                data.text = post.published
                likeButton.setImageResource(getLikeResId(post.likeByMe))
                reposts.text = post.repostConut.toString()
                likes.text = getCountLike(post)
            }
        }

        binding.repostButton.setOnClickListener {
            viewModel.onRepostClicked()
        }

    }

    private fun getCountLike(post: Post): String {


        val likeFormatString =
            when (val liked = if (post.likeByMe) (post.likeCount + 1) else post.likeCount) {
                in 1_000..999_999 -> "${liked / FORMAT_THOUSAND}K"
                in 1_000_000..999_999_999 -> "${liked / FORMAT_MILLION}M"
                else -> liked
            }
        return likeFormatString.toString()
    }

    private fun getLikeResId(liked: Boolean) =

        if (liked) R.drawable.ic_liked_24dp else R.drawable.ic_like_24dp


}






