package com.decagon.android.sq007.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.decagon.android.sq007.repositoryImplementations.RetrofitRepository
import com.decagon.android.sq007.viewmodel.CommentsPageViewModel

class CommentViewFactory (private val repository: RetrofitRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CommentsPageViewModel(repository) as T
    }
}