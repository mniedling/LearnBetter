package org.learnbetter.project.ui.lab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import kotlinx.coroutines.delay
import org.learnbetter.project.ui.theme.ChemistryColors

@Composable
fun MagnesiumBurnScreen(
    onXpEarned: (Int) -> Unit = {},
    onBackToHub: () -> Unit = {}
) {
    var isBurnerLit by remember { mutableStateOf(false) }
    var isHoldingInFlame by remember { mutableStateOf(false) }
    var isBurningGlow by remember { mutableStateOf(false) }
    var isBurnComplete by remember { mutableStateOf(false) }

    // Animation Effect when holding strip in flame
    LaunchedEffect(isHoldingInFlame) {
        if (isHoldingInFlame && !isBurnComplete) {
            delay(1500) // 1.5 Seconds in flame to ignite
            isBurningGlow = true
            delay(2000) // Gleißendes Licht brennt für 2 Sek
            isBurningGlow = false
            isBurnComplete = true
            onXpEarned(40)
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
        // Back Navigation Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onBackToHub) {
                Text("◀️ Zurück zum Labor-Zentrum")
            }
        }

        Text(
            text = "🔥 Magnesiumband entzünden",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Entzünde ein silbriges Magnesiumband über der Bunsenbrennerflamme und beobachte die gleißende Oxidation!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        // 1. Interactive Flame Visualizer
        BurnExperimentVisualizer(
            isBurnerLit = isBurnerLit,
            isHoldingInFlame = isHoldingInFlame,
            isBurningGlow = isBurningGlow,
            isBurnComplete = isBurnComplete
        )

        // 2. Control Steps
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Step 1
            Button(
                onClick = { isBurnerLit = !isBurnerLit },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isBurnerLit) ChemistryColors.ReactionHeat else MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = if (isBurnerLit) "🔥 Bunsenbrenner ausschalten" else "🔥 1. Bunsenbrenner entzünden",
                    fontWeight = FontWeight.Bold
                )
            }

            // Step 2
            Button(
                onClick = { isHoldingInFlame = true },
                enabled = isBurnerLit && !isHoldingInFlame && !isBurnComplete,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = if (isHoldingInFlame && !isBurnComplete) "⚡ Entzündet sich in der Flamme..." else "🥢 2. Magnesiumband in die Flamme halten",
                    fontWeight = FontWeight.Bold
                )
            }

            // Reset Button
            if (isBurnComplete) {
                OutlinedButton(
                    onClick = {
                        isBurnerLit = false
                        isHoldingInFlame = false
                        isBurningGlow = false
                        isBurnComplete = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("🔄 Neues Magnesiumband nehmen")
                }
            }
        }

        // 3. Result & Reaction Formula
        if (isBurnComplete) {
            BurnResultCard()
        }
    }
}

@Composable
private fun BurnExperimentVisualizer(
    isBurnerLit: Boolean,
    isHoldingInFlame: Boolean,
    isBurningGlow: Boolean,
    isBurnComplete: Boolean
) {
    val transition = rememberInfiniteTransition(label = "flameTransition")
    val flameScale by transition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 300, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "flameScale"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isBurningGlow) Color.White else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isBurningGlow) {
                // Gleißend helles Licht Effektscreen
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("✨ ⚡ ✨", fontSize = 48.sp)
                    Text(
                        text = "GLEISSEND HELLES LICHT!",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFD700)
                    )
                    Text(
                        text = "Achtung: Nicht direkt in die Flamme blicken!",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.DarkGray
                    )
                }
            } else {
                // Bunsenbrenner & Magnesiumband
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.fillMaxHeight().padding(bottom = 16.dp)
                ) {
                    // Magnesiumband / Tiegelzange
                    if (isHoldingInFlame) {
                        Box(
                            modifier = Modifier
                                .width(12.dp)
                                .height(70.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(
                                    if (isBurnComplete) Color(0xFFEFEFEF) else Color(0xFFC0C0C0)
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (isBurnComplete) Color.LightGray else Color.Gray,
                                    shape = RoundedCornerShape(4.dp)
                                )
                        )
                    }

                    // Flamme
                    if (isBurnerLit) {
                        Box(
                            modifier = Modifier
                                .size((30 * flameScale).dp, (50 * flameScale).dp)
                                .clip(CircleShape)
                                .background(
                                    androidx.compose.ui.graphics.Brush.verticalGradient(
                                        colors = listOf(Color(0xFF00E5FF), Color(0xFFFF6D00))
                                    )
                                )
                        )
                    }

                    // Bunsenbrenner Sockel
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(60.dp)
                            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                            .background(Color.DarkGray)
                    )
                }
            }
        }
    }
}

@Composable
private fun BurnResultCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("🤍", fontSize = 28.sp)
                Column {
                    Text(
                        text = "Magnesiumoxid (MgO) entstanden!",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Das silbrige Metall hat sich in ein weißes, sprödes Pulver verwandelt. (+40 XP)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                    )
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))

            Text(
                text = "Reaktionsgleichung (Oxidation):",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Text(
                text = "2 Mg + O₂  ➔  2 MgO  +  Energie",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}
