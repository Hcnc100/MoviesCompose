package com.nullpointer.moviescompose.core.utils

import android.os.Message
import com.nullpointer.moviescompose.R
import timber.log.Timber

object ExceptionManager {

    const val NO_NETWORK_ERROR = "NO_NETWORK_ERROR"

    fun getMessageForException(throwable: Throwable):Int{
        return getMessageForException(Exception(throwable))
    }

    fun getMessageForException(exception: Exception,message: String=""): Int {
        return if (exception is NullPointerException) {
            R.string.error_time_out_server
        } else {
            when (exception.message) {
                NO_NETWORK_ERROR -> R.string.error_conecction
                else -> {
                    Timber.e("${message}: $exception")
                    R.string.error_unknow
                }
            }
        }
    }
}