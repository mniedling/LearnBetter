package org.learnbetter.project.model

data class DrivingQuestion(
    val id: Int,
    val questionText: String,
    val options: List<String>,
    val correctOptionIndices: Set<Int>, // Indizes aller richtigen Antworten (0, 1, 2)
    val errorPoints: Int,               // Offizielle Fehlerpunkte (2 bis 5)
    val explanation: String
)

object DrivingSchoolData {
    val testbogenAM1 = listOf(
        DrivingQuestion(
            id = 1,
            questionText = "An Kreuzungen ohne Schild oder Ampel gilt die Vorfahrtsregel. Welches Prinzip müssen Sie beachten?",
            options = listOf(
                "Wer schneller fährt, hat Vorfahrt",
                "Es gilt die Grundregel 'Rechts vor Links'",
                "Schwere Fahrzeuge haben immer Vorfahrt"
            ),
            correctOptionIndices = setOf(1),
            errorPoints = 4,
            explanation = "Auf Kreuzungen und Einmündungen ohne Schild oder Ampel gilt nach § 8 StVO stets die Grundregel 'Rechts vor Links'."
        ),
        DrivingQuestion(
            id = 2,
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
            id = 3,
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
            id = 4,
            questionText = "Welche Ausrüstung ist beim Fahren eines Mopeds / Rollers der Klasse AM gesetzlich vorgeschrieben?",
            options = listOf("Amtlich zugelassener Schutzhelm", "Lederjacke", "Protektorenhandschuhe"),
            correctOptionIndices = setOf(0),
            errorPoints = 3,
            explanation = "Nach § 21a StVO müssen Fahrer von Mopeds/Rollern während der Fahrt einen geeigneten Schutzhelm tragen."
        ),
        DrivingQuestion(
            id = 5,
            questionText = "Welche Promillegrenze gilt für Fahranfänger in der Probezeit und unter 21 Jahren?",
            options = listOf("0,0 Promille (absolutes Alkoholverbot)", "0,3 Promille", "0,5 Promille"),
            correctOptionIndices = setOf(0),
            errorPoints = 5,
            explanation = "Für Fahranfänger in der Probezeit sowie für Personen unter 21 Jahren gilt nach § 24c StVG ein klares Alkoholverbot (0,0 Promille)."
        ),
        DrivingQuestion(
            id = 6,
            questionText = "Worauf müssen Sie achten, wenn Sie die Höchstgeschwindigkeit Ihres Mopeds (Klasse AM) durch Tuning erhöhen?",
            options = listOf(
                "Die Betriebserlaubnis des Fahrzeugs erlischt",
                "Es liegt Fahren ohne Fahrerlaubnis vor, falls Führerscheinklasse AM überschritten wird",
                "Der Versicherungsschutz kann entfallen"
            ),
            correctOptionIndices = setOf(0, 1, 2),
            errorPoints = 4,
            explanation = "Tuning ist hochgefährlich und strafbar: Die Betriebserlaubnis erlischt, der Versicherungsschutz entfällt und es droht eine Strafe wegen Fahrens ohne Fahrerlaubnis!"
        ),
        DrivingQuestion(
            id = 7,
            questionText = "Wie groß muss der Sicherheitsabstand außerorts zu einem vorausfahrenden Fahrzeug mindestens sein?",
            options = listOf("Mindestens 2 Pkw-Längen", "Gleich dem halben Tachowert in Metern ('Halber Tacho')", "Genau 10 Meter"),
            correctOptionIndices = setOf(1),
            errorPoints = 3,
            explanation = "Als Faustregel für den Sicherheitsabstand gilt außerorts der halbe Tachowert in Metern (z.B. bei 50 km/h mindestens 25 Meter Abstand)."
        ),
        DrivingQuestion(
            id = 8,
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
            id = 9,
            questionText = "Wie weit vor einer Kreuzung oder Einmündung gilt ein Parkverbot?",
            options = listOf("1 Meter", "5 Meter ab den Schnittpunkten der Fahrbahnkanten", "15 Meter"),
            correctOptionIndices = setOf(1),
            errorPoints = 2,
            explanation = "Nach § 12 StVO ist das Halten und Parken bis zu 5 Metern vor und nach Kreuzungen und Einmündungen unzulässig."
        ),
        DrivingQuestion(
            id = 10,
            questionText = "Wie berechnet sich der gesamte Anhalteweg eines Fahrzeugs?",
            options = listOf(
                "Anhalteweg = Reaktionsweg + Bremsweg",
                "Anhalteweg = Reaktionsweg - Bremsweg",
                "Anhalteweg = Nur der Bremsweg"
            ),
            correctOptionIndices = setOf(0),
            errorPoints = 3,
            explanation = "Der Anhalteweg setzt sich zusammen aus dem Reaktionsweg (Schrecksekunde) und dem eigentlichen Bremsweg."
        )
    )
}
