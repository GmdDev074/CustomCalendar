package com.client.customcalendar.adapter

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.client.customcalendar.R
import java.time.LocalDate

class CalendarAdapter(
    private val days: List<LocalDate>,
    private val listener: (LocalDate) -> Unit
) : RecyclerView.Adapter<CalendarAdapter.DayViewHolder>() {

    private var selectedDate: LocalDate? = null

    inner class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDayNumber: TextView = itemView.findViewById(R.id.tvDayNumber)
        val tvDayName: TextView = itemView.findViewById(R.id.tvDayName)
        val container: LinearLayout = itemView as LinearLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calendar_day, parent, false)
        return DayViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val date = days[position]
        holder.tvDayNumber.text = date.dayOfMonth.toString()
        holder.tvDayName.text = date.dayOfWeek.name.take(3) // Mon, Tue, Wed...

        if (date == selectedDate) {
            holder.container.setBackgroundResource(R.drawable.bg_day_selected)
            holder.tvDayNumber.setTextColor(Color.WHITE)
            holder.tvDayName.setTextColor(Color.WHITE)
        } else {
            holder.container.setBackgroundResource(R.drawable.bg_day_normal)
            holder.tvDayNumber.setTextColor(Color.BLACK)
            holder.tvDayName.setTextColor(Color.DKGRAY)
        }

        holder.itemView.setOnClickListener {
            selectedDate = date
            notifyDataSetChanged()
            listener(date)
        }
    }

    override fun getItemCount(): Int = days.size

    fun setInitialSelected(date: LocalDate) {
        if (days.contains(date)) {
            selectedDate = date
            notifyDataSetChanged()
        }
    }
}