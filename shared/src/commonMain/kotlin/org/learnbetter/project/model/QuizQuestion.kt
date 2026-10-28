package org.learnbetter.project.model

data class QuizQuestion(
    val id: Int,
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String
)

object QuizData {
    val sampleQuestions = listOf(
        QuizQuestion(
            id = 1,
            questionText = "Welches Gas entsteht, wenn unedle Metalle wie Magnesium mit Salzsäure reagieren?",
            options = listOf("Sauerstoff (O₂)", "Wasserstoff (H₂)", "Kohlendioxid (CO₂)", "Chlor (Cl₂)"),
            correctAnswerIndex = 1,
            explanation = "Bei der Reaktion von unedlen Metallen mit Säuren entsteht Wasserstoffgas (H₂)."
        ),
        QuizQuestion(
            id = 2,
            questionText = "Wie lautet die chemische Summenformel für Salzsäure in wässriger Lösung?",
            options = listOf("H₂SO₄", "HCl (aq)", "HNO₃", "NaOH"),
            correctAnswerIndex = 1,
            explanation = "Salzsäure ist die wässrige Lösung von Chlorwasserstoffgas (HCl)."
        ),
        QuizQuestion(
            id = 3,
            questionText = "Welches Metall reagiert NICHT mit verdünnter Salzsäure?",
            options = listOf("Magnesium", "Zink", "Eisen", "Kupfer"),
            correctAnswerIndex = 3,
            explanation = "Kupfer ist ein edles Metall und reagiert nicht mit verdünnten Säuren wie Salzsäure."
        ),
        QuizQuestion(
            id = 4,
            questionText = "Mit welcher Probe wird Wasserstoffgas im Labor eindeutig nachgewiesen?",
            options = listOf("Glimmspanprobe", "Knallgasprobe", "Kalkwasserprobe", "Flammenfärbung"),
            correctAnswerIndex = 1,
            explanation = "Die Knallgasprobe erzeugt bei Anwesenheit von Wasserstoff ein charakteristisches 'Plopp'-Geräusch."
        ),
        QuizQuestion(
            id = 5,
            questionText = "Welchen pH-Wert hat eine konzentrierte Säure typischerweise?",
            options = listOf("pH 14", "pH 7", "pH 1 - 2", "pH 9"),
            correctAnswerIndex = 2,
            explanation = "Säuren haben einen pH-Wert kleiner als 7. Je strammer/konzentrierter die Säure, desto kleiner der pH-Wert (nahe 0 bis 2)."
        )
    )
}
