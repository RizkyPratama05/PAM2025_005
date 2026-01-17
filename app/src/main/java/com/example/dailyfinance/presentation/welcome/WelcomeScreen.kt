package com.example.dailyfinance.presentation.welcome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dailyfinance.presentation.navigation.Screen
import kotlinx.coroutines.delay
import com.example.dailyfinance.R


@Composable
fun WelcomeScreen(navController: NavController) {

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(200)
        visible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1E3A8A),
                        Color(0xFF0F172A)
                    )
                )
            )
            .padding(horizontal = 24.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(120.dp))

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(600)) + scaleIn(
                    initialScale = 0.85f,
                    animationSpec = tween(600)
                )
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Image(
                        painter = painterResource(id = R.drawable.dailyfinance),
                        contentDescription = "DailyFinance Logo",
                        modifier = Modifier.size(260.dp)
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Kelola keuanganmu dengan mudah",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 17.sp,
                        fontFamily = FontFamily.Serif
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 15.dp, end = 25.dp, start = 25.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF0F172A)
                )
            ) {
                Text(
                    text = "Mulai",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}



