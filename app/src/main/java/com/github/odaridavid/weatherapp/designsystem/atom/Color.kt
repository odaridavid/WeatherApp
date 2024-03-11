package com.github.odaridavid.weatherapp.designsystem.atom

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// TODO Expand color range
val pink50 = Color(0xffffE9F7)
val pink200 = Color(0xffff7597)
val pinkA200 = Color(0xffff3886)
val pink500 = Color(0xffff0266)
val pink600 = Color(0xffd8004d)
val black = Color(0xff24191c)
val darkBlue = Color(0xff1976D2)
val blue500 = Color(0xff448aff)
val blue200 = Color(0xff92Aeff)
val lightBlue = Color(0xffbbdefb)
val grey = Color(0xff757575)
val lightGrey = Color(0xffBDBDBD)
val white = Color(0xffffffff)

val LightColorPalette = lightColorScheme(
    primary = pink500,
    secondary = blue500,
    onPrimary = pink50,
    onSecondary = black,
    primaryContainer = pink200,
    onPrimaryContainer = black,
    secondaryContainer = blue200,
    onSecondaryContainer = black,
    surface = white
)

val DarkColorPalette = darkColorScheme(
    primary = blue500,
    secondary = pinkA200,
    surface = black,
    onPrimary = black,
    onSecondary = white,
    primaryContainer = blue200,
    onPrimaryContainer = black,
    secondaryContainer = pink200,
    onSecondaryContainer = black
)
