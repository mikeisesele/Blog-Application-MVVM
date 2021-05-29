package com.decagon.android.sq007.interfaces

import com.decagon.android.sq007.model.Comments
import com.decagon.android.sq007.model.Post
import com.decagon.android.sq007.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HttpRequests {

    @GET("posts")
    fun getPosts() : Call<MutableList<Post>>

    @GET("posts/{position}/comments")
    fun getComments(@Path("position") url: Int) : Call<MutableList<Comments>>
    
//    @POST("posts/{postId}/comments")
//    fun postComment(@Path("postId") postId:Int, @Body comment: Comments): Call<Comments>
//
//    @POST("posts")
//    fun createPost(@Body post: Post) : Call<Post>

    @GET("users/{position}")
    fun getUser(@Path("position") url: Int) : Call<User>

}