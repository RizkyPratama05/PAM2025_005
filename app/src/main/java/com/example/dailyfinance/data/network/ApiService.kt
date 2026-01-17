package com.example.dailyfinance.data.network

import com.example.dailyfinance.data.model.Transaksi
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("ambil_transaksi.php")
    fun getTransaksi(): Call<List<Transaksi>>

    @FormUrlEncoded
    @POST("tambah_transaksi.php")
    fun addTransaksi(
        @Field("kategori") kategori: String,
        @Field("jenis") jenis: String,
        @Field("nominal") nominal: Int,
        @Field("tanggal") tanggal: Long
    ): Call<String>

    @FormUrlEncoded
    @POST("update_transaksi.php")
    fun updateTransaksi(
        @Field("id") id: Int,
        @Field("kategori") kategori: String,
        @Field("jenis") jenis: String,
        @Field("nominal") nominal: Int,
        @Field("tanggal") tanggal: Long
    ): Call<String>

    @FormUrlEncoded
    @POST("hapus_transaksi.php")
    fun deleteTransaksi(
        @Field("id") id: Int
    ): Call<String>
}