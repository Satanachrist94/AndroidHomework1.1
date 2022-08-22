package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostBinding

private val FORMAT_THOUSAND = 1_000.0
private val FORMAT_MILLION = 1_000_000.0

class PostAdapter( private val postListener: PostListener) :ListAdapter<Post,ViewHolder >(DiffCallBack) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PostBinding.inflate(LayoutInflater.from(parent.context),parent,false)
return ViewHolder(binding,postListener)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val post = getItem(position)// перезаполнение вьюхи
        holder.bind(post)
    }

    }


class ViewHolder (
    private val binding :PostBinding,
        listener: PostListener) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var post: Post

    private val popupMenu =
        PopupMenu(itemView.context,binding.options).apply {
            inflate(R.menu.options_post)
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.delete -> {
                        listener.onDeleteClicked(post)
                        true
                    }
                    R.id.update -> {
                        listener.onUpdateClicked(post)


                        true
                    }
                    else -> false
                }
            }
        }



    init {
        binding.likeButton.setOnClickListener {
            listener.onLikeClicked(post)
            println("жмяк")}

        binding.repostButton.setOnClickListener {
            listener.onRepostClicked(post)

            }
        binding.videoFrameInPost.videoPoster.setOnClickListener {
            listener.onVideoClicked(post)
            binding.root.setOnClickListener{ listener.viewPostDetails(post)}


        }


    }

    fun bind(post: Post) {
        this.post = post
        with(binding){
        authorName.text = post.author
        postContent.text = post.content
        data.text = post.published
        likeButton.isChecked = post.likeByMe
            if (post.videoUrl != null) {
                postVideoGroup.visibility = View.VISIBLE
                binding.videoFrameInPost.videoUrl.setText(post.videoUrl)
            } else {
                postVideoGroup.visibility = View.GONE
            }

            repostButton.setText(post.repostCount.toString())
      //  reposts.text = post.repostConut.toString()
            likeButton.setText(getCountLike(post))
       // likes.text = getCountLike(post)
            options.setOnClickListener { popupMenu.show() }



    }}

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

object DiffCallBack : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post) =
        oldItem.id ==newItem.id


    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem==newItem

}