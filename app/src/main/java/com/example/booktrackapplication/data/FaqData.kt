package com.example.booktrackapplication.data.datastore

data class FaqItem(
    val question: String,
    val answer: String
)

val questionCategories = listOf(
    "Umum",
    "Aktivasi",
    "Kehilangan"
)

val questionsByCategory = mapOf(
    "Umum" to listOf(
        FaqItem(
            question = "Apa yang harus saya lakukan jika buku mata pelajaran hilang?",
            answer = "Silahkan hubungi pihak admin yaitu dengan mendatangi Perpustakaan SMKN 7 Semarang sesuai jam kerjanya."
        ),
        FaqItem(
            question = "Bagaimana cara melakukan scan buku mapel pada saat peminjaman?",
            answer = "Masuk ke menu scan buku bagian peminjaman, mulai scan qr di buku mapel anda. Sesuaikan dengan jumlah peminjaman buku masing - masing jurusan."
        ),
        FaqItem(
            question = "Bagaimana cara mengetahui bahwa buku mapel saya telah tertukar?",
            answer = "Masuk ke menu pencarian dan ketik kode buku maupun dapat scan qr code yang tertera di buku mapel. Setelah itu maka detail buku akan terlihat."
        ),
        FaqItem(
            question = "Bagaimana saya dapat melakukan reset password?",
            answer = "Silahkan menuju ke Profil di bagian reset password."
        )
    ),
    "Aktivasi" to listOf(
        FaqItem(
            question = "Apa saja yang perlu saya lakukan jika ingin aktivasi akun?",
            answer = "Silahkan input Nama lengkap, Nomor telepon dan buat kata sandi"
        ),
        FaqItem(
            question = "Apakah memerlukan untuk memasukkan nomor telepon pribadi?",
            answer = "Sangat penting memasukkan nomor telepon pribadi, apabila terjadi kehilangan buku maupun tertukarnya buku maka dapat siswa dihubungi."
        ),
        FaqItem(
            question = "Bagaimana cara mengubah data pribadi saya?",
            answer = "Silahkan masuk ke menu profil dan ubah di bagian aktivasi akun."
        )
    ),
    "Kehilangan" to listOf(
        FaqItem(
            question = "Jika saya kehilangan buku apa yang harus saya lakukan?",
            answer = "Silahkan hubungi bagian perpustakaan."
        ),
        FaqItem(
            question = "Apa yang harus saya lakukan jika saya menemukan buku hilang?",
            answer = "SIlahkan cek detail buku mapel dan hubungi pemilik buku. Jika tidak dapat dihubungi silahkan informasikan ke Perpustakaan."
        ),
        FaqItem(
            question = "Apa yang harus saya ganti jika saya kehilangan buku?",
            answer = "Siswa hanya dapat mengganti buku hilang dengan buku, akan tetapi diharuskan konfirmasi terlebih dahulu ke pihak perpustakaan."
        )
    )
)