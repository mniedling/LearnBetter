package org.learnbetter.project.model

enum class MilitaryBranch {
    HEER_LUFTWAFFE, // Heer & Luftwaffe
    MARINE          // Marine (Seestreitkräfte)
}

enum class RankCategory(val displayName: String, val icon: String) {
    MANNSCHAFTEN("Mannschaften", "🪖"),
    UNTEROFFIZIERE("Unteroffiziere", "🎗️"),
    OFFIZIERE("Offiziere", "⚔️"),
    GENERALE_ADMIRALE("Generale & Admirale", "⭐")
}

data class MilitaryRank(
    val id: String,
    val name: String,
    val branch: MilitaryBranch,
    val category: RankCategory,
    val level: Int,             // Hierarchie-Ebene (1 = niedrigst, 10 = höchst)
    val insigniaCode: String,   // Vektor-Code für die Schulterklappen-Zeichnung
    val description: String
)

data class MilitaryQuestion(
    val id: Int,
    val questionText: String,
    val introduction: String? = null,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String,
    val category: String
)

object MilitaryData {
    val ranks = listOf(
        // === HEER / LUFTWAFFE - MANNSCHAFTEN ===
        MilitaryRank("h_schuetze", "Schütze / Flieger", MilitaryBranch.HEER_LUFTWAFFE, RankCategory.MANNSCHAFTEN, 1, "BAR_0", "Unsterster Mannschaftsdienstgrad (Grundwehrdienst/Saz)"),
        MilitaryRank("h_gefreiter", "Gefreiter", MilitaryBranch.HEER_LUFTWAFFE, RankCategory.MANNSCHAFTEN, 2, "BAR_1", "1 schräger Balken"),
        MilitaryRank("h_obergefreiter", "Obergefreiter", MilitaryBranch.HEER_LUFTWAFFE, RankCategory.MANNSCHAFTEN, 3, "BAR_2", "2 schräge Balken"),
        MilitaryRank("h_hauptgefreiter", "Hauptgefreiter", MilitaryBranch.HEER_LUFTWAFFE, RankCategory.MANNSCHAFTEN, 4, "BAR_3", "3 schräge Balken"),
        MilitaryRank("h_stabsgefreiter", "Stabsgefreiter", MilitaryBranch.HEER_LUFTWAFFE, RankCategory.MANNSCHAFTEN, 5, "BAR_4", "1 Winkelschleife + Balken"),
        MilitaryRank("h_korporal", "Korporal", MilitaryBranch.HEER_LUFTWAFFE, RankCategory.MANNSCHAFTEN, 6, "KORPORAL", "Spitzendienstgrad Mannschaften mit Doppelwinkel"),

        // === HEER / LUFTWAFFE - UNTEROFFIZIERE ===
        MilitaryRank("h_unteroffizier", "Unteroffizier", MilitaryBranch.HEER_LUFTWAFFE, RankCategory.UNTEROFFIZIERE, 1, "NCO_1", "Offene U-förmige Tresse"),
        MilitaryRank("h_stabsunteroffizier", "Stabsunteroffizier", MilitaryBranch.HEER_LUFTWAFFE, RankCategory.UNTEROFFIZIERE, 2, "NCO_2", "Geschlossene U-förmige Tresse"),
        MilitaryRank("h_feldwebel", "Feldwebel", MilitaryBranch.HEER_LUFTWAFFE, RankCategory.UNTEROFFIZIERE, 3, "NCO_3", "Kopfwinkel mit 1 Kopfwinkel-Spitze"),
        MilitaryRank("h_oberfeldwebel", "Oberfeldwebel", MilitaryBranch.HEER_LUFTWAFFE, RankCategory.UNTEROFFIZIERE, 4, "NCO_4", "Kopfwinkel mit 2 Kopfwinkeln"),
        MilitaryRank("h_hauptfeldwebel", "Hauptfeldwebel", MilitaryBranch.HEER_LUFTWAFFE, RankCategory.UNTEROFFIZIERE, 5, "NCO_5", "Kopfwinkel mit Kopfwinkel-Kopfrand"),

        // === HEER / LUFTWAFFE - OFFIZIERE ===
        MilitaryRank("h_leutnant", "Leutnant", MilitaryBranch.HEER_LUFTWAFFE, RankCategory.OFFIZIERE, 1, "OFF_1", "1 silberner Stern"),
        MilitaryRank("h_oberleutnant", "Oberleutnant", MilitaryBranch.HEER_LUFTWAFFE, RankCategory.OFFIZIERE, 2, "OFF_2", "2 silberne Sterne"),
        MilitaryRank("h_hauptmann", "Hauptmann", MilitaryBranch.HEER_LUFTWAFFE, RankCategory.OFFIZIERE, 3, "OFF_3", "3 silberne Sterne"),
        MilitaryRank("h_major", "Major", MilitaryBranch.HEER_LUFTWAFFE, RankCategory.OFFIZIERE, 4, "OFF_4", "Silbernes Eichenlaubkranz + 1 Stern"),
        MilitaryRank("h_oberstleutnant", "Oberstleutnant", MilitaryBranch.HEER_LUFTWAFFE, RankCategory.OFFIZIERE, 5, "OFF_5", "Silbernes Eichenlaubkranz + 2 Sterne"),
        MilitaryRank("h_oberst", "Oberst", MilitaryBranch.HEER_LUFTWAFFE, RankCategory.OFFIZIERE, 6, "OFF_6", "Silbernes Eichenlaubkranz + 3 Sterne"),

        // === HEER / LUFTWAFFE - GENERALE ===
        MilitaryRank("h_brigadegeneral", "Brigadegeneral", MilitaryBranch.HEER_LUFTWAFFE, RankCategory.GENERALE_ADMIRALE, 1, "GEN_1", "Goldenes Eichenlaub + 1 goldener Stern"),
        MilitaryRank("h_generalmajor", "Generalmajor", MilitaryBranch.HEER_LUFTWAFFE, RankCategory.GENERALE_ADMIRALE, 2, "GEN_2", "Goldenes Eichenlaub + 2 goldene Sterne"),
        MilitaryRank("h_generalleutnant", "Generalleutnant", MilitaryBranch.HEER_LUFTWAFFE, RankCategory.GENERALE_ADMIRALE, 3, "GEN_3", "Goldenes Eichenlaub + 3 goldene Sterne"),
        MilitaryRank("h_general", "General", MilitaryBranch.HEER_LUFTWAFFE, RankCategory.GENERALE_ADMIRALE, 4, "GEN_4", "Goldenes Eichenlaub + 4 goldene Sterne"),

        // === MARINE - MANNSCHAFTEN & BOOTSLEUTE ===
        MilitaryRank("m_matrose", "Matrose", MilitaryBranch.MARINE, RankCategory.MANNSCHAFTEN, 1, "NAVY_0", "Einfacher Mannschaftsdienstgrad Marine"),
        MilitaryRank("m_gefreiter", "Gefreiter (Marine)", MilitaryBranch.MARINE, RankCategory.MANNSCHAFTEN, 2, "NAVY_1", "1 Schrägbalken auf dunkelblauem Grund"),
        MilitaryRank("m_obergefreiter", "Obergefreiter (Marine)", MilitaryBranch.MARINE, RankCategory.MANNSCHAFTEN, 3, "NAVY_2", "2 Schrägbalken auf dunkelblauem Grund"),
        MilitaryRank("m_maat", "Maat", MilitaryBranch.MARINE, RankCategory.UNTEROFFIZIERE, 1, "NAVY_MAAT", "Goldener Maat-Winkel"),
        MilitaryRank("m_bootsmann", "Bootsmann", MilitaryBranch.MARINE, RankCategory.UNTEROFFIZIERE, 2, "NAVY_BOOTSMANN", "Goldenes Seemanns-Emblem mit Anker"),
        MilitaryRank("m_leutnant_z_s", "Leutnant zur See", MilitaryBranch.MARINE, RankCategory.OFFIZIERE, 1, "NAVY_OFF_1", "1 mittelbreite goldene Tresse mit Seesterne"),
        MilitaryRank("m_kapitaenleutnant", "Kapitänleutnant", MilitaryBranch.MARINE, RankCategory.OFFIZIERE, 2, "NAVY_OFF_2", "2 mittelbreite + 1 schmale goldene Tresse"),
        MilitaryRank("m_fregattenkapitaen", "Fregattenkapitän", MilitaryBranch.MARINE, RankCategory.OFFIZIERE, 3, "NAVY_OFF_3", "3 mittelbreite + 1 breite goldene Tresse"),
        MilitaryRank("m_admiral", "Admiral", MilitaryBranch.MARINE, RankCategory.GENERALE_ADMIRALE, 1, "NAVY_ADMIRAL", "Breite Tresse + 3 goldene Sterne + Anker")
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
