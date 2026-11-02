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
    val imageType: DrivingImageType = DrivingImageType.NONE,
    val imageUrl: String? = null        // Direkte Bild-URL von der Fahrschul-Webseite
)

object DrivingSchoolData {
    val allQuestions = listOf(
        // ==================== TESTBOGEN 1 (Klasse AM) ====================
        DrivingQuestion(
            id = 1,
            testbogenNumber = 1,
            questionText = "Sie fahren auf dieser nassen Straße im Stadtverkehr. Welches Verhalten ist richtig?",
            options = listOf(
                "Geschwindigkeit an die nassen Fahrbahnverhältnisse anpassen",
                "Ausreichend Abstand zum vorausfahrenden Fahrzeug halten",
                "Plötzlich stark abbremsen, um die Reifen zu testen"
            ),
            correctOptionIndices = setOf(0, 1),
            errorPoints = 4,
            explanation = "Bei Regen und nasser Fahrbahn verlängert sich der Bremsweg drastisch. Die Geschwindigkeit muss reduziert und der Sicherheitsabstand vergrößert werden.",
            imageType = DrivingImageType.NONE,
            imageUrl = "https://www.fahrschule.de/Testbogen/Ersterwerb/Klasse/AM/Testbogen_1/images/1.jpg"
        ),
        DrivingQuestion(
            id = 2,
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
            imageType = DrivingImageType.RIGHT_OF_WAY_CROSSING,
            imageUrl = "https://www.fahrschule.de/Testbogen/Ersterwerb/Klasse/AM/Testbogen_1/images/2.jpg"
        ),
        DrivingQuestion(
            id = 3,
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
            imageType = DrivingImageType.STOP_SIGN,
            imageUrl = "https://www.fahrschule.de/Testbogen/Ersterwerb/Klasse/AM/Testbogen_1/images/3.jpg"
        ),
        DrivingQuestion(
            id = 4,
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
            id = 5,
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
            id = 6,
            testbogenNumber = 1,
            questionText = "Welche Ausrüstung ist beim Fahren eines Mopeds / Rollers der Klasse AM gesetzlich vorgeschrieben?",
            options = listOf("Amtlich zugelassener Schutzhelm", "Lederjacke", "Protektorenhandschuhe"),
            correctOptionIndices = setOf(0),
            errorPoints = 3,
            explanation = "Nach § 21a StVO müssen Fahrer von Mopeds/Rollern während der Fahrt einen geeigneten Schutzhelm tragen."
        ),
        DrivingQuestion(
            id = 7,
            testbogenNumber = 1,
            questionText = "Welche Promillegrenze gilt für Fahranfänger in der Probezeit und unter 21 Jahren?",
            options = listOf("0,0 Promille (absolutes Alkoholverbot)", "0,3 Promille", "0,5 Promille"),
            correctOptionIndices = setOf(0),
            errorPoints = 5,
            explanation = "Für Fahranfänger in der Probezeit sowie für Personen unter 21 Jahren gilt nach § 24c StVG ein klares Alkoholverbot (0,0 Promille)."
        ),
        DrivingQuestion(
            id = 8,
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
            id = 9,
            testbogenNumber = 1,
            questionText = "Wie groß muss der Sicherheitsabstand außerorts zu einem vorausfahrenden Fahrzeug mindestens sein?",
            options = listOf("Mindestens 2 Pkw-Längen", "Gleich dem halben Tachowert in Metern ('Halber Tacho')", "Genau 10 Meter"),
            correctOptionIndices = setOf(1),
            errorPoints = 3,
            explanation = "Als Faustregel für den Sicherheitsabstand gilt außerorts der halbe Tachowert in Metern (z.B. bei 50 km/h mindestens 25 Meter Abstand)."
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
        )
    )

    fun getQuestionsForBogen(bogenNumber: Int, isRandomOrder: Boolean): List<DrivingQuestion> {
        val pool = if (bogenNumber == 0) allQuestions else allQuestions.filter { it.testbogenNumber == bogenNumber }
        return if (isRandomOrder) pool.shuffled() else pool
    }
}
