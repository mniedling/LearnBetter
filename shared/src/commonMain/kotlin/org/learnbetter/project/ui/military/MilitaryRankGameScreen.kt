package org.learnbetter.project.ui.military

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
import org.learnbetter.project.model.MilitaryBranch
import org.learnbetter.project.model.MilitaryData
import org.learnbetter.project.model.MilitaryRank
import org.learnbetter.project.model.RankCategory
import org.learnbetter.project.model.Subject
import org.learnbetter.project.ui.theme.ChemistryColors

@Composable
fun MilitaryRankGameScreen(
    onXpEarned: (Int) -> Unit = {}
) {
    val uriHandler = LocalUriHandler.current

    var selectedBranch by remember { mutableStateOf(MilitaryBranch.HEER_LUFTWAFFE) }
    var selectedCategory by remember { mutableStateOf(RankCategory.MANNSCHAFTEN) }

    val filteredInitialRanks = remember(selectedBranch, selectedCategory) {
        MilitaryData.ranks.filter { it.branch == selectedBranch && it.category == selectedCategory }
    }

    var currentRankList by remember(selectedBranch, selectedCategory) {
        mutableStateOf(filteredInitialRanks.shuffled())
    }

    var selectedIndex by remember(selectedBranch, selectedCategory) { mutableStateOf<Int?>(null) }
    var isChecked by remember(selectedBranch, selectedCategory) { mutableStateOf(false) }

    val isPerfectOrder = currentRankList.zip(filteredInitialRanks).all { (current, target) -> current.id == target.id }

    var hasEarnedXp by remember(selectedBranch, selectedCategory) { mutableStateOf(false) }

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
            text = "🎖️ Schulterklappen & Dienstgrad Trainer",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Subject.MILITARY_TRAINING.primaryColor,
            textAlign = TextAlign.Center
        )

        // 1. Teilstreitkraft-Wahl (Heer/Luftwaffe vs Marine)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { selectedBranch = MilitaryBranch.HEER_LUFTWAFFE },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedBranch == MilitaryBranch.HEER_LUFTWAFFE) Subject.MILITARY_TRAINING.primaryColor else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text("🟢 Heer & 🔵 Luftwaffe", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = if (selectedBranch == MilitaryBranch.HEER_LUFTWAFFE) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Button(
                onClick = { selectedBranch = MilitaryBranch.MARINE },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedBranch == MilitaryBranch.MARINE) Color(0xFF0D47A1) else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text("⚓ Marine", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = if (selectedBranch == MilitaryBranch.MARINE) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        // 2. Dienstgradgruppen Kategorie Pills
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            RankCategory.entries.forEach { category ->
                val isSelected = selectedCategory == category
                Surface(
                    modifier = Modifier.clickable { selectedCategory = category },
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) Subject.MILITARY_TRAINING.primaryColor else MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = "${category.icon} ${category.displayName}",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
            }
        }

        Text(
            text = "Ordne die Schulterklappen in die richtige Rangordnung von niedrig nach hoch! Tippe 2 Karten an, um sie zu tauschen.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        // Rangliste mit Karten
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            currentRankList.forEachIndexed { index, rank ->
                val isSelectedForSwap = selectedIndex == index
                val targetRank = filteredInitialRanks.getOrNull(index)
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
                            color = Subject.MILITARY_TRAINING.primaryColor.copy(alpha = 0.2f)
                        ) {
                            Box(modifier = Modifier.size(32.dp), contentAlignment = Alignment.Center) {
                                Text("${index + 1}", fontWeight = FontWeight.Bold, color = Subject.MILITARY_TRAINING.primaryColor)
                            }
                        }

                        // HD SCHULTERKLAPPE VEKTOR-GRAFIK
                        InsigniaCanvas(rank = rank)

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
                colors = ButtonDefaults.buttonColors(containerColor = Subject.MILITARY_TRAINING.primaryColor)
            ) {
                Text("Hierarchie prüfen 🔍", fontWeight = FontWeight.Bold, color = Color.White)
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
                    Text("🎉 PERFEKT! Alle Schulterklappen dieser Gruppe exakt geordnet! (+15 XP)", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer, textAlign = TextAlign.Center)
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
                Text("🔗 Offizielle Dienstgrad-Übersicht & Verifizierung:", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)

                Surface(
                    modifier = Modifier.fillMaxWidth().clickable {
                        uriHandler.openUri("https://www.bundeswehrkarriere.de/entdecker/karriere-infos/jugendportal/dienstgrade-der-bundeswehr")
                    },
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text("🌐 Dienstgrade & Schulterklappen (bundeswehrkarriere.de) ➔", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.padding(10.dp))
                }
            }
        }
    }
}

@Composable
private fun InsigniaCanvas(rank: MilitaryRank) {
    Box(
        modifier = Modifier
            .size(54.dp, 72.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (rank.branch == MilitaryBranch.MARINE) Color(0xFF0F172A) else Color(0xFF334155)),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize().padding(4.dp)) {
            val w = size.width
            val h = size.height
            val cx = w / 2f
            val cy = h / 2f

            val flapPath = Path()
            flapPath.moveTo(4f, 4f)
            flapPath.lineTo(w - 4f, 4f)
            flapPath.lineTo(w - 8f, h - 4f)
            flapPath.lineTo(8f, h - 4f)
            flapPath.close()

            drawPath(
                path = flapPath,
                color = if (rank.branch == MilitaryBranch.MARINE) Color(0xFF1E293B) else Color(0xFF1F2937)
            )
            drawPath(
                path = flapPath,
                color = if (rank.branch == MilitaryBranch.MARINE) Color(0xFF3B82F6) else Color(0xFF10B981),
                style = Stroke(2f)
            )

            val goldColor = Color(0xFFFFD700)
            val silverColor = Color(0xFFE2E8F0)

            when (rank.insigniaCode) {
                "BAR_1" -> drawLine(silverColor, start = Offset(8f, h - 15f), end = Offset(w - 8f, h - 25f), strokeWidth = 5f)
                "BAR_2" -> {
                    drawLine(silverColor, start = Offset(8f, h - 15f), end = Offset(w - 8f, h - 25f), strokeWidth = 4f)
                    drawLine(silverColor, start = Offset(8f, h - 25f), end = Offset(w - 8f, h - 35f), strokeWidth = 4f)
                }
                "BAR_3" -> {
                    drawLine(silverColor, start = Offset(8f, h - 15f), end = Offset(w - 8f, h - 25f), strokeWidth = 3f)
                    drawLine(silverColor, start = Offset(8f, h - 23f), end = Offset(w - 8f, h - 33f), strokeWidth = 3f)
                    drawLine(silverColor, start = Offset(8f, h - 31f), end = Offset(w - 8f, h - 41f), strokeWidth = 3f)
                }
                "BAR_4", "KORPORAL" -> {
                    val loopPath = Path()
                    loopPath.moveTo(cx - 12f, h - 10f)
                    loopPath.lineTo(cx, h - 30f)
                    loopPath.lineTo(cx + 12f, h - 10f)
                    drawPath(loopPath, silverColor, style = Stroke(4f))
                }
                "NCO_1", "NCO_2" -> {
                    val ncoPath = Path()
                    ncoPath.moveTo(8f, h - 10f)
                    ncoPath.lineTo(8f, 15f)
                    ncoPath.lineTo(w - 8f, 15f)
                    ncoPath.lineTo(w - 8f, h - 10f)
                    drawPath(ncoPath, silverColor, style = Stroke(5f))
                }
                "NCO_3", "NCO_4", "NCO_5" -> {
                    val chevrons = if (rank.insigniaCode == "NCO_3") 1 else if (rank.insigniaCode == "NCO_4") 2 else 3
                    for (c in 0 until chevrons) {
                        val yOffset = c * 10f
                        val chev = Path()
                        chev.moveTo(10f, h - 15f - yOffset)
                        chev.lineTo(cx, h - 30f - yOffset)
                        chev.lineTo(w - 10f, h - 15f - yOffset)
                        drawPath(chev, silverColor, style = Stroke(4f))
                    }
                }
                "OFF_1" -> drawCircle(silverColor, radius = 6f, center = Offset(cx, cy))
                "OFF_2" -> {
                    drawCircle(silverColor, radius = 5f, center = Offset(cx, cy - 12f))
                    drawCircle(silverColor, radius = 5f, center = Offset(cx, cy + 12f))
                }
                "OFF_3" -> {
                    drawCircle(silverColor, radius = 5f, center = Offset(cx, cy - 18f))
                    drawCircle(silverColor, radius = 5f, center = Offset(cx, cy))
                    drawCircle(silverColor, radius = 5f, center = Offset(cx, cy + 18f))
                }
                "OFF_4", "OFF_5", "OFF_6" -> {
                    drawCircle(silverColor, radius = 12f, center = Offset(cx, h - 18f), style = Stroke(3f))
                    val stars = if (rank.insigniaCode == "OFF_4") 1 else if (rank.insigniaCode == "OFF_5") 2 else 3
                    for (s in 0 until stars) {
                        drawCircle(silverColor, radius = 5f, center = Offset(cx, 12f + (s * 12f)))
                    }
                }
                "GEN_1", "GEN_2", "GEN_3", "GEN_4" -> {
                    drawCircle(goldColor, radius = 12f, center = Offset(cx, h - 18f), style = Stroke(3f))
                    val stars = when(rank.insigniaCode) { "GEN_1" -> 1; "GEN_2" -> 2; "GEN_3" -> 3; else -> 4 }
                    for (s in 0 until stars) {
                        drawCircle(goldColor, radius = 5f, center = Offset(cx, 12f + (s * 10f)))
                    }
                }
                "NAVY_1" -> drawLine(goldColor, start = Offset(8f, h - 15f), end = Offset(w - 8f, h - 25f), strokeWidth = 5f)
                "NAVY_2" -> {
                    drawLine(goldColor, start = Offset(8f, h - 15f), end = Offset(w - 8f, h - 25f), strokeWidth = 4f)
                    drawLine(goldColor, start = Offset(8f, h - 25f), end = Offset(w - 8f, h - 35f), strokeWidth = 4f)
                }
                "NAVY_MAAT" -> {
                    val maatPath = Path()
                    maatPath.moveTo(10f, h - 15f)
                    maatPath.lineTo(cx, h - 30f)
                    maatPath.lineTo(w - 10f, h - 15f)
                    drawPath(maatPath, goldColor, style = Stroke(5f))
                }
                "NAVY_BOOTSMANN" -> {
                    drawCircle(goldColor, radius = 10f, center = Offset(cx, cy))
                    drawLine(goldColor, start = Offset(cx - 10f, h - 15f), end = Offset(cx + 10f, h - 15f), strokeWidth = 4f)
                }
                "NAVY_OFF_1", "NAVY_OFF_2", "NAVY_OFF_3", "NAVY_ADMIRAL" -> {
                    drawCircle(goldColor, radius = 6f, center = Offset(cx, 15f))
                    drawLine(goldColor, start = Offset(6f, h - 15f), end = Offset(w - 6f, h - 15f), strokeWidth = 6f)
                    if (rank.insigniaCode != "NAVY_OFF_1") {
                        drawLine(goldColor, start = Offset(6f, h - 25f), end = Offset(w - 6f, h - 25f), strokeWidth = 4f)
                    }
                }
            }
        }
    }
}
