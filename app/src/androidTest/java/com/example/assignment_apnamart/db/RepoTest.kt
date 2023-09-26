package com.example.assignment_apnamart.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.assignment_apnamart.network.model.GithubRepo
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepoTest: RoomDbTest() {
    @Test
    fun insertDbTest() {
        val repositories: List<GithubRepo> = MockTestUtil().mockRepositories(false)
        db.githubDao()!!.insertRepositories(repositories)

        val storedRepo1: List<GithubRepo>? = db.githubDao()?.getRepositories()?.value
        Assert.assertEquals(1L, storedRepo1!![0].id)

        val storedRepo2: List<GithubRepo>? = db.githubDao()?.getRepositories()?.value
        Assert.assertEquals(3, storedRepo2!!.size)
        Assert.assertEquals(2L, storedRepo2[1].id)
    }


    @Test
    fun emptyDbTest() {
        val repositories: List<GithubRepo> = MockTestUtil().mockRepositories(true)
        db.githubDao()!!.insertRepositories(repositories)
        val storedDB: List<GithubRepo>? = db.githubDao()?.getRepositories()?.value
        Assert.assertEquals(true, storedDB?.isEmpty())
    }
}