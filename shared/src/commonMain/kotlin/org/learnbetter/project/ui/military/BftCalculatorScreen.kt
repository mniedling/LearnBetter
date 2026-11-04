package org.learnbetter.project.ui.military

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
import org.learnbetter.project.model.Subject
import org.learnbetter.project.ui.theme.ChemistryColors

@Composable
fun BftCalculatorScreen(
    onXpEarned: (Int) -> Unit = {}
) {
    // Disziplinen
    var sprintSeconds by remember { mutableFloatStateOf(48f) }   // 35..65 Sek (Passing <= 60s)
    var hangSeconds by remember { mutableFloatStateOf(35f) }     // 0..80 Sek (Passing >= 5s)
    var runSeconds by remember { mutableFloatStateOf(270f) }     // 180..420 Sek (Passing <= 390s / 6:30 min)

    val sprintPassed = sprintSeconds <= 60f
    val hangPassed = hangSeconds >= 5f
    val runPassed = runSeconds <= 390f

    val isBftPassed = sprintPassed && hangPassed && runPassed

    // BFT Punkte Berechnung
    val sprintPoints = (100 + (60f - sprintSeconds) * 15f).coerceIn(0f, 400f).toInt()
    val hangPoints = (100 + (hangSeconds - 5f) * 5.5f).coerceIn(0f, 400f).toInt()
    val runPoints = (100 + (390f - runSeconds) * 1.6f).coerceIn(0f, 400f).toInt()

    val totalBftPoints = if (isBftPassed) sprintPoints + hangPoints + runPoints else 0

    var hasEarnedXp by remember { mutableStateOf(false) }

    LaunchedEffect(isBftPassed) {
        if (isBftPassed && !hasEarnedXp) {
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
            text = "🏋️‍♂️ Basis-Fitness-Test (BFT) Rechner",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Subject.MILITARY_TRAINING.primaryColor,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Testen Sie Ihre sportlichen Leistungen für die drei offiziellen Disziplinen des BFT der Bundeswehr!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        // 1. Sprint-Test (11 x 10 m)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("⚡ 1. Sprint (11 x 10 m):", fontWeight = FontWeight.Bold)
                    Text("${sprintSeconds.toInt()} Sek (${sprintPoints} Pkt)", fontWeight = FontWeight.Bold, color = if (sprintPassed) ChemistryColors.NeutralGreen else MaterialTheme.colorScheme.error)
                }
                Slider(
                    value = sprintSeconds,
                    onValueChange = { sprintSeconds = it },
                    valueRange = 35f..70f,
                    colors = SliderDefaults.colors(thumbColor = Subject.MILITARY_TRAINING.primaryColor, activeTrackColor = Subject.MILITARY_TRAINING.primaryColor)
                )
                Text("Anforderung: Maximal 60,0 Sekunden", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        // 2. Klimmhang
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("💪 2. Klimmhang (Haltezeit):", fontWeight = FontWeight.Bold)
                    Text("${hangSeconds.toInt()} Sek (${hangPoints} Pkt)", fontWeight = FontWeight.Bold, color = if (hangPassed) ChemistryColors.NeutralGreen else MaterialTheme.colorScheme.error)
                }
                Slider(
                    value = hangSeconds,
                    onValueChange = { hangSeconds = it },
                    valueRange = 0f..80f,
                    colors = SliderDefaults.colors(thumbColor = Subject.MILITARY_TRAINING.primaryColor, activeTrackColor = Subject.MILITARY_TRAINING.primaryColor)
                )
                Text("Anforderung: Mindestens 5 Sekunden", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        // 3. 1000m Lauf
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                val runMinutes = (runSeconds / 60).toInt()
                val runSecRemainder = (runSeconds % 60).toInt()
                val formattedTime = "$runMinutes:${if (runSecRemainder < 10) "0" else ""}$runSecRemainder Min"

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("🏃 3. 1000 m Lauf:", fontWeight = FontWeight.Bold)
                    Text("$formattedTime (${runPoints} Pkt)", fontWeight = FontWeight.Bold, color = if (runPassed) ChemistryColors.NeutralGreen else MaterialTheme.colorScheme.error)
                }
                Slider(
                    value = runSeconds,
                    onValueChange = { runSeconds = it },
                    valueRange = 180f..420f,
                    colors = SliderDefaults.colors(thumbColor = Subject.MILITARY_TRAINING.primaryColor, activeTrackColor = Subject.MILITARY_TRAINING.primaryColor)
                )
                Text("Anforderung: Maximal 6:30 Minuten (390 Sek)", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        // Gesamtergebnis BFT
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isBftPassed) ChemistryColors.NeutralGreen.copy(alpha = 0.15f) else MaterialTheme.colorScheme.errorContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = if (isBftPassed) "🎉 BFT BESTANDEN!" else "❌ BFT NICHT BESTANDEN",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = if (isBftPassed) ChemistryColors.NeutralGreen else MaterialTheme.colorScheme.error
                )

                Text(
                    text = if (isBftPassed) "Gesamtpunktzahl: $totalBftPoints von 1200 BFT-Punkten (+15 XP)" else "Mindestens eine Disziplin liegt unter den Mindestanforderungen der Bundeswehr.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
