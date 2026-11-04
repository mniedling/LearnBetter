package org.learnbetter.project.ui.military

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import org.learnbetter.project.model.MilitaryData
import org.learnbetter.project.model.MilitaryRank
import org.learnbetter.project.model.Subject
import org.learnbetter.project.ui.theme.ChemistryColors

@Composable
fun MilitaryRankGameScreen(
    onXpEarned: (Int) -> Unit = {}
) {
    val initialRanks = remember { MilitaryData.ranks }
    var currentRankList by remember { mutableStateOf(initialRanks.shuffled()) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    var isChecked by remember { mutableStateOf(false) }

    val isPerfectOrder = currentRankList.zip(initialRanks).all { (current, target) -> current.id == target.id }

    var hasEarnedXp by remember { mutableStateOf(false) }

    LaunchedEffect(isPerfectOrder, isChecked) {
        if (isChecked && isPerfectOrder && !hasEarnedXp) {
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
            text = "🎖️ Dienstgrad-Rangordnung Trainer",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Subject.MILITARY_TRAINING.primaryColor,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Bringe die Dienstgrade der Bundeswehr in die richtige Reihenfolge von niedrig (1) bis hoch (7)! Tippe zwei Karten an, um ihre Plätze zu tauschen.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        // Rangliste mit Karten
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            currentRankList.forEachIndexed { index, rank ->
                val isSelectedForSwap = selectedIndex == index
                val targetRank = initialRanks.getOrNull(index)
                val isCorrectPosition = rank.id == targetRank?.id

                val cardBorderColor = when {
                    isChecked && isCorrectPosition -> ChemistryColors.NeutralGreen
                    isChecked && !isCorrectPosition -> MaterialTheme.colorScheme.error
                    isSelectedForSwap -> Subject.MILITARY_TRAINING.primaryColor
                    else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                }

                val cardBgColor = when {
                    isChecked && isCorrectPosition -> ChemistryColors.NeutralGreen.copy(alpha = 0.15f)
                    isChecked && !isCorrectPosition -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
                    isSelectedForSwap -> Subject.MILITARY_TRAINING.primaryColor.copy(alpha = 0.2f)
                    else -> MaterialTheme.colorScheme.surface
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(width = 2.dp, color = cardBorderColor, shape = RoundedCornerShape(16.dp))
                        .clickable {
                            if (selectedIndex == null) {
                                selectedIndex = index
                            } else if (selectedIndex == index) {
                                selectedIndex = null
                            } else {
                                // Swap two items
                                val prevIndex = selectedIndex!!
                                val mutableList = currentRankList.toMutableList()
                                val temp = mutableList[prevIndex]
                                mutableList[prevIndex] = mutableList[index]
                                mutableList[index] = temp
                                currentRankList = mutableList
                                selectedIndex = null
                                isChecked = false
                            }
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = cardBgColor)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Subject.MILITARY_TRAINING.primaryColor.copy(alpha = 0.2f)
                        ) {
                            Box(modifier = Modifier.size(36.dp), contentAlignment = Alignment.Center) {
                                Text("${index + 1}", fontWeight = FontWeight.Bold, color = Subject.MILITARY_TRAINING.primaryColor)
                            }
                        }

                        Text(rank.insigniaIcon, fontSize = 20.sp)

                        Column(modifier = Modifier.weight(1f)) {
                            Text(rank.name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text(rank.rankGroup, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }

                        if (isChecked) {
                            Text(if (isCorrectPosition) "✓" else "✗", fontWeight = FontWeight.Bold, color = if (isCorrectPosition) ChemistryColors.NeutralGreen else MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }

        // Steuerung Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { isChecked = true },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Subject.MILITARY_TRAINING.primaryColor)
            ) {
                Text("Hierarchie prüfen 🔍", fontWeight = FontWeight.Bold, color = Color.White)
            }

            OutlinedButton(
                onClick = {
                    currentRankList = initialRanks.shuffled()
                    selectedIndex = null
                    isChecked = false
                    hasEarnedXp = false
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Neu mischen 🔄", fontWeight = FontWeight.Bold)
            }
        }

        if (isChecked && isPerfectOrder) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text("🎉 EXZELLENT! Alle 7 Dienstgrade perfekt geordnet! (+15 XP)", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer, textAlign = TextAlign.Center)
                    Text("Vom Gefreiten bis zum Oberst hast du die militärische Rangordnung gemeistert.", style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
                }
            }
        }
    }
}
