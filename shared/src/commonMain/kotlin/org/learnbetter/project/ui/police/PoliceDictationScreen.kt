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
import org.learnbetter.project.model.DictationTask
import org.learnbetter.project.model.PoliceData
import org.learnbetter.project.model.Subject
import org.learnbetter.project.ui.theme.ChemistryColors

@Composable
fun PoliceDictationScreen(
    onXpEarned: (Int) -> Unit = {}
) {
    val tasks = remember { PoliceData.dictationTasks }
    var activeIndex by remember { mutableIntStateOf(0) }
    val currentTask = tasks[activeIndex % tasks.size]

    var userSelections by remember(activeIndex) {
        mutableStateOf(List(currentTask.gapOptions.size) { -1 })
    }
    var isSubmitted by remember(activeIndex) { mutableStateOf(false) }

    val isAllCorrect = userSelections.zip(currentTask.correctGapIndices).all { (user, target) -> user == target }

    var hasEarnedXp by remember(activeIndex) { mutableStateOf(false) }

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
            text = "📝 Diktat- & Rechtschreibtrainer",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Subject.POLICE_TRAINING.primaryColor,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Prüfe deine Rechtschreibung und Grammatik für den offiziellen Diktattest der Bundespolizei!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(currentTask.reportTitle, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                HorizontalDivider()
                Text("Wähle für jede Lücke im Einsatzbericht das richtige Wort:", style = MaterialTheme.typography.bodySmall)
            }
        }

        // Lücken-Aufgaben
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            currentTask.gapOptions.forEachIndexed { gapIdx, options ->
                val selectedOptIdx = userSelections.getOrNull(gapIdx) ?: -1
                val correctOptIdx = currentTask.correctGapIndices.getOrNull(gapIdx) ?: -1
                val isGapCorrect = selectedOptIdx == correctOptIdx

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Lücke ${gapIdx + 1}: ${currentTask.sentenceParts.getOrElse(gapIdx) { "" }} [ ___ ]", fontWeight = FontWeight.Bold)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            options.forEachIndexed { optIdx, optionText ->
                                val isSelected = selectedOptIdx == optIdx
                                val isOptionCorrect = optIdx == correctOptIdx

                                val bgColor = when {
                                    isSubmitted && isOptionCorrect -> ChemistryColors.NeutralGreen.copy(alpha = 0.25f)
                                    isSubmitted && isSelected -> MaterialTheme.colorScheme.errorContainer
                                    isSelected -> Subject.POLICE_TRAINING.primaryColor.copy(alpha = 0.2f)
                                    else -> MaterialTheme.colorScheme.surface
                                }

                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable(enabled = !isSubmitted) {
                                            val mutable = userSelections.toMutableList()
                                            mutable[gapIdx] = optIdx
                                            userSelections = mutable
                                        },
                                    shape = RoundedCornerShape(10.dp),
                                    color = bgColor
                                ) {
                                    Text(
                                        text = optionText,
                                        modifier = Modifier.padding(10.dp),
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (!isSubmitted) {
            Button(
                onClick = { isSubmitted = true },
                enabled = userSelections.none { it == -1 },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Subject.POLICE_TRAINING.primaryColor)
            ) {
                Text("Diktat auswerten ➔", fontWeight = FontWeight.Bold, color = Color.White)
            }
        } else {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text("💡 Rechtschreibregel:", fontWeight = FontWeight.Bold, color = Subject.POLICE_TRAINING.primaryColor)
                    Text(currentTask.grammarRule, style = MaterialTheme.typography.bodySmall)
                }
            }

            Button(
                onClick = {
                    activeIndex++
                    isSubmitted = false
                    hasEarnedXp = false
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Subject.POLICE_TRAINING.primaryColor)
            ) {
                Text("Nächstes Diktat ➔", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}
