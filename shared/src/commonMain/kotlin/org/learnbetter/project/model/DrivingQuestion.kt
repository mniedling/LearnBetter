package org.learnbetter.project.model

enum class DrivingImageType {
    NONE,
    STOP_SIGN,
    YIELD_SIGN,
    PRIORITY_ROAD,
    RIGHT_OF_WAY_CROSSING,
    NO_ENTRY,
    SPEED_45
}

data class DrivingQuestion(
    val id: Int,
    val testbogenNumber: Int,
    val questionText: String,
    val introduction: String? = null,
    val options: List<String>,
    val correctOptionIndices: Set<Int>,
    val errorPoints: Int,
    val explanation: String,
    val imageType: DrivingImageType = DrivingImageType.NONE,
    val imageUrl: String? = null,
    val localImageName: String? = null
)

object DrivingSchoolData {
    val allQuestions = listOf(
        DrivingQuestion(
            id = 1,
            testbogenNumber = 1,
            questionText = "Worauf müssen Sie sich hier einstellen?",
            introduction = null,
            options = listOf("Fahrzeuge biegen auf die Fahrbahn ein", "Entgegenkommende Fahrzeuge biegen nach links ab", "Personen betreten die Fahrbahn"),
            correctOptionIndices = setOf(0, 1, 2),
            errorPoints = 4,
            explanation = "Fahrzeuge und Abbieger können unvermittelt Ihren Weg kreuzen.",
            imageUrl = "https://video4.kalaiwa.de/assets/1107159m.webp",
            localImageName = "kalaiwa_1107159m.webp"
        ),
        DrivingQuestion(
            id = 2,
            testbogenNumber = 1,
            questionText = "Warum müssen Sie jetzt anhalten?",
            introduction = null,
            options = listOf("Wegen des Lkws", "Wegen der Fußgänger", "Wegen der Radfahrerin"),
            correctOptionIndices = setOf(0),
            errorPoints = 4,
            explanation = "Beim Anhalten wegen vorausfahrender Lkw oder Fußgänger muss ausreichend Sicherheitsabstand gehalten werden.",
            imageUrl = "https://video4.kalaiwa.de/assets/1107171a.webp",
            localImageName = "kalaiwa_1107171a.webp"
        ),
        DrivingQuestion(
            id = 3,
            testbogenNumber = 1,
            questionText = "Wodurch lässt sich die Fahrtüchtigkeit nach übermäßigem Alkoholkonsum kurzfristig wiederherstellen?",
            introduction = null,
            options = listOf("Durch 15 Minuten Sport", "Durch das Trinken von 2 Tassen Kaffee", "Durch gar nichts"),
            correctOptionIndices = setOf(2),
            errorPoints = 4,
            explanation = "Alkohol wird im Körper nur langsam durch die Leber abgebaut (ca. 0,1 Promille pro Stunde). Kaffee oder Sport beschleunigen den Abbau nicht.",
            imageUrl = null,
            localImageName = null
        ),
        DrivingQuestion(
            id = 4,
            testbogenNumber = 1,
            questionText = "Sie sollen ein Ihnen unbekanntes Kraftfahrzeug fahren. Warum müssen Sie dabei zunächst besonders vorsichtig sein?",
            introduction = "Ich weiß möglicherweise zu wenig über die Besonderheiten",
            options = listOf("- im Lenkverhalten", "- im Bremsverhalten", "- in der Anordnung und der Funktion der Bedienelemente"),
            correctOptionIndices = setOf(0, 1, 2),
            errorPoints = 3,
            explanation = "Vor der Fahrt mit einem fremden Fahrzeug muss man sich zwingend mit Schaltern, Bremsen und Fahrverhalten vertraut machen.",
            imageUrl = null,
            localImageName = null
        ),
        DrivingQuestion(
            id = 5,
            testbogenNumber = 1,
            questionText = "Worauf müssen Sie jetzt achten?",
            introduction = null,
            options = listOf("Auf den Radfahrer links", "Auf den grünen Pkw", "Auf die Radfahrerin rechts"),
            correctOptionIndices = setOf(0, 1, 2),
            errorPoints = 4,
            explanation = "Achten Sie stets auf schwächere Verkehrsteilnehmer (Radfahrer, Fußgänger) und vorausfahrende Pkw.",
            imageUrl = "https://video4.kalaiwa.de/assets/1107155ma.webp",
            localImageName = "kalaiwa_1107155ma.webp"
        ),
        DrivingQuestion(
            id = 6,
            testbogenNumber = 1,
            questionText = "Wie verhalten Sie sich in dieser Situation?",
            introduction = null,
            options = listOf("Ich lasse den Fußgänger die Fahrbahn überqueren", "Ich schließe unmittelbar zum blauen Fahrzeug auf", "Ich warte vor dem Fußgängerüberweg, bis das blaue Fahrzeug abgebogen ist"),
            correctOptionIndices = setOf(0, 2),
            errorPoints = 5,
            explanation = "Vor Fußgängerüberwegen darf nicht überholt oder gedrängelt werden. Warten Sie geduldig.",
            imageUrl = "https://video4.kalaiwa.de/assets/1226114.webp",
            localImageName = "kalaiwa_1226114.webp"
        ),
        DrivingQuestion(
            id = 7,
            testbogenNumber = 1,
            questionText = "Warum ist eine defensive Fahrweise beim Annähern an Fußgängerüberwege besonders wichtig?",
            introduction = null,
            options = listOf("Damit Fußgänger nicht verunsichert werden", "Damit nachfolgende Fahrzeuge leichter überholen können", "Damit Auffahrunfälle vermieden werden"),
            correctOptionIndices = setOf(0, 2),
            errorPoints = 4,
            explanation = "Defensives Fahren an Zebrastreifen schützt Fußgänger und verhindert gefährliche Auffahrunfälle.",
            imageUrl = null,
            localImageName = null
        ),
        DrivingQuestion(
            id = 8,
            testbogenNumber = 1,
            questionText = "Vor Ihnen befindet sich ein Fußgängerüberweg, hinter dem der Verkehr auf Ihrem Fahrstreifen stockt. Wie verhalten Sie sich?",
            introduction = null,
            options = listOf("Vor dem Fußgängerüberweg warten, um Personen das Überqueren zu ermöglichen", "Auf dem Fußgängerüberweg warten, um den Fahrzeugrückstau zu verkürzen", "Auf dem Fußgängerüberweg warten, wenn keine Personen zu sehen sind"),
            correctOptionIndices = setOf(0),
            errorPoints = 4,
            explanation = "Fußgängerüberwege müssen immer freigehalten werden. Bei Stau davor anhalten!",
            imageUrl = null,
            localImageName = null
        ),
        DrivingQuestion(
            id = 9,
            testbogenNumber = 1,
            questionText = "Sie bemerken im Tunnel einen Unfall. Was ist zu tun?",
            introduction = null,
            options = listOf("Notdienste über Notrufstation informieren", "Warnblinklicht einschalten", "Fahrzeug nicht verlassen"),
            correctOptionIndices = setOf(0, 1),
            errorPoints = 4,
            explanation = "Bei Unfällen im Tunnel sofort Warnblinklicht einschalten und per Notrufsäule Hilfe rufen.",
            imageUrl = null,
            localImageName = null
        ),
        DrivingQuestion(
            id = 10,
            testbogenNumber = 1,
            questionText = "Ein Polizeifahrzeug überholt Sie und schert unmittelbar vor Ihnen ein. Auf dem Dach erscheint in roter Leuchtschrift „BITTE FOLGEN“. Wie müssen Sie sich verhalten?",
            introduction = "Ich muss...",
            options = listOf("- anhalten, wenn das Polizeifahrzeug anhält", "- dem Polizeifahrzeug folgen", "- nach dem Anhalten den Motor laufen lassen"),
            correctOptionIndices = setOf(0, 1),
            errorPoints = 3,
            explanation = "Signale von Polizeifahrzeugen sind zwingend zu befolgen.",
            imageUrl = null,
            localImageName = null
        ),
        DrivingQuestion(
            id = 11,
            testbogenNumber = 1,
            questionText = "Aus einem unmittelbar vor Ihnen fahrenden Fahrzeug wird Ihnen mit der „Polizeikelle“ Zeichen gegeben. Was bedeutet dies?",
            introduction = null,
            options = listOf("Ich muss dem Fahrzeug folgen, bis es anhält", "Ich muss in die gezeigte Richtung abbiegen", "Ich darf das Fahrzeug nicht überholen"),
            correctOptionIndices = setOf(1),
            errorPoints = 3,
            explanation = "Anhaltesignale der Polizei verpflichten zum Folgen oder Abbiegen.",
            imageUrl = null,
            localImageName = null
        ),
        DrivingQuestion(
            id = 12,
            testbogenNumber = 1,
            questionText = "Was kann es bedeuten, wenn an einem Fahrzeug blaues Blinklicht - jedoch kein Einsatzhorn - eingeschaltet ist?",
            introduction = null,
            options = listOf("An einer Unfallstelle wird vor Gefahren gewarnt", "Ein Abschleppwagen fährt zu einem Einsatz", "Es wird vor einer Einsatzstelle der Feuerwehr gewarnt"),
            correctOptionIndices = setOf(0, 2),
            errorPoints = 3,
            explanation = "Blaues Blinklicht zeigt Einsätze von Rettungskräften oder Gefahrenstellen an.",
            imageUrl = null,
            localImageName = null
        ),
        DrivingQuestion(
            id = 13,
            testbogenNumber = 1,
            questionText = "Wie verhalten Sie sich in dieser Situation richtig?",
            introduction = null,
            options = listOf("Ich wechsle in etwa 200 m auf den rechten Fahrstreifen", "Ich wechsle jetzt auf den rechten Fahrstreifen", "Ich darf die auf dem rechten Fahrstreifen fahrenden Fahrzeuge überholen"),
            correctOptionIndices = setOf(0),
            errorPoints = 4,
            explanation = "Spurwechsel rechtzeitig und ohne Gefährdung anderer durchführen.",
            imageUrl = "https://video4.kalaiwa.de/assets/1207105ma.webp",
            localImageName = "kalaiwa_1207105ma.webp"
        ),
        DrivingQuestion(
            id = 14,
            testbogenNumber = 1,
            questionText = "Zwei Fahrzeuge kommen sich auf einer Kreuzung entgegen. Beide Fahrzeuge möchten jeweils nach links abbiegen. In welchem Ausnahmefall müssen sie hintereinander abbiegen?",
            introduction = null,
            options = listOf("Wenn die Verkehrslage es erfordert", "Wenn eine gleichrangige Kreuzung befahren wird", "Wenn die Gestaltung der Kreuzung es erfordert"),
            correctOptionIndices = setOf(0, 2),
            errorPoints = 3,
            explanation = "Voreinander abbiegen (amerikanisches Abbiegen) ist Pflicht, außer die Kreuzungsform erfordert ein Umeinanderfahren.",
            imageUrl = null,
            localImageName = null
        ),
        DrivingQuestion(
            id = 15,
            testbogenNumber = 1,
            questionText = "Sie möchten nach links abbiegen. Wem müssen Sie Vorrang gewähren?",
            introduction = null,
            options = listOf("Fußgängern, welche die Straße überqueren wollen, in die ich einbiege", "Entgegenkommenden Kraftfahrzeugen", "Entgegenkommenden Radfahrern"),
            correctOptionIndices = setOf(0, 1, 2),
            errorPoints = 4,
            explanation = "Beim Linksabbiegen haben entgegenkommende Fahrzeuge und Fußgänger Vorrang.",
            imageUrl = null,
            localImageName = null
        ),
        DrivingQuestion(
            id = 16,
            testbogenNumber = 1,
            questionText = "Sie möchten mit Ihrem Fahrzeug aus einer Parklücke in den fließenden Verkehr einfahren. Wie müssen Sie sich verhalten?",
            introduction = null,
            options = listOf("Ich muss den rückwärtigen Verkehr beobachten", "Ich muss vor dem Anfahren blinken", "Ich darf den fließenden Verkehr nicht gefährden"),
            correctOptionIndices = setOf(0, 1, 2),
            errorPoints = 2,
            explanation = "Beim Ausparken gilt höchste Sorgfalt, Blinken und Vorrang des fließenden Verkehrs.",
            imageUrl = null,
            localImageName = null
        ),
        DrivingQuestion(
            id = 17,
            testbogenNumber = 1,
            questionText = "Wie kann verhindert werden, dass der Gegenverkehr mit dem Abblendlicht geblendet wird?",
            introduction = null,
            options = listOf("Nach jeder Reparatur am Scheinwerfer sollte eine Überprüfung der Einstellung erfolgen", "Die Leuchtweite ist in Abhängigkeit vom Beladungszustand des Fahrzeugs zu regulieren", "Es dürfen nur für den jeweiligen Scheinwerfer zulässige Leuchtmittel verwendet werden"),
            correctOptionIndices = setOf(0, 1, 2),
            errorPoints = 3,
            explanation = "Falsch eingestellte oder unzulässige Scheinwerfer blenden den Gegenverkehr lebensgefährlich.",
            imageUrl = null,
            localImageName = null
        ),
        DrivingQuestion(
            id = 18,
            testbogenNumber = 1,
            questionText = "Wie verhalten Sie sich in dieser Situation richtig?",
            introduction = null,
            options = listOf("Ich überhole die Straßenbahn links", "Ich bleibe hinter der Straßenbahn", "Ich fahre rechts an der Straßenbahn vorbei"),
            correctOptionIndices = setOf(1, 2),
            errorPoints = 3,
            explanation = "Straßenbahnen werden grundsätzlich rechts überholt.",
            imageUrl = "https://video4.kalaiwa.de/assets/1220107ma.webp",
            localImageName = "kalaiwa_1220107ma.webp"
        ),
        DrivingQuestion(
            id = 19,
            testbogenNumber = 1,
            questionText = "Sie möchten aus der Tankstelle ausfahren. Was ist richtig?",
            introduction = null,
            options = listOf("Ich habe Vorfahrt vor von links kommenden Fahrzeugen", "Ich muss querenden Fußgängern Vorrang gewähren", "Ich darf in beide Richtungen ausfahren"),
            correctOptionIndices = setOf(1),
            errorPoints = 3,
            explanation = "Beim Ausfahren aus Grundstücken oder Tankstellen haben Fußgänger und der fließende Verkehr Vorrang.",
            imageUrl = "https://video4.kalaiwa.de/assets/1210006ma.webp",
            localImageName = "kalaiwa_1210006ma.webp"
        ),
        DrivingQuestion(
            id = 20,
            testbogenNumber = 1,
            questionText = "Welches Verhalten ist richtig?",
            introduction = null,
            options = listOf("Ich muss dem blauen Pkw Vorfahrt gewähren", "Ich muss dem roten Pkw Vorfahrt gewähren", "Ich habe Vorfahrt vor dem blauen Pkw"),
            correctOptionIndices = setOf(0, 1),
            errorPoints = 5,
            explanation = "Auf Vorfahrtsstraßen und Vorfahrtsregeln an Kreuzungen achten.",
            imageUrl = "https://video4.kalaiwa.de/assets/1301125m.webp",
            localImageName = "kalaiwa_1301125m.webp"
        ),
        DrivingQuestion(
            id = 21,
            testbogenNumber = 1,
            questionText = "Wer darf in dieser Situation zuerst fahren?",
            introduction = null,
            options = listOf("Ich selbst", "Der blaue Traktor", "Der rote Pkw"),
            correctOptionIndices = setOf(0),
            errorPoints = 5,
            explanation = "Die Vorfahrtsregel (z.B. Rechts vor Links) bestimmt die Reihenfolge.",
            imageUrl = "https://video4.kalaiwa.de/assets/1301128m.webp",
            localImageName = "kalaiwa_1301128m.webp"
        ),
        DrivingQuestion(
            id = 22,
            testbogenNumber = 1,
            questionText = "Wie verhalten Sie sich bei diesem Verkehrszeichen?",
            introduction = null,
            options = listOf("Geschwindigkeit vermindern", "Gleich nach der Rechtskurve kräftig beschleunigen", "Möglichst weit rechts fahren"),
            correctOptionIndices = setOf(0, 2),
            errorPoints = 3,
            explanation = "In Kurven Geschwindigkeit verringern und stramm rechts fahren.",
            imageUrl = "https://video4.kalaiwa.de/assets/1440105.webp",
            localImageName = "kalaiwa_1440105.webp"
        ),
        DrivingQuestion(
            id = 23,
            testbogenNumber = 1,
            questionText = "Womit müssen Sie bei diesem Verkehrszeichen rechnen?",
            introduction = null,
            options = listOf("Mit Verkehr von Baustellenfahrzeugen", "Mit Baumaterial auf der Fahrbahn", "Mit Arbeitern auf der Fahrbahn"),
            correctOptionIndices = setOf(0, 1, 2),
            errorPoints = 2,
            explanation = "Das Schild Baustelle warnt vor Arbeitern, Baumaterial und Arbeitsfahrzeugen.",
            imageUrl = "https://video4.kalaiwa.de/assets/1440116.webp",
            localImageName = "kalaiwa_1440116.webp"
        ),
        DrivingQuestion(
            id = 24,
            testbogenNumber = 1,
            questionText = "Womit müssen Sie bei diesem Verkehrszeichen rechnen?",
            introduction = null,
            options = listOf("Mit plötzlichem Bremsen der Vorausfahrenden", "Mit Erweiterung der Fahrbahn auf drei Fahrstreifen", "Mit einem Stau"),
            correctOptionIndices = setOf(0, 2),
            errorPoints = 3,
            explanation = "Bei Stauwarnung sofort gefühlvoll abbremsen und Warnblinker nutzen.",
            imageUrl = "https://video4.kalaiwa.de/assets/1440118.webp",
            localImageName = "kalaiwa_1440118.webp"
        ),
        DrivingQuestion(
            id = 25,
            testbogenNumber = 1,
            questionText = "Was endet hier?",
            introduction = null,
            options = listOf("Eine Zone mit absolutem Haltverbot", "Vorausgegangene Streckenverbote", "Eine Zone mit eingeschränktem Haltverbot"),
            correctOptionIndices = setOf(1),
            errorPoints = 2,
            explanation = "Das Aufhebungszeichen beendet alle vorherigen Streckenverbote.",
            imageUrl = "https://video4.kalaiwa.de/assets/1441136.webp",
            localImageName = "kalaiwa_1441136.webp"
        ),
        DrivingQuestion(
            id = 26,
            testbogenNumber = 1,
            questionText = "Was müssen Sie in Verkehrssituationen wie dieser beachten?",
            introduction = "Eine Fahrstreifenbegrenzung (durchgehende Linie) darf man...",
            options = listOf("- überfahren, um zu überholen", "- nicht überfahren, wenn dadurch der Verkehr gefährdet wird", "- überfahren, um an Hindernissen vorbeizufahren"),
            correctOptionIndices = setOf(2),
            errorPoints = 3,
            explanation = "Durchgehende Linien (Fahrstreifenbegrenzung) dürfen nicht überfahren werden.",
            imageUrl = "https://video4.kalaiwa.de/assets/1442145.webp",
            localImageName = "kalaiwa_1442145.webp"
        ),
        DrivingQuestion(
            id = 27,
            testbogenNumber = 1,
            questionText = "Worauf weist dieses Verkehrszeichen hin?",
            introduction = "In diese Straße darf man...",
            options = listOf("- mit Schrittgeschwindigkeit befahren", "- als Anlieger befahren", "- nicht befahren"),
            correctOptionIndices = setOf(1),
            errorPoints = 3,
            explanation = "Zusatzschilder beachten.",
            imageUrl = "https://video4.kalaiwa.de/assets/1441029.webp",
            localImageName = "kalaiwa_1441029.webp"
        ),
        DrivingQuestion(
            id = 28,
            testbogenNumber = 1,
            questionText = "Sie fahren mit ungefähr 40 km/h. Etwa 40 m vor Ihnen wechselt die Ampel von „Grün“ auf „Gelb“. Wie verhalten Sie sich?",
            introduction = null,
            options = listOf("Anhalten", "Weiterfahren"),
            correctOptionIndices = setOf(0),
            errorPoints = 4,
            explanation = "Bei Gelb ist vor der Kreuzung anzuhalten, wenn gefahrlos möglich.",
            imageUrl = null,
            localImageName = null
        ),
        DrivingQuestion(
            id = 29,
            testbogenNumber = 1,
            questionText = "Was gehört zur geeigneten Schutzkleidung für Kraftradfahrer?",
            introduction = null,
            options = listOf("Rückenprotektor", "Eng anliegende Motorradjacke", "Halbschuhe"),
            correctOptionIndices = setOf(0, 1),
            errorPoints = 2,
            explanation = "Motorradjacke und Rückenprotektoren schützen vor schweren Verletzungen.",
            imageUrl = null,
            localImageName = null
        ),
        DrivingQuestion(
            id = 30,
            testbogenNumber = 1,
            questionText = "In welchen Fällen darf man nur sehr gefühlvoll bremsen?",
            introduction = null,
            options = listOf("Auf Straßenbahnschienen, Kanaldeckeln und Fahrbahnmarkierungen", "Auf Fahrbahnen mit Rollsplitt oder Streusand", "Bei der ersten Fahrt mit neuen Reifen"),
            correctOptionIndices = setOf(0, 1, 2),
            errorPoints = 3,
            explanation = "Auf rutschigem Untergrund droht Rutschgefahr beim harten Bremsen.",
            imageUrl = null,
            localImageName = null
        ),
    )

    fun getQuestionsForBogen(bogenNumber: Int, isRandomOrder: Boolean): List<DrivingQuestion> {
        val pool = if (bogenNumber == 0) allQuestions else allQuestions.filter { it.testbogenNumber == bogenNumber }
        return if (isRandomOrder) pool.shuffled() else pool
    }
}
