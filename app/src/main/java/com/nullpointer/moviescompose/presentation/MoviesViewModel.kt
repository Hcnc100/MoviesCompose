package com.nullpointer.moviescompose.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nullpointer.moviescompose.R
import com.nullpointer.moviescompose.core.utils.NetWorkException
import com.nullpointer.moviescompose.core.utils.TimeOutException
import com.nullpointer.moviescompose.domain.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepo: MoviesRepository,
) : ViewModel() {

    private val _messageMovies = Channel<Int>()
    val messageMovie = _messageMovies.receiveAsFlow()

    val listPopularMovies = moviesRepo.listMoviesPopular.catch {
        Timber.e("Error loading list of popular movies $it")
        emit(emptyList())
    }.flowOn(Dispatchers.IO).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        null
    )

    val listUpComingMovies = moviesRepo.listMoviesUpComing.catch {
        Timber.e("Error loading list of upcoming movies $it")
        emit(emptyList())
    }.flowOn(Dispatchers.IO).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        null
    )

    val listTopRated = moviesRepo.listMoviesTopRated.catch {
        Timber.e("Error loading list of top rated movies $it")
        emit(emptyList())
    }.flowOn(Dispatchers.IO).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        null
    )

    val isLoading = combine(
        listPopularMovies,
        listUpComingMovies,
        listTopRated
    ) { listPopular, listUpComing, listTopRated ->
        listPopular == null || listUpComing == null || listTopRated == null
    }

    var jobRequest: Job? = null
    var isRequested by mutableStateOf(true)

    init {
        updateAllMovies()
    }

    fun updateAllMovies() {
        jobRequest?.cancel()
        jobRequest = viewModelScope.launch {
            supervisorScope {
                try {
                    isRequested = true
                    val sizePopularUpdate = async(Dispatchers.IO) { moviesRepo.updateAllPopular() }
                    val sizeTopRatedUpdate =
                        async(Dispatchers.IO) { moviesRepo.updateAllTopRated() }
                    val sizeUpcomingUpdate =
                        async(Dispatchers.IO) { moviesRepo.updateAllUpcoming() }

                    val size =
                        sizePopularUpdate.await() + sizeTopRatedUpdate.await() + sizeUpcomingUpdate.await()
                    Timber.d("Size request movies $size")
                } catch (e: Exception) {
                    when (e) {
                        is CancellationException -> throw e
                        is NetWorkException -> _messageMovies.trySend(R.string.error_conecction)
                        is TimeOutException -> _messageMovies.trySend(R.string.error_time_out_server)
                        else -> {
                            _messageMovies.trySend(R.string.error_unknow)
                            Timber.e("Unknown error api request $e")
                        }
                    }
                } finally {
                    isRequested = false
                }
            }
        }
    }
}