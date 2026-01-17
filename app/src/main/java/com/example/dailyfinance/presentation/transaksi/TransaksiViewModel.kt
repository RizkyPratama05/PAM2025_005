package com.example.dailyfinance.presentation.transaksi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.dailyfinance.data.model.Transaksi
import com.example.dailyfinance.data.repository.TransaksiRepository

class TransaksiViewModel : ViewModel() {
    private val repository = TransaksiRepository()

    val transaksiList = mutableStateListOf<Transaksi>()
    var saldo by mutableStateOf(0)
    var totalMasuk by mutableStateOf(0)
    var totalKeluar by mutableStateOf(0)

    init {
        loadTransaksi()
    }
    fun loadTransaksi() {
        repository.getSemuaTransaksi { data ->
            if (data != null) {
                transaksiList.clear()
                transaksiList.addAll(data)

                var masuk = 0
                var keluar = 0
                data.forEach {
                    val nominal = it.nominal.toIntOrNull() ?: 0
                    if (it.jenis == "Pemasukan") masuk += nominal else keluar += nominal
                }
                totalMasuk = masuk
                totalKeluar = keluar
                saldo = masuk - keluar
            }
        }
    }

    fun tambahTransaksi(kategori: String, nominal: Int, jenis: String, tanggal: Long) {
        val baru = Transaksi(
            id = null,
            kategori = kategori,
            jenis = jenis,
            nominal = nominal.toString(),
            tanggal = tanggal.toString()
        )
        repository.tambahTransaksi(baru) { hasil ->
            if (hasil == "success") {
                loadTransaksi()
            }
        }
    }


    fun getTransaksiById(id: Int): Transaksi? {
        return transaksiList.find { it.id?.toInt() == id }
    }

    fun hapusTransaksi(id: Int) {
        repository.hapusTransaksi(id.toString()) { hasil ->
            if (hasil == "success") {
                loadTransaksi()
            }
        }
    }

    fun updateTransaksi(id: Int, kategori: String, nominal: Int, jenis: String, tanggal: Long) {
        val data = Transaksi(id.toString(), kategori, jenis, nominal.toString(), tanggal.toString())
        repository.updateTransaksi(data) { hasil ->
            if (hasil == "success") loadTransaksi()
        }
    }
}