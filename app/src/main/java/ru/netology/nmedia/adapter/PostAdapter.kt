package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostBinding
import kotlin.math.ceil

private val FORMAT_THOUSAND = 1_000.0
private val FORMAT_MILLION = 1_000_000.0

class PostAdapter(

    private val onLikeClicked :(Post)  ->Unit,
    private val onRepostClicked :(Post) ->Unit

    ) :ListAdapter<Post,ViewHolder >(DiffCallBack) {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PostBinding.inflate(LayoutInflater.from(parent.context),parent,false)
return ViewHolder(binding,onLikeClicked,onRepostClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val post = getItem(position)// перезаполнение вьюхи
        holder.bind(post)
    }

    }


class ViewHolder (
    private val binding :PostBinding,
    private val onLikeClicked :(Post)  ->Unit,
    private val onRepostClicked :(Post) ->Unit) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var post: Post

    init {
        binding.likeButton.setOnClickListener {
            onLikeClicked(post)
            println("жмяк")}

        binding.repostButton.setOnClickListener {
            onRepostClicked(post)
        }

    }



    fun bind(post: Post) {
        this.post = post
        with(binding){

        authorName.text = post.author
        postContent.text = post.content
        data.text = post.published
        likeButton.setImageResource(getLikeResId(post.likeByMe))
        reposts.text = post.repostConut.toString()
        likes.text = getCountLike(post)


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
private fun getLikeResId(liked: Boolean) =

    if (liked) R.drawable.ic_liked_24dp else R.drawable.ic_like_24dp

object DiffCallBack : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post) =
        oldItem.id ==newItem.id


    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem==newItem

}