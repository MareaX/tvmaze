package com.ex.tvmaze.common.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ex.tvmaze.common.entities.ShowEntity

@Dao
interface ShowDao {
    @Query ("SELECT * FROM ShowEntity")
    fun getAllShows() : List<ShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAllShows(shows: List<ShowEntity>)
}