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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dailyfinance.presentation.components.AppTopBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val Bg = Color(0xFFF8FAFC)
private val CardBg = Color.White
private val Border = Color(0xFFE2E8F0)

private val Blue = Color(0xFF1E3A8A)
private val Green = Color(0xFF22C55E)
private val Red = Color(0xFFEF4444)

private val TextMain = Color(0xFF0F172A)
private val TextHint = Color(0xFF64748B)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryTransaksiScreen(
    navController: NavController,
    viewModel: TransaksiViewModel
) {
    var kategori by remember { mutableStateOf("") }
    var nominal by remember { mutableStateOf("") }
    var jenis by remember { mutableStateOf("Pengeluaran") }
    var isSaving by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val isFormValid = kategori.isNotBlank() && nominal.isNotBlank()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Tambah Transaksi",
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
                colors = CardDefaults.cardColors(containerColor = CardBg),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Text(
                        "Detail Transaksi",
                        fontWeight = FontWeight.Bold,
                        color = TextMain
                    )


                    OutlinedTextField(
                        value = kategori,
                        onValueChange = { kategori = it },
                        label = { Text("Kategori") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Blue,
                            focusedLabelColor = Blue,
                            cursorColor = Blue
                        )
                    )


                    OutlinedTextField(
                        value = nominal,
                        onValueChange = { nominal = it.filter { c -> c.isDigit() } },
                        label = { Text("Nominal") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Blue,
                            focusedLabelColor = Blue,
                            cursorColor = Blue
                        )
                    )


                    Text(
                        "Jenis Transaksi",
                        fontWeight = FontWeight.Medium,
                        color = TextMain
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        JenisButton(
                            title = "Pemasukan",
                            selected = jenis == "Pemasukan",
                            color = Green
                        ) { jenis = "Pemasukan" }

                        JenisButton(
                            title = "Pengeluaran",
                            selected = jenis == "Pengeluaran",
                            color = Red
                        ) { jenis = "Pengeluaran" }
                    }


                    Button(
                        onClick = {
                            isSaving = true

                            viewModel.tambahTransaksi(
                                kategori = kategori,
                                nominal = nominal.toIntOrNull() ?: 0,
                                jenis = jenis,
                                tanggal = System.currentTimeMillis()
                            )

                            scope.launch {
                                delay(600)
                                isSaving = false
                                showSuccess = true

                                delay(800)
                                navController.popBackStack()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(16.dp),
                        enabled = isFormValid && !isSaving,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Green,
                            disabledContainerColor = Color.LightGray
                        )
                    ) {
                        if (isSaving) {
                            CircularProgressIndicator(
                                color = Color.White,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(22.dp)
                            )
                        } else {
                            Text(
                                "Simpan",
                                fontWeight = FontWeight.Bold,
                                color = if (isFormValid) Color.White else TextHint
                            )
                        }
                    }
                }
            }
        }


        AnimatedVisibility(
            visible = showSuccess,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Green,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            "Berhasil disimpan",
                            fontWeight = FontWeight.Bold,
                            color = TextMain
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RowScope.JenisButton(
    title: String,
    selected: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.weight(1f),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(
            1.dp,
            if (selected) color else Color.LightGray
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (selected) color.copy(alpha = 0.12f) else Color.Transparent,
            contentColor = color
        )
    ) {
        Text(title, fontWeight = FontWeight.SemiBold)
    }
}