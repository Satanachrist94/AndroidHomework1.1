package ru.netology.nmedia

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.PostBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: PostViewModel by viewModels()

    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = PostAdapter(
            onLikeClicked = {
                    post ->  viewModel.onLikeClicked(post)
            },
            onRepostClicked = {
                    post ->viewModel.onRepostClicked(post)
            }

        )

binding.postRecycleView.adapter =adapter
        viewModel.data.observe(this) { posts ->

          adapter.submitList(posts)

        }

    }

}






