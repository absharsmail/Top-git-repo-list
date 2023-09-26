package com.example.assignment_apnamart.db

import com.example.assignment_apnamart.network.model.GithubRepo
import com.example.assignment_apnamart.network.model.Owner

class MockTestUtil {

    private val owner = Owner("", "")
    private val githubRepo1 = GithubRepo(1L,"AndroidTest1", "", null, "", 0, 0, null, 0, owner, "","", 0, false)
    private val githubRepo2 = GithubRepo(2L,"AndroidTest2", "", null, "", 0, 0, null, 0, owner, "","", 0, false)
    private val githubRepo3 = GithubRepo(3L,"AndroidTest3", "", null, "", 0, 0, null, 0, owner, "","", 0, false)

    fun mockRepositories(isEmpty: Boolean): List<GithubRepo> {
        val repositories: ArrayList<GithubRepo> = ArrayList()
        if (!isEmpty) {
            val repository1 = githubRepo1
            repositories.add(repository1)
            val repository2 = githubRepo2
            repositories.add(repository2)
            val repository3 = githubRepo3
            repositories.add(repository3)
        }
        return repositories
    }
}
