package com.dicoding.moviecatalogue.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "commententities")
data class CommentEntity (
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "film_type")
    val film_type: String = "",

    @ColumnInfo(name = "film_id")
    val film_id: String = "",

    @ColumnInfo(name = "user_id")
    val user_id: String = "",

    @ColumnInfo(name = "date")
    val date: String = "",

    @ColumnInfo(name = "comment")
    val comment: String = ""
)