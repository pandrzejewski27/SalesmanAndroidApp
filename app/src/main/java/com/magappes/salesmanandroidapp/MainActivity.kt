package com.magappes.salesmanandroidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.magappes.salesmanandroidapp.ui.salesmen.SalesmenScreen
import com.magappes.salesmanandroidapp.ui.theme.SalesmanTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            WindowCompat.setDecorFitsSystemWindows(window, false)
            SalesmanTheme {
                SalesmenScreen()
            }
        }
    }
}