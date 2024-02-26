package com.example.okegas.di

import android.content.Context
import com.example.okegas.model.MovieRepository
import com.example.okegas.model.local.LocalDataSource
import com.example.okegas.model.local.room.MovieDatabase
import com.example.okegas.model.remote.RemoteDataSource

object Injection {
    fun provideRepository(context: Context): MovieRepository {
        val database = MovieDatabase.getInstance(context)
        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.movieDao())

        return MovieRepository.getInstance(remoteDataSource, localDataSource)
    }
}