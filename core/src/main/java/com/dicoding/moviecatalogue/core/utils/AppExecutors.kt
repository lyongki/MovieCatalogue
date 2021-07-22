package com.dicoding.moviecatalogue.core.utils

import android.os.Looper
import androidx.annotation.VisibleForTesting
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors @VisibleForTesting constructor(
    private val diskIo: Executor,
    private val networkIo: Executor,
    private val mainThread: Executor
) {
    constructor(): this(
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(THREAD_COUNT),
        MainThreadExecutor()
    )

    fun diskIo(): Executor = diskIo

    fun networkIo(): Executor = networkIo

    fun mainThread(): Executor = mainThread

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = android.os.Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }

    }

    companion object{
        private const val THREAD_COUNT = 3
    }
}