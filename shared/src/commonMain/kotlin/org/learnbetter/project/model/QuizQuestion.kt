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
                questionText = "Welches Gas entsteht, wenn unedle Metalle wie Magnesium mit Salzsäure reagieren?",
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
                questionText = "Was versteht man in der Chemie unter einer 'Protolyse' nach Brønsted?",
                options = listOf("Übertragung eines Elektrons", "Übertragung eines Protons (H⁺-Ion)", "Verdampfen von Wasser", "Verbrennung von Metallen"),
                correctAnswerIndex = 1,
                explanation = "Eine Protolyse ist eine Säure-Base-Reaktion, bei der ein Proton (H⁺) von der Säure auf die Base übertragen wird."
            ),
            QuizQuestion(
                id = 4,
                subjectId = "chemistry",
                questionText = "Welches Ion ist für den sauren Charakter einer wässrigen Säurelösung verantwortlich?",
                options = listOf("Hydroxid-Ion (OH⁻)", "Oxonium-Ion (H₃O⁺)", "Chlorid-Ion (Cl⁻)", "Natrium-Ion (Na⁺)"),
                correctAnswerIndex = 1,
                explanation = "Die Übertragung eines Protons auf ein Wassermolekül bildet das saure Oxonium-Ion (H₃O⁺)."
            ),
            QuizQuestion(
                id = 5,
                subjectId = "chemistry",
                questionText = "Wie lautet die Summenformel für Schwefelsäure?",
                options = listOf("H₂SO₄", "HNO₃", "H₃PO₄", "H₂CO₃"),
                correctAnswerIndex = 0,
                explanation = "Schwefelsäure hat die Summenformel H₂SO₄."
            ),
            QuizQuestion(
                id = 6,
                subjectId = "chemistry",
                questionText = "Wie heißt das Säurerest-Ion der Schwefelsäure (H₂SO₄)?",
                options = listOf("Sulfit-Ion (SO₃²⁻)", "Sulfat-Ion (SO₄²⁻)", "Nitrat-Ion (NO₃⁻)", "Chlorid-Ion (Cl⁻)"),
                correctAnswerIndex = 1,
                explanation = "Das Säurerest-Ion der Schwefelsäure heißt Sulfat-Ion (SO₄²⁻)."
            ),
            QuizQuestion(
                id = 7,
                subjectId = "chemistry",
                questionText = "Welches Säurerest-Ion gehört zur Salpetersäure (HNO₃)?",
                options = listOf("Nitrit-Ion (NO₂⁻)", "Nitrat-Ion (NO₃⁻)", "Carbonat-Ion (CO₃²⁻)", "Phosphat-Ion (PO₄³⁻)"),
                correctAnswerIndex = 1,
                explanation = "Das Säurerest-Ion der Salpetersäure ist das Nitrat-Ion (NO₃⁻)."
            ),
            QuizQuestion(
                id = 8,
                subjectId = "chemistry",
                questionText = "Welches Gas entsteht beim Tropfen von Säure auf Marmorfliesen (CaCO₃)?",
                options = listOf("Sauerstoff (O₂)", "Kohlendioxid (CO₂)", "Wasserstoff (H₂)", "Chlor (Cl₂)"),
                correctAnswerIndex = 1,
                explanation = "Säure löst den Kalk (CaCO₃) im Marmor auf unter Zersetzung zu Kohlendioxid-Gas (CO₂)."
            ),
            QuizQuestion(
                id = 9,
                subjectId = "chemistry",
                questionText = "Warum sollte man Marmorfliesen niemals mit Essig oder Zitronensäure reinigen?",
                options = listOf("Weil die Fliese schmilzt", "Weil die Säure den Kalk auflöst und die Fliese ruiniert", "Weil sich die Farbe verfärbt", "Weil das Wasser verdunstet"),
                correctAnswerIndex = 1,
                explanation = "Die Säure greift das Calciumcarbonat (Kalk) des Marmors an und macht die Fliese matt und rau."
            ),
            QuizQuestion(
                id = 10,
                subjectId = "chemistry",
                questionText = "Was passiert bei einer Ionenbindung zwischen Natrium (Na) und Chlor (Cl)?",
                options = listOf("Atome teilen sich Elektronen", "Natrium gibt 1 Elektron an Chlor ab", "Chlor gibt 1 Elektron an Natrium ab", "Nichts passiert"),
                correctAnswerIndex = 1,
                explanation = "Natrium gibt sein Außenelektron ab, Chlor nimmt es auf. Die geladenen Ionen (Na⁺, Cl⁻) ziehen sich an."
            ),
            QuizQuestion(
                id = 11,
                subjectId = "chemistry",
                questionText = "Was kennzeichnet eine Atombindung / Elektronenpaarbindung bei Molekülen wie Wasser (H₂O)?",
                options = listOf("Elektronen werden vollständig abgegeben", "Atome teilen sich gemeinsame Elektronenpaare", "Keine Elektronen sind beteiligt", "Ionen ziehen sich an"),
                correctAnswerIndex = 1,
                explanation = "Bei Nichtmetallen teilen sich die Atome Elektronenpaare, um die Edelgaskonfiguration zu erreichen."
            ),
            QuizQuestion(
                id = 12,
                subjectId = "chemistry",
                questionText = "Welchen pH-Wert hat eine neutrale Lösung wie reines Wasser?",
                options = listOf("pH 0", "pH 7", "pH 14", "pH 1"),
                correctAnswerIndex = 1,
                explanation = "pH 7 entspricht einer neutralen Lösung mit gleichem H₃O⁺ und OH⁻ Verhältnis."
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
            ),
            QuizQuestion(
                id = 31,
                subjectId = "social_studies",
                questionText = "Wann fiel die Berliner Mauer im Zuge der Friedlichen Revolution?",
                options = listOf("17. Juni 1953", "13. August 1961", "9. November 1989", "3. Oktober 1990"),
                correctAnswerIndex = 2,
                explanation = "Am 9. November 1989 öffnete sich die Berliner Mauer."
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
            )
        )
    )

    fun getQuestionsForSubject(subjectId: String): List<QuizQuestion> {
        return questionsBySubject[subjectId] ?: questionsBySubject["chemistry"]!!
    }
}
