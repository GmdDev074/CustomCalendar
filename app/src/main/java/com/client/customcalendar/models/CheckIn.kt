package com.client.customcalendar.models

data class CheckIn(
    val name: String,
    val caseNumber: String,
    val phone: String,
    val address: String,
    val city: String,
    val zip: String,
    val employer: String,
    val employerPhone: String,
    val latitude: Double?,
    val longitude: Double?,
    val mapAddress: String,
    val date: String,
    val time: String
)
