package com.nullpointer.moviescompose.inject

import android.content.Context
import androidx.room.Room
import com.nullpointer.moviescompose.data.local.database.MovieDAO
import com.nullpointer.moviescompose.data.local.database.MovieDataBase
import com.nullpointer.moviescompose.data.local.datasource.MovieLocalDataSource
import com.nullpointer.moviescompose.data.local.datasource.MovieLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideMoviesDatabase(
        @ApplicationContext context: Context,
    ): MovieDataBase = Room.databaseBuilder(
        context,
        MovieDataBase::class.java,
        "MOVIES_DB").build()

    @Provides
    @Singleton
    fun provideMoviesDao(
        movieDataBase: MovieDataBase,
    ): MovieDAO = movieDataBase.getMoviesDao()

    @Singleton
    @Provides
    fun provideMoviesDataSource(
        movieDAO: MovieDAO,
    ): MovieLocalDataSource = MovieLocalDataSourceImpl(movieDAO)

}