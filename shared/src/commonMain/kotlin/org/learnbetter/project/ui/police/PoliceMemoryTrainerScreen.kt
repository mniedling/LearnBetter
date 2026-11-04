package org.learnbetter.project.ui.police

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.learnbetter.project.model.PoliceData
import org.learnbetter.project.model.Subject
import org.learnbetter.project.ui.theme.ChemistryColors

@Composable
fun PoliceMemoryTrainerScreen(
    onXpEarned: (Int) -> Unit = {}
) {
    val profiles = remember { PoliceData.memoryProfiles }
    var activeProfileIndex by remember { mutableIntStateOf(0) }
    val currentProfile = profiles[activeProfileIndex % profiles.size]

    // Phase: 1 = Memorize (10s Countdown), 2 = Test Recall, 3 = Result
    var phase by remember { mutableIntStateOf(1) }
    var countdownSeconds by remember { mutableIntStateOf(10) }

    // Test Fragen zur aktiven Karte
    var selectedPlate by remember { mutableStateOf<String?>(null) }
    var selectedColor by remember { mutableStateOf<String?>(null) }
    var isSubmitted by remember { mutableStateOf(false) }

    val isPlateCorrect = selectedPlate == currentProfile.licensePlate
    val isColorCorrect = selectedColor == currentProfile.vehicleColor
    val isAllCorrect = isPlateCorrect && isColorCorrect

    var hasEarnedXp by remember { mutableStateOf(false) }

    // 10-Sekunden Timer
    LaunchedEffect(phase, activeProfileIndex) {
        if (phase == 1) {
            countdownSeconds = 10
            while (countdownSeconds > 0) {
                delay(1000)
                countdownSeconds--
            }
            phase = 2 // Auto-switch to Recall Phase
        }
    }

    LaunchedEffect(isSubmitted) {
        if (isSubmitted && isAllCorrect && !hasEarnedXp) {
            hasEarnedXp = true
            onXpEarned(15)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🧠 Steckbrief-Merkfähigkeitstrainer",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Subject.POLICE_TRAINING.primaryColor,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Ein wichtiger Teil des BPol-Kognitionstests! Präge dir den Steckbrief in 10 Sekunden ein, danach wird abgefragt.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        if (phase == 1) {
            // PHASE 1: EINPRÄGEN
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(currentProfile.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        Surface(shape = RoundedCornerShape(10.dp), color = Subject.POLICE_TRAINING.primaryColor) {
                            Text("⏳ $countdownSeconds Sek", fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                        }
                    }

                    LinearProgressIndicator(
                        progress = { countdownSeconds / 10f },
                        modifier = Modifier.fillMaxWidth().height(6.dp),
                        color = Subject.POLICE_TRAINING.primaryColor
                    )

                    HorizontalDivider()

                    Text("🚘 Modell: ${currentProfile.vehicleModel}", fontWeight = FontWeight.Bold)
                    Text("🎨 Farbe: ${currentProfile.vehicleColor}", fontWeight = FontWeight.Bold)
                    Text("🪪 Kennzeichen: ${currentProfile.licensePlate}", fontWeight = FontWeight.Bold)
                    Text("🧭 Fluchtrichtung: ${currentProfile.escapeDirection}", fontWeight = FontWeight.Bold)
                    Text("👤 Täterbeschreibung: ${currentProfile.suspectDescription}", style = MaterialTheme.typography.bodySmall)
                }
            }

            Button(
                onClick = { phase = 2 },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Subject.POLICE_TRAINING.primaryColor)
            ) {
                Text("Bereit! Direkt abfragen ➔", fontWeight = FontWeight.Bold, color = Color.White)
            }
        } else {
            // PHASE 2: ABFRAGE
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("❓ Gedächtnisabfrage: ${currentProfile.title}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                    Text("1. Wie lautete das Kennzeichen des Fahrzeugs?", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)

                    val plateOptions = remember(currentProfile) {
                        listOf(currentProfile.licensePlate, "B-BP 991", "M-BP 204", "FRA-BP 303").shuffled()
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        plateOptions.forEach { option ->
                            val isSelected = selectedPlate == option
                            val isCorrect = option == currentProfile.licensePlate

                            val bgColor = when {
                                isSubmitted && isCorrect -> ChemistryColors.NeutralGreen.copy(alpha = 0.25f)
                                isSubmitted && isSelected -> MaterialTheme.colorScheme.errorContainer
                                isSelected -> Subject.POLICE_TRAINING.primaryColor.copy(alpha = 0.2f)
                                else -> MaterialTheme.colorScheme.surface
                            }

                            Surface(
                                modifier = Modifier.fillMaxWidth().clickable(enabled = !isSubmitted) { selectedPlate = option },
                                shape = RoundedCornerShape(10.dp),
                                color = bgColor
                            ) {
                                Text(option, modifier = Modifier.padding(12.dp), fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Text("2. Welche Farbe hatte das Fahrzeug?", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)

                    val colorOptions = remember(currentProfile) {
                        listOf(currentProfile.vehicleColor, "Schwarz", "Silber-Grau", "Dunkelblau", "Rot").distinct().take(3).shuffled()
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        colorOptions.forEach { option ->
                            val isSelected = selectedColor == option
                            val isCorrect = option == currentProfile.vehicleColor

                            val bgColor = when {
                                isSubmitted && isCorrect -> ChemistryColors.NeutralGreen.copy(alpha = 0.25f)
                                isSubmitted && isSelected -> MaterialTheme.colorScheme.errorContainer
                                isSelected -> Subject.POLICE_TRAINING.primaryColor.copy(alpha = 0.2f)
                                else -> MaterialTheme.colorScheme.surface
                            }

                            Surface(
                                modifier = Modifier.fillMaxWidth().clickable(enabled = !isSubmitted) { selectedColor = option },
                                shape = RoundedCornerShape(10.dp),
                                color = bgColor
                            ) {
                                Text(option, modifier = Modifier.padding(12.dp), fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            if (!isSubmitted) {
                Button(
                    onClick = { isSubmitted = true },
                    enabled = selectedPlate != null && selectedColor != null,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Subject.POLICE_TRAINING.primaryColor)
                ) {
                    Text("Erinnerung auswerten", fontWeight = FontWeight.Bold, color = Color.White)
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isAllCorrect) ChemistryColors.NeutralGreen.copy(alpha = 0.15f) else MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(if (isAllCorrect) "🎉 FOTOGRAFISCHES GEDÄCHTNIS! (+15 XP)" else "❌ Details nicht ganz richtig gemerkt", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                    }
                }

                Button(
                    onClick = {
                        activeProfileIndex++
                        phase = 1
                        selectedPlate = null
                        selectedColor = null
                        isSubmitted = false
                        hasEarnedXp = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Subject.POLICE_TRAINING.primaryColor)
                ) {
                    Text("Nächsten Steckbrief laden ➔", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}
