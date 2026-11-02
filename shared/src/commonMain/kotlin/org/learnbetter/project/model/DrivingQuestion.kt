package org.learnbetter.project.model

enum class DrivingImageType {
    NONE,
    STOP_SIGN,          // Stoppschild 🛑
    YIELD_SIGN,         // Vorfahrt gewähren 🔻
    PRIORITY_ROAD,      // Vorfahrtsstraße 🟡
    RIGHT_OF_WAY_CROSSING, // Kreuzungssituation (Vorfahrt)
    NO_ENTRY,           // Verbot der Einfahrt ⛔
    SPEED_45            // Höchstgeschwindigkeit 45 km/h
}

data class DrivingQuestion(
    val id: Int,
    val testbogenNumber: Int,           // Bogen 1, Bogen 2, Bogen 3
    val questionText: String,
    val options: List<String>,
    val correctOptionIndices: Set<Int>, // Indizes aller richtigen Antworten (0, 1, 2)
    val errorPoints: Int,               // Offizielle Fehlerpunkte (2 bis 5)
    val explanation: String,
    val imageType: DrivingImageType = DrivingImageType.NONE
)

object DrivingSchoolData {
    val allQuestions = listOf(
        // ==================== TESTBOGEN 1 (Klasse AM) ====================
        DrivingQuestion(
            id = 1,
            testbogenNumber = 1,
            questionText = "An Kreuzungen ohne Schild oder Ampel gilt die Vorfahrtsregel. Welches Prinzip müssen Sie beachten?",
            options = listOf(
                "Wer schneller fährt, hat Vorfahrt",
                "Es gilt die Grundregel 'Rechts vor Links'",
                "Schwere Fahrzeuge haben immer Vorfahrt"
            ),
            correctOptionIndices = setOf(1),
            errorPoints = 4,
            explanation = "Auf Kreuzungen und Einmündungen ohne Schild oder Ampel gilt nach § 8 StVO stets die Grundregel 'Rechts vor Links'.",
            imageType = DrivingImageType.RIGHT_OF_WAY_CROSSING
        ),
        DrivingQuestion(
            id = 2,
            testbogenNumber = 1,
            questionText = "Was bedeutet dieses rote Stoppschild mit weißer Aufschrift 'STOP' an einer Kreuzung?",
            options = listOf(
                "Möglichst langsam ohne Anhalten weiterfahren",
                "An der Haltlinie (oder Sichtlinie) anhalten und Vorfahrt gewähren",
                "Nur anhalten, wenn Gegenverkehr sichtbar ist"
            ),
            correctOptionIndices = setOf(1),
            errorPoints = 5,
            explanation = "Bei Zeichen 206 (Halt! Vorfahrt gewähren!) muss zwingend an der Haltlinie angehalten und allen anderen Verkehrsteilnehmern Vorfahrt gewährt werden.",
            imageType = DrivingImageType.STOP_SIGN
        ),
        DrivingQuestion(
            id = 3,
            testbogenNumber = 1,
            questionText = "Wie lautet die Faustformel zur Berechnung des Reaktionswegs bei einer Geschwindigkeit v (in km/h)?",
            options = listOf(
                "(Geschwindigkeit in km/h ÷ 10) × 3",
                "(Geschwindigkeit in km/h ÷ 10) × (Geschwindigkeit in km/h ÷ 10)",
                "Geschwindigkeit in km/h ÷ 2"
            ),
            correctOptionIndices = setOf(0),
            errorPoints = 3,
            explanation = "Der Reaktionsweg berechnet sich nach der Faustformel: (v ÷ 10) × 3. Bei 50 km/h entspricht das 15 Metern."
        ),
        DrivingQuestion(
            id = 4,
            testbogenNumber = 1,
            questionText = "Wie lautet die Faustformel zur Berechnung des normalen Bremswegs bei einer Geschwindigkeit v (in km/h)?",
            options = listOf(
                "(Geschwindigkeit in km/h ÷ 10) × 3",
                "(Geschwindigkeit in km/h ÷ 10) × (Geschwindigkeit in km/h ÷ 10)",
                "(Geschwindigkeit in km/h ÷ 10) × 5"
            ),
            correctOptionIndices = setOf(1),
            errorPoints = 3,
            explanation = "Der normale Bremsweg berechnet sich als (v ÷ 10) × (v ÷ 10). Bei 50 km/h ergibt sich ein Bremsweg von 25 Metern."
        ),
        DrivingQuestion(
            id = 5,
            testbogenNumber = 1,
            questionText = "Welche Ausrüstung ist beim Fahren eines Mopeds / Rollers der Klasse AM gesetzlich vorgeschrieben?",
            options = listOf("Amtlich zugelassener Schutzhelm", "Lederjacke", "Protektorenhandschuhe"),
            correctOptionIndices = setOf(0),
            errorPoints = 3,
            explanation = "Nach § 21a StVO müssen Fahrer von Mopeds/Rollern während der Fahrt einen geeigneten Schutzhelm tragen."
        ),
        DrivingQuestion(
            id = 6,
            testbogenNumber = 1,
            questionText = "Welche Promillegrenze gilt für Fahranfänger in der Probezeit und unter 21 Jahren?",
            options = listOf("0,0 Promille (absolutes Alkoholverbot)", "0,3 Promille", "0,5 Promille"),
            correctOptionIndices = setOf(0),
            errorPoints = 5,
            explanation = "Für Fahranfänger in der Probezeit sowie für Personen unter 21 Jahren gilt nach § 24c StVG ein klares Alkoholverbot (0,0 Promille)."
        ),
        DrivingQuestion(
            id = 7,
            testbogenNumber = 1,
            questionText = "Worauf müssen Sie achten, wenn Sie die Höchstgeschwindigkeit Ihres Mopeds (Klasse AM) durch Tuning erhöhen?",
            options = listOf(
                "Die Betriebserlaubnis des Fahrzeugs erlischt",
                "Es liegt Fahren ohne Fahrerlaubnis vor",
                "Der Versicherungsschutz kann entfallen"
            ),
            correctOptionIndices = setOf(0, 1, 2),
            errorPoints = 4,
            explanation = "Tuning ist hochgefährlich und strafbar: Die Betriebserlaubnis erlischt, der Versicherungsschutz entfällt und es droht eine Strafe wegen Fahrens ohne Fahrerlaubnis!",
            imageType = DrivingImageType.SPEED_45
        ),
        DrivingQuestion(
            id = 8,
            testbogenNumber = 1,
            questionText = "Wie groß muss der Sicherheitsabstand außerorts zu einem vorausfahrenden Fahrzeug mindestens sein?",
            options = listOf("Mindestens 2 Pkw-Längen", "Gleich dem halben Tachowert in Metern ('Halber Tacho')", "Genau 10 Meter"),
            correctOptionIndices = setOf(1),
            errorPoints = 3,
            explanation = "Als Faustregel für den Sicherheitsabstand gilt außerorts der halbe Tachowert in Metern (z.B. bei 50 km/h mindestens 25 Meter Abstand)."
        ),
        DrivingQuestion(
            id = 9,
            testbogenNumber = 1,
            questionText = "Wo ist das Überholen verboten?",
            options = listOf(
                "Bei unübersichtlicher Verkehrslage",
                "Vor und an Fußgängerüberwegen (Zebra-Streifen)",
                "Wenn der Gegenverkehr behindert oder gefährdet werden könnte"
            ),
            correctOptionIndices = setOf(0, 1, 2),
            errorPoints = 4,
            explanation = "Nach § 5 StVO ist Überholen streng verboten bei Unübersichtlichkeit, vor Zebrastreifen sowie bei Gefährdung des Gegenverkehrs."
        ),
        DrivingQuestion(
            id = 10,
            testbogenNumber = 1,
            questionText = "Wie berechnet sich der gesamte Anhalteweg eines Fahrzeugs?",
            options = listOf(
                "Anhalteweg = Reaktionsweg + Bremsweg",
                "Anhalteweg = Reaktionsweg - Bremsweg",
                "Anhalteweg = Nur der Bremsweg"
            ),
            correctOptionIndices = setOf(0),
            errorPoints = 3,
            explanation = "Der Anhalteweg setzt sich zusammen aus dem Reaktionsweg (Schrecksekunde) und dem eigentlichen Bremsweg."
        ),

        // ==================== TESTBOGEN 2 (Klasse AM) ====================
        DrivingQuestion(
            id = 11,
            testbogenNumber = 2,
            questionText = "Sie sehen dieses Zeichen 'Vorfahrt gewähren' (Zeichen 205). Wie verhalten Sie sich?",
            options = listOf(
                "Ich muss anderen Fahrzeugen Vorfahrt gewähren",
                "Ich habe Vorfahrt vor allen anderen",
                "Ich darf ohne zu schauen durchfahren"
            ),
            correctOptionIndices = setOf(0),
            errorPoints = 4,
            explanation = "Zeichen 205 verpflichtet zum Gewähren der Vorfahrt für den kreuzenden Verkehr.",
            imageType = DrivingImageType.YIELD_SIGN
        ),
        DrivingQuestion(
            id = 12,
            testbogenNumber = 2,
            questionText = "Sie befinden sich auf einer Vorfahrtsstraße (Zeichen 306). Was bedeutet das?",
            options = listOf(
                "Sie haben an allen Einmündungen Vorfahrt, bis ein anderes Schild dies aufhebt",
                "Sie müssen an jeder Kreuzung anhalten",
                "Sie dürfen höchstens 20 km/h fahren"
            ),
            correctOptionIndices = setOf(0),
            errorPoints = 4,
            explanation = "Das gelb-weiße Schild verpflichtet kreuzende Fahrzeuge zur Vorfahrtsgewährung.",
            imageType = DrivingImageType.PRIORITY_ROAD
        ),
        DrivingQuestion(
            id = 13,
            testbogenNumber = 2,
            questionText = "Was bedeutet dieses runde rote Schild mit weißem Querbalken (Zeichen 267)?",
            options = listOf(
                "Verbot der Einfahrt für Fahrzeuge aller Art",
                "Parkverbot",
                "Einbahnstraße in Fahrtrichtung"
            ),
            correctOptionIndices = setOf(0),
            errorPoints = 4,
            explanation = "Zeichen 267 verbietet die Einfahrt (z. B. am Ausgang einer Einbahnstraße).",
            imageType = DrivingImageType.NO_ENTRY
        ),
        DrivingQuestion(
            id = 14,
            testbogenNumber = 2,
            questionText = "Welche Höchstgeschwindigkeit gilt für Mopeds der Klasse AM grundsätzlich in Deutschland?",
            options = listOf("25 km/h", "45 km/h", "60 km/h"),
            correctOptionIndices = setOf(1),
            errorPoints = 4,
            explanation = "Die bauartbedingte Höchstgeschwindigkeit für Klasse AM beträgt max. 45 km/h.",
            imageType = DrivingImageType.SPEED_45
        ),
        DrivingQuestion(
            id = 15,
            testbogenNumber = 2,
            questionText = "Wie verändert sich der Bremsweg, wenn Sie Ihre Geschwindigkeit verdoppeln (z.B. von 25 auf 50 km/h)?",
            options = listOf("Der Bremsweg verdoppelt sich", "Der Bremsweg vervierfacht sich", "Der Bremsweg bleibt gleich"),
            correctOptionIndices = setOf(1),
            errorPoints = 4,
            explanation = "Da die Geschwindigkeit quadratisch in den Bremsweg eingeht, vervierfacht sich der Bremsweg bei doppelter Geschwindigkeit!"
        ),

        // ==================== TESTBOGEN 3 (Klasse AM) ====================
        DrivingQuestion(
            id = 16,
            testbogenNumber = 3,
            questionText = "Wie verhalten Sie sich bei einer Gefahrenbremsung?",
            options = listOf(
                "Schlagartig und mit maximaler Kraft bremsen",
                "Gar nicht bremsen",
                "Nur die Hinterradbremse sanft betätigen"
            ),
            correctOptionIndices = setOf(0),
            errorPoints = 4,
            explanation = "Bei der Gefahrenbremsung wird der Bremsweg halbiert durch schlagartiges Betätigen beider Bremsen."
        ),
        DrivingQuestion(
            id = 17,
            testbogenNumber = 3,
            questionText = "Was müssen Sie vor Fahrtantritt mit einem Sozius (Mitfahrer) auf dem Moped prüfen?",
            options = listOf(
                "Dass das Fahrzeug für 2 Personen zugelassen ist",
                "Dass der Mitfahrer ebenfalls einen Schutzhelm trägt",
                "Dass Fußrasten für den Mitfahrer vorhanden sind"
            ),
            correctOptionIndices = setOf(0, 1, 2),
            errorPoints = 3,
            explanation = "Ein Mitfahrer ist nur erlaubt, wenn ein Soziussitz, Fußrasten und Helm vorhanden sind und das Moped für 2 Personen zugelassen ist."
        ),
        DrivingQuestion(
            id = 18,
            testbogenNumber = 3,
            questionText = "An welchen Stellen ist das Parken verboten?",
            options = listOf(
                "Vor Grundstücksein- und -ausfahrten",
                "Vor und nach Kreuzungen im 5-Meter-Bereich",
                "Auf Bordsteinen, sofern nicht ausdrücklich erlaubt"
            ),
            correctOptionIndices = setOf(0, 1, 2),
            errorPoints = 2,
            explanation = "Nach § 12 StVO gelten diese klaren Parkverbote."
        )
    )

    fun getQuestionsForBogen(bogenNumber: Int, isRandomOrder: Boolean): List<DrivingQuestion> {
        val pool = if (bogenNumber == 0) allQuestions else allQuestions.filter { it.testbogenNumber == bogenNumber }
        return if (isRandomOrder) pool.shuffled() else pool
    }
}
