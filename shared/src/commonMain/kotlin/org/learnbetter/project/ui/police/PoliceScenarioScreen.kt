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
import org.learnbetter.project.model.PoliceData
import org.learnbetter.project.model.PoliceScenario
import org.learnbetter.project.model.Subject
import org.learnbetter.project.ui.theme.ChemistryColors

@Composable
fun PoliceScenarioScreen(
    onXpEarned: (Int) -> Unit = {}
) {
    val scenarios = remember { PoliceData.scenarios }
    var activeIndex by remember { mutableIntStateOf(0) }
    val currentScenario = scenarios[activeIndex % scenarios.size]

    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    var isSubmitted by remember { mutableStateOf(false) }

    val isCorrect = selectedIndex == currentScenario.correctOptionIndex

    var hasEarnedXp by remember { mutableStateOf(false) }

    LaunchedEffect(isSubmitted) {
        if (isSubmitted && isCorrect && !hasEarnedXp) {
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
            text = "🚨 BPol Einsatzszenarien & Recht",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Subject.POLICE_TRAINING.primaryColor,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Analysiere polizeiliche Lagen an Bahnhöfen, Flughäfen und Grenzen. Treffe die verhältnismäßig und rechtlich richtige Entscheidung!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        // Szenario Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Subject.POLICE_TRAINING.primaryColor
                ) {
                    Text(
                        text = currentScenario.title,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Text(
                    text = currentScenario.situationText,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        // Taktische Entscheidungen
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            currentScenario.options.forEachIndexed { index, optionText ->
                val isSelected = selectedIndex == index
                val isCorrectOption = index == currentScenario.correctOptionIndex

                val bgColor = when {
                    isSubmitted && isCorrectOption -> ChemistryColors.NeutralGreen.copy(alpha = 0.25f)
                    isSubmitted && isSelected -> MaterialTheme.colorScheme.errorContainer
                    isSelected -> Subject.POLICE_TRAINING.primaryColor.copy(alpha = 0.2f)
                    else -> MaterialTheme.colorScheme.surface
                }

                val borderColor = when {
                    isSubmitted && isCorrectOption -> ChemistryColors.NeutralGreen
                    isSubmitted && isSelected -> MaterialTheme.colorScheme.error
                    isSelected -> Subject.POLICE_TRAINING.primaryColor
                    else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(width = 2.dp, color = borderColor, shape = RoundedCornerShape(16.dp))
                        .clickable(enabled = !isSubmitted) { selectedIndex = index },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = bgColor)
                ) {
                    Text(
                        text = optionText,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        if (!isSubmitted) {
            Button(
                onClick = { isSubmitted = true },
                enabled = selectedIndex != null,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Subject.POLICE_TRAINING.primaryColor)
            ) {
                Text("Entscheidung treffen ➔", fontWeight = FontWeight.Bold, color = Color.White)
            }
        } else {
            // Rechtsgrundlage & Erläuterung
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text("⚖️ Rechtsgrundlage: ${currentScenario.legalBasis}", fontWeight = FontWeight.Bold, color = Subject.POLICE_TRAINING.primaryColor)
                    Text(currentScenario.explanation, style = MaterialTheme.typography.bodySmall)
                }
            }

            Button(
                onClick = {
                    activeIndex++
                    selectedIndex = null
                    isSubmitted = false
                    hasEarnedXp = false
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Subject.POLICE_TRAINING.primaryColor)
            ) {
                Text("Nächstes Szenario ➔", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}
