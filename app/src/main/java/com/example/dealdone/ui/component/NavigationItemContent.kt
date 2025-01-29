package com.example.dealdone.ui.component

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.dealdone.ui.DealDoneScreen

data class NavigationItemContent(
    val screen: DealDoneScreen,
    val icon: ImageVector,
    val text: String
)
