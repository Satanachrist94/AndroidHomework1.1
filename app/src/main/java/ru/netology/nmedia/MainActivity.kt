package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    var likes: TextView? = null
    var reposts: TextView? = null
    var views :TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_list)
        likes = findViewById(R.id.likes)
        likes?.setText("500")

        reposts = findViewById(R.id.reposts)
        reposts?.setText("1k")

        views = findViewById(R.id.views)
        views?.setText("1k")


    }
}