package com.decagon.android.sq007.interfaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.decagon.android.sq007.model.Comments
import com.decagon.android.sq007.model.Post
import com.decagon.android.sq007.model.User

interface Repository {

    fun fetchPosts(): LiveData<MutableList<Post>>

    fun fetchComments(position: Int): MutableLiveData<MutableList<Comments>>

    fun postComment(position: Int, comment: Comments): MutableLiveData<MutableList<Comments>>

    fun createPost(post: Post): MutableLiveData<MutableList<Post>>

    fun getUser(position: Int): LiveData<User>

}