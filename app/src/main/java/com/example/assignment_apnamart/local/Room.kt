package com.example.assignment_apnamart.local

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.assignment_apnamart.network.model.GithubRepo

@Dao
interface GithubDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepositories(githubEntities: List<GithubRepo>)

    @Query("SELECT * FROM `GithubRepo`")
    fun getRepositories(): LiveData<List<GithubRepo>>
}

@Database(entities = [GithubRepo::class], version = 1, exportSchema = false)
//@TypeConverters([TimestampConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun githubDao(): GithubDao?
}

private lateinit var INSTANCE: AppDatabase

fun getDatabase(context: Context): AppDatabase {
    synchronized(AppDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java,
                "Github.db").build()
        }
    }
    return INSTANCE
}
