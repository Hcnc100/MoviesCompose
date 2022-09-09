package com.nullpointer.moviescompose.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.nullpointer.moviescompose.core.states.SavableComposeState
import com.nullpointer.moviescompose.core.utils.ExceptionManager
import com.nullpointer.moviescompose.core.utils.Resource
import com.nullpointer.moviescompose.core.utils.launchSafeIO
import com.nullpointer.moviescompose.domain.MoviesRepository
import com.nullpointer.moviescompose.models.apiResponse.movie.MovieDTO
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

    companion object {
        private const val KEY_QUERY = "KEY_QUERY"
    }

    private var jobSearch: Job? = null

    private var _listSearch = MutableStateFlow<Resource<List<MovieDTO>>?>(null)
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
                blockBefore = { _listSearch.value = Resource.Loading },
                blockIO = {
                    delay(1_500)
                    val listSearch = moviesRepo.getMoviesForSearch(query)
                    withContext(Dispatchers.Main) {
                        _listSearch.value = Resource.Success(listSearch)
                    }
                },
                blockException = {
                    _listSearch.value = Resource.Failure
                    _messageSearch.trySend(
                        ExceptionManager.getMessageForException(it, "Error search request")
                    )
                }
            )
        } else {
            clearSearch()
        }

    }

    private fun clearSearch() {
        querySearch = ""
        jobSearch?.cancel()
        _listSearch.value = null
    }
}