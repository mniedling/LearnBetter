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
            // --- CHEMIE (1-50) ---
            QuizQuestion(1, "chemistry", "Wie lautet die chemische Summenformel für Salzsäure?", listOf("HNO₃", "HCl", "H₂SO₄", "H₃PO₄"), 1, "Salzsäure ist die wässrige Lösung von Chlorwasserstoffgas (HCl)."),
            QuizQuestion(2, "chemistry", "Wie lautet die Summenformel für Schwefelsäure?", listOf("H₂SO₄", "H₂SO₃", "HNO₃", "H₂CO₃"), 0, "Schwefelsäure hat die Summenformel H₂SO₄."),
            QuizQuestion(3, "chemistry", "Wie lautet die Summenformel für Phosphorsäure?", listOf("H₃PO₄", "H₂SO₄", "HNO₃", "HCl"), 0, "Phosphorsäure hat die Summenformel H₃PO₄."),
            QuizQuestion(4, "chemistry", "Wie lautet die Summenformel für Salpetersäure?", listOf("HNO₂", "HNO₃", "HCl", "H₂CO₃"), 1, "Salpetersäure hat die Summenformel HNO₃."),
            QuizQuestion(5, "chemistry", "Wie lautet die Summenformel für Kohlensäure?", listOf("H₂CO₃", "CH₃COOH", "H₂SO₄", "HNO₃"), 0, "Kohlensäure hat die Summenformel H₂CO₃."),
            QuizQuestion(6, "chemistry", "Wie lautet die Summenformel für Schweflige Säure?", listOf("H₂SO₄", "H₂SO₃", "H₂S", "SO₂"), 1, "Schweflige Säure hat ein Sauerstoffatom weniger als Schwefelsäure: H₂SO₃."),
            QuizQuestion(7, "chemistry", "Wie lautet die Summenformel für Salpetrige Säure?", listOf("HNO₃", "HNO₂", "NH₃", "HCl"), 1, "Salpetrige Säure hat die Summenformel HNO₂."),
            QuizQuestion(8, "chemistry", "Wie lautet die chemische Formel für Essigsäure?", listOf("CH₃COOH", "HCOOH", "HCl", "H₂CO₃"), 0, "Essigsäure ist eine organische Säure mit der Formel CH₃COOH."),
            QuizQuestion(9, "chemistry", "Welche der folgenden Säuren ist eine dreibasige (dreiprotonige) Säure?", listOf("Salzsäure (HCl)", "Schwefelsäure (H₂SO₄)", "Phosphorsäure (H₃PO₄)", "Salpetersäure (HNO₃)"), 2, "Phosphorsäure (H₃PO₄) kann schrittweise drei Protonen (H⁺) abgeben."),
            QuizQuestion(10, "chemistry", "Welche Säure ist ein Hauptbestandteil menschlicher Magenflüssigkeit?", listOf("Salpetersäure", "Salzsäure", "Schwefelsäure", "Kohlensäure"), 1, "Magensäure besteht aus verdünnter Salzsäure (HCl)."),
            QuizQuestion(11, "chemistry", "Wie heißt das Säurerest-Ion der Phosphorsäure (H₃PO₄)?", listOf("Phosphit-Ion", "Phosphat-Ion", "Phosphid-Ion", "Sulfat-Ion"), 1, "Das Säurerest-Ion der Phosphorsäure ist das Phosphat-Ion (PO₄³⁻)."),
            QuizQuestion(12, "chemistry", "Wie heißt das Säurerest-Ion der Salzsäure (HCl)?", listOf("Chlorat-Ion", "Chlorid-Ion", "Chlorit-Ion", "Sulfid-Ion"), 1, "Das Säurerest-Ion der Salzsäure ist das Chlorid-Ion (Cl⁻)."),
            QuizQuestion(13, "chemistry", "Wie heißt das Säurerest-Ion der Schwefelsäure (H₂SO₄)?", listOf("Sulfit-Ion", "Sulfat-Ion", "Sulfid-Ion", "Nitrat-Ion"), 1, "Das Säurerest-Ion der Schwefelsäure heißt Sulfat-Ion (SO₄²⁻)."),
            QuizQuestion(14, "chemistry", "Wie heißt das Säurerest-Ion der Salpetersäure (HNO₃)?", listOf("Nitrit-Ion", "Nitrat-Ion", "Nitrid-Ion", "Carbonat-Ion"), 1, "Das Säurerest-Ion der Salpetersäure heißt Nitrat-Ion (NO₃⁻)."),
            QuizQuestion(15, "chemistry", "Wie heißt das Säurerest-Ion der Kohlensäure (H₂CO₃)?", listOf("Carbid-Ion", "Carbonat-Ion", "Acetat-Ion", "Hydroxid-Ion"), 1, "Das Säurerest-Ion der Kohlensäure heißt Carbonat-Ion (CO₃²⁻)."),
            QuizQuestion(16, "chemistry", "Wie heißt das Säurerest-Ion der Schwefligen Säure (H₂SO₃)?", listOf("Sulfat-Ion", "Sulfit-Ion", "Sulfid-Ion", "Oxid-Ion"), 1, "Das Säurerest-Ion der Schwefligen Säure heißt Sulfit-Ion (SO₃²⁻)."),
            QuizQuestion(17, "chemistry", "Wie heißt das Säurerest-Ion der Salpetrigen Säure (HNO₂)?", listOf("Nitrat-Ion", "Nitrit-Ion", "Nitrid-Ion", "Ammonium-Ion"), 1, "Das Säurerest-Ion der Salpetrigen Säure heißt Nitrit-Ion (NO₂⁻)."),
            QuizQuestion(18, "chemistry", "Wie heißt das Säurerest-Ion der Essigsäure (CH₃COOH)?", listOf("Formiat-Ion", "Acetat-Ion", "Carbonat-Ion", "Citrat-Ion"), 1, "Das Säurerest-Ion der Essigsäure ist das Acetat-Ion (CH₃COO⁻)."),
            QuizQuestion(19, "chemistry", "Wie lautet die Formel des Phosphat-Ions?", listOf("PO₄³⁻", "PO₃³⁻", "P³⁻", "SO₄²⁻"), 0, "Das Phosphat-Ion hat die Formel PO₄³⁻."),
            QuizQuestion(20, "chemistry", "Wie lautet die Formel des Sulfat-Ions?", listOf("SO₃²⁻", "SO₄²⁻", "S²⁻", "HSO₄⁻"), 1, "Das Sulfat-Ion hat die Formel SO₄²⁻."),
            QuizQuestion(31, "chemistry", "Was passiert bei einer 'Protolyse' nach Brønsted?", listOf("Übertragung von Elektronen", "Übertragung von Protonen (H⁺)", "Spaltung von Atomkernen", "Verdampfen von Wasser"), 1, "Eine Protolyse ist eine Reaktion mit Protonenübergang (H⁺)."),
            QuizQuestion(34, "chemistry", "Welches Ion bildet sich, wenn ein Proton (H⁺) an Wasser (H₂O) gebunden wird?", listOf("Hydroxid-Ion (OH⁻)", "Oxonium-Ion (H₃O⁺)", "Wasserstoffgas", "Sauerstoff"), 1, "Es entsteht das saure Oxonium-Ion (H₃O⁺)."),
            QuizQuestion(43, "chemistry", "Welches Gas entsteht beim Tropfen von Säure auf Marmorfliesen (CaCO₃)?", listOf("Sauerstoff", "Kohlendioxid (CO₂)", "Wasserstoff", "Chlor"), 1, "Säure zersetzt Kalk (CaCO₃) zu Kohlendioxid-Gas (CO₂)."),
            QuizQuestion(44, "chemistry", "Warum soll man Marmorflächen niemals mit Essig- oder Zitronenreiniger putzen?", listOf("Weil die Fliese schmilzt", "Weil die Säure den Kalk auflöst und den Marmor zerstört", "Weil Wasser entsteht", "Weil es stinkt"), 1, "Die Säure zerstört das Calciumcarbonat (Kalk) im Marmor dauerhaft."),
            QuizQuestion(48, "chemistry", "Was passiert bei einer Ionenbindung zwischen Natrium (Na) und Chlor (Cl)?", listOf("Natrium gibt 1 Elektron an Chlor ab", "Chlor gibt 1 Elektron an Natrium ab", "Atome teilen sich Elektronen", "Keine Elektronen wandern"), 0, "Natrium gibt sein Außenelektron an Chlor ab -> Na⁺ und Cl⁻ Ionen bilden ein Salzgitter."),
            QuizQuestion(49, "chemistry", "Was kennzeichnet eine Atombindung bei Molekülen wie Wasser (H₂O)?", listOf("Elektronen werden abgegeben", "Atome teilen sich gemeinsame Elektronenpaare", "Keine Elektronen beteiligt", "Ionen ziehen sich an"), 1, "Nichtmetall-Atome teilen sich Elektronenpaare.")
        ),

        "biology" to listOf(
            // --- BIOLOGIE (10-30) ---
            QuizQuestion(10, "biology", "In welchem Zellorganell der Pflanzenzelle findet die Fotosynthese statt?", listOf("Mitochondrium", "Zellkern", "Chloroplast", "Vakuole"), 2, "Chloroplasten enthalten Chlorophyll und wandeln Sonnenlicht in Glucose um."),
            QuizQuestion(11, "biology", "Welches Gas nehmen Pflanzen bei der Fotosynthese aus der Luft auf?", listOf("Sauerstoff (O₂)", "Kohlendioxid (CO₂)", "Stickstoff (N₂)", "Wasserstoff (H₂)"), 1, "Pflanzen benötigen CO₂ und Wasser zur Fotosynthese."),
            QuizQuestion(12, "biology", "Welches Organell wird als 'Kraftwerk der Zelle' bezeichnet?", listOf("Vakuole", "Mitochondrium", "Ribosom", "Zellwand"), 1, "Mitochondrien erzeugen durch Zellatmung Energie (ATP)."),
            QuizQuestion(13, "biology", "Wo befindet sich die Erbinformation (DNA) der Zelle?", listOf("Im Zellkern", "In der Zellwand", "In der Vakuole", "Im Chloroplast"), 0, "Der Zellkern speichert die DNA im Chromatin."),
            QuizQuestion(14, "biology", "Welches Produkt entsteht bei der Fotosynthese neben Sauerstoff?", listOf("Glucose (Traubenzucker)", "Salzsäure", "Kohlendioxid", "Stärke allein"), 0, "Pflanzen stellen bei der Fotosynthese Glucose (C₆H₁₂O₆) her."),
            QuizQuestion(15, "biology", "Welches Bestandteil besitzen Pflanzenzellen, das Tierzellen NICHT besitzen?", listOf("Zellkern", "Zellwand & Chloroplasten", "Mitochondrien", "Zellmembran"), 1, "Tierzellen besitzen weder eine starre Zellwand noch Chloroplasten."),
            QuizQuestion(16, "biology", "Welche Aufgabe erfüllt die große Vakuole in der Pflanzenzelle?", listOf("Speichert Wasser und erzeugt den Turgordruck", "Betreibt Fotosynthese", "Enthält die DNA", "Erzeugt Energie"), 0, "Die Vakuole saugt Wasser auf wie ein Ballon und erzeugt den Turgordruck."),
            QuizQuestion(17, "biology", "Was versteht man in der Biologie unter 'Plasmolyse'?", listOf("Anschwellen der Zelle", "Ablösen der Zellmembran von der Zellwand durch Wasserentzug", "Teilung des Zellkerns", "Herstellung von Zucker"), 1, "In Salzwasser strömt Wasser aus der Vakuole, die Zelle schrumpft und die Membran löst sich ab."),
            QuizQuestion(18, "biology", "Was wies Joachim Hämmerling mit seinem berühmten Hutalgen-Experiment nach?", listOf("Dass die Zelle Wasser braucht", "Dass der Zellkern im Fuß die Erbinformation für die Form steuert", "Dass Fotosynthese im Licht abläuft", "Dass Chloroplasten grün sind"), 1, "Transplantation des Kerns B führte zur Ausbildung der Hutform B."),
            QuizQuestion(19, "biology", "Welche Aufgabe erfüllen die Kernporen in der doppelten Kernhülle?", listOf("Schutz vor Wasser", "Gezielter Transport von RNA und Proteinen zwischen Kern und Plasma", "Lichtaufnahme", "Säurepufferung"), 1, "Kernporen steuern den Molekülaustausch zwischen Zellkern und Zytoplasma."),
            QuizQuestion(20, "biology", "Aus was besteht das fädige Chromatin im Zellkern?", listOf("Wasser und Salzen", "DNA-Strängen und Eiweißen (Proteinen)", "Zucker und Chlorophyll", "Cellulose"), 1, "Chromatin ist der Komplex aus DNA-Doppelhelix und Histon-Proteinen.")
        ),

        "math" to listOf(
            // --- MATHEMATIK (20-40) ---
            QuizQuestion(20, "math", "Wie verändert sich die Parabel f(x) = a·x², wenn der Faktor 'a' größer als 1 ist?", listOf("Gestaucht (breiter)", "Gestreckt (schmaler)", "Nach unten geöffnet", "Nach rechts verschoben"), 1, "Für |a| > 1 ist die Parabel steiler bzw. gestreckt."),
            QuizQuestion(21, "math", "Wo liegt der Scheitelpunkt der Parabel f(x) = x² + 3?", listOf("S(3|0)", "S(0|3)", "S(-3|0)", "S(0|-3)"), 1, "Das c = +3 verschiebt die Normalparabel um 3 Einheiten nach oben auf der y-Achse."),
            QuizQuestion(22, "math", "In welche Richtung ist die Parabel f(x) = -2x² geöffnet?", listOf("Nach oben", "Nach unten", "Nach links", "Nach rechts"), 1, "Bei einem negativen Vorzeichen (a < 0) ist die Parabel nach unten geöffnet."),
            QuizQuestion(23, "math", "Wie lautet die Formel für die Grundfläche G eines Zylinders mit Radius r?", listOf("G = 2 · π · r", "G = π · r²", "G = a²", "G = 1/3 · π · r²"), 1, "Die Grundfläche eines Zylinders ist ein Kreis mit G = π · r²."),
            QuizQuestion(24, "math", "Wie lautet die Formel für die Mantelfläche M eines Zylinders mit Radius r und Höhe h?", listOf("M = 2π · r · h", "M = π · r² · h", "M = 2π · r²", "M = a · h"), 0, "Die Mantelfläche des Zylinders ist M = 2π · r · h."),
            QuizQuestion(25, "math", "Aus wie vielen Flächen besteht der Mantel M einer quadratischen Pyramide?", listOf("2 Dreiecke", "3 Quadraten", "4 gleichschenkligen Dreiecken", "6 Rechtecken"), 2, "Der Mantel einer quadratischen Pyramide besteht aus 4 deckungsgleichen Dreiecken."),
            QuizQuestion(26, "math", "Wie lautet die Formel für die Grundfläche G einer quadratischen Pyramide mit Kante a?", listOf("G = a²", "G = 4 · a", "G = 1/2 · a · h", "G = π · a²"), 0, "Die Grundfläche einer quadratischen Pyramide ist ein Quadrat mit G = a²."),
            QuizQuestion(27, "math", "Wie berechnet man das Volumen V einer Pyramide?", listOf("V = G · h", "V = 1/3 · G · h", "V = 1/2 · G · h", "V = 2 · G · h"), 1, "Das Volumen einer Pyramide ist genau ein Drittel des Quader-Volumens: V = 1/3 · G · h."),
            QuizQuestion(28, "math", "Wie berechnet man die Seitenhöhe hs einer Pyramide aus Körperhöhe h und Grundkante a?", listOf("hs = h + a", "hs = √(h² + (a/2)²)", "hs = √(h² + a²)", "hs = 2 · h"), 1, "Nach dem Satz des Pythagoras gilt hs = √(h² + (a/2)²)."),
            QuizQuestion(29, "math", "Wie lautet die Formel für das Volumen V eines Zylinders?", listOf("V = π · r² · h", "V = 2π · r · h", "V = 4/3 · π · r³", "V = r · h"), 0, "Das Zylindervolumen berechnet sich als V = G · h = π · r² · h.")
        ),

        "physics" to listOf(
            // --- PHYSIK (60-75) ---
            QuizQuestion(60, "physics", "Wie lautet die Formel des Hookeschen Gesetzes für die Federkraft?", listOf("F = m · g", "F = D · Δs", "F = p · A", "F = m · v²"), 1, "Das Hookesche Gesetz beschreibt F = D · Δs."),
            QuizQuestion(61, "physics", "Was bezeichnet das 'D' in der Formel F = D · Δs?", listOf("Dichte", "Druck", "Federkonstante (Federhärte)", "Durchmesser"), 2, "D ist die Federkonstante in N/m."),
            QuizQuestion(62, "physics", "Welche Verlängerung Δs erfährt eine Feder mit D = 50 N/m bei F = 5 N?", listOf("1 cm", "10 cm", "50 cm", "0,1 cm"), 1, "Δs = F / D = 5 N / (50 N/m) = 0,1 m = 10 cm."),
            QuizQuestion(63, "physics", "In welcher Einheit wird die Kraft F im SI-System gemessen?", listOf("Joule (J)", "Watt (W)", "Newton (N)", "Pascal (Pa)"), 2, "Kräfte werden in Newton (N) gemessen."),
            QuizQuestion(64, "physics", "In welcher Einheit wird die Federkonstante D angegeben?", listOf("Newton pro Meter (N/m)", "Meter (m)", "Kilogramm (kg)", "Newtonmeter (Nm)"), 0, "Die Federkonstante D wird in N/m angegeben."),
            QuizQuestion(65, "physics", "Wie verändert sich die Auslenkung Δs einer Feder, wenn die ziehende Kraft F verdoppelt wird?", listOf("Verdoppelt sich ebenfalls", "Halbiert sich", "Vervierfacht sich", "Bleibt gleich"), 0, "Aufgrund der proportionalen Zuordnung verdoppelt sich die Verlängerung Δs."),
            QuizQuestion(66, "physics", "Wie sieht der Graph im F-Δs-Diagramm beim Hookeschen Gesetz aus?", listOf("Parabel", "Ursprungsgerade (steigend)", "Waagerechte Linie", "Hyperbel"), 1, "Da F proportional zu Δs ist, ergibt sich eine steigende Ursprungsgerade."),
            QuizQuestion(67, "physics", "Wie viel N Gewichtskraft entspricht eine Masse von ca. 100 g auf der Erde?", listOf("ca. 1 N", "ca. 10 N", "ca. 100 N", "ca. 0,1 N"), 0, "100 g entsprechen der Gewichtskraft F_g = m · g ≈ 0,1 kg · 9,81 m/s² ≈ 1 N.")
        ),

        "social_studies" to listOf(
            QuizQuestion(30, "social_studies", "In welchem Artikel des Grundgesetzes steht 'Die Würde des Menschen ist unantastbar'?", listOf("Artikel 1", "Artikel 20", "Artikel 5", "Artikel 3"), 0, "Artikel 1 GG bildet das Fundament."),
            QuizQuestion(31, "social_studies", "Wann fiel die Berliner Mauer?", listOf("17. Juni 1953", "13. August 1961", "9. November 1989", "3. Oktober 1990"), 2, "Am 9. November 1989 fiel die Mauer.")
        ),

        "music" to listOf(
            QuizQuestion(40, "music", "Aus welchen Intervallen baut sich ein Dur-Dreiklang vom Grundton aus auf?", listOf("Kleine Terz + Große Terz", "Große Terz + Kleine Terz", "Zwei Große Terzen", "Zwei Quarten"), 1, "Große Terz unten, kleine Terz darüber."),
            QuizQuestion(41, "music", "Aus welchen Tönen besteht der C-Dur Dreiklang?", listOf("C - E - G", "C - F - A", "C - D - E", "A - C - E"), 0, "C-Dur = C - E - G.")
        ),

        "german" to listOf(
            QuizQuestion(50, "german", "Welches Stilmittel liegt vor bei: 'Milch macht müde Männer munter'?", listOf("Metapher", "Alliteration", "Anapher", "Hyperbel"), 1, "Anfangslaut-Wiederholung ist Alliteration."),
            QuizQuestion(51, "german", "Was bezeichnet man in der Literatur als 'Metapher'?", listOf("Übertragener sprachlicher Ausdruck", "Wiederholung von Wörtern", "Starke Übertreibung", "Vermenschlichung"), 0, "Bildhafter übertragener Ausdruck.")
        )
    )

    fun getQuestionsForSubject(subjectId: String): List<QuizQuestion> {
        val pool = questionsBySubject[subjectId] ?: questionsBySubject["chemistry"]!!
        return pool.shuffled().take(10)
    }
}
