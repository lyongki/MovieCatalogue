package com.dicoding.moviecatalogue.core.utils

import androidx.annotation.VisibleForTesting
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors @VisibleForTesting constructor(
    private val diskIo: Executor
) {
    constructor(): this(
        Executors.newSingleThreadExecutor()
    )

    fun diskIo(): Executor = diskIo
}