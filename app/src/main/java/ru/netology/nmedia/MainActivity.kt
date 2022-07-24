package ru.netology.nmedia

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.adapter.PostListener

import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.hideKeyboard
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: PostViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.group.visibility = View.GONE
//val adapter = Postadapter(viewModel


        val adapter = PostAdapter(object: PostListener {
            override fun onLikeClicked(post: Post) {
                viewModel.onLikeClicked(post)
            }

            override fun onRepostClicked(post: Post) {
                viewModel.onRepostClicked(post)
            }

            override fun onDeleteClicked(post: Post) {
               viewModel.onDeleteClicked(post)
            }

            override fun onUpdateClicked(post: Post) {
                viewModel.onUpdateClicked(post)
            }

            override fun onCancelClicked() {
                binding.group.visibility = View.VISIBLE
            }

        })
        viewModel.currentPost.observe(this) {currentPost ->
            binding.content.setText(currentPost?.content)


        }
        binding.postRecycleView.adapter =adapter
        viewModel.data.observe(this) {posts ->
            adapter.submitList(posts)

        }
        binding.saveButton.setOnClickListener{

            val content = binding.content.text.toString()
            viewModel.onSaveButtomClicked(content)
            binding.content.clearFocus() //чистит ползунок
            binding.content.hideKeyboard() //убирает клавиатуру после добавление текста

        }
        binding.cancelButton.setOnClickListener {

            binding.group.visibility = View.GONE
            binding.content.hideKeyboard()
            viewModel.onCancelBottomClicked() //скрывает текст при нажатии кнопки отмены редактирования

        }





/*binding.postRecycleView.adapter =adapter
        viewModel.data.observe(this) { posts ->



          adapter.submitList(posts)

        }
        binding.saveButton.setOnClickListener{

            val content = binding.content.text.toString()
            viewModel.onSaveButtomClicked(content)
            binding.content.clearFocus() //чистит ползунок
            binding.content.hideKeyboard() //убирает клавиатуру после добавление текста

        }
        binding.cancelButton.setOnClickListener {

            binding.group.visibility = View.GONE
            binding.content.hideKeyboard()
            viewModel.onCancelBottomClicked()

        }*/


        /*observe(this) { currentPost -> //удаление текста после того, как ввел его в строке
        binding.content.setText(currentPost?.content)

        }*/


    }

}






