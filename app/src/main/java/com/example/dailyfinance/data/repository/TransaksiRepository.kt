package com.example.dailyfinance.data.repository

import com.example.dailyfinance.data.model.Transaksi
import com.example.dailyfinance.data.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransaksiRepository {
    private val apiService = RetrofitClient.instance

    fun getSemuaTransaksi(onResult: (List<Transaksi>?) -> Unit) {
        apiService.getTransaksi().enqueue(object : Callback<List<Transaksi>> {
            override fun onResponse(call: Call<List<Transaksi>>, response: Response<List<Transaksi>>) {
                if (response.isSuccessful) onResult(response.body())
                else onResult(null)
            }

            override fun onFailure(call: Call<List<Transaksi>>, t: Throwable) {
                onResult(null)
            }
        })
    }

    fun tambahTransaksi(transaksi: Transaksi, callback: (String) -> Unit) {
        apiService.addTransaksi(
            transaksi.kategori,
            transaksi.jenis,
            transaksi.nominal.toInt(),
            transaksi.tanggal.toLong()
        ).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {

                println("DEBUG_API: Response Body -> ${response.body()}")
                println("DEBUG_API: Response Code -> ${response.code()}")

                if (response.isSuccessful) callback("success")
                else callback("failed")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {

                println("DEBUG_API: Error -> ${t.message}")
                callback("error")
            }
        })
    }

    fun updateTransaksi(t: Transaksi, onResult: (String) -> Unit) {
        apiService.updateTransaksi(
            t.id?.toInt() ?: 0,
            t.kategori,
            t.jenis,
            t.nominal.toInt(),
            t.tanggal.toLong()
        ).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                onResult(response.body() ?: "error")
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                onResult("error")
            }
        })
    }

    fun hapusTransaksi(id: String, onResult: (String) -> Unit) {
        apiService.deleteTransaksi(id.toInt()).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                onResult(response.body() ?: "error")
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                onResult("error")
            }
        })
    }
}