package com.example.dailyfinance.presentation.navigation

sealed class Screen(
    val route: String,
    val label: String
) {

    object Welcome : Screen("welcome", "Welcome")

    object Dashboard : Screen("dashboard", "Home")
    object EntryTransaksi : Screen("entry", "Tambah")
    object Transaksi : Screen("transaksi", "Transaksi")

    object DetailTransaksi : Screen("detail/{id}", "Detail") {
        fun createRoute(id: Int) = "detail/$id"
    }

    object EditTransaksi : Screen("edit/{id}", "Edit") {
        fun createRoute(id: Int) = "edit/$id"
    }
}



