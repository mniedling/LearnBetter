package org.learnbetter.project.model

enum class PoliceServiceGroup(val displayName: String, val starColor: String) {
    MITTLERER_DIENST("Mittlerer Dienst", "BLUE"),
    GEHOBENER_DIENST("Gehobener Dienst", "SILVER"),
    HOEHERER_DIENST("Höherer Dienst", "GOLD")
}

data class PoliceRank(
    val id: String,
    val name: String,
    val serviceGroup: PoliceServiceGroup,
    val starsCount: Int,
    val level: Int,
    val description: String
)

data class MemoryProfile(
    val id: Int,
    val title: String,
    val vehicleModel: String,
    val vehicleColor: String,
    val licensePlate: String,
    val escapeDirection: String,
    val suspectDescription: String
)

data class PoliceQuestion(
    val id: Int,
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String,
    val category: String
)

data class PoliceScenario(
    val id: Int,
    val title: String,
    val situationText: String,
    val options: List<String>,
    val correctOptionIndex: Int,
    val legalBasis: String,
    val explanation: String
)

data class PoliceEquipment(
    val id: String,
    val name: String,
    val icon: String,
    val category: String,
    val description: String,
    val safetyRule: String
)

data class DictationTask(
    val id: Int,
    val reportTitle: String,
    val sentenceParts: List<String>,
    val gapOptions: List<List<String>>,
    val correctGapIndices: List<Int>,
    val grammarRule: String
)

object PoliceData {
    val ranks = listOf(
        // Mittlerer Dienst (Blaue Sterne)
        PoliceRank("pm", "Polizeimeister", PoliceServiceGroup.MITTLERER_DIENST, 1, 1, "Eingangsamt im Mittleren Dienst (1 blauer Stern)"),
        PoliceRank("pom", "Polizeiobermeister", PoliceServiceGroup.MITTLERER_DIENST, 2, 2, "2 blaue Sterne"),
        PoliceRank("phm", "Polizeihauptmeister", PoliceServiceGroup.MITTLERER_DIENST, 3, 3, "3 blaue Sterne"),
        PoliceRank("phm_a", "Polizeihauptmeister mit Amtszulage", PoliceServiceGroup.MITTLERER_DIENST, 4, 4, "Spitzenamt Mittlerer Dienst (4 blaue Sterne)"),

        // Gehobener Dienst (Silberne Sterne)
        PoliceRank("pk", "Polizeikommissar", PoliceServiceGroup.GEHOBENER_DIENST, 1, 5, "Eingangsamt Gehobener Dienst (1 silberner Stern)"),
        PoliceRank("pok", "Polizeioberkommissar", PoliceServiceGroup.GEHOBENER_DIENST, 2, 6, "2 silberne Sterne"),
        PoliceRank("phk", "Polizeihauptkommissar", PoliceServiceGroup.GEHOBENER_DIENST, 3, 7, "3 silberne Sterne"),
        PoliceRank("ephk", "Erster Polizeihauptkommissar", PoliceServiceGroup.GEHOBENER_DIENST, 5, 8, "Spitzenamt Gehobener Dienst (5 silberne Sterne)"),

        // Höherer Dienst (Goldene Sterne)
        PoliceRank("pr", "Polizeirat", PoliceServiceGroup.HOEHERER_DIENST, 1, 9, "Eingangsamt Höherer Dienst (1 goldener Stern)"),
        PoliceRank("pd", "Polizeidirektor", PoliceServiceGroup.HOEHERER_DIENST, 3, 10, "3 goldene Sterne")
    )

    val memoryProfiles = listOf(
        MemoryProfile(
            id = 1,
            title = "Fahndung A4 - Einbruchsdiebstahl",
            vehicleModel = "Audi A4 Kombi",
            vehicleColor = "Silber-Grau",
            licensePlate = "B-BP 402",
            escapeDirection = "Autobahn A1 Richtung Hamburg",
            suspectDescription = "Männlich, ca. 1,85m, schwarze Lederjacke, blaue Jeans, auffallende Narbe an der linken Wangenseite."
        ),
        MemoryProfile(
            id = 2,
            title = "Fahndung Luftsicherheit - Gepäckdiebstahl",
            vehicleModel = "VW Transporter T6",
            vehicleColor = "Dunkelblau",
            licensePlate = "FRA-BP 882",
            escapeDirection = "Flughafenzubringer Terminal 1",
            suspectDescription = "Weiblich, ca. 1,70m, rotes Haar zum Zopf gebunden, grauer Hoodie, schwarze Rucksacktasche."
        ),
        MemoryProfile(
            id = 3,
            title = "Fahndung Bahnpolizei - Sachbeschädigung",
            vehicleModel = "BMW 3er Limousine",
            vehicleColor = "Schwarz",
            licensePlate = "M-BP 119",
            escapeDirection = "Hauptbahnhof Vorplatz Ost",
            suspectDescription = "Zwei männliche Personen, ca. 20 Jahre alt, schwarze Caps, neon-grüne Turnschuhe."
        )
    )

    val scenarios = listOf(
        PoliceScenario(
            id = 1,
            title = "Luftsicherheit - Herrenloses Gepäckstück",
            situationText = "Am Flughafen Terminal 2 steht seit 20 Minuten ein unbeaufsichtigter Koffer neben der Bank. Wie verhalten Sie sich rechtlich und taktisch richtig?",
            options = listOf(
                "Den Koffer sofort selbst öffnen, um nach Namensschildern zu suchen",
                "Den Bereich sofort weiträumig absperren, Passanten auffordern den Bereich zu verlassen und den Entschärfungsdienst verständigen",
                "Den Koffer zum Fundbüro am Ausgang tragen"
            ),
            correctOptionIndex = 1,
            legalBasis = "Gefahrenabwehr & EOD-Sicherheitskonzept BPol",
            explanation = "Unbeaufsichtigtes Gepäck an Flughäfen birgt Sprengstoffgefahr. Erst sichern & absperren, niemals selbst anfassen!"
        ),
        PoliceScenario(
            id = 2,
            title = "Bahnpolizei - Fahrgast ohne Ticket",
            situationText = "Im ICE weigert sich ein Fahrgast, dem Zugbegleiter seinen Ausweis zu zeigen und droht mit Fäusten. Wie reagieren Sie als Streife?",
            options = listOf(
                "Sofort Pfefferspray einsetzen",
                "Deeskalierend ansprechen, Sicherheitsabstand halten und Verhältnismäßigkeit zur Identitätsfeststellung wahren",
                "Den Fahrgast sofort zu Boden ringen"
            ),
            correctOptionIndex = 1,
            legalBasis = "§ 23 BPolG - Identitätsfeststellung",
            explanation = "Zuerst kommunikative Deeskalation! Zwangsmittel dürfen nur als letztes Mittel verhältnismäßig eingesetzt werden."
        ),
        PoliceScenario(
            id = 3,
            title = "Grenzschutz - Einreisekontrolle",
            situationText = "Bei der Grenzkontrolle legt ein Fahrer einen Reisepass vor, bei dem das Lichtbild sichtbar manipulierte Ränder aufweist. Welche Maßnahme ergreifen Sie?",
            options = listOf(
                "Den Fahrer unverzüglich weiterfahren lassen",
                "Pass sicherstellen, die Person zur Dienststelle begleiten und Urkundenfälschung prüfen",
                "Den Pass sofort zerreißen"
            ),
            correctOptionIndex = 1,
            legalBasis = "§ 267 StGB - Urkundenfälschung & § 43 BPolG",
            explanation = "Verdacht auf Urkundenfälschung rechtfertigt die Sicherstellung des Dokuments und die Einleitung von Ermittlungen."
        )
    )

    val equipment = listOf(
        PoliceEquipment(
            id = "p30",
            name = "Dienstpistole HK P30",
            icon = "🔫",
            category = "Schusswaffe",
            description = "Halbautomatische Dienstpistole im Kaliber 9 x 19 mm mit Polizeisicherung.",
            safetyRule = "Mündung stets in eine sichere Richtung halten! Der Finger liegt am Abzugsbügel, bis die Zielaufnahme erfolgt ist."
        ),
        PoliceEquipment(
            id = "rsg4",
            name = "Pfefferspray RSG 4",
            icon = "🧪",
            category = "Reizstoffgerät",
            description = "Reizstoffsprühgerät mit Flüssigstrahl zur Abwehr von Angreifern.",
            safetyRule = "Windrichtung beachten! Strahl gezielt auf Augen/Gesicht des Angreifers ausrichten."
        ),
        PoliceEquipment(
            id = "eka",
            name = "Einsatzstock EKA",
            icon = "🦯",
            category = "Schlagstock",
            description = "Einsatzstock kurz, ausziehbar (Teleskopschlagstock) zur Abwehr von Angriffen.",
            safetyRule = "Trefferzonen beachten: Schläge gegen den Kopf, Hals oder Gelenke vermeiden (Verhältnismäßigkeit!)."
        ),
        PoliceEquipment(
            id = "weste",
            name = "Ballistische Schutzweste",
            icon = "🛡️",
            category = "Schutzausrüstung",
            description = "Unterziehweste zum Schutz vor Stich- und Schussverletzungen.",
            safetyRule = "Vor jedem Dienstantritt auf korrekten Sitz und Unversehrtheit des Keramik/Kevlar-Inlays prüfen."
        )
    )

    val dictationTasks = listOf(
        DictationTask(
            id = 1,
            reportTitle = "Einsatzbericht: Personenfeststellung am Hauptbahnhof",
            sentenceParts = listOf("Die Streife wurde alarmiert, ", " eine tatverdächtige Person flüchtig war. Die Beamtin stellte fest, ", " der Ausweis gefälscht war."),
            gapOptions = listOf(
                listOf("weil", "dass", "das"),
                listOf("dass", "das", "daß")
            ),
            correctGapIndices = listOf(0, 0),
            grammarRule = "'dass' steht als Bindewort nach Komma, wenn es nicht durch 'welches' ersetzt werden kann."
        ),
        DictationTask(
            id = 2,
            reportTitle = "Einsatzbericht: Sicherstellung am Flughafen",
            sentenceParts = listOf("Der Beamte veranlasste die umgehende ", " des herrenlosen Koffers zur ", " der Gefahr."),
            gapOptions = listOf(
                listOf("Sicherstellung", "sicherstellung", "Sicher stellung"),
                listOf("Abwehr", "abwehr", "Abwer")
            ),
            correctGapIndices = listOf(0, 0),
            grammarRule = "Nomen und Substantivierungen schreibt man im Deutschen stets groß."
        )
    )

    val quizQuestions = listOf(
        PoliceQuestion(
            id = 1,
            questionText = "Welche der folgenden Aufgaben gehört NICHT zum Kernauftrag der Bundespolizei (BPol)?",
            options = listOf("Grenzschutz an den Bundesgrenzen", "Bahnpolizeiliche Aufgaben der Eisenbahnen des Bundes", "Luftsicherheit an Verkehrsflughäfen", "Bearbeitung örtlicher Nachbarschaftsstreitigkeiten"),
            correctAnswerIndex = 3,
            explanation = "Örtliche Nachbarschaftsstreitigkeiten fallen in die Zuständigkeit der Landespolizei der jeweiligen Bundesländer.",
            category = "BPol-Wissen"
        ),
        PoliceQuestion(
            id = 2,
            questionText = "Ein Einsatzfahrzeug legt bei einer Verfolgungsfahrt eine Strecke von 15 km in genau 6 Minuten zurück. Wie hoch war die Durchschnittsgeschwindigkeit in km/h?",
            options = listOf("120 km/h", "150 km/h", "180 km/h", "200 km/h"),
            correctAnswerIndex = 1,
            explanation = "6 Minuten entsprechen 0,1 Stunden. v = s / t = 15 km ÷ 0,1 h = 150 km/h.",
            category = "Mathematik & Weg-Zeit"
        ),
        PoliceQuestion(
            id = 3,
            questionText = "Welches Wort ist im Rechtschreibtest der Bundespolizei richtig geschrieben?",
            options = listOf("Gefahrenabwehr", "Gefarenabwehr", "Gefahrnabwehr", "Gefahrenabwer"),
            correctAnswerIndex = 0,
            explanation = "Das Wort setzt sich aus 'Gefahren' und 'Abwehr' zusammen und wird mit 'h' in der Mitte geschrieben.",
            category = "Rechtschreibung & Diktat"
        ),
        PoliceQuestion(
            id = 4,
            questionText = "Wie nennt man die weltbekannte Spezialeinheit der Bundespolizei zur Bekämpfung von Schwerstkriminalität und Terrorismus?",
            options = listOf("SEK", "MEK", "GSG 9", "KSM"),
            correctAnswerIndex = 2,
            explanation = "Die GSG 9 der Bundespolizei ist die Präzisions- und Spezialeinheit des Bundes.",
            category = "BPol-Wissen"
        ),
        PoliceQuestion(
            id = 5,
            questionText = "Setzen Sie die Zahlenreihe logisch fort: 4, 9, 16, 25, 36, ...?",
            options = listOf("42", "49", "54", "64"),
            correctAnswerIndex = 1,
            explanation = "Es handelt sich um die Quadratzahlen: 2², 3², 4², 5², 6², 7² = 49.",
            category = "Logik"
        ),
        PoliceQuestion(
            id = 6,
            questionText = "Welche Farbe haben die Sterne auf den Schulterklappen des 'Gehobenen Dienstes' bei der Bundespolizei?",
            options = listOf("Blaue Sterne", "Silberne Sterne", "Goldene Sterne", "Schwarze Sterne"),
            correctAnswerIndex = 1,
            explanation = "Der Mittlere Dienst trägt blaue Sterne, der Gehobene Dienst silberne Sterne und der Höhere Dienst goldene Sterne.",
            category = "BPol-Wissen"
        ),
        PoliceQuestion(
            id = 7,
            questionText = "Wie lautet die korrekte grammatikalische Lücke? 'Die Beamtin versicherte, _____ der Personalausweis vorgelegt wurde.'",
            options = listOf("das", "dass", "daß", "welches"),
            correctAnswerIndex = 1,
            explanation = "'dass' bindet als einleitende Konjunktion den Nebensatz an.",
            category = "Rechtschreibung & Diktat"
        ),
        PoliceQuestion(
            id = 8,
            questionText = "Berechnen Sie ohne Taschenrechner: 15 % von 240 €.",
            options = listOf("24 €", "32 €", "36 €", "40 €"),
            correctAnswerIndex = 2,
            explanation = "10 % von 240 € = 24 €. 5 % von 240 € = 12 €. 24 € + 12 € = 36 €.",
            category = "Mathematik & Weg-Zeit"
        ),
        PoliceQuestion(
            id = 9,
            questionText = "Ergänzen Sie die Wortanalogie: Handschellen verhalten sich zu Handgelenk wie Gürtel zu ...?",
            options = listOf("Kopf", "Taillenumfang / Hüfte", "Fußknöchel", "Schulter"),
            correctAnswerIndex = 1,
            explanation = "Handschellen umschließen das Handgelenk, ein Gürtel liegt an Hüfte/Taille an.",
            category = "Logik"
        ),
        PoliceQuestion(
            id = 10,
            questionText = "Welche Mindestweite müssen MÄNNLICHE Bewerber beim Standweitsprung im Bundespolizei-Sporttest erreichen?",
            options = listOf("1,50 m", "1,85 m", "2,05 m", "2,50 m"),
            correctAnswerIndex = 2,
            explanation = "Die Mindestanforderung beim Standweitsprung der Bundespolizei beträgt für Männer 2,05 Meter (Frauen 1,65 Meter).",
            category = "BPol-Wissen"
        )
    )
}
