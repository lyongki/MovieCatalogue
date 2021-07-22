package com.dicoding.moviecatalogue.core.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {
    const val NEWEST = "Newest"
    const val OLDEST = "Oldest"

    fun getSortedQuery(tableName: String, favorite: Boolean, filter: String): SimpleSQLiteQuery{
        val query = StringBuilder().append("SELECT * FROM ")
        query.append(tableName)
        if (favorite) query.append(" WHERE favorited = 1")
        query.append(" ORDER BY id ")
        when(filter){
            OLDEST -> query.append("ASC")
            NEWEST -> query.append("DESC")
        }
        return SimpleSQLiteQuery(query.toString())
    }
}