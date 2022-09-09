package com.nullpointer.moviescompose.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.nullpointer.moviescompose.core.states.SavableComposeState
import com.nullpointer.moviescompose.core.utils.ExceptionManager
import com.nullpointer.moviescompose.core.utils.launchSafeIO
import com.nullpointer.moviescompose.domain.MoviesRepository
import com.nullpointer.moviescompose.models.apiResponse.MovieApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val moviesRepo: MoviesRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    companion object{
        private const val KEY_QUERY="KEY_QUERY"
    }

    var isLoading by mutableStateOf(false)
    private var jobSearch: Job? = null

    private var _listSearch = MutableStateFlow<List<MovieApiResponse.Movie>>(emptyList())
    val listSearch = _listSearch.asStateFlow()

    private val _messageSearch = Channel<Int>()
    val messageSearch = _messageSearch.receiveAsFlow()

    var querySearch by SavableComposeState(savedStateHandle, KEY_QUERY, "")
        private set

    fun searchMovie(query: String) {
        jobSearch?.cancel()
        if (query.isNotEmpty()) {
            querySearch = query
            jobSearch = launchSafeIO(
                blockBefore = { isLoading = true },
                // ! send is loading only if is not for cancel coroutine
                blockAfter = { isForCancelled ->
                    if (!isForCancelled) isLoading = false
                },
                blockIO = {
                    delay(1_500)
                    val listSearch = moviesRepo.getMoviesForSearch(query)
                    withContext(Dispatchers.Main) {
                        _listSearch.value = listSearch
                        isLoading = false
                    }
                },
                blockException = {
                    _messageSearch.trySend(
                        ExceptionManager.getMessageForException(it, "Error search request")
                    )
                }
            )
        } else {
            clearSearch()
        }

    }

    fun clearSearch() {
        isLoading=false
        querySearch = ""
        jobSearch?.cancel()
        _listSearch.value = emptyList()
    }
}