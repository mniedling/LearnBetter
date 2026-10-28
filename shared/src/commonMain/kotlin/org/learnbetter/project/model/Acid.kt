package org.learnbetter.project.model

enum class Acid(
    val displayName: String,
    val formula: String,
    val ionFormula: String,
    val ionName: String,
    val pKaValue: String,
    val isStrong: Boolean,
    val typicalPh: Float,
    val description: String,
    val everydayExample: String
) {
    HYDROCHLORIC(
        displayName = "Salzsäure",
        formula = "HCl",
        ionFormula = "Cl⁻",
        ionName = "Chlorid-Ion",
        pKaValue = "Stark (pKs = -6)",
        isStrong = true,
        typicalPh = 1.0f,
        description = "Magenflüssigkeit, Rosteiferner.",
        everydayExample = "Magensäure"
    ),
    SULFURIC(
        displayName = "Schwefelsäure",
        formula = "H₂SO₄",
        ionFormula = "SO₄²⁻",
        ionName = "Sulfat-Ion",
        pKaValue = "Sehr stark (zweiprotonig)",
        isStrong = true,
        typicalPh = 0.5f,
        description = "Wichtige Industriesäure, stark ätzend.",
        everydayExample = "Autobatterie (Akkumulatorsäure)"
    ),
    PHOSPHORIC(
        displayName = "Phosphorsäure",
        formula = "H₃PO₄",
        ionFormula = "PO₄³⁻",
        ionName = "Phosphat-Ion",
        pKaValue = "Mittelstark (dreiprotonig)",
        isStrong = false,
        typicalPh = 2.0f,
        description = "Säuerungsmittel in Cola und Düngemitteln.",
        everydayExample = "Cola Getränke, Entroster"
    ),
    NITRIC(
        displayName = "Salpetersäure",
        formula = "HNO₃",
        ionFormula = "NO₃⁻",
        ionName = "Nitrat-Ion",
        pKaValue = "Stark (pKs = -1.3)",
        isStrong = true,
        typicalPh = 1.2f,
        description = "Stark oxidierende Säure. Färbt Proteine gelb.",
        everydayExample = "Düngemittelherstellung"
    ),
    CARBONIC(
        displayName = "Kohlensäure",
        formula = "H₂CO₃",
        ionFormula = "CO₃²⁻",
        ionName = "Carbonat-Ion",
        pKaValue = "Sehr schwach",
        isStrong = false,
        typicalPh = 4.5f,
        description = "Instabile Säure in Mineralwasser.",
        everydayExample = "Sprudelwasser"
    ),
    SULFUROUS(
        displayName = "Schweflige Säure",
        formula = "H₂SO₃",
        ionFormula = "SO₃²⁻",
        ionName = "Sulfit-Ion",
        pKaValue = "Mittelstark",
        isStrong = false,
        typicalPh = 2.5f,
        description = "Entsteht beim Lösen von Schwefeldioxid in Wasser.",
        everydayExample = "Konservierungsmittel in Wein"
    ),
    NITROUS(
        displayName = "Salpetrige Säure",
        formula = "HNO₂",
        ionFormula = "NO₂⁻",
        ionName = "Nitrit-Ion",
        pKaValue = "Schwach",
        isStrong = false,
        typicalPh = 3.2f,
        description = "Instabile schwache Säure.",
        everydayExample = "Nitpoking / Pökelsalz"
    )
}
