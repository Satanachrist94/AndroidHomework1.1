/*package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.Post
import ru.netology.nmedia.databinding.ActivityEditPostBinding
import ru.netology.nmedia.databinding.ActivityNewPostBinding

class EditPostActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val postEditBinding = ActivityEditPostBinding.inflate(layoutInflater)
        postEditBinding.editPost.requestFocus()
        postEditBinding.saveEditPost.setOnClickListener{
            val intent = Intent()
            if (postEditBinding.editPost.text.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val content = postEditBinding.editPost.text?.toString()
                intent.putExtra(EditPostActivity.POST_CONTENT_EXTRA_KEY, content)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()

        }

        }
    companion object {
        const val POST_CONTENT_EXTRA_KEY = "content"
    }
    object EditPostResultContract : ActivityResultContract<String?, String?>() {

        override fun createIntent(context: Context, input: String?): Intent =
            Intent(context, NewPostActivity::class.java)

        override fun parseResult(resultCode: Int, intent: Intent?): String? =
            if (resultCode == Activity.RESULT_OK) {
                intent?.getStringExtra(EditPostActivity.POST_CONTENT_EXTRA_KEY)
            } else {
                null
            }
    }
        //postEditBinding.editPost




    }

*/