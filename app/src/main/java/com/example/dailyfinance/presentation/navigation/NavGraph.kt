package com.example.dailyfinance.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.dailyfinance.presentation.components.BottomNavBar
import com.example.dailyfinance.presentation.dashboard.DashboardScreen
import com.example.dailyfinance.presentation.detail.DetailTransaksiScreen
import com.example.dailyfinance.presentation.transaksi.*
import com.example.dailyfinance.presentation.welcome.WelcomeScreen
import com.example.dailyfinance.ui.theme.BgMain
private val GreenAccent = Color(0xFF22C55E)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph() {

    val navController = rememberNavController()
    val viewModel: TransaksiViewModel = viewModel()


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Welcome.route) {
                BottomNavBar(navController)
            }
        },
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = Screen.Welcome.route,
            modifier = Modifier.padding(padding)
        ) {

            composable(Screen.Welcome.route) {
                WelcomeScreen(navController)
            }

            composable(Screen.Dashboard.route) {
                DashboardScreen(navController, viewModel)
            }

            composable(Screen.Transaksi.route) {
                TransaksiScreen(navController, viewModel)
            }

            composable(Screen.EntryTransaksi.route) {
                EntryTransaksiScreen(navController, viewModel)
            }

            composable(Screen.DetailTransaksi.route) { backStack ->
                val id = backStack.arguments?.getString("id")?.toInt() ?: return@composable
                DetailTransaksiScreen(navController, id, viewModel)
            }

            composable(Screen.EditTransaksi.route) { backStack ->
                val id = backStack.arguments?.getString("id")?.toInt() ?: return@composable
                EditTransaksiScreen(navController, id, viewModel)
            }
        }
    }
}
