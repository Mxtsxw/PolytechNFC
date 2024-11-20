package com.polytech.polytechnfc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.polytech.polytechnfc.screens.NavGraph
import com.polytech.polytechnfc.screens.NavGraphs
import com.polytech.polytechnfc.ui.theme.PolytechNFCTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PolytechNFCTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                val snackbarScope = rememberCoroutineScope()

                Scaffold(modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                        ) { innerPadding ->
                    DestinationsNavHost(
                        dependenciesContainerBuilder = {
                            dependency(snackbarHostState)
                            dependency(snackbarScope)
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