package org.learnbetter.project.model

data class QuizQuestion(
    val id: Int,
    val subjectId: String,
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String
)

object QuizData {
    val questionsBySubject = mapOf(
        "chemistry" to listOf(
            QuizQuestion(
                id = 1,
                subjectId = "chemistry",
                questionText = "Welches Gas entsteht, wenn unedle Metalle mit Säuren reagieren?",
                options = listOf("Sauerstoff (O₂)", "Wasserstoff (H₂)", "Kohlendioxid (CO₂)", "Chlor (Cl₂)"),
                correctAnswerIndex = 1,
                explanation = "Bei der Reaktion von unedlen Metallen mit Säuren entsteht Wasserstoffgas (H₂)."
            ),
            QuizQuestion(
                id = 2,
                subjectId = "chemistry",
                questionText = "Wie lautet die chemische Summenformel für Salzsäure in wässriger Lösung?",
                options = listOf("H₂SO₄", "HCl (aq)", "HNO₃", "NaOH"),
                correctAnswerIndex = 1,
                explanation = "Salzsäure ist die wässrige Lösung von Chlorwasserstoffgas (HCl)."
            ),
            QuizQuestion(
                id = 3,
                subjectId = "chemistry",
                questionText = "Mit welcher Probe wird Wasserstoffgas im Labor nachgewiesen?",
                options = listOf("Glimmspanprobe", "Knallgasprobe", "Kalkwasserprobe", "Flammenfärbung"),
                correctAnswerIndex = 1,
                explanation = "Die Knallgasprobe erzeugt bei Anwesenheit von Wasserstoff ein charakteristisches 'Plopp'."
            )
        ),
        "physics" to listOf(
            QuizQuestion(
                id = 60,
                subjectId = "physics",
                questionText = "Wie lautet die Formel des Hookeschen Gesetzes für die Federkraft?",
                options = listOf("F = m · g", "F = D · Δs", "F = p · A", "F = m · v²"),
                correctAnswerIndex = 1,
                explanation = "Das Hookesche Gesetz beschreibt die proportionale Beziehung F = D · Δs zwischen Kraft und Verlängerung."
            ),
            QuizQuestion(
                id = 61,
                subjectId = "physics",
                questionText = "Was bezeichnet das 'D' in der Formel F = D · Δs?",
                options = listOf("Dichte", "Druck", "Federkonstante (Federhärte)", "Durchmesser"),
                correctAnswerIndex = 2,
                explanation = "D (oder k) ist die Federkonstante in N/m und gibt die Steifigkeit der Feder an."
            ),
            QuizQuestion(
                id = 62,
                subjectId = "physics",
                questionText = "Welche Verlängerung Δs erfährt eine Feder mit D = 50 N/m bei einer Kraft F = 5 N?",
                options = listOf("1 cm", "10 cm", "50 cm", "0,1 cm"),
                correctAnswerIndex = 1,
                explanation = "Δs = F / D = 5 N / (50 N/m) = 0,1 m = 10 cm."
            )
        ),
        "biology" to listOf(
            QuizQuestion(
                id = 10,
                subjectId = "biology",
                questionText = "In welchem Zellorganell der Pflanzenzelle findet die Fotosynthese statt?",
                options = listOf("Mitochondrium", "Zellkern", "Chloroplast", "Vakuole"),
                correctAnswerIndex = 2,
                explanation = "Chloroplasten enthalten das grüne Chlorophyll und wandeln Sonnenlicht in Traubenzucker um."
            ),
            QuizQuestion(
                id = 11,
                subjectId = "biology",
                questionText = "Welches Gas nehmen Pflanzen bei der Fotosynthese aus der Luft auf?",
                options = listOf("Sauerstoff (O₂)", "Kohlendioxid (CO₂)", "Stickstoff (N₂)", "Wasserstoff (H₂)"),
                correctAnswerIndex = 1,
                explanation = "Pflanzen benötigen CO₂ und Wasser, um mithilfe von Licht O₂ und Glucose zu produzieren."
            )
        ),
        "math" to listOf(
            QuizQuestion(
                id = 20,
                subjectId = "math",
                questionText = "Wie verändert sich die Parabel f(x) = a·x², wenn der Faktor 'a' größer als 1 ist?",
                options = listOf("Gestaucht (breiter)", "Gestreckt (schmaler)", "Nach unten geöffnet", "Nach rechts verschoben"),
                correctAnswerIndex = 1,
                explanation = "Für |a| > 1 ist die Parabel steiler bzw. gestreckt."
            ),
            QuizQuestion(
                id = 25,
                subjectId = "math",
                questionText = "Wie lautet die Formel für die Grundfläche G eines Zylinders mit Radius r?",
                options = listOf("G = 2 · π · r", "G = π · r²", "G = a²", "G = 1/3 · π · r²"),
                correctAnswerIndex = 1,
                explanation = "Die Grundfläche eines Zylinders ist ein Kreis mit G = π · r²."
            ),
            QuizQuestion(
                id = 26,
                subjectId = "math",
                questionText = "Aus wie vielen Flächen besteht der Mantel M einer quadratischen Pyramide?",
                options = listOf("2 Dreiecke", "3 Quadraten", "4 gleichschenkligen Dreiecken", "6 Rechtecken"),
                correctAnswerIndex = 2,
                explanation = "Der Mantel einer quadratischen Pyramide besteht aus 4 deckungsgleichen Dreiecken."
            )
        ),
        "social_studies" to listOf(
            QuizQuestion(
                id = 30,
                subjectId = "social_studies",
                questionText = "In welchem Artikel des Grundgesetzes steht 'Die Würde des Menschen ist unantastbar'?",
                options = listOf("Artikel 1", "Artikel 20", "Artikel 5", "Artikel 3"),
                correctAnswerIndex = 0,
                explanation = "Artikel 1 Abs. 1 GG bildet das Fundament der Rechtsordnung der Bundesrepublik Deutschland."
            )
        ),
        "music" to listOf(
            QuizQuestion(
                id = 40,
                subjectId = "music",
                questionText = "Aus welchen Intervallen baut sich ein Dur-Dreiklang vom Grundton aus auf?",
                options = listOf("Kleine Terz + Große Terz", "Große Terz + Kleine Terz", "Zwei Große Terzen", "Zwei Quarten"),
                correctAnswerIndex = 1,
                explanation = "Ein Dur-Dreiklang besteht aus einer großen Terz unten und einer kleinen Terz darüber."
            )
        ),
        "german" to listOf(
            QuizQuestion(
                id = 50,
                subjectId = "german",
                questionText = "Welches Stilmittel liegt vor bei: 'Milch macht müde Männer munter'?",
                options = listOf("Metapher", "Alliteration", "Anapher", "Hyperbel"),
                correctAnswerIndex = 1,
                explanation = "Eine Alliteration ist die Wiederholung des gleichen Anfangslauts bei benachbarten Wörtern."
            )
        )
    )

    fun getQuestionsForSubject(subjectId: String): List<QuizQuestion> {
        return questionsBySubject[subjectId] ?: questionsBySubject["chemistry"]!!
    }
}
