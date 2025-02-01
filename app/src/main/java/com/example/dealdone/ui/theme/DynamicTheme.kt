package com.example.dealdone.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import material.util.color.scheme.Scheme

@Composable
fun DealDoneDynamicTheme(
    state: DynamicThemeState,
    content: @Composable () -> Unit,
) {
    val scheme = rememberColorScheme(state.primaryColor)

    MaterialTheme(
        colorScheme = scheme,
        content = content
    )
}

@Composable
fun rememberDynamicThemeState(
    initialPrimaryColor: Color = MaterialTheme.colorScheme.primary,
): DynamicThemeState {
    return remember { DynamicThemeState(initialPrimaryColor) }
}

@Composable
fun rememberDynamicThemeState(
    initialPrimaryColor: Int,
): DynamicThemeState {
    return rememberDynamicThemeState(Color(initialPrimaryColor))
}

@Stable
class DynamicThemeState(
    initialPrimaryColor: Color,
) {
    var primaryColor: Color by mutableStateOf(initialPrimaryColor)

    var primaryColorArgb: Int
        set(value) {
            primaryColor = Color(value)
        }
        get() = primaryColor.toArgb()

}

@Composable
private fun rememberColorScheme(color: Color): ColorScheme {
    val isDarkTheme = isSystemInDarkTheme()
    val colorArgb = color.toArgb()
    return remember(color) {
        if (isDarkTheme) {
            Scheme.darkContent(colorArgb).toDarkThemeColorScheme()
        } else {
            Scheme.lightContent(colorArgb).toLightThemeColorScheme()
        }
    }
}

private fun Scheme.toDarkThemeColorScheme(): ColorScheme {
    return darkColorScheme(
        primary = Color(primary),
        onPrimary = Color(onPrimary),
        primaryContainer = Color(primaryContainer),
        onPrimaryContainer = Color(onPrimaryContainer),
        inversePrimary = Color(inversePrimary),
        secondary = Color(secondary),
        onSecondary = Color(onSecondary),
        secondaryContainer = Color(secondaryContainer),
        onSecondaryContainer = Color(onSecondaryContainer),
        tertiary = Color(tertiary),
        onTertiary = Color(onTertiary),
        tertiaryContainer = Color(tertiaryContainer),
        onTertiaryContainer = Color(onTertiaryContainer),
        background = Color(background),
        onBackground = Color(onBackground),
        surface = Color(surface),
        onSurface = Color(onSurface),
        surfaceVariant = Color(surfaceVariant),
        onSurfaceVariant = Color(onSurfaceVariant),
        surfaceTint = Color(surfaceVariant),
        inverseSurface = Color(inverseSurface),
        inverseOnSurface = Color(inverseOnSurface),
        error = Color(error),
        onError = Color(onError),
        errorContainer = Color(errorContainer),
        onErrorContainer = Color(onErrorContainer),
        outline = Color(outline),
        outlineVariant = Color(outlineVariant),
        scrim = Color(scrim),
    )
}

private fun Scheme.toLightThemeColorScheme(): ColorScheme {
    return lightColorScheme(
        primary = Color(primary),
        onPrimary = Color(onPrimary),
        primaryContainer = Color(primaryContainer),
        onPrimaryContainer = Color(onPrimaryContainer),
        inversePrimary = Color(inversePrimary),
        secondary = Color(secondary),
        onSecondary = Color(onSecondary),
        secondaryContainer = Color(secondaryContainer),
        onSecondaryContainer = Color(onSecondaryContainer),
        tertiary = Color(tertiary),
        onTertiary = Color(onTertiary),
        tertiaryContainer = Color(tertiaryContainer),
        onTertiaryContainer = Color(onTertiaryContainer),
        background = Color(background),
        onBackground = Color(onBackground),
        surface = Color(surface),
        onSurface = Color(onSurface),
        surfaceVariant = Color(surfaceVariant),
        onSurfaceVariant = Color(onSurfaceVariant),
        surfaceTint = Color(surfaceVariant),
        inverseSurface = Color(inverseSurface),
        inverseOnSurface = Color(inverseOnSurface),
        error = Color(error),
        onError = Color(onError),
        errorContainer = Color(errorContainer),
        onErrorContainer = Color(onErrorContainer),
        outline = Color(outline),
        outlineVariant = Color(outlineVariant),
        scrim = Color(scrim),
    )
}