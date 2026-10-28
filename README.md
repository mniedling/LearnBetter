# LearnBetter – Multi-Fach Lernapp (10. Klasse Gesamtschule)

Ein interaktives, modernes Lernprogramm zur Unterstützung von Schülern der 10. Klasse der Gesamtschule in den Fächern **Chemie, Biologie, Mathematik, Gesellschaftslehre, Musik und Deutsch**.

Das Projekt kombiniert Fachwissen mit **interaktiven Werkstätten/Simulatoren**, **Quizzen** und einem **pädagogischen Computerzeit-Belohnungssystem**.

---

## 📚 Fächerübersicht & Werkstätten

### 🧪 1. Chemie
- **Säuren- & Ion-Laborkolben (Dissoziations-Werkstatt):**
  - **Erlenmeyerkolben-Werkstatt:** Interaktives Zuordnen von Säureformeln ($HCl, H_2SO_4, H_3PO_4, HNO_3, H_2CO_3, H_2SO_3, HNO_2$).
  - **Wasser-Dissoziation:** Wasser dazugießen ($H_2O$), Reaktionen beobachten und Säurerest-Ionen ($Cl^-, SO_4^{2-}, PO_4^{3-}, NO_3^-, CO_3^{2-}, SO_3^{2-}, NO_2^-$) sowie deren Namen (*Chlorid, Sulfat, Phosphat, Nitrat, Carbonat, Sulfit, Nitrit*) zuordnen.
- **Atombinder - Das Bindungs-Spiel mit Elektronen-Animation (⚛️):**
  - **Ionenbindung:** Ein gelbes Außenelektron wandert in einer flüssigen Animation von Natrium ($Na$) rüber zu Chlor ($Cl$) $\rightarrow Kochsalz (NaCl)$.
  - **Atombindung / Elektronenpaarbindung:** Wasserstoff-Atome ($H$) nähern sich Sauerstoff ($O$) und bilden sichtbare gemeinsame Elektronenpaare $\rightarrow Wasser (H_2O)$.
- **Schwefelsäure ($H_2SO_4$) & Kontaktverfahren:**
  - Erläuterung der 3 Stufen ($S \rightarrow SO_2 \rightarrow SO_3 \rightarrow H_2SO_4$).
  - Verkohlungseffekt auf Puderzucker (starker Wasserentzug $\rightarrow$ Kohlenstoff-Säule $C$).
- **Säure vs. Marmorfliesen ($CaCO_3$):**
  - Reaktion von Säuren auf Marmor (Kohlendioxid-Schäumen $CO_2$).
  - Alltags-Erkenntnis: Marmor nie mit sauren Reinigern/Essig putzen, da sich der Kalk zersetzt!
- **pH-Test Simulator (15 Flüssigkeiten):**
  - Testen von Batteriesäure, Magensäure, Essig, Orangensaft, Tomatensaft, Kaffee, Urin, Wasser, Meerwasser, Backpulver, Verdauungstabletten, Salmiakgeist, Seifenlauge, Bleichmittel und Rohrreiniger.
- **Magnesiumband entzünden:**
  - Verbrennung über der Bunsenbrennerflamme unter gleißendem Licht zu Magnesiumoxid ($2\,Mg + O_2 \rightarrow 2\,MgO$).

### 🧬 2. Biologie
- **Virtuelles Mikroskop:**
  - Stufenlose Zoomstufen ($10\text{x}, 100\text{x}, 1000\text{x}$).
  - $1000\text{x}$ Subzelluläre Ebene: Nahaufnahme von Chloroplasten-Thylakoiden (Fotosynthese) und Zellkern-Chromatin (DNA).
  - Erforschung aller Organellen (Chloroplast, Zellkern, Mitochondrium, Vakuole, Zellwand).

### 📐 3. Mathematik
- **Interaktiver Parabel-Plotter:**
  - Untersuchung quadratischer Funktionen $f(x) = a \cdot x^2 + c$.
  - Schieberegler für $a$ (Öffnung/Streckung) und $c$ (Y-Verschiebung).
  - Live-Anzeige von Scheitelpunkt $S(0|c)$ und reellen Nullstellen.

### 🌍 4. Gesellschaftslehre (GL)
- **Historische Zeitleiste & Grundgesetz-Explorer:**
  - Meilensteine von 1914 bis 1990 (Erster Weltkrieg, Weimarer Republik, NS-Diktatur, Grundgesetz 1949, Mauerfall 1989, Wiedervereinigung 1990).
  - Verankerung von Artikel 1 Abs. 1 GG (*"Die Würde des Menschen ist unantastbar"*).

### 🎵 5. Musik
- **Virtuelles Piano & Dreiklang-Trainer:**
  - Interaktives Klaviatur-Tastenfeld ($C, D, E, F, G, A, H, C'$).
  - Gehör- und Bautrainer für $C$-Dur ($C-E-G$) und $A$-Moll ($A-C-E$).

### 📖 6. Deutsch
- **Stilmittel- & Rhetorik-Baukasten:**
  - Interaktives Zuordnen von Stilmitteln aus Gedichten und Texten (Alliteration, Metapher, Personifikation, Anapher) mit Erklärungsboxen.

---

## 🎮 Computerzeit-Belohnungssystem (Gamification)

- **XP-Gutschrift:** Punkte gibt es für gelöste Quizfragen, erforschte Organellen und durchgeführte Experimente.
- **Wechselkurs:** Konfigurierbar über die **Eltern-Einstellung (⚙️)**:
  - *Leicht:* $25\text{ XP} = 5\text{ Min.}$
  - *Ausgewogen (Standard):* $50\text{ XP} = 5\text{ Min.}$
  - *Anspruchsvoll:* $100\text{ XP} = 5\text{ Min.}$
- **Einlösen-Dialog:** Generiert eine Bestätigungskarte für die Eltern zum Freischalten der PC-Zeit.

---

## 🏗️ Architektur & Tech-Stack

- **Framework:** Kotlin Multiplatform (KMP) & Compose Multiplatform
- **Design System:** Material 3 mit Fach-spezifischen Farbwelten (Säuregrün, Smaragdgrün, Violett, Amber, Karmesinrot, Königsblau)
- **Repository:** Synchronisiert auf GitHub (`https://github.com/mniedling/LearnBetter.git`)

---

## 🚀 App & APK ausführen

- **Android App Bauen:**
  ```bash
  ./gradlew :androidApp:assembleDebug
  ```
- **Fertige APK-Datei:**
  `androidApp/build/outputs/apk/debug/androidApp-debug.apk`
