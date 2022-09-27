package me.yaoanay107.roomdeadlocktest.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "forums"
)
data class ForumEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String = "",
    @ColumnInfo(name = "gameId") val gameId: String = "",
    @ColumnInfo(name = "language") val language: String = "",
)