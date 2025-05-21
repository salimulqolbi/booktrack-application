package com.example.booktrackapplication.data

import com.example.booktrackapplication.R
import com.example.booktrackapplication.model.NewsItems
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class NewsData() {
    fun loadNewsData(): List<NewsItems> {
        val formatterDate = DateTimeFormatter.ofPattern("d MMMM yyyy")
        val formatterTime = DateTimeFormatter.ofPattern("HH:mm")

        val date = LocalDate.now().format(formatterDate)
        val time = LocalTime.now().format(formatterTime) + "WIB"

        return listOf<NewsItems>(
            NewsItems(
                "Digitalisasi Pengambilan Buku Mapel Stemba",
                date = date,
                time = time,
                image = R.drawable.newsitem_image_1,
                tags = listOf("Digitalisasi", "Mapel", "Stemba")
            )
        )
    }
}