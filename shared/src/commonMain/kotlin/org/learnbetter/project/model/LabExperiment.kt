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
            id = "chemical_bond",
            title = "Atombinder - Das Bindungsspiel",
            icon = "⚛️",
            subtitle = "Ionen- & Elektronenpaarbindung",
            description = "Lustige Atome leihen sich Elektronen aus oder nutzen sie gemeinsam (Kochsalz NaCl & Wasser H₂O).",
            xpReward = 15,
            isAvailable = true
        ),
        LabExperiment(
            id = "sulfuric_acid",
            title = "Schwefelsäure & Kontaktverfahren",
            icon = "🧪",
            subtitle = "Herstellung & Zucker-Verkohlung",
            description = "Erforsche das Kontaktverfahren zur Herstellung und den extremen Wasserentzug aus Puderzucker.",
            xpReward = 20,
            isAvailable = true
        ),
        LabExperiment(
            id = "marble_acid",
            title = "Säure vs. Marmorfliesen",
            icon = "🏛️",
            subtitle = "Zersetzung von Calciumcarbonat (CaCO₃)",
            description = "Testet Zitronensäure, Essig & Salzsäure auf Marmorfliesen und beobachtet das heftige Schäumen von CO₂-Gas.",
            xpReward = 20,
            isAvailable = true
        ),
        LabExperiment(
            id = "acid_metal",
            title = "Säure & Metall-Reaktionen",
            icon = "💥",
            subtitle = "Reagenzglas & Knallgasprobe",
            description = "Untersuche wie Salzsäure, Schwefelsäure und Essigsäure mit Magnesium, Zink oder Kupfer reagieren.",
            xpReward = 15,
            isAvailable = true
        ),
        LabExperiment(
            id = "ph_indicator",
            title = "pH-Wert & Indikator-Test",
            icon = "🎨",
            subtitle = "Universalindikator & 15 Lösungen",
            description = "Tauche den Teststreifen in 15 Alltagsflüssigkeiten und messe den pH-Wert von 0 bis 14.",
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
