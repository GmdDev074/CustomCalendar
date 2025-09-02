# CustomCalendar Library 📅

[![](https://jitpack.io/v/GmdDev074/CustomCalendar.svg)](https://jitpack.io/#GmdDev074/CustomCalendar)

A fully customizable **horizontal scrolling calendar view** for Android.  
Supports date selection, “Today” navigation, and displaying dynamic data like check-ins, events, or attendance.

---

## Features

- Horizontal scrollable calendar for current month
- Highlight selected date
- “Today” button to navigate quickly to the current date
- Display associated data per date (e.g., check-ins)
- Fully customizable colors, fonts, and layouts
- Lightweight and easy to integrate

---

## Screenshots

Screenshots
<p float="left"> <img src="https://github.com/user-attachments/assets/820036fa-5d14-4f33-9d63-2834cfc1a087" width="180" /> <img src="https://github.com/user-attachments/assets/05fa279d-6b5d-4879-aed5-8e3661f6f9ac" width="180" /> <img src="https://github.com/user-attachments/assets/ff51e500-a671-4dcf-bb8f-7d40246363b0" width="180" /> <img src="https://github.com/user-attachments/assets/fcd5e259-100f-4913-bd91-3d786a82d2b9" width="180" /> </p>

---

## Customization

Colors: Update bg_day_selected.xml and bg_day_normal.xml for selected and normal day backgrounds.

Fonts & Sizes: Modify item_calendar_day.xml for text styles.

Layout: Change spacing, padding, or orientation in XML.

Dynamic Data: Connect your own adapter (like CheckInAdapter) to display per-date data.

---

## Installation

### Step 1: Add JitPack repository

Add the JitPack repository in your **project-level** `build.gradle`:

```gradle
allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation("com.github.GmdDev074:CustomCalendar:919bb465c8")
}



<androidx.recyclerview.widget.RecyclerView
    android:id="@+id/calendarRecyclerView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:clipToPadding="false"
    android:padding="16dp"/>


val calendarRecyclerView: RecyclerView = findViewById(R.id.calendarRecyclerView)
val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
calendarRecyclerView.layoutManager = layoutManager

// Generate days for the current month
val days = generateCurrentMonthDays() // List<LocalDate>

val calendarAdapter = CalendarAdapter(days) { selectedDate ->
    // Handle date selection
    updateCheckIns(selectedDate)
}
calendarRecyclerView.adapter = calendarAdapter

// Optionally set today's date as initially selected
calendarAdapter.setInitialSelected(LocalDate.now())


fabToday.setOnClickListener {
    calendarAdapter.setInitialSelected(LocalDate.now())
    scrollToToday()
}


