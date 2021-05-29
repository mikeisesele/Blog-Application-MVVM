package com.decagon.android.sq007.view.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagon.android.sq007.databinding.ActivityMainBinding
import com.decagon.android.sq007.factory.ViewModelFactory
import com.decagon.android.sq007.interfaces.ClickListenerInterface
import com.decagon.android.sq007.model.Post
import com.decagon.android.sq007.repositoryImplementations.RetrofitRepository
import com.decagon.android.sq007.util.ConnectivityLiveData
import com.decagon.android.sq007.util.toast
import com.decagon.android.sq007.view.adapter.PostRecyclerVIewAdapter
import com.decagon.android.sq007.viewmodel.PostViewModel
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity(), ClickListenerInterface {

    // initialize variables
    lateinit var binding: ActivityMainBinding
    private lateinit var connectivityLiveData: ConnectivityLiveData
    private lateinit var viewModel: PostViewModel
    private lateinit var viewModelFactory: ViewModelFactory
    private var recyclerViewAdapter = PostRecyclerVIewAdapter(this, this@MainActivity)
    private val repository = RetrofitRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // set up bindings
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        // get recycler view xml
        val recyclerViewXML = binding.recyclerView
        recyclerViewXML.adapter = recyclerViewAdapter // connect xml to recycler view adapter
        recyclerViewXML.layoutManager = LinearLayoutManager(this@MainActivity) // set layout manager
        recyclerViewXML.setHasFixedSize(true)

        // initialize view model
        viewModelFactory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PostViewModel::class.java)

        // set up connectivity watcher
        connectivityLiveData = ConnectivityLiveData(application)

        // observe network state
        connectivityLiveData.observe(this, { NetworkIsAvailable ->
            when (NetworkIsAvailable) {
                true -> {
                    // observe response from remote data source in view model
                    viewModel.postFromServer.observe(this, {
                        recyclerViewAdapter.setAdapterList(it) // pass in list to adapter
                        recyclerViewAdapter.notifyDataSetChanged()
                    })
                }
                false -> {Log.i("network", "Network not available") }
            }
        })

        // lunch second activity to add post
        binding.floatingAddPostButton.setOnClickListener {
            val intent = Intent(this, AddPostActivity::class.java)
            // initiate intent with authentication
            startActivity(intent)
        }

        // query posts
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean { return false }
            override fun onQueryTextChange(text: String?): Boolean {

                val filteredList = text?.let { viewModel.filterText(it) }

                if (filteredList != null) { recyclerViewAdapter.setAdapterList(filteredList) } // pass in list to adapter// in list to adapter
                recyclerViewAdapter.notifyDataSetChanged()
                return false
            }
        })


        // get post details from second activity
        val newPostResponse: Bundle? = intent.extras

        if (newPostResponse != null){
            val title = newPostResponse.get("title")
            val body = newPostResponse.get("body")

            // create post object to make network call
            val post = Post(0,0,title.toString(),body.toString())

            viewModel.createPost(post)

            // observe network call for updated posts
                viewModel.postFromServer.observe(this@MainActivity,  {it ->
                    if (it != null){
                        recyclerViewAdapter.setAdapterList(it) // pass in list to adapter// in list to adapter
                        recyclerViewAdapter.notifyDataSetChanged()
                    }
                })
            }
        }

    // getting the position of a unique view holder, passing its details to next activity
    override fun onItemClicked(position: Int, text: String, id: Int) {
        val intent = Intent(this, PostDetailsActivity::class.java)
        intent.putExtra("position", position.toString())
        intent.putExtra("post", text)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Click one more time to exit application", Toast.LENGTH_SHORT).show()
        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

}
