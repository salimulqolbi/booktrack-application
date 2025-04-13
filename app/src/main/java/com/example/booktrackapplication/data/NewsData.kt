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
            ),
            NewsItems(
                "Peluncuran Aplikasi Perpustakaan Biblioo",
                date = date,
                time = time,
                image = R.drawable.newsitem_image_2,
                tags = listOf("Buku Mapel")
            ),
            NewsItems(
                "Digitalisasi Pengambilan Buku Mapel Stemba",
                date = date,
                time = time,
                image = R.drawable.newsitem_image_1,
                tags = listOf("Digitalisasi", "Mapel", "Stemba")
            ),
            NewsItems(
                "Peluncuran Aplikasi Perpustakaan Biblioo",
                date = date,
                time = time,
                image = R.drawable.newsitem_image_2,
                tags = listOf("Buku Mapel")
            ),
            NewsItems(
                "Perpustakaan Bukan Lagi Tempat Kuno",
                date = date,
                time = time,
                image = R.drawable.newsitem_image_3,
                tags = listOf("Perpustakaan", "Pendidikan", "Inovasi")
            ),
            NewsItems(
                "Wajib Tahu! Kebiasaan Buruk di Perpustakaan",
                date = date,
                time = time,
                image = R.drawable.newsitem_image_4,
                tags = listOf("Attitude", "Perpustakaan")
            ),
            NewsItems(
                "Pahami Fungsi Perpustakaan Sebenarnya",
                date = date,
                time = time,
                image = R.drawable.newsitem_image5,
                tags = listOf("Kreativitas", "Teknologi")
            ),
            NewsItems(
                "Perpustakaan Bukan Lagi Tempat Kuno",
                date = date,
                time = time,
                image = R.drawable.newsitem_image_3,
                tags = listOf("Perpustakaan", "Pendidikan", "Inovasi")
            ),
            NewsItems(
                "Wajib Tahu! Kebiasaan Buruk di Perpustakaan",
                date = date,
                time = time,
                image = R.drawable.newsitem_image_4,
                tags = listOf("Attitude", "Perpustakaan")
            ),
            NewsItems(
                "Pahami Fungsi Perpustakaan Sebenarnya",
                date = date,
                time = time,
                image = R.drawable.newsitem_image5,
                tags = listOf("Kreativitas", "Teknologi")
            )
        )
    }
}