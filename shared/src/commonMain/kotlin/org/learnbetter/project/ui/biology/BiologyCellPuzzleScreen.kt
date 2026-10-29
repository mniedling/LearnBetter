package org.learnbetter.project.ui.biology

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.learnbetter.project.model.Subject

@Composable
fun BiologyCellPuzzleScreen(
    showTurgorOnly: Boolean = false,
    onXpEarned: (Int) -> Unit = {},
    onBackToHub: (() -> Unit)? = null
) {
    var cellTypeMode by remember { mutableIntStateOf(1) } // 1 = Pflanze, 2 = Tier
    var isTurgorInflated by remember { mutableStateOf(false) }

    var hasPlacedNucleus by remember { mutableStateOf(false) }
    var hasPlacedWall by remember { mutableStateOf(false) }
    var hasPlacedChloroplast by remember { mutableStateOf(false) }
    var hasPlacedMitochondrium by remember { mutableStateOf(false) }

    val isPlantPuzzleComplete = hasPlacedNucleus && hasPlacedWall && hasPlacedChloroplast && hasPlacedMitochondrium
    val isAnimalPuzzleComplete = hasPlacedNucleus && hasPlacedMitochondrium

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (onBackToHub != null) {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = onBackToHub) {
                    Text("◀️ Zurück zur Übersicht")
                }
            }
        }

        Text(
            text = if (showTurgorOnly) "🎈 Turgor-Experiment" else "🧩 Pflanzenzelle vs. Tierzelle Puzzle",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Subject.BIOLOGY.primaryColor,
            textAlign = TextAlign.Center
        )

        Text(
            text = if (showTurgorOnly) "Erforsche wie Wasser in der Vakuole gegen die starre Zellwand drückt!" else "Baue eine Pflanzen- oder Tierzelle zusammen!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        if (!showTurgorOnly) {
            // Switcher Pflanzenzelle vs. Tierzelle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        cellTypeMode = 1
                        hasPlacedNucleus = false
                        hasPlacedWall = false
                        hasPlacedChloroplast = false
                        hasPlacedMitochondrium = false
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (cellTypeMode == 1) Subject.BIOLOGY.primaryColor else MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text("🌱 Pflanzenzelle", fontWeight = FontWeight.Bold, color = if (cellTypeMode == 1) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
                }

                Button(
                    onClick = {
                        cellTypeMode = 2
                        hasPlacedNucleus = false
                        hasPlacedWall = false
                        hasPlacedChloroplast = false
                        hasPlacedMitochondrium = false
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (cellTypeMode == 2) Subject.BIOLOGY.primaryColor else MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text("🦁 Tierzelle", fontWeight = FontWeight.Bold, color = if (cellTypeMode == 2) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            // Interactive Cell Construction Canvas
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1B2A1E))
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    val isPlant = cellTypeMode == 1
                    Box(
                        modifier = Modifier
                            .size(if (isPlant) 220.dp else 190.dp, 160.dp)
                            .clip(if (isPlant) RoundedCornerShape(20.dp) else CircleShape)
                            .background(if (hasPlacedWall && isPlant) Color(0xFF388E3C) else Color(0xFF2E7D32).copy(alpha = 0.5f))
                            .border(
                                width = if (hasPlacedWall && isPlant) 6.dp else 2.dp,
                                color = if (hasPlacedWall && isPlant) Color(0xFF8D6E63) else Color.White.copy(alpha = 0.6f),
                                shape = if (isPlant) RoundedCornerShape(20.dp) else CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = if (isPlant) "Pflanzenzelle" else "Tierzelle",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (hasPlacedNucleus) Surface(shape = CircleShape, color = Color(0xFF6A1B9A)) { Text("🟣 Kern", color = Color.White, modifier = Modifier.padding(4.dp), fontSize = 10.sp) }
                                if (hasPlacedChloroplast && isPlant) Surface(shape = CircleShape, color = Color(0xFF2E7D32)) { Text("🟢 Chloroplast", color = Color.White, modifier = Modifier.padding(4.dp), fontSize = 10.sp) }
                                if (hasPlacedMitochondrium) Surface(shape = CircleShape, color = Color(0xFFC62828)) { Text("🔴 Mito", color = Color.White, modifier = Modifier.padding(4.dp), fontSize = 10.sp) }
                            }
                        }
                    }
                }
            }

            // Puzzle Teile
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Bestandteile einbauen:", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                hasPlacedNucleus = true
                                if (cellTypeMode == 2 && isAnimalPuzzleComplete) onXpEarned(15)
                            },
                        shape = RoundedCornerShape(12.dp),
                        color = if (hasPlacedNucleus) Color(0xFF6A1B9A) else MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text("🟣 Zellkern", fontWeight = FontWeight.Bold, color = if (hasPlacedNucleus) Color.White else MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(10.dp), textAlign = TextAlign.Center)
                    }

                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                hasPlacedMitochondrium = true
                            },
                        shape = RoundedCornerShape(12.dp),
                        color = if (hasPlacedMitochondrium) Color(0xFFC62828) else MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text("🔴 Mitochondrium", fontWeight = FontWeight.Bold, color = if (hasPlacedMitochondrium) Color.White else MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(10.dp), textAlign = TextAlign.Center)
                    }
                }

                if (cellTypeMode == 1) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    hasPlacedWall = true
                                },
                            shape = RoundedCornerShape(12.dp),
                            color = if (hasPlacedWall) Color(0xFF8D6E63) else MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text("🛡️ Zellwand", fontWeight = FontWeight.Bold, color = if (hasPlacedWall) Color.White else MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(10.dp), textAlign = TextAlign.Center)
                        }

                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    hasPlacedChloroplast = true
                                    if (isPlantPuzzleComplete) onXpEarned(20)
                                },
                            shape = RoundedCornerShape(12.dp),
                            color = if (hasPlacedChloroplast) Color(0xFF2E7D32) else MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text("🟢 Chloroplast", fontWeight = FontWeight.Bold, color = if (hasPlacedChloroplast) Color.White else MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(10.dp), textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        }

        if (showTurgorOnly) {
            // Turgor Experiment (Luftballon & Holzrahmen)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("🎈 Turgor-Experiment (Luftballon in Holzrahmen):", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSecondaryContainer)
                    Text(
                        text = "Die Vakuole saugt Wasser auf wie ein Luftballon 🎈. Ohne feste Zellwand würde die Zelle schlaff sein oder platzen. Die starre Zellwand (Holzrahmen 🪵) hält den Innendruck (Turgor) stand – so stehen Pflanzen stramm aufrecht!",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )

                    Button(
                        onClick = { isTurgorInflated = !isTurgorInflated },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(if (isTurgorInflated) "💧 Wasser ablassen (Welken 🥀)" else "💧 Wasser in Vakuole pumpen (Turgor 🔥)", fontWeight = FontWeight.Bold)
                    }

                    if (isTurgorInflated) {
                        Text("✨ Turgordruck stabilisiert die Pflanze! Die Zellwand verhindert das Platzen.", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = Subject.BIOLOGY.primaryColor)
                    }
                }
            }
        }
    }
}
