package com.example.dailyfinance.presentation.transaksi

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dailyfinance.presentation.navigation.Screen
import com.example.dailyfinance.utils.formatRupiah
import com.example.dailyfinance.utils.formatTanggal
import com.example.dailyfinance.utils.groupLabel

private val Bg = Color(0xFFF8FAFC)
private val CardBg = Color.White
private val Border = Color(0xFFE2E8F0)

private val Blue = Color(0xFF1E3A8A)
private val Green = Color(0xFF22C55E)
private val Red = Color(0xFFEF4444)

private val TextMain = Color(0xFF0F172A)
private val TextHint = Color(0xFF64748B)

@Composable
fun TransaksiScreen(
    navController: NavController,
    viewModel: TransaksiViewModel
) {

    val grouped = viewModel.transaksiList
        .sortedByDescending { it.tanggal }
        .groupBy { groupLabel(it.tanggal.toLongOrNull() ?: 0L) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
            .padding(16.dp)
    ) {
        Text(
            text = "Semua Transaksi",
            fontWeight = FontWeight.Bold,
            color = TextMain
        )

        Spacer(Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            grouped.forEach { (label, list) ->

                item {
                    Text(
                        text = label,
                        color = TextHint,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                items(list) { t ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    Screen.DetailTransaksi.createRoute(t.id?.toInt() ?: 0)
                                )
                            },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = CardBg),
                        border = BorderStroke(1.dp, Border),
                        elevation = CardDefaults.cardElevation(0.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    t.kategori,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextMain
                                )
                                Text(
                                    t.jenis,
                                    fontSize = 12.sp,
                                    color = if (t.jenis == "Pemasukan") Green else Red
                                )
                                Text(
                                    formatTanggal(t.tanggal.toLongOrNull() ?: 0L),
                                    color = TextHint,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Text(
                                text = "Rp ${formatRupiah(t.nominal.toIntOrNull() ?: 0)}",
                                fontWeight = FontWeight.Bold,
                                color = Blue
                            )
                        }
                    }
                }
            }
        }
    }
}

