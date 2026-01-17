package com.example.dailyfinance.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dailyfinance.presentation.transaksi.TransaksiViewModel
import com.example.dailyfinance.utils.formatRupiah
import com.example.dailyfinance.utils.formatTanggal


private val BluePrimary = Color(0xFF1E3A8A)
private val BlueDark = Color(0xFF0F172A)
private val GreenAccent = Color(0xFF22C55E)
private val GreenSoft = Color(0xFF16A34A)
private val RedAccent = Color(0xFFEF4444)

private val CardBg = Color.White
private val TextPrimary = Color(0xFF0F172A)
private val TextSecondary = Color(0xFF64748B)
private val TextHint = Color(0xFF64748B)


@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: TransaksiViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.loadTransaksi()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(BluePrimary, BlueDark)
                )
            )
    ) {


        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "DailyFinance",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Kelola keuanganmu",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    CardBg,
                    RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
                )
                .padding(16.dp)
        ) {


            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF1F5F9)
                )
            ) {
                Column(Modifier.padding(20.dp)) {
                    Text(
                        text = "Total Saldo",
                        color = TextSecondary,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Rp ${formatRupiah(viewModel.saldo)}",
                        color = BluePrimary,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Pemasukan", fontSize = 12.sp, color = TextSecondary)
                            Text(
                                "Rp ${formatRupiah(viewModel.totalMasuk)}",
                                color = GreenAccent,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("Pengeluaran", fontSize = 12.sp, color = TextSecondary)
                            Text(
                                "Rp ${formatRupiah(viewModel.totalKeluar)}",
                                color = RedAccent,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Transaksi Terakhir",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = TextPrimary
            )

            Spacer(Modifier.height(12.dp))


            if (viewModel.transaksiList.isEmpty()) {
                EmptyState()
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(viewModel.transaksiList) { t ->
                        Card(
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFF8FAFC)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        t.kategori,
                                        fontWeight = FontWeight.SemiBold,
                                        color = TextPrimary
                                    )
                                    Text(
                                        t.jenis,
                                        fontSize = 12.sp,
                                        color = if (t.jenis == "Pemasukan")
                                            GreenSoft else RedAccent
                                    )
                                    Text(
                                        formatTanggal(t.tanggal.toLongOrNull() ?: 0L),
                                        color = TextHint,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                Text(
                                    formatRupiah(t.nominal.toIntOrNull() ?: 0),
                                    fontWeight = FontWeight.Bold,
                                    color = BluePrimary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Belum ada transaksi",
            color = TextSecondary
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = "Mulai catat keuanganmu sekarang 💸",
            color = TextSecondary,
            fontSize = 13.sp
        )
    }
}
