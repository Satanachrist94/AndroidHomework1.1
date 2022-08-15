package ru.netology.nmedia.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostDetailsFragmentBinding


import ru.netology.nmedia.viewmodel.PostViewModel

class PostDetailsFragment : Fragment() {

    private val viewModel by viewModels<PostViewModel>(
        ownerProducer = ::requireParentFragment

    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = PostDetailsFragmentBinding.inflate(layoutInflater, container, false).also { binding ->

        val post = viewModel.currentPost.value

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val sortedPosts = posts.filter { it.id == post?.id }
            if (sortedPosts.isNotEmpty()) {
                binding.render(sortedPosts.first())
            } else {
                findNavController().navigateUp()
            }
        }

        setFragmentResultListener(
            requestKey =FragmentNewPost.REQUEST_KEY
        ) { requestKey, bundle ->
            if (requestKey != FragmentNewPost.REQUEST_KEY) return@setFragmentResultListener
            val newPostContent = bundle.getString(
                FragmentNewPost.RESULT_KEY
            ) ?: return@setFragmentResultListener
            viewModel.onSaveButtomClicked(newPostContent)
        }

        viewModel.sharePostContent.observe(viewLifecycleOwner) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, getString(R.string.repostsCount))
            startActivity(shareIntent)
        }

        viewModel.playVideoContent.observe(viewLifecycleOwner) { postUrl ->
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(postUrl)
            }
            startActivity(intent)
        }

        post?.let {
            binding.likesIcon.setOnClickListener {

                viewModel.onLikeClicked(post)
            }

            binding.repostIcon.setOnClickListener {
                viewModel.onRepostClicked(post)
            }

            binding.videoFrameInPost.videoPoster.setOnClickListener {
                viewModel.onVideoClicked(post)
            }

            val popupMenu by lazy {
                PopupMenu(layoutInflater.context, binding.options).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.delete -> {
                                viewModel.onDeleteClicked(post)
                                true
                            }
                            R.id.edit -> {
                                viewModel.onEditClicked(post)

                                true
                            }
                            else -> false
                        }
                    }
                }
            }

            binding.options.setOnClickListener { popupMenu.show() }

        }

        viewModel.navigateToPostContent.observe(viewLifecycleOwner) { initialContent ->
            val direction = PostDetailsFragmentDirections.toPostContentFragment(initialContent.toString())
            findNavController().navigate(direction)
        }

    }.root

    private fun PostDetailsFragmentBinding.render(post: Post) {


       post.let {
            authorName.text = post.author
           postText.text = post.content
            data.text = post.published
           likesIcon.isChecked = post.likeByMe
           likesIcon.setText(post.likeCount.toString())
            if (post.videoUrl != null) {
                videoFrameInPost.root.visibility = View.VISIBLE


            } else {
                videoFrameInPost.root.visibility =View.GONE
            }

           repostIcon.setText(post.repostConut.toString())

           // options.setOnClickListener { popupMenu.show() }
       }


    }
}