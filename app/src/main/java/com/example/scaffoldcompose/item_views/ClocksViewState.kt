package com.example.scaffoldcompose.item_views

import androidx.compose.runtime.Immutable
import java.util.Date

@Immutable
data class Clock(val time: Long, val isVisible: Boolean = false, val name: String) {
    fun getTime(): String {
        val date = Date()
        date.time = time
        val dateMinutes = date.minutes
        val dateSeconds = date.seconds
        val minute = if (dateMinutes in 0..9) "0$dateMinutes" else dateMinutes
        val seconds = if (dateSeconds in 0..9) "0${dateSeconds}" else dateSeconds
        return buildString { append("$minute : $seconds") }
    }
}