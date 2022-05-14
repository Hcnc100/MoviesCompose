package com.nullpointer.moviescompose.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nullpointer.moviescompose.R
import com.nullpointer.moviescompose.core.states.SavableComposeState
import com.nullpointer.moviescompose.core.states.SavableProperty
import com.nullpointer.moviescompose.core.utils.NetWorkException
import com.nullpointer.moviescompose.core.utils.TimeOutException
import com.nullpointer.moviescompose.domain.MoviesRepository
import com.nullpointer.moviescompose.models.Cast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CastViewModel @Inject constructor(
    private val moviesRepo: MoviesRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _messageCast = Channel<Int>()
    val messageCast = _messageCast.receiveAsFlow()

    var jobRequestCast: Job? = null
    var isRequestedCast by mutableStateOf(true)
    var listCastMovie: List<Cast> by SavableComposeState(savedStateHandle, "LIST_CAST", emptyList())
    private var idMovieCast: Long by SavableProperty(savedStateHandle, "KEY_ID_MOVIE", -1)


    fun getCastFromMovie(idMovie: Long) {
        jobRequestCast?.cancel()
        if(idMovie!=idMovieCast){
            idMovieCast = idMovie
            jobRequestCast = viewModelScope.launch {
                isRequestedCast = true
                try {
                    listCastMovie = withContext(Dispatchers.IO) {
                        moviesRepo.getCastFromMovie(idMovie)
                    }
                } catch (e: Exception) {
                    when (e) {
                        is CancellationException -> throw e
                        is NetWorkException -> _messageCast.trySend(R.string.error_conecction)
                        is TimeOutException -> _messageCast.trySend(R.string.error_time_out_server)
                        else -> {
                            _messageCast.trySend(R.string.error_unknow)
                            Timber.e("Unknown error api cast request $e")
                        }
                    }
                } finally {
                    isRequestedCast = false
                }
            }
        }
    }
}