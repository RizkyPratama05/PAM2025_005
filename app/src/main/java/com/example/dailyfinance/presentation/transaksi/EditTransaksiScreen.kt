package com.example.dailyfinance.presentation.transaksi

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dailyfinance.presentation.components.AppTopBar
import com.example.dailyfinance.presentation.components.ConfirmDialog
import com.example.dailyfinance.presentation.navigation.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val Bg = Color(0xFFF8FAFC)
private val CardBg = Color.White
private val Blue = Color(0xFF1E3A8A)
private val Green = Color(0xFF22C55E)
private val Red = Color(0xFFEF4444)
private val TextMain = Color(0xFF0F172A)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTransaksiScreen(
    navController: NavController,
    transaksiId: Int,
    viewModel: TransaksiViewModel
) {
    val transaksi = viewModel.getTransaksiById(transaksiId) ?: return

    var isSaving by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }
    var kategori by remember { mutableStateOf(transaksi.kategori) }
    var nominal by remember { mutableStateOf(transaksi.nominal) }
    var jenis by remember { mutableStateOf(transaksi.jenis) }

    var showDeleteDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Edit Transaksi",
                onBack = { navController.popBackStack() }
            )
        },
        containerColor = Bg
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text("Detail Transaksi", fontWeight = FontWeight.Bold, color = TextMain)

                    OutlinedTextField(
                        value = kategori,
                        onValueChange = { kategori = it },
                        label = { Text("Kategori") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Blue)
                    )

                    OutlinedTextField(
                        value = nominal,
                        onValueChange = { nominal = it.filter { c -> c.isDigit() } },
                        label = { Text("Nominal") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Blue)
                    )

                    Text("Jenis Transaksi", fontWeight = FontWeight.Medium, color = TextMain)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        JenisButton(
                            title = "Pemasukan",
                            selected = jenis == "Pemasukan",
                            color = Green,
                            onClick = { jenis = "Pemasukan" }
                        )
                        JenisButton(
                            title = "Pengeluaran",
                            selected = jenis == "Pengeluaran",
                            color = Red,
                            onClick = { jenis = "Pengeluaran" }
                        )
                    }


                    Button(
                        onClick = {
                            isSaving = true
                            viewModel.updateTransaksi(
                                id = transaksiId,
                                kategori = kategori,
                                nominal = nominal.toIntOrNull() ?: 0,
                                jenis = jenis,
                                tanggal = transaksi.tanggal.toLongOrNull() ?: System.currentTimeMillis()
                            )

                            scope.launch {
                                delay(600)
                                isSaving = false
                                showSuccess = true
                                delay(800)
                                navController.popBackStack(Screen.Transaksi.route, inclusive = false)
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        shape = RoundedCornerShape(16.dp),
                        enabled = !isSaving,
                        colors = ButtonDefaults.buttonColors(containerColor = Green)
                    ) {
                        if (isSaving) {
                            CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp, modifier = Modifier.size(22.dp))
                        } else {
                            Text("Simpan Perubahan", fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }


                    OutlinedButton(
                        onClick = { showDeleteDialog = true },
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, Red),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Red)
                    ) {
                        Text("Hapus Transaksi", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }


        AnimatedVisibility(visible = showSuccess, enter = fadeIn() + scaleIn(), exit = fadeOut()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Green, modifier = Modifier.size(64.dp))
                        Spacer(Modifier.height(12.dp))
                        Text("Berhasil diperbarui", fontWeight = FontWeight.Bold, color = TextMain)
                    }
                }
            }
        }
    }

    if (showDeleteDialog) {
        ConfirmDialog(
            title = "Hapus Transaksi",
            message = "Yakin ingin menghapus transaksi ini?",
            onConfirm = {
                viewModel.hapusTransaksi(transaksiId)
                showDeleteDialog = false
                navController.popBackStack(Screen.Transaksi.route, inclusive = false)
            },
            onDismiss = { showDeleteDialog = false }
        )
    }
}