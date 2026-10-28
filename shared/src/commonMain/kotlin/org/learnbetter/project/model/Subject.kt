package org.learnbetter.project.model

import androidx.compose.ui.graphics.Color

enum class Subject(
    val id: String,
    val displayName: String,
    val icon: String,
    val primaryColor: Color,
    val subtitle: String
) {
    CHEMISTRY(
        id = "chemistry",
        displayName = "Chemie",
        icon = "🧪",
        primaryColor = Color(0xFF006C4C),
        subtitle = "Salzsäure, Säuren & Reaktionen"
    ),
    PHYSICS(
        id = "physics",
        displayName = "Physik",
        icon = "⚙️",
        primaryColor = Color(0xFFD32F2F),
        subtitle = "Kräfte & Hookesches Gesetz (F = k · Δs)"
    ),
    BIOLOGY(
        id = "biology",
        displayName = "Biologie",
        icon = "🧬",
        primaryColor = Color(0xFF2E7D32),
        subtitle = "Zellbiologie & Virtuelles Mikroskop"
    ),
    MATH(
        id = "math",
        displayName = "Mathematik",
        icon = "📐",
        primaryColor = Color(0xFF6A1B9A),
        subtitle = "Parabeln, Zylinder & Pyramiden"
    ),
    SOCIAL_STUDIES(
        id = "social_studies",
        displayName = "Gesellschaftslehre",
        icon = "🌍",
        primaryColor = Color(0xFFE65100),
        subtitle = "Geschichte, Demokratie & Grundgesetz"
    ),
    MUSIC(
        id = "music",
        displayName = "Musik",
        icon = "🎵",
        primaryColor = Color(0xFFC2185B),
        subtitle = "Notenlesen, Dreiklänge & Rhythmus"
    ),
    GERMAN(
        id = "german",
        displayName = "Deutsch",
        icon = "📖",
        primaryColor = Color(0xFF1565C0),
        subtitle = "Gedichtanalyse & Stilmittel-Baukasten"
    )
}
