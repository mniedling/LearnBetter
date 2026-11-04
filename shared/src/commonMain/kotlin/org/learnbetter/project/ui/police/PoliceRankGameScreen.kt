package org.learnbetter.project.ui.police

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.learnbetter.project.model.PoliceData
import org.learnbetter.project.model.PoliceRank
import org.learnbetter.project.model.PoliceServiceGroup
import org.learnbetter.project.model.Subject
import org.learnbetter.project.ui.theme.ChemistryColors

@Composable
fun PoliceRankGameScreen(
    onXpEarned: (Int) -> Unit = {}
) {
    val uriHandler = LocalUriHandler.current

    var selectedGroup by remember { mutableStateOf(PoliceServiceGroup.MITTLERER_DIENST) }

    val filteredInitialRanks = remember(selectedGroup) {
        PoliceData.ranks.filter { it.serviceGroup == selectedGroup }
    }

    var currentRankList by remember(selectedGroup) {
        mutableStateOf(filteredInitialRanks.shuffled())
    }

    var selectedIndex by remember(selectedGroup) { mutableStateOf<Int?>(null) }
    var isChecked by remember(selectedGroup) { mutableStateOf(false) }

    val isPerfectOrder = currentRankList.zip(filteredInitialRanks).all { (current, target) -> current.id == target.id }

    var hasEarnedXp by remember(selectedGroup) { mutableStateOf(false) }

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
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "👮‍♂️ Dienstgrade der Bundespolizei",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Subject.POLICE_TRAINING.primaryColor,
            textAlign = TextAlign.Center
        )

        // Laufbahn-Auswahl Pills
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PoliceServiceGroup.entries.forEach { group ->
                val isSelected = selectedGroup == group
                Surface(
                    modifier = Modifier.clickable { selectedGroup = group },
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) Subject.POLICE_TRAINING.primaryColor else MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = group.displayName,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
            }
        }

        Text(
            text = "Ordne die Dienstgrade der Bundespolizei nach der Anzahl ihrer Sterne! Tippe 2 Karten an, um ihre Plätze zu tauschen.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        // Rangliste
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            currentRankList.forEachIndexed { index, rank ->
                val isSelectedForSwap = selectedIndex == index
                val targetRank = filteredInitialRanks.getOrNull(index)
                val isCorrectPosition = rank.id == targetRank?.id

                val cardBorderColor = when {
                    isChecked && isCorrectPosition -> ChemistryColors.NeutralGreen
                    isChecked && !isCorrectPosition -> MaterialTheme.colorScheme.error
                    isSelectedForSwap -> Subject.POLICE_TRAINING.primaryColor
                    else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                }

                val cardBgColor = when {
                    isChecked && isCorrectPosition -> ChemistryColors.NeutralGreen.copy(alpha = 0.15f)
                    isChecked && !isCorrectPosition -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
                    isSelectedForSwap -> Subject.POLICE_TRAINING.primaryColor.copy(alpha = 0.2f)
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
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Subject.POLICE_TRAINING.primaryColor.copy(alpha = 0.2f)
                        ) {
                            Box(modifier = Modifier.size(32.dp), contentAlignment = Alignment.Center) {
                                Text("${index + 1}", fontWeight = FontWeight.Bold, color = Subject.POLICE_TRAINING.primaryColor)
                            }
                        }

                        // HD POLIZEI SCHULTERKLAPPE VEKTOR
                        PoliceInsigniaCanvas(rank = rank)

                        Column(modifier = Modifier.weight(1f)) {
                            Text(rank.name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text(rank.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }

                        if (isChecked) {
                            Text(if (isCorrectPosition) "✓" else "✗", fontWeight = FontWeight.Bold, color = if (isCorrectPosition) ChemistryColors.NeutralGreen else MaterialTheme.colorScheme.error, fontSize = 20.sp)
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
                colors = ButtonDefaults.buttonColors(containerColor = Subject.POLICE_TRAINING.primaryColor)
            ) {
                Text("Rangordnung prüfen 🔍", fontWeight = FontWeight.Bold, color = Color.White)
            }

            OutlinedButton(
                onClick = {
                    currentRankList = filteredInitialRanks.shuffled()
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
                    Text("🎉 PERFEKT! Alle Dienstgrade dieser Laufbahn richtig geordnet! (+15 XP)", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer, textAlign = TextAlign.Center)
                }
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
                    Text("🌐 Karriere-Portal (komm-zur-bundespolizei.de) ➔", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.padding(10.dp))
                }
            }
        }
    }
}

@Composable
private fun PoliceInsigniaCanvas(rank: PoliceRank) {
    Box(
        modifier = Modifier
            .size(54.dp, 72.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF0F172A)), // Polizeiblau / Dunkelblau
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize().padding(4.dp)) {
            val w = size.width
            val h = size.height
            val cx = w / 2f

            // Schulterklappe Rand
            drawRoundRect(
                color = Color(0xFF1E293B),
                topLeft = Offset(2f, 2f),
                size = Size(w - 4f, h - 4f),
                cornerRadius = CornerRadius(6f, 6f)
            )
            drawRoundRect(
                color = Color(0xFF0288D1),
                topLeft = Offset(2f, 2f),
                size = Size(w - 4f, h - 4f),
                cornerRadius = CornerRadius(6f, 6f),
                style = Stroke(2f)
            )

            // Sternenfarbe
            val starColor = when (rank.serviceGroup) {
                PoliceServiceGroup.MITTLERER_DIENST -> Color(0xFF0288D1)  // Blau
                PoliceServiceGroup.GEHOBENER_DIENST -> Color(0xFFE2E8F0)  // Silber
                PoliceServiceGroup.HOEHERER_DIENST -> Color(0xFFFFD700)    // Gold
            }

            // Sterne zeichnen
            val count = rank.starsCount
            val startY = h - 16f
            val spacing = 12f

            for (s in 0 until count) {
                val sy = startY - (s * spacing)
                drawCircle(starColor, radius = 4.5f, center = Offset(cx, sy))
            }
        }
    }
}
