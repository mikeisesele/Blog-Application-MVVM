package com.decagon.android.sq007.repositoryImplementations

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.decagon.android.sq007.interfaces.Repository
import com.decagon.android.sq007.model.Comments
import com.decagon.android.sq007.model.Post
import com.decagon.android.sq007.model.User
import com.decagon.android.sq007.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RetrofitRepository : Repository {

    var postLists = MutableLiveData<MutableList<Post>>()
    var commentList = MutableLiveData<MutableList<Comments>>()
    var user = MutableLiveData<User>()


    // fetch posts
    override fun fetchPosts(): LiveData<MutableList<Post>> {

        var client : Call<MutableList<Post>> =  RetrofitClient.retroAPIservice.getPosts()

            RetrofitClient.retroAPIservice.getPosts().enqueue(object: Callback<MutableList<Post>> {
                override fun onResponse(call: Call<MutableList<Post>>, response: Response<MutableList<Post>>) {
                    if (response.isSuccessful){
                        postLists.value = response.body()!!
                    }   else {
                        Log.i("PostNot","${response.errorBody()}")
                    }
                }

                override fun onFailure(call: Call<MutableList<Post>>, t: Throwable) {
                    Log.i("PostErr", "${t.message}")
                }
            }
        )
        return postLists
    }

    // fetch comment
    override fun fetchComments(position: Int): MutableLiveData<MutableList<Comments>>  {
        RetrofitClient.retroAPIservice.getComments(position).enqueue(
            object: Callback<MutableList<Comments>> {
                override fun onResponse(call: Call<MutableList<Comments>>,
                    response: Response<MutableList<Comments>>
                ) {
                    if (response.isSuccessful){
                        commentList.value = response.body()!!
                    } else {
                        Log.i("CommentNot","${response.errorBody()}")
                    }
                }

                override fun onFailure(call: Call<MutableList<Comments>>, t: Throwable) {
                    Log.i("CommentErr", "${t.message}")
                }
            }
        )
        return commentList
    }

    // post comment
    override fun postComment(position: Int, comment: Comments): MutableLiveData<MutableList<Comments>> {
           commentList.value?.add(0, comment)
          return commentList
    }

    // create post
    override fun createPost(post: Post): MutableLiveData<MutableList<Post>> {
            postLists.value?.add(0, post)
            return postLists
    }

    override fun getUser(position: Int): MutableLiveData<User> {
        RetrofitClient.retroAPIservice.getUser(position).enqueue(
            object: Callback<User>{
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    user.value = response.body()
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    t.message?.let { Log.d("user error", it) }
                }
            }
        )
        return user
    }
}