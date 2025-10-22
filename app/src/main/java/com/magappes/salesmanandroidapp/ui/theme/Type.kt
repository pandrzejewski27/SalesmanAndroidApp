package com.magappes.salesmanandroidapp.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.magappes.salesmanandroidapp.R

data class RobotoSize(
    val small: TextStyle,
    val medium: TextStyle,
)

data class RobotoWeight(
    val regular: RobotoSize,
    val medium: RobotoSize,
    val bold: RobotoSize
)

data class SalesmanTypography(
    val roboto: RobotoWeight
)

val Roboto = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_bold, FontWeight.Bold)
)

val LocalSalesmanTypography = staticCompositionLocalOf {
    SalesmanTypography(
        roboto = RobotoWeight(
            regular = RobotoSize(
                small = TextStyle(
                    fontFamily = Roboto,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                ),
                medium = TextStyle(
                    fontFamily = Roboto,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                ),
            ),
            medium = RobotoSize(
                small = TextStyle(
                    fontFamily = Roboto,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                ),
                medium = TextStyle(
                    fontFamily = Roboto,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                ),
            ),
            bold = RobotoSize(
                small = TextStyle(
                    fontFamily = Roboto,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                ),
                medium = TextStyle(
                    fontFamily = Roboto,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                ),
            )
        )
    )
}