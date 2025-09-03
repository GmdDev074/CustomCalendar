# CustomCalendar Library 📅

[![](https://jitpack.io/v/GmdDev074/CustomCalendar.svg)](https://jitpack.io/#GmdDev074/CustomCalendar)

![GitHub stars](https://img.shields.io/github/stars/GmdDev074/CustomCalendar?style=social)
![GitHub forks](https://img.shields.io/github/forks/GmdDev074/CustomCalendar?style=social)
![GitHub watchers](https://img.shields.io/github/watchers/GmdDev074/CustomCalendar?style=social)

![License](https://img.shields.io/github/license/GmdDev074/CustomCalendar)
![GitHub release](https://img.shields.io/github/v/release/GmdDev074/CustomCalendar)
![Release Date](https://img.shields.io/github/release-date/GmdDev074/CustomCalendar)
![Issues](https://img.shields.io/github/issues/GmdDev074/CustomCalendar)
![Pull Requests](https://img.shields.io/github/issues-pr/GmdDev074/CustomCalendar)
![Top Language](https://img.shields.io/github/languages/top/GmdDev074/CustomCalendar)
![Contributors](https://img.shields.io/github/contributors/GmdDev074/CustomCalendar)

![Last Commit](https://img.shields.io/github/last-commit/GmdDev074/CustomCalendar)
![GitHub all releases](https://img.shields.io/github/downloads/GmdDev074/CustomCalendar/total)
![Commit Activity](https://img.shields.io/github/commit-activity/m/GmdDev074/CustomCalendar)
![Maintenance](https://img.shields.io/maintenance/yes/2025)
[![codecov](https://codecov.io/gh/GmdDev074/CustomCalendar/branch/main/graph/badge.svg)](https://codecov.io/gh/GmdDev074/CustomCalendar)
![Dependabot Status](https://img.shields.io/badge/dependabot-enabled-brightgreen?logo=dependabot)
![GitHub Discussions](https://img.shields.io/github/discussions/GmdDev074/CustomCalendar)
![GitHub Wiki](https://img.shields.io/badge/wiki-available-brightgreen)

![Min SDK](https://img.shields.io/badge/minSdk-21%2B-blue)
![API](https://img.shields.io/badge/API-21%2B-green.svg?style=flat)
![Lint](https://img.shields.io/badge/lint-passing-brightgreen)
![Open Source Love](https://badges.frapsoft.com/os/v1/open-source.svg?v=103)
[![Sponsor](https://img.shields.io/badge/sponsor-%E2%9D%A4-red)](https://github.com/sponsors/GmdDev074)

---

## Contributors

[![Contributors](https://contrib.rocks/image?repo=GmdDev074/CustomCalendar)](https://github.com/GmdDev074/CustomCalendar/graphs/contributors)

---

## Overview


A fully customizable horizontal scrolling calendar view for Android that makes date management intuitive and visually appealing. Designed to be simple to integrate yet highly flexible, this library allows developers to display monthly calendars with dynamic content like check-ins, events, appointments, or attendance.

---

## Features

- **Horizontal scrolling monthly view** – smoothly swipe through the days of the month.  
- **Selectable dates** – highlight the selected date with custom colors and backgrounds.  
- **Today navigation** – quickly jump back to the current date using a floating action button (FAB).  
- **Dynamic data support** – attach events, check-ins, or any type of date-specific data and display it in a list below the calendar.  
- **Fully customizable UI** – change backgrounds, text colors, item spacing, fonts, and more to match your app’s design.  
- **Lightweight and optimized** – built with RecyclerView for smooth performance even with large datasets.  
- **Supports edge-to-edge layouts** – works well with modern immersive Android designs.

---

## Use Cases

- Attendance tracking apps  
- Habit tracking or journaling apps  
- Event scheduling apps  
- Health check-ins or appointment logs  
- Any app requiring date-specific data visualization 

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

Add the JitPack repository in your **project-level** `build.gradle` or `settings.gradle`:

```gradle
allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}

```

### 2: Add Dependency

Add dependency in your **app-level** `build.gradle`:

```dependencies {
    implementation("com.github.GmdDev074:CustomCalendar:v1.0.0")
}
```

### Step 3: Integrate in your project

```
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

```
---

## ⭐ Support

If you like **CustomCalendar** and find it helpful, please **give it a ⭐ on GitHub**!  

Your support helps us improve the library, add new features, and keep it maintained.

[![GitHub Stars](https://img.shields.io/github/stars/GmdDev074/CustomCalendar?style=social)](https://github.com/GmdDev074/CustomCalendar/stargazers)



