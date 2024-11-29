package com.polytech.polytechnfc

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.polytech.polytechnfc.screens.NavGraph
import com.polytech.polytechnfc.screens.NavGraphs
import com.polytech.polytechnfc.screens.components.CustomTopAppBar
import com.polytech.polytechnfc.ui.theme.PolytechNFCTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PolytechNFCTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                val snackbarScope = rememberCoroutineScope()

                // Handle top bar visibility
                val showTopBar = remember { mutableStateOf(true) }

                Scaffold(
                    topBar = {
                        if (showTopBar.value) {
                            CustomTopAppBar(
                                title = "Polytech NFC",
                                onMenuClick = {}
                            )
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                        ) { innerPadding ->
                    DestinationsNavHost(
                        dependenciesContainerBuilder = {
                            dependency(snackbarHostState)
                            dependency(snackbarScope)
                            dependency(showTopBar)
                        },
                        navGraph = NavGraphs.root,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    PolytechNFCTheme {
//        Greeting("Android")
//    }
//}