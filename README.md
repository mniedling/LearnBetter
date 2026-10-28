# LearnBetter – Chemie Lernapp (10. Klasse Gesamtschule)

Ein interaktives, modernes Lernprogramm zur Unterstützung von Schülern der 10. Klasse der Gesamtschule im Fach Chemie.

Der erste Schwerpunkt liegt auf dem Thema **Salzsäure ($HCl$), Metallreaktionen, pH-Werte und Indikatoren**.

---

## 🎯 Didaktisches & Fachliches Konzept

### 1. Modul: Salzsäure ($HCl$) Grundlagen
- **Eigenschaften:** Lösung von Chlorwasserstoffgas ($HCl$) in Wasser, stark ätzend, saurer Geschmack.
- **Dissoziation:** $HCl + H_2O \rightarrow H_3O^+ + Cl^-$ (Entstehung von Oxonium- und Chlorid-Ionen).
- **Sicherheit im Labor:** Piktogramme (Ätzend), Schutzbrille, Handhabung von Säuren.

### 2. Modul: Reaktion von Salzsäure mit Metallen
- **Unedle vs. Edle Metalle:**
  - Unedle Metalle ($Mg$, $Zn$, $Fe$) reagieren unter Bildung von Wasserstoffgas ($H_2$) und Metallsalzen (z. B. Magnesiumchlorid $MgCl_2$).
  - Reaktionsgleichung: $Mg + 2\,HCl \rightarrow MgCl_2 + H_2 \uparrow$
  - Edle Metalle ($Cu$) reagieren nicht mit verdünnter Salzsäure.
- **Gasnachweis:** Die **Knallgasprobe** zum eindeutigen Nachweis von Wasserstoff ("Plopp!").

### 3. Modul: pH-Wert & Messmethoden
- **Die pH-Skala (0–14):**
  - $< 7$: Sauer (hohe $H_3O^+$-Konzentration)
  - $= 7$: Neutral (z. B. reines Wasser)
  - $> 7$: Basisch / Alkalisch (z. B. Natronlauge)
- **Indikatoren:** Universalindikator, Lackmus, Phenolphthalein.
- **Messmethoden:** Indikator-Teststreifen (Farbvergleich) und digitale pH-Elektrode.

---

## 🎨 UI/UX & Interaktive Features

- **Modernes Material 3 Dashboard:**
  - Übersicht über Lernfortschritt, Tages-Streak (🔥) und Erfahrungspunkte (XP).
  - Themenkarten mit Fortschrittsbalken.
- **Virtuelles Chemie-Labor (Lab-Simulator):**
  - Interaktives Reagenzglas: Metall wählen ($Mg$, $Zn$, $Cu$), Salzsäure hinzugeben.
  - Bläschen-Animation, Erwärmung & Knallgasprobe-Auslöser.
  - Virtueller pH-Teststreifen zum Eintauchen in Alltagsflüssigkeiten.
- **Interaktiver Formel-Builder & Quiz:**
  - Reaktionsgleichungen durch Drag & Drop / Buttons vervollständigen.
  - Quiz mit unmittelbarem visuellen Feedback.

---

## 🏗️ Architektur & Tech-Stack

- **Framework:** Kotlin Multiplatform (KMP) & Compose Multiplatform
- **Design System:** Material 3 mit speziellem Chemie-Farbschema (Neon-Säuregrün, Wasserstoff-Cyan, Indikator-Violett/Orange)
- **Architekturmuster:** MVI / MVVM mit `StateFlow` und Compose State

---

## 🚀 App ausführen

- **Android App:**
  ```bash
  ./gradlew :androidApp:assembleDebug
  ```
