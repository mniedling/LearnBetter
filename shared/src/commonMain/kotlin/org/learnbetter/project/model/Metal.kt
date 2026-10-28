package org.learnbetter.project.model

enum class Metal(
    val displayName: String,
    val symbol: String,
    val isBaseMetal: Boolean, // Unedles vs. Edles Metall
    val reactivitySpeed: Float, // 0.0 bis 1.0 (Reaktionsgeschwindigkeit)
    val reactionFormula: String,
    val description: String
) {
    MAGNESIUM(
        displayName = "Magnesium",
        symbol = "Mg",
        isBaseMetal = true,
        reactivitySpeed = 0.9f,
        reactionFormula = "Mg + 2 HCl -> MgCl2 + H2 ↑",
        description = "Sehr heftige Reaktion! Starke Bläschenbildung und deutliche Erwärmung."
    ),
    ZINC(
        displayName = "Zink",
        symbol = "Zn",
        isBaseMetal = true,
        reactivitySpeed = 0.5f,
        reactionFormula = "Zn + 2 HCl -> ZnCl2 + H2 ↑",
        description = "Mittlere Reaktion. Stetige Entwicklung von Wasserstoffgas."
    ),
    IRON(
        displayName = "Eisen",
        symbol = "Fe",
        isBaseMetal = true,
        reactivitySpeed = 0.25f,
        reactionFormula = "Fe + 2 HCl -> FeCl2 + H2 ↑",
        description = "Langsame Reaktion. Bläschen bilden sich nur langsam."
    ),
    COPPER(
        displayName = "Kupfer",
        symbol = "Cu",
        isBaseMetal = false,
        reactivitySpeed = 0.0f,
        reactionFormula = "Cu + HCl -> Keine Reaktion",
        description = "Kupfer ist ein edles Metall und reagiert nicht mit verdünnter Salzsäure!"
    )
}
