package com.client.customcalendar.database

import com.client.customcalendar.models.CheckIn
import io.paperdb.Paper

object DatabaseHelper {

    private const val BOOK_NAME = "checkins"
    private const val KEY_LIST = "checkin_list"

    // Save new check-in (append to list)
    fun saveCheckIn(checkIn: CheckIn) {
        val list = getAllCheckIns().toMutableList()
        list.add(checkIn)
        Paper.book(BOOK_NAME).write(KEY_LIST, list)
    }

    // Get all check-ins
    fun getAllCheckIns(): List<CheckIn> {
        return Paper.book(BOOK_NAME).read(KEY_LIST, emptyList()) ?: emptyList()
    }

    // Clear all check-ins
    fun clearAll() {
        Paper.book(BOOK_NAME).delete(KEY_LIST)
    }
}