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
            ),
            QuizQuestion(
                id = 4,
                subjectId = "chemistry",
                questionText = "Welches Metall reagiert NICHT mit verdünnter Salzsäure?",
                options = listOf("Magnesium", "Zink", "Eisen", "Kupfer"),
                correctAnswerIndex = 3,
                explanation = "Kupfer ist ein edles Metall und reagiert nicht mit verdünnter Salzsäure."
            ),
            QuizQuestion(
                id = 5,
                subjectId = "chemistry",
                questionText = "Welcher pH-Wert entspricht einer neutralen Lösung wie reinem Wasser?",
                options = listOf("pH 0", "pH 7", "pH 14", "pH 1"),
                correctAnswerIndex = 1,
                explanation = "pH 7 ist genau neutral. Werte kleiner als 7 sind sauer, Werte größer als 7 sind basisch."
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
            ),
            QuizQuestion(
                id = 12,
                subjectId = "biology",
                questionText = "Welches Organell wird als 'Kraftwerk der Zelle' bezeichnet?",
                options = listOf("Vakuole", "Mitochondrium", "Ribosom", "Zellwand"),
                correctAnswerIndex = 1,
                explanation = "Mitochondrien erzeugen durch Zellatmung die Energie (ATP) für alle Zellprozesse."
            ),
            QuizQuestion(
                id = 13,
                subjectId = "biology",
                questionText = "Wo befindet sich die Erbinformation (DNA) der Pflanze?",
                options = listOf("Im Zellkern", "In der Zellwand", "In der Vakuole", "Im Chloroplast"),
                correctAnswerIndex = 0,
                explanation = "Der Zellkern ist die Steuerzentrale der Zelle und speichert die DNA."
            ),
            QuizQuestion(
                id = 14,
                subjectId = "biology",
                questionText = "Welches Produkt entsteht bei der Fotosynthese neben Sauerstoff?",
                options = listOf("Glucose (Traubenzucker)", "Salzsäure", "Kohlendioxid", "Stärke allein"),
                correctAnswerIndex = 0,
                explanation = "Pflanzen stellen bei der Fotosynthese Glucose (C₆H₁₂O₆) her."
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
                id = 21,
                subjectId = "math",
                questionText = "Wo liegt der Scheitelpunkt der Parabel f(x) = x² + 3?",
                options = listOf("S(3|0)", "S(0|3)", "S(-3|0)", "S(0|-3)"),
                correctAnswerIndex = 1,
                explanation = "Das c = +3 verschiebt die Normalparabel um 3 Einheiten nach oben auf der y-Achse."
            ),
            QuizQuestion(
                id = 22,
                subjectId = "math",
                questionText = "In welche Richtung ist die Parabel f(x) = -2x² geöffnet?",
                options = listOf("Nach oben", "Nach unten", "Nach links", "Nach rechts"),
                correctAnswerIndex = 1,
                explanation = "Bei einem negativen Vorzeichen (a < 0) ist die Parabel nach unten geöffnet."
            ),
            QuizQuestion(
                id = 23,
                subjectId = "math",
                questionText = "Wie heißt die Form einer Parabel mit f(x) = x²?",
                options = listOf("Scheitelparabel", "Normalparabel", "Kubische Parabel", "Potenzfunktion"),
                correctAnswerIndex = 1,
                explanation = "f(x) = x² ist die klassische Normalparabel."
            ),
            QuizQuestion(
                id = 24,
                subjectId = "math",
                questionText = "Wie viele Nullstellen kann eine nach oben geöffnete Parabel maximal haben?",
                options = listOf("Keine", "Genau 1", "Maximal 2", "Unendlich viele"),
                correctAnswerIndex = 2,
                explanation = "Eine quadratische Funktion hat höchstens 2 reale Nullstellen."
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
            ),
            QuizQuestion(
                id = 31,
                subjectId = "social_studies",
                questionText = "Wann fiel die Berliner Mauer im Zuge der Friedlichen Revolution?",
                options = listOf("17. Juni 1953", "13. August 1961", "9. November 1989", "3. Oktober 1990"),
                correctAnswerIndex = 2,
                explanation = "Am 9. November 1989 öffnete sich die Berliner Mauer."
            ),
            QuizQuestion(
                id = 32,
                subjectId = "social_studies",
                questionText = "An welchem Tag feiern wir die Deutsche Wiedervereinigung?",
                options = listOf("3. Oktober", "1. Mai", "9. November", "23. Mai"),
                correctAnswerIndex = 0,
                explanation = "Der 3. Oktober ist als Tag der Deutschen Einheit der Nationalfeiertag."
            ),
            QuizQuestion(
                id = 33,
                subjectId = "social_studies",
                questionText = "Welche Staatsform ist in Artikel 20 des Grundgesetzes festgelegt?",
                options = listOf("Monarchie", "Demokratischer und sozialer Bundesstaat", "Diktatur", "Staatenbund"),
                correctAnswerIndex = 1,
                explanation = "Artikel 20 GG bestimmt Deutschland als demokratischen, sozialen Bundesstaat."
            ),
            QuizQuestion(
                id = 34,
                subjectId = "social_studies",
                questionText = "Wann wurde die Bundesrepublik Deutschland gegründet?",
                options = listOf("1919", "1933", "1949", "1989"),
                correctAnswerIndex = 2,
                explanation = "Die BRD wurde am 23. Mai 1949 mit Verkündung des Grundgesetzes gegründet."
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
            ),
            QuizQuestion(
                id = 41,
                subjectId = "music",
                questionText = "Aus welchen Tönen besteht der C-Dur Dreiklang?",
                options = listOf("C - E - G", "C - F - A", "C - D - E", "A - C - E"),
                correctAnswerIndex = 0,
                explanation = "Der C-Dur Dreiklang wird aus Grundton C, Terz E und Quinte G gebildet."
            ),
            QuizQuestion(
                id = 42,
                subjectId = "music",
                questionText = "Wie viele Stammton-Noten gibt es in einer Oktave?",
                options = listOf("5 Töne", "7 Töne", "12 Töne", "8 Töne"),
                correctAnswerIndex = 1,
                explanation = "Die 7 Stammton-Noten lauten: C, D, E, F, G, A, H."
            ),
            QuizQuestion(
                id = 43,
                subjectId = "music",
                questionText = "Welcher Dreiklang klingt typischerweise eher traurig oder melancholisch?",
                options = listOf("Dur-Dreiklang", "Moll-Dreiklang", "Übermäßiger Dreiklang", "Quartakkord"),
                correctAnswerIndex = 1,
                explanation = "Moll-Akkorde klingen wegen der kleinen Terz unten meist dunkler oder melancholischer."
            ),
            QuizQuestion(
                id = 44,
                subjectId = "music",
                questionText = "Wie lauten die Noten des A-Moll Dreiklangs?",
                options = listOf("A - C - E", "A - C# - E", "C - E - G", "F - A - C"),
                correctAnswerIndex = 0,
                explanation = "Der A-Moll Dreiklang besteht aus A, C und E."
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
            ),
            QuizQuestion(
                id = 51,
                subjectId = "german",
                questionText = "Was bezeichnet man in der Literatur als 'Metapher'?",
                options = listOf("Übertragener sprachlicher Ausdruck", "Wiederholung von Wörtern", "Starke Übertreibung", "Vermenschlichung"),
                correctAnswerIndex = 0,
                explanation = "Eine Metapher verknüpft zwei Bereiche ohne Vergleicher ('wie') in bildhafter Übertragung."
            ),
            QuizQuestion(
                id = 52,
                subjectId = "german",
                questionText = "Welche Gattung verbindet lyrische, epische und dramatische Elemente?",
                options = listOf("Roman", "Ballade", "Fabel", "Anekdote"),
                correctAnswerIndex = 1,
                explanation = "Die Ballade ist ein erzähltes Gedicht mit Handlung und Dialogen."
            ),
            QuizQuestion(
                id = 53,
                subjectId = "german",
                questionText = "Aus welchen Hauptteilen besteht ein überzeugendes Argument in der Erörterung?",
                options = listOf("Behauptung (These), Begründung, Beispiel", "Einleitung, Hauptteil, Schluss", "Titel, Autor, Datum", "Einleitung, Metapher, These"),
                correctAnswerIndex = 0,
                explanation = "Die 3 B's eines Arguments: Behauptung (These), Begründung und Beispiel/Beleg."
            ),
            QuizQuestion(
                id = 54,
                subjectId = "german",
                questionText = "Welches Stilmittel ist 'Die Sonne lacht us entgegen'?",
                options = listOf("Alliteration", "Personifikation", "Anapher", "Inversion"),
                correctAnswerIndex = 1,
                explanation = "Bei der Personifikation werden unbelebten Dingen menschliche Eigenschaften zugeschrieben."
            )
        )
    )

    fun getQuestionsForSubject(subjectId: String): List<QuizQuestion> {
        return questionsBySubject[subjectId] ?: questionsBySubject["chemistry"]!!
    }
}
