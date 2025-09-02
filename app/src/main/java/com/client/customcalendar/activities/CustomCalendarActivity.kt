package com.client.customcalendar.activities

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.client.customcalendar.R
import com.client.customcalendar.adapter.CalendarAdapter
import com.client.customcalendar.adapter.CheckInAdapter
import com.client.customcalendar.database.DatabaseHelper
import com.client.customcalendar.models.CheckIn
import com.client.customcalendar.utils.FakeDataGenerator
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class CustomCalendarActivity : AppCompatActivity() {

    private lateinit var allCheckIns: List<CheckIn>  //  for static data
    private lateinit var calendarAdapter: CalendarAdapter
    private lateinit var checkInAdapter: CheckInAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var fabToday: AppCompatButton
    private lateinit var tvMonthYear: TextView
    private lateinit var calendarRecyclerView: RecyclerView
    private lateinit var customCalendarRecyclerView: RecyclerView
    private lateinit var toolbar: Toolbar
    private lateinit var currentDate: LocalDate

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_custom_calendar)


        // Insets for edge-to-edge
        val root: View = findViewById(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val b = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(b.left, b.top, b.right, b.bottom)
            WindowInsetsCompat.CONSUMED
        }

        WindowInsetsControllerCompat(window, root).setAppearanceLightStatusBars(true)
        window.navigationBarColor = Color.TRANSPARENT
        window.statusBarColor = Color.TRANSPARENT

        // Views
        tvMonthYear = findViewById(R.id.tvMonthYear)
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView)
        customCalendarRecyclerView = findViewById(R.id.customCalendarRecyclerView)
        fabToday = findViewById(R.id.fabToday)
        toolbar = findViewById(R.id.toolbar)

        // Toolbar setup
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.app_name)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        // Current date
        currentDate = LocalDate.now()
        tvMonthYear.text = currentDate.format(DateTimeFormatter.ofPattern("MMMM yyyy"))

        // Generate static demo data for current month
        allCheckIns = FakeDataGenerator.generateMonthData(currentDate)

        // Generate days for current month
        val firstOfMonth = currentDate.withDayOfMonth(1)
        val lastOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth())
        val days = mutableListOf<LocalDate>()
        var day = firstOfMonth
        while (!day.isAfter(lastOfMonth)) {
            days.add(day)
            day = day.plusDays(1)
        }

        // Calendar setup
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        calendarRecyclerView.layoutManager = layoutManager

        calendarAdapter = CalendarAdapter(days) { selectedDate ->
            updateCheckIns(selectedDate)
            toggleFab(selectedDate)
        }
        calendarRecyclerView.adapter = calendarAdapter

        // Scroll to today initially
        val currentPosition = currentDate.dayOfMonth - 1
        layoutManager.scrollToPositionWithOffset(
            currentPosition,
            (resources.displayMetrics.widthPixels / 2) -
                    (60 * resources.displayMetrics.density).toInt() / 2
        )

        // Check-in list setup
        checkInAdapter = CheckInAdapter(emptyList())
        customCalendarRecyclerView.layoutManager = LinearLayoutManager(this)
        customCalendarRecyclerView.adapter = checkInAdapter

        calendarAdapter.setInitialSelected(currentDate)
        updateCheckIns(currentDate)

        // FAB action
        fabToday.setOnClickListener {
            calendarAdapter.setInitialSelected(currentDate)
            updateCheckIns(currentDate)
            scrollToToday()
            fabToday.visibility = View.GONE
        }
    }

    private fun toggleFab(selectedDate: LocalDate) {
        fabToday.visibility = if (selectedDate == currentDate) View.GONE else View.VISIBLE
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun scrollToToday() {
        val todayPosition = currentDate.dayOfMonth - 1
        layoutManager.scrollToPositionWithOffset(
            todayPosition,
            (resources.displayMetrics.widthPixels / 2) -
                    (60 * resources.displayMetrics.density).toInt() / 2
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateCheckIns(date: LocalDate) {
        val startOfDay = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val endOfDay = date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - 1

        val filtered = allCheckIns.filter {
            val checkInTime = it.date.toLongOrNull() ?: 0L
            checkInTime in startOfDay..endOfDay
        }

        checkInAdapter.updateList(filtered)
    }

}
