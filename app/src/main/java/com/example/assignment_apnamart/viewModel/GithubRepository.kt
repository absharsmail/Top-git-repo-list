package com.example.assignment_apnamart.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.assignment_apnamart.AppPreferences
import com.example.assignment_apnamart.local.getDatabase
import com.example.assignment_apnamart.network.DataService
import com.example.assignment_apnamart.network.IRepoSvc
import com.example.assignment_apnamart.network.model.GithubRepo
import com.example.assignment_apnamart.network.model.ResponseParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.Calendar

class GithubRepository(mApplication: Application): IRepoSvc {
    private val viewModelJob = SupervisorJob()
    private val networkManager = DataService()
    var isLoading = MutableLiveData<Boolean>()
    var error = MutableLiveData<Boolean>()
    private var repoList = MutableLiveData<List<GithubRepo>>()
    private var database = getDatabase(mApplication)
    private var tempRepoList: List<GithubRepo>? = null
    private var preferences: AppPreferences

    init {
        preferences = AppPreferences((mApplication))
    }

    fun getRepositoriesFromLocal(): LiveData<List<GithubRepo>>?{
        val currentTime = Calendar.getInstance()
        if (currentTime.timeInMillis - preferences.getLastUpdatedDate().timeInMillis >= 7200000) {
            getRepositoriesFromRemote()
            return null
        }
        val temp = database.githubDao()?.getRepositories()
        tempRepoList = temp?.value
        isLoading.value = false
        return temp
    }

    fun getRepositoriesFromRemote() {
        isLoading.value = true
        networkManager.fetchData(this)
    }

    override fun onRepoSuccess(responseParser: ResponseParser?) {
        val data = responseParser?.items!!
        repoList.value = data
        isLoading.value = false
        error.value = false
        Log.i("ApiCall", "Success")
        CoroutineScope(viewModelJob).launch(Dispatchers.IO) {
            addDataToDB(data)
        }
    }

    private fun addDataToDB(data: List<GithubRepo>) {
        val currentTime = Calendar.getInstance()
        preferences.setLastUpdatedDate(currentTime)
        database.githubDao()?.insertRepositories(data)
    }

    override fun failure(message: String?, errorCode: Int, extraParams: Throwable?) {
        isLoading.value = false
        error.value = true
        Log.i("ApiCall", "Failure")
    }

    override fun noData() {
        isLoading.value = false
        error.value = true
        Log.i("ApiCall", "No Data")
    }
}
