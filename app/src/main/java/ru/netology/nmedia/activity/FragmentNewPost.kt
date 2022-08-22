package ru.netology.nmedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.databinding.FragmentNewPostBinding


class FragmentNewPost: Fragment() {

    private val args by navArgs<FragmentNewPostArgs>()

     override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentNewPostBinding.inflate(layoutInflater, container, false).also { binding ->

        binding.edit.requestFocus()
        binding.edit.setText(args.initialContent)

        binding.ok.setOnClickListener {
            onOkButtonClicked(binding)
        }
    }.root

    private fun onOkButtonClicked(binding: FragmentNewPostBinding) {
        val text = binding.edit.text
        if (!text.isNullOrBlank()) {
            val resultBundle = Bundle(1)
            resultBundle.putString(RESULT_KEY, text.toString())
            setFragmentResult(REQUEST_KEY, resultBundle)
        }
        findNavController().navigateUp()
    }

    companion object {
        const val REQUEST_KEY = "requestKey"
        const val RESULT_KEY = "postNewContent"
    }

}


