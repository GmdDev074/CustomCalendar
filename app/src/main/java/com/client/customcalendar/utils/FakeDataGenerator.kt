package com.client.customcalendar.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.client.customcalendar.models.CheckIn
import java.time.LocalDate
import java.time.ZoneId
import kotlin.random.Random

object FakeDataGenerator {

    private val names = listOf("Alice", "Bob", "Charlie", "Diana", "Ethan", "Fiona")
    private val cities = listOf("New York", "Los Angeles", "Chicago", "Houston", "Miami")
    private val employers = listOf("Google", "Amazon", "Tesla", "Netflix", "Meta")
    private val streets = listOf("Main St", "Broadway", "Sunset Blvd", "Ocean Dr", "Fifth Ave")

    @RequiresApi(Build.VERSION_CODES.O)
    fun generateMonthData(month: LocalDate): List<CheckIn> {
        val checkIns = mutableListOf<CheckIn>()
        val firstDay = month.withDayOfMonth(1)
        val lastDay = month.withDayOfMonth(month.lengthOfMonth())

        var day = firstDay
        while (!day.isAfter(lastDay)) {
            val numEntries = Random.nextInt(4, 6) // 4–5 entries per day
            repeat(numEntries) {
                val millis = day.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

                val lat = Random.nextDouble(34.0, 41.0)
                val lng = Random.nextDouble(-118.0, -73.0)

                checkIns.add(
                    CheckIn(
                        name = names.random(),
                        caseNumber = "CASE-${Random.nextInt(1000, 9999)}",
                        phone = "555-${Random.nextInt(100, 999)}-${Random.nextInt(1000, 9999)}",
                        address = "${Random.nextInt(100, 999)} ${streets.random()}",
                        city = cities.random(),
                        zip = Random.nextInt(10000, 99999).toString(),
                        employer = employers.random(),
                        employerPhone = "555-${Random.nextInt(100, 999)}-${Random.nextInt(1000, 9999)}",
                        latitude = lat,
                        longitude = lng,
                        mapAddress = "https://maps.google.com/?q=$lat,$lng",
                        date = millis.toString(),
                        time = (millis + Random.nextLong(0, 86_400_000)).toString() // random offset in the day
                    )
                )
            }
            day = day.plusDays(1)
        }

        return checkIns
    }
}
