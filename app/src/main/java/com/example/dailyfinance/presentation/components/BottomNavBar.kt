package com.example.dailyfinance.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.dailyfinance.presentation.navigation.Screen
import com.example.dailyfinance.ui.theme.*



@Composable
fun BottomNavBar(navController: NavController) {

    NavigationBar(
        containerColor = Color(0xFFE6F4F1),
        tonalElevation = 0.dp
    ) {


        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.Dashboard.route) { inclusive = false }
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(Icons.Default.Home, contentDescription = "Home")
            },
            label = { Text("Home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Accent,
                unselectedIconColor = TextSecondary,
                indicatorColor = Color.Transparent
            )
        )


        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.EntryTransaksi.route)
                },
                containerColor = Color(0xFF22C55E),
                shape = CircleShape
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Tambah",
                    tint = Color.White
                )
            }
        }


        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate(Screen.Transaksi.route) {
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(Icons.Default.ReceiptLong, contentDescription = "Transaksi")
            },
            label = { Text("Transaksi") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Accent,
                unselectedIconColor = TextSecondary,
                indicatorColor = Color.Transparent
            )
        )
    }
}


