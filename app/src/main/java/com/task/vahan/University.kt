package com.task.vahan

data class University(
    val name: String,       // University name
    val country: String,    // Country where the university is located
    val web_pages: List<String>, // Add this field to hold web_pages
    // University's website URL
)
