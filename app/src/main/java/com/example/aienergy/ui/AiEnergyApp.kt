package com.example.aienergy.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.example.aienergy.ui.theme.AIEnergyTheme
import com.google.accompanist.insets.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun AiEnergyApp() {
    AIEnergyTheme {
        ProvideWindowInsets {
            //设置状态栏、导航条透明
            val systemUiController = rememberSystemUiController()
            val darkIcons = !isSystemInDarkTheme()
            SideEffect() {
                systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = darkIcons)
            }
            BottomMainView()
        }

    }
}


