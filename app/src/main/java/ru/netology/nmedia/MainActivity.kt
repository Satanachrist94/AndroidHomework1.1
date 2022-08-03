package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

import ru.netology.nmedia.activity.NewPostActivity
import ru.netology.nmedia.activity.NewPostResultContract
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


        val adapter = PostAdapter(object : PostListener {
            override fun onLikeClicked(post: Post) {
                viewModel.onLikeClicked(post)
            }

            override fun onRepostClicked(post: Post) {
                viewModel.sharePostContent.value = post.content
                viewModel.onRepostClicked(post)

            }

            override fun onDeleteClicked(post: Post) {
                viewModel.onDeleteClicked(post)
            }

           override fun onUpdateClicked(post:Post) {

               viewModel.currentPost.value = post //отображение корректируемого поста на экране
               viewModel.editPostContent.value = post.content

            }


            override fun onVideoClicked(post: Post) {
                viewModel.onVideoClicked(post)

            }



        })

        binding.postRecycleView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)

        }


        viewModel.sharePostContent.observe(this) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, "Поделиться")
            startActivity(shareIntent)

        }



        val postContentActivityLauncher = registerForActivityResult(NewPostResultContract) { postContent ->
            postContent ?: return@registerForActivityResult
            viewModel.onSaveButtomClicked(postContent)

        }

        viewModel.navigateToPostContent.observe(this) {
            postContentActivityLauncher.launch(null)

        }


        binding.fab.setOnClickListener {
            viewModel.onAddClicked()

        }
        viewModel.editPostContent.observe(this) {
            val textForEdit = viewModel.currentPost.value?.content
            postContentActivityLauncher.launch(textForEdit)
        }




        viewModel.playVideoContent.observe(this) { postUrl ->
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(postUrl)
            }
            startActivity(intent)
        }
        /*viewModel.editPostContent.observe(this) {
             viewModel.currentPost.value?.content
            postContentActivityLauncher.launch(textForEdit)
        }*/




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






