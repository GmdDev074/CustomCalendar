package com.client.customcalendar.adapter

import android.os.Build
import androidx.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.client.customcalendar.models.CheckIn
import com.client.customcalendar.R
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.let
import kotlin.text.all
import kotlin.text.ifEmpty
import kotlin.text.isDigit
import kotlin.text.isEmpty
import kotlin.text.orEmpty
import kotlin.text.toLongOrNull
import kotlin.text.trim

@RequiresApi(Build.VERSION_CODES.O)
class CheckInAdapter(private var checkIns: List<CheckIn>) :
    RecyclerView.Adapter<CheckInAdapter.CheckInViewHolder>() {

    inner class CheckInViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.nameText)
        val caseText: TextView = itemView.findViewById(R.id.caseText)
        val phoneText: TextView = itemView.findViewById(R.id.phoneText)
        val addressText: TextView = itemView.findViewById(R.id.addressText)
        val cityText: TextView = itemView.findViewById(R.id.cityText)
        val zipText: TextView = itemView.findViewById(R.id.zipText)
        val employerText: TextView = itemView.findViewById(R.id.employerText)
        val employerPhoneText: TextView = itemView.findViewById(R.id.employerPhoneText)
        val latText: TextView = itemView.findViewById(R.id.latText)
        val lngText: TextView = itemView.findViewById(R.id.lngText)
        val mapAddressText: TextView = itemView.findViewById(R.id.mapAddressText)
        val dateText: TextView = itemView.findViewById(R.id.dateText)
        val timeText: TextView = itemView.findViewById(R.id.timeText)
    }

    // Display formats
    private val dateOutFmt = DateTimeFormatter.ofPattern("EEE, MMM d, yyyy", Locale.getDefault())
    private val timeOutFmt = DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckInViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_checkin, parent, false)
        return CheckInViewHolder(view)
    }

    override fun onBindViewHolder(holder: CheckInViewHolder, position: Int) {
        val checkIn = checkIns[position]

        holder.nameText.text = "${checkIn.name}"
        holder.caseText.text = "${checkIn.caseNumber}"
        holder.phoneText.text = "${checkIn.phone}"
        holder.addressText.text = "${checkIn.address}"
        holder.cityText.text = "${checkIn.city}"
        holder.zipText.text = "${checkIn.zip}"
        holder.employerText.text = "${checkIn.employer}"
        holder.employerPhoneText.text = "${checkIn.employerPhone}"
        holder.latText.text = "${checkIn.latitude ?: "N/A"}"
        holder.lngText.text = "${checkIn.longitude ?: "N/A"}"
        holder.mapAddressText.text = "${checkIn.mapAddress}"

        // ---- Human-readable date & time ----
        val millisFromDate = parseToMillis(checkIn.date)

        // Date prefers epoch from 'date'; falls back to common string formats; else original
        holder.dateText.text = humanizeDate(checkIn.date, millisFromDate)

        // Time prefers epoch (from 'date'); else parse 'time'; else original
        holder.timeText.text = humanizeTime(checkIn.time, millisFromDate)
    }

    override fun getItemCount(): Int = checkIns.size

    fun updateList(newList: List<CheckIn>) {
        checkIns = newList
        notifyDataSetChanged()
    }

    // ---------------- Helpers ----------------

    private fun humanizeDate(rawDate: String?, millis: Long?): String {
        millis?.let { return formatDate(it) }
        // Try a few common date-only patterns if not epoch
        val patterns = listOf(
            "yyyy-MM-dd",
            "dd/MM/yyyy",
            "MM/dd/yyyy",
            "dd MMM yyyy",
            "EEE, dd MMM yyyy"
        )
        for (p in patterns) {
            try {
                val ld = LocalDate.parse(rawDate?.trim(),
                    DateTimeFormatter.ofPattern(p, Locale.getDefault()))
                return ld.format(dateOutFmt)
            } catch (_: Exception) { /* try next */ }
        }
        // Try ISO date-time forms
        try {
            val ldt = LocalDateTime.parse(rawDate?.trim(), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            return ldt.toLocalDate().format(dateOutFmt)
        } catch (_: Exception) { /* ignore */ }

        // Fallback: original string or N/A
        return rawDate?.trim().orEmpty().ifEmpty { "N/A" }
    }

    private fun humanizeTime(rawTime: String?, millisFromDate: Long?): String {
        // If we have epoch from 'date', use it for accurate local time
        millisFromDate?.let { return formatTime(it) }

        // If 'time' itself is epoch (ms or s)
        parseToMillis(rawTime)?.let { return formatTime(it) }

        // Try common time-only patterns and reformat
        val timePatterns = listOf("HH:mm", "HH:mm:ss", "h:mm a")
        for (p in timePatterns) {
            try {
                val lt = LocalTime.parse(rawTime?.trim(), DateTimeFormatter.ofPattern(p, Locale.getDefault()))
                return lt.format(timeOutFmt)
            } catch (_: Exception) { /* try next */ }
        }

        // Fallback: original string or N/A
        return rawTime?.trim().orEmpty().ifEmpty { "N/A" }
    }

    private fun formatDate(millis: Long): String =
        Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).format(dateOutFmt)

    private fun formatTime(millis: Long): String =
        Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).format(timeOutFmt)

    /**
     * Attempts to parse a string into epoch millis.
     * - Pure digits: treat as ms; if <=10 digits, treat as seconds.
     * - ISO-8601 instants: Instant.parse(...)
     * - ISO local date-time: LocalDateTime.parse(...), assume system zone.
     */
    private fun parseToMillis(value: String?): Long? {
        val s = value?.trim().orEmpty()
        if (s.isEmpty()) return null

        // Digits -> epoch (seconds or millis)
        if (s.all { it.isDigit() }) {
            val n = s.toLongOrNull() ?: return null
            return if (s.length <= 10) n * 1000 else n
        }

        // ISO instant
        try {
            return Instant.parse(s).toEpochMilli()
        } catch (_: Exception) { /* not an instant */ }

        // ISO local date-time
        return try {
            val ldt = LocalDateTime.parse(s, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        } catch (_: Exception) {
            null
        }
    }
}
