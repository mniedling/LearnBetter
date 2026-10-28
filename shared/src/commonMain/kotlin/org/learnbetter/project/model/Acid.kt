package org.learnbetter.project.model

enum class Acid(
    val displayName: String,
    val formula: String,
    val pKaValue: String, // Säurestärke
    val isStrong: Boolean,
    val typicalPh: Float,
    val description: String,
    val everydayExample: String
) {
    HYDROCHLORIC(
        displayName = "Salzsäure",
        formula = "HCl",
        pKaValue = "Stark (pKs = -6)",
        isStrong = true,
        typicalPh = 1.0f,
        description = "Anorganische, sehr starke Säure. Bestandteil der Magenflüssigkeit.",
        everydayExample = "Magensäure, Rosteiferner"
    ),
    SULFURIC(
        displayName = "Schwefelsäure",
        formula = "H₂SO₄",
        pKaValue = "Sehr stark (zweiprotonig)",
        isStrong = true,
        typicalPh = 0.5f,
        description = "Wichtige Industriesäure, stark wasserentziehend und ätzend.",
        everydayExample = "Autobatterie (Akkumulatorsäure)"
    ),
    NITRIC(
        displayName = "Salpetersäure",
        formula = "HNO₃",
        pKaValue = "Stark (pKs = -1.3)",
        isStrong = true,
        typicalPh = 1.2f,
        description = "Stark oxidierende Säure. Färbt Proteine (z.B. Haut) gelb.",
        everydayExample = "Düngemittelherstellung, Scheidewasser"
    ),
    ACETIC(
        displayName = "Essigsäure",
        formula = "CH₃COOH",
        pKaValue = "Schwach (pKs = 4.76)",
        isStrong = false,
        typicalPh = 3.0f,
        description = "Organische, schwache Säure. Reagiert langsamer mit Metallen.",
        everydayExample = "Speiseessig, Entkalker"
    ),
    CARBONIC(
        displayName = "Kohlensäure",
        formula = "H₂CO₃",
        pKaValue = "Sehr schwach",
        isStrong = false,
        typicalPh = 4.5f,
        description = "Instabile Säure, zerfällt leicht in Wasser (H₂O) und Kohlendioxid (CO₂).",
        everydayExample = "Sprudelwasser, Erfrischungsgetränke"
    )
}
