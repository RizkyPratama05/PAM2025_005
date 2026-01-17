package com.example.dailyfinance.presentation.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dailyfinance.presentation.navigation.Screen
import com.example.dailyfinance.presentation.transaksi.TransaksiViewModel
import com.example.dailyfinance.utils.formatRupiah
import com.example.dailyfinance.utils.formatTanggal

private val Bg = Color(0xFFF8FAFC)
private val CardBg = Color.White
private val garisBorder = Color(0xFFE2E8F0)

private val Blue = Color(0xFF1E3A8A)
private val Green = Color(0xFF22C55E)
private val Red = Color(0xFFEF4444)

private val TextMain = Color(0xFF0F172A)
private val TextSecondary = Color(0xFF64748B)

@Composable
fun DetailTransaksiScreen(
    navController: NavController,
    transaksiId: Int,
    viewModel: TransaksiViewModel
) {
    val transaksi = viewModel.transaksiList.find { it.id?.toInt() == transaksiId }

    Scaffold(
        containerColor = Bg
    ) { padding ->
        if (transaksi == null) {

            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Data tidak ditemukan")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Text("←", fontSize = 24.sp, color = Blue, fontWeight = FontWeight.Bold)
                    }
                    Text(
                        "Detail Transaksi",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = TextMain
                    )
                }


                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, garisBorder, RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBg),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        DetailItem("Kategori", transaksi.kategori)

                        DetailItem(
                            label = "Tanggal",
                            value = formatTanggal(transaksi.tanggal.toLongOrNull() ?: 0L)
                        )

                        DetailItem(
                            label = "Jenis",
                            value = transaksi.jenis,
                            valueColor = if (transaksi.jenis == "Pemasukan") Green else Red
                        )

                        DetailItem(
                            label = "Nominal",
                            value = "Rp ${formatRupiah(transaksi.nominal.toIntOrNull() ?: 0)}"
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))


                Button(
                    onClick = {
                        navController.navigate(Screen.EditTransaksi.createRoute(transaksiId))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Blue)
                ) {
                    Text("Edit Transaksi", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

@Composable
private fun DetailItem(
    label: String,
    value: String,
    valueColor: Color = TextMain
) {
    Column {
        Text(text = label, color = TextSecondary, fontSize = 13.sp)
        Spacer(Modifier.height(4.dp))
        Text(
            text = value,
            color = valueColor,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )
    }
}