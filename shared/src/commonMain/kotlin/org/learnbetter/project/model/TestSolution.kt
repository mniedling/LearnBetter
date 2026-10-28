package org.learnbetter.project.model

data class TestSolution(
    val id: String,
    val name: String,
    val icon: String,
    val phValue: Float,
    val category: String, // "Sauer", "Neutral", "Basisch"
    val description: String
)

object TestSolutionsData {
    val sampleSolutions = listOf(
        TestSolution(
            id = "hcl",
            name = "Verdünnte Salzsäure",
            icon = "🧪",
            phValue = 1.0f,
            category = "Stark sauer",
            description = "Starke Säure mit hoher Oxonium-Ionen (H₃O⁺) Konzentration."
        ),
        TestSolution(
            id = "lemon",
            name = "Zitronensaft",
            icon = "🍋",
            phValue = 2.4f,
            category = "Sauer",
            description = "Enthält natürliche Zitronensäure."
        ),
        TestSolution(
            id = "cola",
            name = "Cola Getränk",
            icon = "🥤",
            phValue = 3.0f,
            category = "Sauer",
            description = "Enthält Phosphorsäure und Kohlensäure."
        ),
        TestSolution(
            id = "water",
            name = "Destilliertes Wasser",
            icon = "💧",
            phValue = 7.0f,
            category = "Neutral",
            description = "Konzentration von H₃O⁺ und OH⁻ Ionen ist genau gleich groß."
        ),
        TestSolution(
            id = "soap",
            name = "Seifenlauge",
            icon = "🧼",
            phValue = 9.5f,
            category = "Basisch / Alkalisch",
            description = "Milde Lauge mit Überschuss an Hydroxid-Ionen (OH⁻)."
        ),
        TestSolution(
            id = "cleaner",
            name = "Rohrreiniger (NaOH)",
            icon = "🧹",
            phValue = 13.5f,
            category = "Stark basisch",
            description = "Starke Natronlauge, ätzend für organische Stoffe."
        )
    )
}
