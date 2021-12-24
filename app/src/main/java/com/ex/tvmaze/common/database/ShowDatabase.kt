package com.ex.tvmaze.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ex.tvmaze.common.database.dao.ShowDao
import com.ex.tvmaze.common.entities.ShowEntity
import com.ex.tvmaze.common.utils.Converters

@Database(entities = [ShowEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class ShowDatabase : RoomDatabase() {
    abstract fun showDao() : ShowDao
}