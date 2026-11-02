package org.learnbetter.project.model

enum class DrivingImageType {
    NONE,
    STOP_SIGN,          // Stoppschild 🛑
    YIELD_SIGN,         // Vorfahrt gewähren 🔻
    PRIORITY_ROAD,      // Vorfahrtsstraße 🟡
    RIGHT_OF_WAY_CROSSING, // Kreuzungssituation (Vorfahrt)
    NO_ENTRY,           // Verbot der Einfahrt ⛔
    SPEED_45,           // Höchstgeschwindigkeit 45 km/h
    ANDREASKREUZ_BLITZ  // Andreaskreuz mit Blitz
}

data class DrivingQuestion(
    val id: Int,
    val testbogenNumber: Int,
    val questionText: String,
    val introduction: String? = null,
    val options: List<String>,
    val correctOptionIndices: Set<Int>, // 0-basierte Indizes
    val errorPoints: Int,
    val explanation: String,
    val imageType: DrivingImageType = DrivingImageType.NONE,
    val imageUrl: String? = null
)

object DrivingSchoolData {
    val allQuestions = listOf(
        // ==================== TESTBOGEN 1 (Klasse AM) - Originalgetreu ====================
        DrivingQuestion(
            id = 1,
            testbogenNumber = 1,
            questionText = "Sie möchten eine Pferdekutsche auf einer Landstraße überholen. Wie verhalten Sie sich richtig?",
            options = listOf(
                "Ich halte einen ausreichenden Abstand beim Wiedereinscheren ein",
                "Ich achte auf Handzeichen zum Abbiegen des Kutschers",
                "Ich halte einen möglichst geringen Seitenabstand ein"
            ),
            correctOptionIndices = setOf(0, 1),
            errorPoints = 4,
            explanation = "Pferde können scheuen. Beim Überholen und Wiedereinscheren muss ausreichend Abstand gehalten werden. Kutschen haben keine Blinker, daher auf Handzeichen achten.",
            imageUrl = null // Reine Textfrage, kein Bild anzeigen!
        ),
        DrivingQuestion(
            id = 2,
            testbogenNumber = 1,
            questionText = "Womit müssen Sie bei diesem Verkehrszeichen rechnen?",
            options = listOf(
                "Mit Verkehr von Baustellenfahrzeugen",
                "Mit Arbeitern auf der Fahrbahn",
                "Mit Baumaterial auf der Fahrbahn"
            ),
            correctOptionIndices = setOf(0, 1, 2),
            errorPoints = 2,
            explanation = "Das Gefahrenzeichen 'Baustelle' weist auf alle mit einer Baustelle verbundenen Gefahren hin: Arbeiter, Maschinen, Schmutz und Material auf der Fahrbahn.",
            imageUrl = "frage_2.png" // Hier wird das Baustellen-Schild benötigt
        ),
        DrivingQuestion(
            id = 3,
            testbogenNumber = 1,
            questionText = "Sie wollen rechts abbiegen. Worauf müssen Sie sich einstellen?",
            options = listOf(
                "Nach dem Abbiegen des Lkws könnten Fußgänger von der gegenüberliegenden Straßenseite kommen",
                "Das Kind könnte den Lkw vorbeilassen und dann vor mir die Fahrbahn überqueren",
                "Der Lkw wird anhalten"
            ),
            correctOptionIndices = setOf(0, 1),
            errorPoints = 4,
            explanation = "Kinder verhalten sich unberechenbar und könnten unvermittelt loslaufen. Auch Fußgänger, die durch den LKW verdeckt sind, stellen eine Gefahr beim Abbiegen dar.",
            imageUrl = "frage_3.png" // Hier wird die Cockpit-Ansicht benötigt
        ),
        DrivingQuestion(
            id = 4,
            testbogenNumber = 1,
            questionText = "Sie möchten innerorts einen Elektroroller überholen. Welchen seitlichen Mindestabstand müssen Sie dabei einhalten?",
            options = listOf("1,0 m", "1,5 m", "2,0 m"),
            correctOptionIndices = setOf(1),
            errorPoints = 4,
            explanation = "Nach § 5 StVO muss innerorts beim Überholen von Radfahrern, Fußgängern und Elektrokleinstfahrzeugen ein Mindestabstand von 1,5 Metern eingehalten werden.",
            imageUrl = null // Reine Textfrage
        ),
        DrivingQuestion(
            id = 5,
            testbogenNumber = 1,
            questionText = "Ein Polizeifahrzeug überholt Sie und schert unmittelbar vor Ihnen ein. Auf dem Dach erscheint in roter Leuchtschrift „BITTE FOLGEN”. Für wen gilt dies?",
            options = listOf(
                "Für alle Fahrzeuge auf meinem Fahrstreifen",
                "Für mich",
                "Für nachfolgende Polizei- und Rettungsfahrzeuge"
            ),
            correctOptionIndices = setOf(1),
            errorPoints = 3,
            explanation = "Das Signal gilt immer dem unmittelbar hinter dem Polizeifahrzeug fahrenden Fahrzeug. Sie müssen der Polizei folgen und anhalten, sobald diese anhält.",
            imageUrl = null // Reine Textfrage
        ),
        DrivingQuestion(
            id = 6,
            testbogenNumber = 1,
            questionText = "Sie fahren innerorts hinter einem Fahrzeug mit ortsfremdem Kennzeichen. Was könnte geschehen? Der Vorausfahrende...",
            options = listOf(
                "...bremst unerwartet",
                "...betätigt den Blinker vor dem Abbiegen zu spät",
                "...hält unerwartet an, um nach dem Weg zu fragen"
            ),
            correctOptionIndices = setOf(0, 1, 2),
            errorPoints = 3,
            explanation = "Ortsfremde Fahrer suchen oft nach dem Weg, Straßenschildern oder Navigationsanweisungen und reagieren deshalb oft plötzlich, langsam oder fehlerhaft.",
            imageUrl = null // Reine Textfrage
        ),
        DrivingQuestion(
            id = 7,
            testbogenNumber = 1,
            questionText = "Was bedeutet dieses Verkehrszeichen?",
            options = listOf(
                "Der Schienenverkehr hat immer Vorrang",
                "Hinweis auf vorhandene elektrische Oberleitungen",
                "Es ist immer zu warten, wenn sich ein Schienenfahrzeug nähert"
            ),
            correctOptionIndices = setOf(0, 1, 2),
            errorPoints = 3,
            explanation = "Das Andreaskreuz räumt Schienenfahrzeugen absoluten Vorrang ein. Der Blitz weist auf lebensgefährliche Hochspannungs-Oberleitungen hin.",
            imageUrl = "frage_7.png" // Hier wird das Andreaskreuz benötigt
        ),
        DrivingQuestion(
            id = 8,
            testbogenNumber = 1,
            questionText = "Wie sollten Sie sich verhalten?",
            options = listOf(
                "Bremsbereit bleiben",
                "Auf Schrittgeschwindigkeit abbremsen",
                "Unverändert weiterfahren"
            ),
            correctOptionIndices = setOf(0, 1),
            errorPoints = 5,
            explanation = "Wenn Kinder am Fahrbahnrand oder zwischen geparkten Autos stehen, muss sofort auf Schrittgeschwindigkeit abgebremst werden. Höchste Bremsbereitschaft ist erforderlich!",
            imageUrl = "frage_8.png" // Hier wird die Cockpit-Ansicht benötigt
        ),
        DrivingQuestion(
            id = 9,
            testbogenNumber = 1,
            questionText = "Wo führt schnelles Fahren häufig zu Unfällen?",
            options = listOf(
                "An Fußgängerüberwegen",
                "An Straßenkreuzungen und -einmündungen",
                "In Kurven"
            ),
            correctOptionIndices = setOf(0, 1, 2),
            errorPoints = 4,
            explanation = "Überhöhte Geschwindigkeit ist in Kreuzungsbereichen, Kurven und an Fußgängerüberwegen (Zebrastreifen) eine der Hauptunfallursachen.",
            imageUrl = null // Reine Textfrage
        ),
        DrivingQuestion(
            id = 10,
            testbogenNumber = 1,
            questionText = "Sie wollen eine landwirtschaftliche Zugmaschine mit nach hinten herausragendem Anbaugerät überholen. Was ist zu beachten?",
            options = listOf(
                "Zugmaschinen können wegen ihrer niedrigen Geschwindigkeit immer gefahrlos überholt werden",
                "Die hintere Beleuchtung kann stark verschmutzt oder verdeckt sein, so dass eingeschaltete Blinkleuchten schlecht oder gar nicht zu sehen sind",
                "Beim Abbiegen kann das Anbaugerät ausschwenken"
            ),
            correctOptionIndices = setOf(1, 2),
            errorPoints = 4,
            explanation = "Beim Abbiegen von Traktoren schwenken lange Anbaugeräte aus und ragen in die Gegen- oder Überholspur. Schmutz verdeckt oft wichtige Signalleuchten.",
            imageUrl = null // Reine Textfrage
        ),

        // Dummy-Einträge für Bogen 2 & 3 bleiben vorerst bestehen...
        DrivingQuestion(
            id = 11,
            testbogenNumber = 2,
            questionText = "Sie sehen dieses Zeichen 'Vorfahrt gewähren' (Zeichen 205). Wie verhalten Sie sich?",
            options = listOf("Ich muss anderen Fahrzeugen Vorfahrt gewähren", "Ich habe Vorfahrt"),
            correctOptionIndices = setOf(0),
            errorPoints = 4,
            explanation = "Zeichen 205 verpflichtet zum Gewähren der Vorfahrt.",
            imageType = DrivingImageType.YIELD_SIGN
        )
    )

    fun getQuestionsForBogen(bogenNumber: Int, isRandomOrder: Boolean): List<DrivingQuestion> {
        val pool = if (bogenNumber == 0) allQuestions else allQuestions.filter { it.testbogenNumber == bogenNumber }
        return if (isRandomOrder) pool.shuffled() else pool
    }
}
