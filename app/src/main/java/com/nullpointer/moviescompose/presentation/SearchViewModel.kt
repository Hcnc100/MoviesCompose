package com.nullpointer.moviescompose.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nullpointer.moviescompose.R
import com.nullpointer.moviescompose.core.states.SavableComposeState
import com.nullpointer.moviescompose.core.utils.NetWorkException
import com.nullpointer.moviescompose.core.utils.TimeOutException
import com.nullpointer.moviescompose.domain.MoviesRepoImpl
import com.nullpointer.moviescompose.domain.MoviesRepository
import com.nullpointer.moviescompose.models.apiResponse.MovieApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val moviesRepo: MoviesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var isLoading by mutableStateOf(false)
    private var jobSearch: Job? = null

    private var _listSearch = MutableStateFlow<List<MovieApiResponse.Movie>>(emptyList())
    val listSearch = _listSearch.asStateFlow()

    private val _messageSearch = Channel<Int>()
    val messageSearch = _messageSearch.receiveAsFlow()

    var querySearch by SavableComposeState(savedStateHandle,"KEY_QUERY","")
    private set

    fun searchMovie(query: String) {
        querySearch=query
        jobSearch?.cancel()
        if(query.isNotEmpty())
        jobSearch = viewModelScope.launch {
            isLoading = true
            try {
                delay(1_500)
                _listSearch.value = withContext(Dispatchers.IO){
                    moviesRepo.getMoviesForSearch(query)
                }
            } catch (e: Exception) {
                when(e){
                    is CancellationException -> throw e
                    is NetWorkException -> _messageSearch.trySend(R.string.error_conecction)
                    is TimeOutException -> _messageSearch.trySend(R.string.error_time_out_server)
                    else -> {
                        _messageSearch.trySend(R.string.error_unknow)
                        Timber.e("Unknown error api request $e")
                    }
                }
            } finally {
                isLoading = false
            }
        }
    }

    fun clearSearch(){
       querySearch=""
       jobSearch?.cancel()
       _listSearch.value= emptyList()
    }
}