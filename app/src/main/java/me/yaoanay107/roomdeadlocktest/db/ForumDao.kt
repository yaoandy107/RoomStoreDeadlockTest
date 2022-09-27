package me.yaoanay107.roomdeadlocktest.db

import androidx.room.*

@Dao
interface ForumDao {
    @Query("SELECT * FROM forums WHERE id = :id")
    suspend fun getForum(id: String): ForumEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addForum(forumEntity: ForumEntity)

    @Query("DELETE FROM forums")
    suspend fun deleteAll()
}