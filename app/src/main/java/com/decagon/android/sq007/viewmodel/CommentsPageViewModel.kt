package com.decagon.android.sq007.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.decagon.android.sq007.model.Comments
import com.decagon.android.sq007.model.Post
import com.decagon.android.sq007.model.User
import com.decagon.android.sq007.repositoryImplementations.RetrofitRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class CommentsPageViewModel(private val repository: RetrofitRepository): ViewModel() {

       // initialize livedata
       lateinit var commentFromServer: LiveData<MutableList<Comments>>
       lateinit var userName: MutableLiveData<User>

       // send position to repo for http call
       fun getComments(position: Int){
              repository.fetchComments(position)
              commentFromServer = repository.fetchComments(position)
       }
       
       fun postComments(position: Int, comment: Comments){
              repository.postComment(position, comment)
              commentFromServer = repository.postComment(position, comment)
       }

       fun getUsers(position: Int){
              userName = repository.getUser(position)
       }

}

