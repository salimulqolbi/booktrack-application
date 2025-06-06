package com.example.booktrackapplication.model

import com.example.booktrackapplication.R

sealed class NavItems(
    val title: String,
    val route: String,
    val selectedIcon: Int,
    val unselectedIcon: Int
) {
    object Home : NavItems(
        title = "Beranda",
        route = "home",
        selectedIcon = R.drawable.home_selected,
        unselectedIcon = R.drawable.home_unselected
    )

    object Activity : NavItems(
        title = "Aktivitas",
        route = "activity",
        selectedIcon = R.drawable.aktivitas_selected,
        unselectedIcon = R.drawable.aktivitas_unselected
    )

    object History : NavItems(
        title = "Riwayat",
        route = "history",
        selectedIcon = R.drawable.riwayat_selected,
        unselectedIcon = R.drawable.riwayat_unselected
    )

    object Profile : NavItems(
        title = "Profil",
        route = "profile",
        selectedIcon = R.drawable.profil_selected,
        unselectedIcon = R.drawable.profil_unselected
    )
}