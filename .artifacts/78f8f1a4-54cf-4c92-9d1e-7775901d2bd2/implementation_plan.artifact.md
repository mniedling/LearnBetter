# Implementation Plan - Fahrschule Klasse AM & Testbogen 1

Implement a dedicated **Fahrschule (Klasse AM / Moped)** subject category in LearnBetter with Testbogen 1, official multi-selection checkbox UI, Fehlerpunkte evaluation (≤10 max error points to pass), detailed StVO explanations, interactive driving formula calculator (Reaktionsweg, Bremsweg, Anhalteweg), and result evaluation.

## Proposed Changes

### Domain & Data Models

#### [NEW] [DrivingQuestion.kt](file:///home/mniedling/StudioProjects/LearnBetter/shared/src/commonMain/kotlin/org/learnbetter/project/model/DrivingQuestion.kt)
- Data model for driving test questions: `id`, `questionText`, `options`, `correctOptionIndices: Set<Int>`, `errorPoints: Int`, `explanation: String`.
- Complete official **Klasse AM Testbogen 1** question dataset (traffic signs, right of way, moped safety, speed formulas, alcohol limits).

#### [MODIFY] [Subject.kt](file:///home/mniedling/StudioProjects/LearnBetter/shared/src/commonMain/kotlin/org/learnbetter/project/model/Subject.kt)
- Add `DRIVING_SCHOOL("driving", "Fahrschule AM", "🛵", Color(0xFFFBC02D), "Führerschein Klasse AM • Testbogen 1")` to the subject enum.

---

### UI Components & Screens

#### [NEW] [DrivingSchoolScreen.kt](file:///home/mniedling/StudioProjects/LearnBetter/shared/src/commonMain/kotlin/org/learnbetter/project/ui/driving/DrivingSchoolScreen.kt)
- Official driving test UI with checkboxes ($\square$), Fehlerpunkte badge (`4 Fehlerpunkte`), explanation card, and question stepper.
- Test result evaluation: Sums error points, displays **BESTANDEN (≤ 10 Fehlerpunkte)** or **NICHT BESTANDEN (> 10 Fehlerpunkte)**, grants XP for passing.

#### [NEW] [DrivingFormulaCalculatorScreen.kt](file:///home/mniedling/StudioProjects/LearnBetter/shared/src/commonMain/kotlin/org/learnbetter/project/ui/driving/DrivingFormulaCalculatorScreen.kt)
- Interactive driving formula calculator tool for the Werkstatt/Labor tab:
  - Speed $v$ slider (10 to 100 km/h).
  - Live calculation & visual gauge for:
    - **Reaktionsweg**: $\frac{v}{10} \times 3$
    - **Bremsweg**: $\left(\frac{v}{10}\right)^2$
    - **Anhalteweg**: Reaktionsweg + Bremsweg
    - **Gefahrenbremsung**: Bremsweg / 2

#### [MODIFY] [LabHubScreen.kt](file:///home/mniedling/StudioProjects/LearnBetter/shared/src/commonMain/kotlin/org/learnbetter/project/ui/lab/LabHubScreen.kt)
- Route `DRIVING_SCHOOL` in the Werkstatt tab to `DrivingFormulaCalculatorScreen`.

#### [MODIFY] [QuizScreen.kt](file:///home/mniedling/StudioProjects/LearnBetter/shared/src/commonMain/kotlin/org/learnbetter/project/ui/quiz/QuizScreen.kt)
- Route `DRIVING_SCHOOL` in the Quiz tab to the official Testbogen 1 UI in `DrivingSchoolScreen`.

#### [MODIFY] [README.md](file:///home/mniedling/StudioProjects/LearnBetter/README.md)
- Update README to document the new **Fahrschule Klasse AM** module, Testbogen 1, error points evaluation, and formula calculator.

---

## Verification Plan

### Automated Tests
- Run Gradle assemble build: `./gradlew :androidApp:assembleDebug`
- Verify KMP source set compilation with zero errors.

### Manual Verification
- Test selecting **🛵 Fahrschule AM** in the subject bar.
- Test multi-selection checkboxes on Testbogen 1.
- Test error points sum and passing threshold (≤10 error points).
- Test interactive speed $v$ slider for Reaktionsweg and Bremsweg formulas.
