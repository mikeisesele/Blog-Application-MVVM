package com.decagon.android.sq007.view.ui

import android.content.Intent
import android.content.Intent.getIntent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.decagon.android.sq007.databinding.ActivityAddPostBinding

class AddPostActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // set binding
        binding = ActivityAddPostBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        // click to lunnch main activity
        binding.button.setOnClickListener{

            // get values from input fields
            val postBody = binding.postBody.text.toString().trim()
            val postTitle = binding.postTitleEditText.text.toString().trim()

            // lunch main activity
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("title", postTitle)
            intent.putExtra("body", postBody)
            startActivity(intent)
            finish()
        }
    }
}