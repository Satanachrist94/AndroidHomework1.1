import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository

class PostReposytoryFileImpl( private  val application: Application) : PostRepository {
    private var nextID = 1000L
    private val FILE_NAME = "posts.json"
    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type


    override val data = MutableLiveData<List<Post>>()
    init {
        val postFile =application.filesDir.resolve(FILE_NAME)
        val posts :List<Post> = if(postFile.exists()) {
           val inputStream =  application.openFileInput(FILE_NAME)
            val reader = inputStream.bufferedReader()
            reader.use {
                gson.fromJson(it,type)
            }

        } else  emptyList<Post>()
        data.value = posts
    }


    private var posts
        get() = checkNotNull(data.value) {

        }
    set(value) {
        application.openFileOutput(
            FILE_NAME,Context.MODE_PRIVATE).bufferedWriter().use {
                it.write(gson.toJson(value))

        }
        data.value = value
    }


    override fun like(postId: Long) {
        data.value = posts.map {
            if (it.id == postId) it.copy(
                likeByMe = !it.likeByMe
            ) else it


        }
        sync()
    }

    override fun repost(postId: Long) {

        data.value = posts.map {
            if (it.id == postId) it.copy(
                repostConut = it.repostConut + 1
            )
            else it
        }
    }

    override fun delete(postId: Long) {
        data.value = posts.filter {
            it.id != postId

        }

    }

    override fun save(post: Post) {
        if(post.id == PostRepository.NEW_POST_ID) add(post) else update(post)
        sync()
    }

    private fun add(post: Post) {
        data.value = listOf(post.copy(
            id =++nextID

        )) +posts
        sync()


    }

    private fun update(post: Post) {

        data.value = posts.map {
            if(it.id ==post.id) post else it
        }
    }
    private fun sync() {
        application.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }
}
