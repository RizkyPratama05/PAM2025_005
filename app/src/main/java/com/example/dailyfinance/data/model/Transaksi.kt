package com.example.dailyfinance.data.model

import com.google.gson.annotations.SerializedName

data class Transaksi(
    @SerializedName("id") val id: String? = null,
    @SerializedName("kategori") val kategori: String,
    @SerializedName("jenis") val jenis: String,
    @SerializedName("nominal") val nominal: String,
    @SerializedName("tanggal") val tanggal: String
)