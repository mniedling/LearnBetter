package org.learnbetter.project.model

data class TestSolution(
    val id: String,
    val name: String,
    val icon: String,
    val phValue: Float,
    val category: String, // "Stark sauer", "Sauer", "Neutral", "Basisch", "Stark basisch"
    val description: String
)

object TestSolutionsData {
    val sampleSolutions = listOf(
        TestSolution(
            id = "battery",
            name = "Batteriesäure (H₂SO₄)",
            icon = "🔋",
            phValue = 0.5f,
            category = "Extrem sauer",
            description = "Enthält hochkonzentrierte Schwefelsäure. Ätzend!"
        ),
        TestSolution(
            id = "stomach",
            name = "Magensäure (HCl)",
            icon = "🫁",
            phValue = 1.5f,
            category = "Stark sauer",
            description = "Enthält verdünnte Salzsäure zur Bakterienabwehr und Eiweißverdauung."
        ),
        TestSolution(
            id = "lemon",
            name = "Zitronensaft / Essig",
            icon = "🍋",
            phValue = 2.4f,
            category = "Stark sauer",
            description = "Enthält organische Citronen- bzw. Essigsäure."
        ),
        TestSolution(
            id = "oj",
            name = "Orangensaft",
            icon = "🍊",
            phValue = 3.5f,
            category = "Sauer",
            description = "Fruchtsäure mit erfrischend saurem Geschmack."
        ),
        TestSolution(
            id = "tomato",
            name = "Tomatensaft",
            icon = "🍅",
            phValue = 4.1f,
            category = "Mäßig sauer",
            description = "Enthält natürliche Apfel- und Citronensäure."
        ),
        TestSolution(
            id = "coffee",
            name = "Schwarzer Kaffee",
            icon = "☕",
            phValue = 5.0f,
            category = "Schwach sauer",
            description = "Enthält Röstsäuren und Kaffeesäure."
        ),
        TestSolution(
            id = "urine",
            name = "Urin",
            icon = "🧪",
            phValue = 6.0f,
            category = "Schwach sauer",
            description = "Ausscheidungsprodukt des Stoffwechsels mit leicht saurem pH-Wert."
        ),
        TestSolution(
            id = "water",
            name = "Reines Wasser / Speichel",
            icon = "💧",
            phValue = 7.0f,
            category = "Neutral",
            description = "Gleichgewicht von H₃O⁺ und OH⁻ Ionen (pH = 7)."
        ),
        TestSolution(
            id = "seawater",
            name = "Meerwasser",
            icon = "🌊",
            phValue = 8.1f,
            category = "Schwach basisch",
            description = "Leicht alkalisch durch gelöste Salze und Carbonate."
        ),
        TestSolution(
            id = "baking_soda",
            name = "Backpulver (Natron)",
            icon = "🧁",
            phValue = 8.5f,
            category = "Mäßig basisch",
            description = "Natriumhydrogencarbonat neutralisiert Säuren."
        ),
        TestSolution(
            id = "antacid",
            name = "Verdauungstabletten (Antazida)",
            icon = "💊",
            phValue = 9.5f,
            category = "Basisch",
            description = "Neutralisiert überschüssige Magensäure bei Sodbrennen."
        ),
        TestSolution(
            id = "ammonia",
            name = "Ammoniaklösung (Salmiakgeist)",
            icon = "🧪",
            phValue = 11.5f,
            category = "Stark basisch",
            description = "Stechend riechende Lauge, bildet Hydroxid-Ionen (OH⁻)."
        ),
        TestSolution(
            id = "soap",
            name = "Seifenlauge",
            icon = "🧼",
            phValue = 12.0f,
            category = "Stark basisch",
            description = "Alkalische Lösung zum Fettlösen beim Händewaschen."
        ),
        TestSolution(
            id = "bleach",
            name = "Bleichmittel",
            icon = "🧴",
            phValue = 12.5f,
            category = "Extrem basisch",
            description = "Starkes Oxidations- und Reinigungsmittel."
        ),
        TestSolution(
            id = "cleaner",
            name = "Rohrreiniger (NaOH)",
            icon = "🧹",
            phValue = 13.5f,
            category = "Extrem basisch",
            description = "Konzentrierte Natronlauge (NaOH). Stark ätzend für Proteine und Fette!"
        )
    )
}
