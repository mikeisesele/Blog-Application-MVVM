package com.decagon.android.sq007.viewmodel

import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.*
import com.decagon.android.sq007.model.Post
import com.decagon.android.sq007.repositoryImplementations.RetrofitRepository

class PostViewModel(repository: RetrofitRepository) : ViewModel() {

    lateinit var addedPostsFromServer: MutableLiveData<MutableList<Post>>
    val repository = RetrofitRepository

    init {
        this.repository.fetchPosts()
    }

    // live data of posts from server
    var postFromServer = this.repository.postLists

    fun createPost(post: Post) {
        addedPostsFromServer = repository.createPost(post)
    }

    fun filterText(text: String): List<Post>? {
        if (text != null && text != "") {
            val filteredListText = postFromServer.value?.filter { it -> it.title.contains(text) }

            if (text.isDigitsOnly()) {
                val filteredListNumber =
                    postFromServer.value?.filter { inputNumber -> inputNumber.id == text.toInt() }
                return filteredListNumber
            } else {
                Log.i("err", "Search field")
            }

            return filteredListText
        } else {
            return postFromServer.value
        }
    }
}

