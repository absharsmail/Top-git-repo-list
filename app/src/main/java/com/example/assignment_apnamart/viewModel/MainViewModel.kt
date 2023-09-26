package com.example.assignment_apnamart.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.assignment_apnamart.network.model.GithubRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(mApplication: Application): AndroidViewModel(mApplication) {
    private var repository: GithubRepository
    val isLoading get() = repository.isLoading
    val error get() = repository.error
    //val repoList get() = repository.repoList
    private var mApplication: Application? = null
    var tempRepoList: List<GithubRepo>? = null

    init {
        this.mApplication = mApplication
        repository = GithubRepository(mApplication)
        getRepositories(true)
    }

    fun getRepositories(): LiveData<List<GithubRepo>>?{
        isLoading.value = true
        return repository.getRepositoriesFromLocal()
    }

    fun getRepositories(fromPullToRefresh: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val shouldFetch = true
            viewModelScope.launch(Dispatchers.Main) {
                if (fromPullToRefresh || shouldFetch) {
                    repository.getRepositoriesFromRemote()
                }
            }
        }
    }
}