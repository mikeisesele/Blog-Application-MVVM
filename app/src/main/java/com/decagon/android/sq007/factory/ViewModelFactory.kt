package com.decagon.android.sq007.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.decagon.android.sq007.viewmodel.PostViewModel
import com.decagon.android.sq007.repositoryImplementations.RetrofitRepository

class ViewModelFactory(private val repository: RetrofitRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostViewModel(repository) as T
    }
}