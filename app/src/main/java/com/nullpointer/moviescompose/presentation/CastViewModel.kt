package com.nullpointer.moviescompose.presentation

import androidx.lifecycle.ViewModel
import com.nullpointer.moviescompose.core.utils.ExceptionManager
import com.nullpointer.moviescompose.core.utils.Resource
import com.nullpointer.moviescompose.core.utils.launchSafeIO
import com.nullpointer.moviescompose.domain.MoviesRepository
import com.nullpointer.moviescompose.models.Cast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CastViewModel @Inject constructor(
    private val moviesRepo: MoviesRepository,
) : ViewModel() {

    private val _messageCast = Channel<Int>()
    val messageCast = _messageCast.receiveAsFlow()

    private var jobRequestCast: Job? = null

    private val _listCastMovie = MutableStateFlow<Resource<List<Cast>>>(Resource.Loading)
    val listCastMovie = _listCastMovie.asStateFlow()

    private var idMovieCast: Long = -1


    fun getCastFromMovie(idMovie: Long) {
        jobRequestCast?.cancel()
        if (idMovie != idMovieCast || _listCastMovie.value is Resource.Failure) {
            idMovieCast = idMovie
            jobRequestCast = launchSafeIO(
                blockBefore = { _listCastMovie.value = Resource.Loading },
                blockIO = {
                    val listCast = moviesRepo.getCastFromMovie(idMovie)
                    withContext(Dispatchers.Main) {
                        _listCastMovie.value = Resource.Success(listCast)
                    }
                },
                blockException = { exception ->
                    _listCastMovie.value = Resource.Failure
                    _messageCast.trySend(
                        ExceptionManager.getMessageForException(
                            exception,
                            "Error request cast from $idMovie:"
                        )
                    )
                }
            )
        }
    }
}