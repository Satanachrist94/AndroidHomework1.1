package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.fragment.app.*

import androidx.navigation.fragment.findNavController


import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.adapter.PostListener

import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.ui.FragmentNewPost

import ru.netology.nmedia.viewmodel.PostViewModel

class FeedFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentFeedBinding.inflate(layoutInflater, container, false).also { binding ->

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

            override fun onUpdateClicked(post: Post) {

               //viewModel.editPostContent.value =post.content
                viewModel.onEditClicked(post)

            }


            override fun onVideoClicked(post: Post) {
                viewModel.onVideoClicked(post)

            }
            override fun viewPostDetails(post: Post) {
                viewModel.currentPost.value = post
                viewModel.navigateToPostDetails.value = post.id
            }
        })

        binding.postRecycleView.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)

        }

        viewModel.sharePostContent.observe(viewLifecycleOwner) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, "Поделиться")
            startActivity(shareIntent)
        }


        viewModel.navigateToPostContent.observe(viewLifecycleOwner) { initialContent ->
            val direction = FeedFragmentDirections.toPostContentFragment(initialContent.toString())
            findNavController().navigate(direction)



        }
        viewModel.navigateToPostDetails.observe(viewLifecycleOwner){postID ->
            val direction = FeedFragmentDirections.toPostDetailsFragment(postID.toString())
            findNavController().navigate(direction)
        }


        setFragmentResultListener(
            requestKey = FragmentNewPost.REQUEST_KEY
        ) {
            requestKey, bundle ->
            if(requestKey!= FragmentNewPost.REQUEST_KEY) return@setFragmentResultListener
            val newPostContent = bundle.getString(
                FragmentNewPost.RESULT_KEY) ?: return@setFragmentResultListener
        viewModel.onSaveButtomClicked(newPostContent)}
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.toPostContentFragment)

        }



        viewModel.playVideoContent.observe(viewLifecycleOwner) { postUrl ->
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(postUrl)
            }
            startActivity(intent)
        }


    }.root

}










