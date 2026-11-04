package org.learnbetter.project.model

data class MilitaryRank(
    val id: String,
    val name: String,
    val rankGroup: String, // Mannschaften, Unteroffiziere, Offiziere
    val level: Int,        // 1 bis 7 Hierarchie
    val insigniaIcon: String
)

data class MilitaryQuestion(
    val id: Int,
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String,
    val category: String
)

object MilitaryData {
    val ranks = listOf(
        MilitaryRank("gefreiter", "Gefreiter", "Mannschaften", 1, "🎗️"),
        MilitaryRank("stabsgefreiter", "Stabsgefreiter", "Mannschaften", 2, "🎗️🎗️"),
        MilitaryRank("stabsunteroffizier", "Stabsunteroffizier", "Unteroffiziere", 3, "🎖️"),
        MilitaryRank("feldwebel", "Feldwebel", "Unteroffiziere", 4, "🎖️⭐"),
        MilitaryRank("leutnant", "Leutnant", "Offiziere", 5, "⭐"),
        MilitaryRank("hauptmann", "Hauptmann", "Offiziere", 6, "⭐⭐⭐"),
        MilitaryRank("oberst", "Oberst", "Offiziere", 7, "🦅")
    )

    val catTestQuestions = listOf(
        MilitaryQuestion(
            id = 1,
            questionText = "In welchem Artikel des Grundgesetzes ist der Auftrag der Bundeswehr zur Verteidigung verankert?",
            options = listOf("Artikel 1", "Artikel 20", "Artikel 87a", "Artikel 104"),
            correctAnswerIndex = 2,
            explanation = "Artikel 87a Abs. 1 GG regelt: 'Der Bund stellt Streitkräfte zur Verteidigung auf.'",
            category = "Staatsbürgerkunde"
        ),
        MilitaryQuestion(
            id = 2,
            questionText = "Ein Bundeswehr-Lkw verbraucht auf 100 km genau 24 Liter Diesel. Wie viel Liter verbraucht er auf einer Strecke von 350 km?",
            options = listOf("72 Liter", "84 Liter", "96 Liter", "100 Liter"),
            correctAnswerIndex = 1,
            explanation = "Dreisatz: 24 Liter ÷ 100 × 350 = 84 Liter Diesel.",
            category = "Mathematik"
        ),
        MilitaryQuestion(
            id = 3,
            questionText = "Ergänzen Sie die Wortanalogie: Helm verhält sich zu Kopf wie Weste zu ...?",
            options = listOf("Bein", "Oberkörper", "Arm", "Schuh"),
            correctAnswerIndex = 1,
            explanation = "Der Helm schützt den Kopf, die Schutzweste schützt den Oberkörper.",
            category = "Logik"
        ),
        MilitaryQuestion(
            id = 4,
            questionText = "Setzen Sie die Zahlenreihe logisch fort: 3, 6, 12, 24, 48, ...?",
            options = listOf("60", "72", "96", "108"),
            correctAnswerIndex = 2,
            explanation = "Jede Zahl wird mit 2 multipliziert: 48 × 2 = 96.",
            category = "Logik"
        ),
        MilitaryQuestion(
            id = 5,
            questionText = "Ein Fahrzeug fährt mit einer Geschwindigkeit von 72 km/h. Wie viel Meter legt es in einer Sekunde (m/s) zurück?",
            options = listOf("10 m/s", "15 m/s", "20 m/s", "25 m/s"),
            correctAnswerIndex = 2,
            explanation = "Umrechnung von km/h in m/s durch Teilen durch 3,6: 72 ÷ 3,6 = 20 m/s.",
            category = "Mathematik"
        ),
        MilitaryQuestion(
            id = 6,
            questionText = "Wo befindet sich der Hauptsitz des internationalen Verteidigungsbündnisses NATO?",
            options = listOf("New York", "Berlin", "Brüssel", "Paris"),
            correctAnswerIndex = 2,
            explanation = "Das politische und militärische Hauptquartier der NATO liegt in Brüssel (Belgien).",
            category = "Allgemeinwissen"
        ),
        MilitaryQuestion(
            id = 7,
            questionText = "Zwei Zahnräder greifen ineinander. Zahnrad A hat 20 Zähne und dreht sich im Uhrzeigersinn. Wie dreht sich Zahnrad B?",
            options = listOf("Im Uhrzeigersinn", "Gegen den Uhrzeigersinn", "Gar nicht", "Sich abwechselnd"),
            correctAnswerIndex = 1,
            explanation = "Zwei direkt ineinandergreifende Zahnräder drehen sich stets in entgegengesetzte Richtungen.",
            category = "Technik"
        ),
        MilitaryQuestion(
            id = 8,
            questionText = "Welche der folgenden Disziplinen gehört NICHT zum offiziellen Basis-Fitness-Test (BFT) der Bundeswehr?",
            options = listOf("11 x 10 m Sprint-Test", "Klimmhang", "1000 m Lauf", "Bankdrücken"),
            correctAnswerIndex = 3,
            explanation = "Der BFT besteht aus 11x10m Sprint, Klimmhang und 1000m Lauf (oder Ergometer). Bankdrücken gehört nicht dazu.",
            category = "Bundeswehr-Wissen"
        ),
        MilitaryQuestion(
            id = 9,
            questionText = "Welches Satzzeichen gehört in die Lücke? 'Der Kommandeur befahl, _____ alle Soldaten antreten sollen.'",
            options = listOf("das", "dass", "daß", "welches"),
            correctAnswerIndex = 1,
            explanation = "'dass' als Konjunktion zur Einleitung eines Nebensatzes schreibt man mit doppel-s.",
            category = "Deutsch"
        ),
        MilitaryQuestion(
            id = 10,
            questionText = "Zu welcher Dienstgradgruppe gehört der Rang 'Feldwebel'?",
            options = listOf("Mannschaften", "Unteroffiziere", "Offiziere", "Zivilbeschäftigte"),
            correctAnswerIndex = 1,
            explanation = "Feldwebel gehören zur Gruppe der Unteroffiziere mit Portepee.",
            category = "Bundeswehr-Wissen"
        ),
        MilitaryQuestion(
            id = 11,
            questionText = "Berechnen Sie das Ergebnis: 3/4 + 1/2 = ?",
            options = listOf("4/6", "5/4 (1 1/4)", "1", "3/8"),
            correctAnswerIndex = 1,
            explanation = "1/2 erweitern auf 2/4. 3/4 + 2/4 = 5/4 = 1 1/4.",
            category = "Mathematik"
        ),
        MilitaryQuestion(
            id = 12,
            questionText = "Was bedeutet das Prinzip der 'Innere Führung' bei der Bundeswehr?",
            options = listOf(
                "Gehorsam ohne Hinterfragen",
                "Das Leitbild des 'Staatsbürgers in Uniform' mit Gewissensfreiheit und Grundgesetzbindung",
                "Nur im Inland zu dienen",
                "Reine Kampfausbildung"
            ),
            correctAnswerIndex = 1,
            explanation = "Die Innere Führung verankert die demokratischen Werte des Grundgesetzes im Handeln jedes Soldaten ('Staatsbürger in Uniform').",
            category = "Staatsbürgerkunde"
        ),
        MilitaryQuestion(
            id = 13,
            questionText = "Was passiert mit der Gesamtstromstärke in einem Stromkreis bei Parallelschaltung von Widerständen?",
            options = listOf("Sie sinkt", "Sie bleibt gleich", "Sie steigt an", "Sie wird null"),
            correctAnswerIndex = 2,
            explanation = "Durch das Parallelschalten sinkt der Gesamtwiderstand, wodurch die Gesamtsumme der Stromstärke steigt.",
            category = "Technik"
        ),
        MilitaryQuestion(
            id = 14,
            questionText = "Ein Zeltplatz für 80 Soldaten benötigt Reserven für 5 Tage. Wie lange reichen die Reserven für 100 Soldaten?",
            options = listOf("3 Tage", "4 Tage", "6 Tage", "2.5 Tage"),
            correctAnswerIndex = 1,
            explanation = "Inverser Dreisatz: (80 × 5) ÷ 100 = 400 ÷ 100 = 4 Tage.",
            category = "Mathematik"
        ),
        MilitaryQuestion(
            id = 15,
            questionText = "Welcher Minister / welche Ministerin hat im Frieden den Oberbefehl über die Streitkräfte (Inhaber der Befehls- und Kommandogewalt)?",
            options = listOf("Der Bundeskanzler", "Der Bundespräsident", "Der Bundesminister der Verteidigung", "Der Generalinspekteur"),
            correctAnswerIndex = 2,
            explanation = "Nach Art. 65a GG hat im Frieden der Bundesminister der Verteidigung den Oberbefehl über die Streitkräfte.",
            category = "Staatsbürgerkunde"
        )
    )
}
