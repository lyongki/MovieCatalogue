package com.dicoding.moviecatalogue.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.moviecatalogue.core.data.source.local.entity.CommentEntity
import com.dicoding.moviecatalogue.core.data.source.local.entity.MovieEntity
import com.dicoding.moviecatalogue.core.data.source.local.entity.TvShowEntity

@Database(
    entities = [MovieEntity::class, TvShowEntity::class, CommentEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FilmDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}