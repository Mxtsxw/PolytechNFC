package com.polytech.polytechnfc.screens.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String,
    onMenuClick: () -> Unit,
    backgroundColor: Color = Color(0xFF1570EF),
    titleColor: Color = Color.White
) {
    TopAppBar(
        title = { Text(title, color = titleColor) },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu Icon",
                    tint = Color.White
                )
            }
        },
        actions = {
            // Add the logout icon button
            IconButton(onClick = {
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,  // Logout icon
                    contentDescription = "Logout Icon",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = backgroundColor, // Set the background color of the AppBar
            titleContentColor = titleColor
        )
    )
}