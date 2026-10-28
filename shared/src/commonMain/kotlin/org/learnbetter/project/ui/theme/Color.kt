package org.learnbetter.project.ui.theme

import androidx.compose.ui.graphics.Color

// Light Theme Colors
val PrimaryLight = Color(0xFF006C4C)
val OnPrimaryLight = Color(0xFFFFFFFF)
val PrimaryContainerLight = Color(0xFF89F8CA)
val OnPrimaryContainerLight = Color(0xFF002114)

val SecondaryLight = Color(0xFF4C5D8B)
val OnSecondaryLight = Color(0xFFFFFFFF)
val SecondaryContainerLight = Color(0xFFD9E2FF)
val OnSecondaryContainerLight = Color(0xFF041943)

val TertiaryLight = Color(0xFF7E5200)
val OnTertiaryLight = Color(0xFFFFFFFF)
val TertiaryContainerLight = Color(0xFFFFDEAC)
val OnTertiaryContainerLight = Color(0xFF281800)

val BackgroundLight = Color(0xFFF5FBF5)
val OnBackgroundLight = Color(0xFF171D1A)
val SurfaceLight = Color(0xFFF5FBF5)
val OnSurfaceLight = Color(0xFF171D1A)
val SurfaceVariantLight = Color(0xFFDBE5DE)
val OnSurfaceVariantLight = Color(0xFF404944)

// Dark Theme Colors
val PrimaryDark = Color(0xFF6CDBAF)
val OnPrimaryDark = Color(0xFF003825)
val PrimaryContainerDark = Color(0xFF005238)
val OnPrimaryContainerDark = Color(0xFF89F8CA)

val SecondaryDark = Color(0xFFB3C5FF)
val OnSecondaryDark = Color(0xFF1C2E59)
val SecondaryContainerDark = Color(0xFF344572)
val OnSecondaryContainerDark = Color(0xFFD9E2FF)

val TertiaryDark = Color(0xFFFFBA53)
val OnTertiaryDark = Color(0xFF432B00)
val TertiaryContainerDark = Color(0xFF603E00)
val OnTertiaryContainerDark = Color(0xFFFFDEAC)

val BackgroundDark = Color(0xFF0F1512)
val OnBackgroundDark = Color(0xFFDEE4E0)
val SurfaceDark = Color(0xFF0F1512)
val OnSurfaceDark = Color(0xFFDEE4E0)
val SurfaceVariantDark = Color(0xFF404944)
val OnSurfaceVariantDark = Color(0xFFC0C9C2)

// Spezialfarben für Chemie & pH-Skala
object ChemistryColors {
    val AcidRed = Color(0xFFE53935)       // pH 0 - 3 (Stark sauer, z. B. Salzsäure)
    val AcidOrange = Color(0xFFFF9800)    // pH 4 - 6 (Schwach sauer, z. B. Essig, Cola)
    val NeutralGreen = Color(0xFF4CAF50)  // pH 7 (Neutral, z. B. Wasser)
    val BaseBlue = Color(0xFF2196F3)      // pH 8 - 11 (Schwach basisch, z. B. Seife)
    val BaseViolet = Color(0xFF9C27B0)    // pH 12 - 14 (Stark basisch, z. B. Rohrreiniger)

    val HydrogenCyan = Color(0xFF00E5FF)  // Wasserstoffgas Bläschen ($H_2$)
    val ReactionHeat = Color(0xFFFF6D00)  // Exotherme Wärmeentwicklung
    val StreakFire = Color(0xFFFF3D00)    // Tages-Streak 🔥
    val AchievementGold = Color(0xFFFFD700) // XP / Badges ⭐
}
