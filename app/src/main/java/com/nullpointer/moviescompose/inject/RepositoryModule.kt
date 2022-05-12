package com.nullpointer.moviescompose.inject

import com.nullpointer.moviescompose.domain.MoviesRepoImpl
import com.nullpointer.moviescompose.domain.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideMovieRepository(
        moviesRepoImpl: MoviesRepoImpl
    ):MoviesRepository
}