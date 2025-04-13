package com.example.booktrackapplication.model

import androidx.annotation.DrawableRes

data class NewsItems(
    val title: String,
    val date: String,
    val time: String,
    @DrawableRes val image: Int,
    val tags: List<String>
)
