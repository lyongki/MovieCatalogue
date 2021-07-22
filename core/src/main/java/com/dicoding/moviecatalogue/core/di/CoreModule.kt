package com.dicoding.moviecatalogue.core.di

import androidx.room.Room
import com.dicoding.moviecatalogue.core.data.FilmRepository
import com.dicoding.moviecatalogue.core.data.source.local.LocalDataSource
import com.dicoding.moviecatalogue.core.data.source.local.room.FilmDatabase
import com.dicoding.moviecatalogue.core.data.source.remote.RemoteDataSource
import com.dicoding.moviecatalogue.core.data.source.remote.api.ApiService
import com.dicoding.moviecatalogue.core.domain.repository.IFilmRepository
import com.dicoding.moviecatalogue.core.utils.AppExecutors
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val databaseModule = module{
    factory { get<FilmDatabase>().filmDao() }
    single { Room.databaseBuilder(
        androidContext(),
        FilmDatabase::class.java,
        "Film.db"
    ).fallbackToDestructiveMigration().build() }
}

val networkModule = module {
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IFilmRepository> { FilmRepository(get(), get(), get()) }
}