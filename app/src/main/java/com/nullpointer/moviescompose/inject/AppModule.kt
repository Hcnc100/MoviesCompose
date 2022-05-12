package com.nullpointer.moviescompose.inject

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nullpointer.moviescompose.data.local.MovieDAO
import com.nullpointer.moviescompose.data.local.MovieDataBase
import com.nullpointer.moviescompose.data.remote.MoviesApiServices
import com.nullpointer.moviescompose.data.remote.MoviesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Named("baseUrl")
    @Provides
    fun provideBaseUrl(): String = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideRetrofit(
        baseUrl: String,
        gson: Gson,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Singleton
    @Provides
    fun provideMoviesApiServices(
        retrofit: Retrofit,
    ): MoviesApiServices = retrofit.create(MoviesApiServices::class.java)

    @Singleton
    @Provides
    fun provideMoviesDataSource(
        moviesApiServices: MoviesApiServices,
    ): MoviesDataSource = MoviesDataSource(moviesApiServices)

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

}