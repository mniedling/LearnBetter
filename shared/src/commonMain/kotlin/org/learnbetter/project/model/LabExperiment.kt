package org.learnbetter.project.model

data class LabExperiment(
    val id: String,
    val title: String,
    val icon: String,
    val subtitle: String,
    val description: String,
    val xpReward: Int,
    val isAvailable: Boolean = true
)

object LabExperimentsData {
    val experiments = listOf(
        LabExperiment(
            id = "acid_ions",
            title = "Säuren & Säurerest-Ionen Trainer",
            icon = "⚡",
            subtitle = "Formeln & Säurerest-Namen lernen",
            description = "Lerne die Formeln und Namen aller 7 Säuren und ihrer Säurerest-Ionen (Chlorid, Sulfat, Phosphat, Nitrat, Carbonat, Sulfit, Nitrit).",
            xpReward = 20,
            isAvailable = true
        ),
        LabExperiment(
            id = "acid_metal",
            title = "Säure & Metall-Reaktionen",
            icon = "🧪",
            subtitle = "Reagenzglas & Knallgasprobe",
            description = "Untersuche wie Salzsäure, Schwefelsäure und Essigsäure mit Magnesium, Zink oder Kupfer reagieren.",
            xpReward = 15,
            isAvailable = true
        ),
        LabExperiment(
            id = "ph_indicator",
            title = "pH-Wert & Indikator-Test",
            icon = "🎨",
            subtitle = "Universalindikator & Becherglas",
            description = "Tauche den Teststreifen in Alltagsflüssigkeiten und messe den pH-Wert von 0 bis 14.",
            xpReward = 10,
            isAvailable = true
        ),
        LabExperiment(
            id = "magnesium_burn",
            title = "Magnesiumband entzünden",
            icon = "🔥",
            subtitle = "Gleißende Verbrennung & Redox",
            description = "Entzünde Magnesium über der Bunsenbrennerflamme und beobachte die Bildung von weißem Magnesiumoxid (MgO).",
            xpReward = 15,
            isAvailable = true
        )
    )
}
