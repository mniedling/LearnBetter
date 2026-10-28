package org.learnbetter.project.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Liefert die passende Indikatorfarbe für einen spezifischen pH-Wert (0.0 bis 14.0).
 */
fun getPhColor(phValue: Float): Color {
    return when {
        phValue < 3.0f -> ChemistryColors.AcidRed       // Stark sauer (pH 0-3)
        phValue < 6.5f -> ChemistryColors.AcidOrange    // Schwach sauer (pH 3-6.5)
        phValue <= 7.5f -> ChemistryColors.NeutralGreen // Neutral (pH 6.5-7.5)
        phValue <= 11.0f -> ChemistryColors.BaseBlue    // Schwach basisch (pH 7.5-11)
        else -> ChemistryColors.BaseViolet             // Stark basisch (pH 11-14)
    }
}

/**
 * Beschreibung des Säure-Base-Zustands.
 */
fun getPhLabel(phValue: Float): String {
    return when {
        phValue < 3.0f -> "Stark sauer (z.B. Salzsäure)"
        phValue < 6.5f -> "Schwach sauer (z.B. Cola, Essig)"
        phValue <= 7.5f -> "Neutral (z.B. Reines Wasser)"
        phValue <= 11.0f -> "Schwach basisch (z.B. Seife)"
        else -> "Stark basisch (z.B. Rohrreiniger, NaOH)"
    }
}
