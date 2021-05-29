package com.decagon.android.sq007.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagon.android.sq007.databinding.ActivityPostDetailsBinding
import com.decagon.android.sq007.factory.CommentViewFactory
import com.decagon.android.sq007.model.Comments
import com.decagon.android.sq007.repositoryImplementations.RetrofitRepository
import com.decagon.android.sq007.util.ConnectivityLiveData
import com.decagon.android.sq007.util.toast
import com.decagon.android.sq007.view.adapter.PostDetailRecyclerViewAdapter
import com.decagon.android.sq007.viewmodel.CommentsPageViewModel

class PostDetailsActivity : AppCompatActivity()  {

    lateinit var binding: ActivityPostDetailsBinding
    private lateinit var connectivityLiveData: ConnectivityLiveData
    private lateinit var viewModel: CommentsPageViewModel
    private var postRecyclerViewAdapter = PostDetailRecyclerViewAdapter(this@PostDetailsActivity)
    var post: String? = null
    var position: String? = ""
    var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPostDetailsBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        // create viewmodel
        val repository = RetrofitRepository
        viewModel = ViewModelProvider(this, CommentViewFactory(repository)).get(CommentsPageViewModel::class.java)

        // bind xml to recycler view adapter
        val commentRecyclerViewXML = binding.commentListRecyclerView

        // get intents from main activity
        val receiver = intent.extras

        if (receiver != null) {
            post = receiver.get("post") as String?
            position = receiver.get("position") as String?
            id = receiver.getInt("id")
            // set view content
            binding.mainComment.text = post
            // get and pass position to view model for http get request
            position?.toInt()?.let { viewModel.getComments(it) }
            id.let { viewModel.getUsers(it) }
        }

        // check for connection
        connectivityLiveData = ConnectivityLiveData(application)

        // observe connectivity live data
        connectivityLiveData.observe(this, { NetworkIsAvailable ->
            when (NetworkIsAvailable) {
                true -> {
                    // observe view model live data
                    viewModel.commentFromServer.observe(this, {
                        commentRecyclerViewXML.adapter = postRecyclerViewAdapter
                        commentRecyclerViewXML.layoutManager = LinearLayoutManager(this@PostDetailsActivity)
                        commentRecyclerViewXML.setHasFixedSize(true)
                        postRecyclerViewAdapter.setAdapterList(it)
                        postRecyclerViewAdapter.notifyDataSetChanged()
                    })

                        binding.userName.text = viewModel.userName.value?.name
                }
                false -> { Log.i("network", "Network not available") }

            }
        })

        // add comments
        binding.commmentButton.setOnClickListener(){
            if(binding.addCommentField.text.toString() != ""){

                val comment = Comments(null, null,"michael","codaarX",binding.addCommentField.text.toString())

                position?.toInt()?.let { it1 -> viewModel.postComments(it1, comment)

                    viewModel.commentFromServer.observe(this, {
                        postRecyclerViewAdapter.setAdapterList(it)
                        postRecyclerViewAdapter.notifyDataSetChanged()
                    })
                }
            }
            binding.addCommentField.text?.clear()
        }
    }


}