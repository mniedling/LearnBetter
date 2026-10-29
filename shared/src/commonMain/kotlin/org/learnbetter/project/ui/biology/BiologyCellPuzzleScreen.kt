package org.learnbetter.project.ui.biology

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
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
    var waterLevel by remember { mutableFloatStateOf(1.0f) } // 0.0 (Salzwasser/Plasmolyse) bis 1.0 (Turgordruck)

    var hasPlacedNucleus by remember { mutableStateOf(false) }
    var hasPlacedWall by remember { mutableStateOf(false) }
    var hasPlacedChloroplast by remember { mutableStateOf(false) }
    var hasPlacedMitochondrium by remember { mutableStateOf(false) }

    val isPlantPuzzleComplete = hasPlacedNucleus && hasPlacedWall && hasPlacedChloroplast && hasPlacedMitochondrium
    val isAnimalPuzzleComplete = hasPlacedNucleus && hasPlacedMitochondrium

    var hasTestedTurgor by remember { mutableStateOf(false) }

    LaunchedEffect(waterLevel) {
        if (!hasTestedTurgor && waterLevel < 0.3f) {
            hasTestedTurgor = true
            onXpEarned(20)
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
        if (onBackToHub != null) {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = onBackToHub) {
                    Text("◀️ Zurück zur Übersicht")
                }
            }
        }

        Text(
            text = if (showTurgorOnly) "🎈 Turgordruck & Plasmolyse" else "🧩 Pflanzenzelle vs. Tierzelle Puzzle",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Subject.BIOLOGY.primaryColor,
            textAlign = TextAlign.Center
        )

        Text(
            text = if (showTurgorOnly) "Bewege den Schieberegler von Salzwasser (Plasmolyse 🥀) bis Reines Wasser (Turgor 🌱)!" else "Baue eine Pflanzen- oder Tierzelle zusammen!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        if (!showTurgorOnly) {
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
            // Turgor & Plasmolyse Canvas Simulation
            TurgorPlasmolysisCanvas(waterLevel = waterLevel)

            // Schieberegler Wassergehalt / Umweltlösung
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Umgebungslösung anpassen:", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)

                    Slider(
                        value = waterLevel,
                        onValueChange = { waterLevel = (it * 100).toInt() / 100.0f },
                        valueRange = 0.0f..1.0f,
                        colors = SliderDefaults.colors(
                            thumbColor = Subject.BIOLOGY.primaryColor,
                            activeTrackColor = Subject.BIOLOGY.primaryColor
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("🥀 Salzwasser (Plasmolyse)", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        Text("🌱 Reines Wasser (Turgor)", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Erklärung Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = when {
                            waterLevel < 0.35f -> "🥀 PLASMOLYSE (Salzwasser-Umgebung):"
                            waterLevel < 0.7f -> "⚖️ NORMALZUSTAND:"
                            else -> "🌱 TURGORD RUCK / DEPLASMOLYSE (Reines Wasser):"
                        },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    Text(
                        text = when {
                            waterLevel < 0.35f -> "Wasser strömt per Osmose aus der Vakuole nach außen! Die Vakuole schrumpft zusammen, und die elastische Zellmembran löst sich schmerzlich von der starren Zellwand ab. Die Pflanze welkt!"
                            waterLevel < 0.7f -> "Das Ein- und Ausströmen von Wasser ist im Gleichgewicht."
                            else -> "Wasser wird gierig in die Vakuole gesaugt 🎈. Die Vakuole dehnt sich aus und drückt die Zellmembran stramm gegen die starre Zellwand (Holzrahmen 🪵). Das verleiht der Pflanze Standfestigkeit!"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}

@Composable
private fun TurgorPlasmolysisCanvas(waterLevel: Float) {
    val animatedWater by animateFloatAsState(
        targetValue = waterLevel,
        animationSpec = tween(durationMillis = 600)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
    ) {
        Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            val width = size.width
            val height = size.height
            val centerX = width * 0.5f
            val centerY = height * 0.5f

            // 1. Starre, unnachgiebige ZELLWAND (Äußerer Holzrahmen)
            val wallW = width * 0.78f
            val wallH = height * 0.75f
            drawRoundRect(
                color = Color(0xFF8D6E63),
                topLeft = Offset(centerX - (wallW / 2f), centerY - (wallH / 2f)),
                size = Size(wallW, wallH),
                cornerRadius = CornerRadius(16f, 16f),
                style = Stroke(8f)
            )

            // 2. Elastische ZELLMEMBRAN (Schrumpft bei Plasmolyse nach innen!)
            val membraneW = wallW * (0.55f + (0.43f * animatedWater))
            val membraneH = wallH * (0.55f + (0.43f * animatedWater))

            drawRoundRect(
                color = Color(0xFF22C55E).copy(alpha = 0.4f),
                topLeft = Offset(centerX - (membraneW / 2f), centerY - (membraneH / 2f)),
                size = Size(membraneW, membraneH),
                cornerRadius = CornerRadius(12f, 12f)
            )
            drawRoundRect(
                color = Color(0xFF4ADE80),
                topLeft = Offset(centerX - (membraneW / 2f), centerY - (membraneH / 2f)),
                size = Size(membraneW, membraneH),
                cornerRadius = CornerRadius(12f, 12f),
                style = Stroke(3f)
            )

            // 3. VAKUOLE (Der Wasserballon 🎈)
            val vacuoleW = membraneW * 0.65f
            val vacuoleH = membraneH * 0.65f

            drawOval(
                color = Color(0xFF0288D1).copy(alpha = 0.8f),
                topLeft = Offset(centerX - (vacuoleW / 2f), centerY - (vacuoleH / 2f)),
                size = Size(vacuoleW, vacuoleH)
            )

            // 4. Osmose Wasser-Pfeile
            val arrowColor = if (animatedWater < 0.5f) Color(0xFFEF4444) else Color(0xFF38BDF8)
            val arrowDir = if (animatedWater < 0.5f) -1f else 1f // Ausströmen vs. Einströmen

            for (i in 0..3) {
                val angle = (i * 90f) * (3.14159f / 180f)
                val startX = centerX + (membraneW / 2f + (15f * arrowDir)) * kotlin.math.cos(angle)
                val startY = centerY + (membraneH / 2f + (15f * arrowDir)) * kotlin.math.sin(angle)
                drawCircle(arrowColor, radius = 5f, center = Offset(startX, startY))
            }
        }
    }
}
