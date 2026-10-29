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
            // --- KATEGORIE A: SÄUREN & FORMELN (1-10) ---
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

            // --- KATEGORIE B: SÄUREREST-IONEN NAMEN (11-20) ---
            QuizQuestion(11, "chemistry", "Wie heißt das Säurerest-Ion der Phosphorsäure (H₃PO₄)?", listOf("Phosphit-Ion", "Phosphat-Ion", "Phosphid-Ion", "Sulfat-Ion"), 1, "Das Säurerest-Ion der Phosphorsäure ist das Phosphat-Ion (PO₄³⁻)."),
            QuizQuestion(12, "chemistry", "Wie heißt das Säurerest-Ion der Salzsäure (HCl)?", listOf("Chlorat-Ion", "Chlorid-Ion", "Chlorit-Ion", "Sulfid-Ion"), 1, "Das Säurerest-Ion der Salzsäure ist das Chlorid-Ion (Cl⁻)."),
            QuizQuestion(13, "chemistry", "Wie heißt das Säurerest-Ion der Schwefelsäure (H₂SO₄)?", listOf("Sulfit-Ion", "Sulfat-Ion", "Sulfid-Ion", "Nitrat-Ion"), 1, "Das Säurerest-Ion der Schwefelsäure heißt Sulfat-Ion (SO₄²⁻)."),
            QuizQuestion(14, "chemistry", "Wie heißt das Säurerest-Ion der Salpetersäure (HNO₃)?", listOf("Nitrit-Ion", "Nitrat-Ion", "Nitrid-Ion", "Carbonat-Ion"), 1, "Das Säurerest-Ion der Salpetersäure heißt Nitrat-Ion (NO₃⁻)."),
            QuizQuestion(15, "chemistry", "Wie heißt das Säurerest-Ion der Kohlensäure (H₂CO₃)?", listOf("Carbid-Ion", "Carbonat-Ion", "Acetat-Ion", "Hydroxid-Ion"), 1, "Das Säurerest-Ion der Kohlensäure heißt Carbonat-Ion (CO₃²⁻)."),
            QuizQuestion(16, "chemistry", "Wie heißt das Säurerest-Ion der Schwefligen Säure (H₂SO₃)?", listOf("Sulfat-Ion", "Sulfit-Ion", "Sulfid-Ion", "Oxid-Ion"), 1, "Das Säurerest-Ion der Schwefligen Säure heißt Sulfit-Ion (SO₃²⁻)."),
            QuizQuestion(17, "chemistry", "Wie heißt das Säurerest-Ion der Salpetrigen Säure (HNO₂)?", listOf("Nitrat-Ion", "Nitrit-Ion", "Nitrid-Ion", "Ammonium-Ion"), 1, "Das Säurerest-Ion der Salpetrigen Säure heißt Nitrit-Ion (NO₂⁻)."),
            QuizQuestion(18, "chemistry", "Wie heißt das Säurerest-Ion der Essigsäure (CH₃COOH)?", listOf("Formiat-Ion", "Acetat-Ion", "Carbonat-Ion", "Citrat-Ion"), 1, "Das Säurerest-Ion der Essigsäure ist das Acetat-Ion (CH₃COO⁻)."),
            QuizQuestion(19, "chemistry", "Welche Endung tragen Säurerest-Ionen sauerstofffreier Säuren (z.B. HCl)?", listOf("-at", "-it", "-id", "-on"), 2, "Sauerstofffreie Säurereste enden auf '-id' (z.B. Chlorid, Fluorid, Sulfid)."),
            QuizQuestion(20, "chemistry", "Welche Endung tragen die sauerstoffreichen Säurerest-Ionen (z.B. SO₄²⁻, NO₃⁻)?", listOf("-id", "-at", "-it", "-ur"), 1, "Sauerstoffreichere Säurerest-Ionen enden auf '-at' (z.B. Sulfat, Nitrat, Phosphat)."),

            // --- KATEGORIE C: SÄUREREST-IONEN FORMELN & LADUNGEN (21-30) ---
            QuizQuestion(21, "chemistry", "Wie lautet die Formel des Phosphat-Ions?", listOf("PO₄³⁻", "PO₃³⁻", "P³⁻", "SO₄²⁻"), 0, "Das Phosphat-Ion hat die Formel PO₄³⁻."),
            QuizQuestion(22, "chemistry", "Wie lautet die Formel des Sulfat-Ions?", listOf("SO₃²⁻", "SO₄²⁻", "S²⁻", "HSO₄⁻"), 1, "Das Sulfat-Ion hat die Formel SO₄²⁻."),
            QuizQuestion(23, "chemistry", "Wie lautet die Formel des Chlorid-Ions?", listOf("Cl⁻", "ClO₃⁻", "Cl₂", "HCl"), 0, "Das Chlorid-Ion hat die Formel Cl⁻."),
            QuizQuestion(24, "chemistry", "Wie lautet die Formel des Nitrat-Ions?", listOf("NO₂⁻", "NO₃⁻", "N³⁻", "NH₄⁺"), 1, "Das Nitrat-Ion hat die Formel NO₃⁻."),
            QuizQuestion(25, "chemistry", "Wie lautet die Formel des Carbonat-Ions?", listOf("CO₃²⁻", "HCO₃⁻", "CO₂", "C⁴⁻"), 0, "Das Carbonat-Ion hat die Formel CO₃²⁻."),
            QuizQuestion(26, "chemistry", "Wie lautet die Formel des Sulfit-Ions?", listOf("SO₄²⁻", "SO₃²⁻", "S²⁻", "SO₂"), 1, "Das Sulfit-Ion hat die Formel SO₃²⁻."),
            QuizQuestion(27, "chemistry", "Wie lautet die Formel des Nitrit-Ions?", listOf("NO₃⁻", "NO₂⁻", "N³⁻", "NO"), 1, "Das Nitrit-Ion hat die Formel NO₂⁻."),
            QuizQuestion(28, "chemistry", "Welche elektrische Ladung trägt das Phosphat-Ion (PO₄³⁻)?", listOf("Einfach negativ (-1)", "Zweifach negativ (-2)", "Dreifach negativ (-3)", "Neutral"), 2, "Das Phosphat-Ion ist dreifach negativ geladen (3-)."),
            QuizQuestion(29, "chemistry", "Welche Ladung trägt das Sulfat-Ion (SO₄²⁻)?", listOf("Einfach negativ (-1)", "Zweifach negativ (-2)", "Dreifach negativ (-3)", "Einfach positiv (+1)"), 1, "Das Sulfat-Ion trägt eine zweifache negative Ladung (2-)."),
            QuizQuestion(30, "chemistry", "Welche Ladung trägt das Nitrat-Ion (NO₃⁻)?", listOf("Einfach negativ (-1)", "Zweifach negativ (-2)", "Dreifach negativ (-3)", "Neutral"), 0, "Das Nitrat-Ion ist einfach negativ geladen (-1)."),

            // --- KATEGORIE D: PROTOLYSE & SÄURE-BASE THEORIE (31-38) ---
            QuizQuestion(31, "chemistry", "Was passiert bei einer 'Protolyse' nach der Säure-Base-Theorie von Brønsted?", listOf("Übertragung von Elektronen", "Übertragung von Protonen (H⁺)", "Spaltung von Atomkernen", "Verdampfen von Flüssigkeit"), 1, "Eine Protolyse ist eine Reaktion mit Protonenübergang (H⁺)."),
            QuizQuestion(32, "chemistry", "Wie wird ein Stoff bezeichnet, der in einer Protolyse Protonen (H⁺) abgibt?", listOf("Protonen-Akzeptor", "Protonen-Donator (Säure)", "Elektronen-Donator", "Katalysator"), 1, "Säuren sind Protonendonatoren."),
            QuizQuestion(33, "chemistry", "Wie wird ein Stoff bezeichnet, der bei einer Protolyse Protonen (H⁺) aufnimmt?", listOf("Protonen-Donator", "Protonen-Akzeptor (Base)", "Anion", "Kation"), 1, "Basen/Laugen sind Protonenakzeptoren."),
            QuizQuestion(34, "chemistry", "Welches Ion bildet sich, wenn ein Proton (H⁺) an ein Wassermolekül (H₂O) gebunden wird?", listOf("Hydroxid-Ion (OH⁻)", "Oxonium-Ion (H₃O⁺)", "Wasserstoffgas (H₂)", "Sauerstoff (O₂)"), 1, "Es entsteht das saure Oxonium-Ion (H₃O⁺)."),
            QuizQuestion(35, "chemistry", "Welches Ion kennzeichnet eine alkalische/basische Lösung?", listOf("Oxonium-Ion (H₃O⁺)", "Hydroxid-Ion (OH⁻)", "Chlorid-Ion (Cl⁻)", "Nitrat-Ion (NO₃⁻)"), 1, "Hydroxid-Ionen (OH⁻) bewirken den alkalischen/basischen Charakter."),
            QuizQuestion(36, "chemistry", "Wie nennt man einen Stoff wie Wasser (H₂O), der sowohl als Säure als auch als Base reagieren kann?", listOf("Ampholyt", "Indikator", "Katalysator", "Isotop"), 0, "Stoffe, die je nach Reaktionspartner als Säure oder Base wirken, heißen Ampholyte."),
            QuizQuestion(37, "chemistry", "Welche Ionen entstehen bei der Dissoziation von Chlorwasserstoff (HCl) in Wasser?", listOf("H₃O⁺ und Cl⁻", "OH⁻ und Cl⁻", "H₂ und Cl₂", "Na⁺ und Cl⁻"), 0, "HCl + H₂O ➔ H₃O⁺ + Cl⁻."),
            QuizQuestion(38, "chemistry", "Wie lautet die Reaktionsgleichung für die Autoprotolyse des Wassers?", listOf("2 H₂O ⇌ H₃O⁺ + OH⁻", "H₂O ➔ H₂ + O₂", "H₂O + HCl ➔ H₃O⁺ + Cl⁻", "H₂O ➔ 2 H⁺ + O²⁻"), 0, "Zwei Wassermoleküle stehen im Gleichgewicht mit H₃O⁺ und OH⁻."),

            // --- KATEGORIE E: pH-WERT, REAKTIONEN & BINDUNGEN (39-50) ---
            QuizQuestion(39, "chemistry", "Welchen pH-Wert hat eine neutrale Lösung wie reines Wasser bei 25°C?", listOf("pH 0", "pH 7", "pH 14", "pH 1"), 1, "pH 7 entspricht genau dem neutralen Zustand."),
            QuizQuestion(40, "chemistry", "In welchem Bereich liegt der pH-Wert saurer Lösungen?", listOf("Größer als 7", "Kleiner als 7 (0 bis <7)", "Genau 14", "Zwischen 8 und 12"), 1, "Säuren haben einen pH-Wert kleiner als 7."),
            QuizQuestion(41, "chemistry", "Welche Farbe nimmt Universalindikator in stark saurer Lösung (pH 1) an?", listOf("Blau / Violett", "Grün", "Rot", "Gelb"), 2, "Starke Säuren färben Universalindikator kräftig rot."),
            QuizQuestion(42, "chemistry", "Welche Farbe nimmt Universalindikator in einer starken Lauge (pH 13) an?", listOf("Rot", "Grün", "Blau / Violett", "Orange"), 2, "Starke Basen färben Universalindikator blau bis tiefviolett."),
            QuizQuestion(43, "chemistry", "Welches Gas entsteht beim Tropfen von Säure auf Marmorfliesen (CaCO₃)?", listOf("Sauerstoff (O₂)", "Kohlendioxid (CO₂)", "Wasserstoff (H₂)", "Chlor (Cl₂)"), 1, "Säure zersetzt Kalk (CaCO₃) zu Kohlendioxid-Gas (CO₂)."),
            QuizQuestion(44, "chemistry", "Warum soll man Marmorflächen im Bad niemals mit Essig- oder Zitronenreiniger putzen?", listOf("Weil die Fliese schmilzt", "Weil die Säure den Kalk auflöst und den Marmor zerstört", "Weil Wasser entsteht", "Weil es stinkt"), 1, "Die Säure zerstört das Calciumcarbonat (Kalk) im Marmor dauerhaft."),
            QuizQuestion(45, "chemistry", "Welches Gas entsteht bei der Reaktion von unedlen Metallen mit Säuren?", listOf("Sauerstoff", "Wasserstoff (H₂)", "Kohlendioxid", "Stickstoff"), 1, "Unedle Metalle wie Mg oder Zn bilden mit Säuren Wasserstoffgas."),
            QuizQuestion(46, "chemistry", "Mit welcher Probe wird Wasserstoffgas im Labor nachgewiesen?", listOf("Glimmspanprobe", "Knallgasprobe", "Kalkwasserprobe", "Flammenfärbung"), 1, "Die Knallgasprobe macht ein deutliches 'Plopp'."),
            QuizQuestion(47, "chemistry", "Welches Metall reagiert NICHT mit verdünnter Salzsäure?", listOf("Magnesium", "Zink", "Eisen", "Kupfer"), 3, "Kupfer ist ein edles Metall und reagiert nicht mit verdünnten Säuren."),
            QuizQuestion(48, "chemistry", "Was passiert bei einer Ionenbindung zwischen Natrium (Na) und Chlor (Cl)?", listOf("Natrium gibt 1 Elektron an Chlor ab", "Chlor gibt 1 Elektron an Natrium ab", "Atome teilen sich Elektronen", "Keine Elektronen wandern"), 0, "Natrium gibt sein Außenelektron an Chlor ab -> Na⁺ und Cl⁻ Ionen bilden ein Salzgitter."),
            QuizQuestion(49, "chemistry", "Was kennzeichnet eine Atombindung / Elektronenpaarbindung bei Molekülen wie Wasser (H₂O)?", listOf("Elektronen werden abgegeben", "Atome teilen sich gemeinsame Elektronenpaare", "Keine Elektronen beteiligt", "Ionen ziehen sich an"), 1, "Nichtmetall-Atome teilen sich Elektronenpaare."),
            QuizQuestion(50, "chemistry", "Was beschreibt der Begriff 'Edelgaskonfiguration'?", listOf("Vollbesetzte Außenelektronenschale (z.B. 8 Elektronen)", "1 Elektron auf der Hülle", "Ungeladener Atomkern", "Flüssiger Zustand"), 0, "Atome streben durch Bindungen eine vollbesetzte Außenschale an (Oktettregel).")
        ),
        "physics" to listOf(
            QuizQuestion(60, "physics", "Wie lautet die Formel des Hookeschen Gesetzes für die Federkraft?", listOf("F = m · g", "F = D · Δs", "F = p · A", "F = m · v²"), 1, "Das Hookesche Gesetz beschreibt F = D · Δs."),
            QuizQuestion(61, "physics", "Was bezeichnet das 'D' in der Formel F = D · Δs?", listOf("Dichte", "Druck", "Federkonstante (Federhärte)", "Durchmesser"), 2, "D ist die Federkonstante in N/m."),
            QuizQuestion(62, "physics", "Welche Verlängerung Δs erfährt eine Feder mit D = 50 N/m bei F = 5 N?", listOf("1 cm", "10 cm", "50 cm", "0,1 cm"), 1, "Δs = F / D = 5 N / (50 N/m) = 0,1 m = 10 cm.")
        ),
        "biology" to listOf(
            QuizQuestion(10, "biology", "In welchem Zellorganell der Pflanzenzelle findet die Fotosynthese statt?", listOf("Mitochondrium", "Zellkern", "Chloroplast", "Vakuole"), 2, "Chloroplasten enthalten Chlorophyll."),
            QuizQuestion(11, "biology", "Welches Gas nehmen Pflanzen bei der Fotosynthese aus der Luft auf?", listOf("Sauerstoff (O₂)", "Kohlendioxid (CO₂)", "Stickstoff (N₂)", "Wasserstoff (H₂)"), 1, "Pflanzen benötigen CO₂."),
            QuizQuestion(12, "biology", "Welches Organell wird als 'Kraftwerk der Zelle' bezeichnet?", listOf("Vakuole", "Mitochondrium", "Ribosom", "Zellwand"), 1, "Mitochondrien erzeugen Energie (ATP)."),
            QuizQuestion(13, "biology", "Wo befindet sich die Erbinformation (DNA) der Pflanze?", listOf("Im Zellkern", "In der Zellwand", "In der Vakuole", "Im Chloroplast"), 0, "Der Zellkern speichert die DNA.")
        ),
        "math" to listOf(
            QuizQuestion(20, "math", "Wie verändert sich die Parabel f(x) = a·x², wenn der Faktor 'a' größer als 1 ist?", listOf("Gestaucht (breiter)", "Gestreckt (schmaler)", "Nach unten geöffnet", "Nach rechts verschoben"), 1, "Für |a| > 1 ist die Parabel gestreckt."),
            QuizQuestion(25, "math", "Wie lautet die Formel für die Grundfläche G eines Zylinders mit Radius r?", listOf("G = 2 · π · r", "G = π · r²", "G = a²", "G = 1/3 · π · r²"), 1, "Die Grundfläche eines Zylinders ist G = π · r²."),
            QuizQuestion(26, "math", "Aus wie vielen Flächen besteht der Mantel M einer quadratischen Pyramide?", listOf("2 Dreiecke", "3 Quadraten", "4 gleichschenkligen Dreiecken", "6 Rechtecken"), 2, "Der Mantel besteht aus 4 Dreiecken.")
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
