package com.dicoding.moviecatalogue.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieentities")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "movieId")
    val movieId: String = "",

    @ColumnInfo(name = "title")
    val title: String = "",

    @ColumnInfo(name = "year")
    val year: String = "",

    @ColumnInfo(name = "genre")
    val genre: String = "",

    @ColumnInfo(name = "productionCompanies")
    val productionCompanies: String = "",

    @ColumnInfo(name = "overview")
    val overview: String = "",

    @ColumnInfo(name = "duration")
    val duration: String = "",

    @ColumnInfo(name = "rating")
    val rating: String = "",

    @ColumnInfo(name = "imagePath")
    val imagePath: String = "",

    @ColumnInfo(name = "favorited")
    val favorited: Boolean = false
)
