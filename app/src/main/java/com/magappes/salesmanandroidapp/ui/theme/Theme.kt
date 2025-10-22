package com.magappes.salesmanandroidapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider


@Composable
fun SalesmanTheme(
    colors: SalesmanColors = DefaultSalesmanColors,
    content: @Composable() () -> Unit
) {
    CompositionLocalProvider(
        LocalSalesmanColors provides colors,
        LocalSalesmanTypography provides LocalSalesmanTypography.current
    ) {
        MaterialTheme(
            colorScheme = lightColorScheme(
                primary = colors.primary,
                background = colors.white
            ),
            content = content
        )
    }
}

object SalesmanTheme {
    val colors: SalesmanColors
        @Composable get() = LocalSalesmanColors.current

    val typography: SalesmanTypography
        @Composable get() = LocalSalesmanTypography.current
}