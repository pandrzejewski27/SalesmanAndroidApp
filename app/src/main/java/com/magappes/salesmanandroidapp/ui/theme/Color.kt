package com.magappes.salesmanandroidapp.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class SalesmanColors(
    val primary: Color,
    val textPrimary: Color,
    val lightSteelBlueGray: Color,
    val lightGrey: Color,
    val grey200: Color,
    val grey400: Color,
    val white: Color,
)

val DefaultSalesmanColors = SalesmanColors(
    primary = Color(0xFF0B3A83),
    textPrimary = Color(0xFF000000),
    lightSteelBlueGray = Color(0xFFD5DAE3),
    lightGrey = Color(0xFFC6C5C9),
    grey200 = Color(0xFFE5E5E5),
    grey400 = Color(0xFF999999),
    white = Color(0xFFFFFFFF)
)

val LocalSalesmanColors = staticCompositionLocalOf { DefaultSalesmanColors }