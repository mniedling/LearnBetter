package org.learnbetter.project.ui.police

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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.learnbetter.project.model.Subject
import org.learnbetter.project.ui.theme.ChemistryColors

@Composable
fun PoliceSportCalculatorScreen(
    onXpEarned: (Int) -> Unit = {}
) {
    val uriHandler = LocalUriHandler.current

    var isMale by remember { mutableStateOf(true) }

    // Disziplinen
    var sprintSeconds by remember { mutableFloatStateOf(9.0f) }   // Pendellauf 4x10m (M: <= 9.5s, F: <= 10.3s)
    var jumpMeters by remember { mutableFloatStateOf(2.15f) }     // Standweitsprung (M: >= 2.05m, F: >= 1.65m)
    var runMeters by remember { mutableFloatStateOf(2500f) }      // 12-Min-Lauf (M: >= 2400m, F: >= 2000m)

    val sprintReq = if (isMale) 9.5f else 10.3f
    val jumpReq = if (isMale) 2.05f else 1.65f
    val runReq = if (isMale) 2400f else 2000f

    val sprintPassed = sprintSeconds <= sprintReq
    val jumpPassed = jumpMeters >= jumpReq
    val runPassed = runMeters >= runReq

    val isSportPassed = sprintPassed && jumpPassed && runPassed

    var hasEarnedXp by remember { mutableStateOf(false) }

    LaunchedEffect(isSportPassed) {
        if (isSportPassed && !hasEarnedXp) {
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
            text = "🏃‍♂️ Bundespolizei Sporttest Rechner",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Subject.POLICE_TRAINING.primaryColor,
            textAlign = TextAlign.Center
        )

        // Geschlecht Switch
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { isMale = true },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isMale) Subject.POLICE_TRAINING.primaryColor else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text("👨‍✈️ Männer", fontWeight = FontWeight.Bold, color = if (isMale) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Button(
                onClick = { isMale = false },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isMale) Subject.POLICE_TRAINING.primaryColor else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text("👩‍✈️ Frauen", fontWeight = FontWeight.Bold, color = if (!isMale) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        // 1. Pendellauf (4 x 10 m)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("⚡ 1. Pendellauf (4 x 10 m):", fontWeight = FontWeight.Bold)
                    Text("${((sprintSeconds * 10).toInt() / 10.0)} Sek", fontWeight = FontWeight.Bold, color = if (sprintPassed) ChemistryColors.NeutralGreen else MaterialTheme.colorScheme.error)
                }
                Slider(
                    value = sprintSeconds,
                    onValueChange = { sprintSeconds = it },
                    valueRange = 7.0f..14.0f,
                    colors = SliderDefaults.colors(thumbColor = Subject.POLICE_TRAINING.primaryColor, activeTrackColor = Subject.POLICE_TRAINING.primaryColor)
                )
                Text("Anforderung: Maximal ${sprintReq} Sekunden", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        // 2. Standweitsprung
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("🦘 2. Standweitsprung:", fontWeight = FontWeight.Bold)
                    Text("${((jumpMeters * 100).toInt() / 100.0)} m", fontWeight = FontWeight.Bold, color = if (jumpPassed) ChemistryColors.NeutralGreen else MaterialTheme.colorScheme.error)
                }
                Slider(
                    value = jumpMeters,
                    onValueChange = { jumpMeters = it },
                    valueRange = 1.20f..2.80f,
                    colors = SliderDefaults.colors(thumbColor = Subject.POLICE_TRAINING.primaryColor, activeTrackColor = Subject.POLICE_TRAINING.primaryColor)
                )
                Text("Anforderung: Mindestens ${jumpReq} Meter", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        // 3. 12-Minuten Cooper-Lauf
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("🏃 3. 12-Minuten-Lauf:", fontWeight = FontWeight.Bold)
                    Text("${runMeters.toInt()} m", fontWeight = FontWeight.Bold, color = if (runPassed) ChemistryColors.NeutralGreen else MaterialTheme.colorScheme.error)
                }
                Slider(
                    value = runMeters,
                    onValueChange = { runMeters = (it / 50).toInt() * 50.0f },
                    valueRange = 1500f..3200f,
                    colors = SliderDefaults.colors(thumbColor = Subject.POLICE_TRAINING.primaryColor, activeTrackColor = Subject.POLICE_TRAINING.primaryColor)
                )
                Text("Anforderung: Mindestens ${runReq.toInt()} Meter", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        // Gesamtergebnis BPol Sporttest
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isSportPassed) ChemistryColors.NeutralGreen.copy(alpha = 0.15f) else MaterialTheme.colorScheme.errorContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = if (isSportPassed) "🎉 BPol-SPORTTEST BESTANDEN!" else "❌ BPol-SPORTTEST NICHT BESTANDEN",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = if (isSportPassed) ChemistryColors.NeutralGreen else MaterialTheme.colorScheme.error
                )

                Text(
                    text = if (isSportPassed) "Du erfüllst alle sportlichen Mindestanforderungen der Bundespolizei! (+15 XP)" else "Mindestens eine Disziplin liegt unter den Mindestanforderungen der Bundespolizei.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Verifizierung Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("🔗 Offizielle Informationen der Bundespolizei:", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)

                Surface(
                    modifier = Modifier.fillMaxWidth().clickable {
                        uriHandler.openUri("https://www.komm-zur-bundespolizei.de")
                    },
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text("🌐 Sporttest Anforderungen (komm-zur-bundespolizei.de) ➔", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.padding(10.dp))
                }
            }
        }
    }
}
