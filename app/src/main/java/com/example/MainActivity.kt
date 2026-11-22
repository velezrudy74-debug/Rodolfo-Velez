package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.ui.screens.MainScreen
import com.example.ui.theme.RudyVelezTheme
import com.example.ui.viewmodel.PortfolioViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: PortfolioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            RudyVelezTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}
