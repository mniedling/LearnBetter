package org.learnbetter.project.ui.lab

import androidx.compose.animation.core.*
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.learnbetter.project.model.ExperimentState
import org.learnbetter.project.model.Metal
import org.learnbetter.project.ui.theme.ChemistryColors

@Composable
fun LabScreen(
    onXpEarned: (Int) -> Unit = {}
) {
    var state by remember { mutableStateOf(ExperimentState()) }

    // Coroutine für die chemische Reaktion
    LaunchedEffect(state.isReacting, state.selectedMetal) {
        if (state.isReacting && state.selectedMetal.isBaseMetal) {
            val speed = state.selectedMetal.reactivitySpeed
            while (state.reactionProgress < 1.0f && state.isReacting) {
                delay(100)
                val newProgress = (state.reactionProgress + 0.05f * speed).coerceAtMost(1.0f)
                val newTemp = 20.0f + (35.0f * newProgress * speed)
                val newGas = 25.0f * newProgress * speed

                state = state.copy(
                    reactionProgress = newProgress,
                    temperatureCelsius = newTemp,
                    hydrogenGasAmount = newGas
                )
            }
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
        // Titel
        Text(
            text = "🧪 Virtuelles Säure-Labor",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Wähle ein Metall und gib Salzsäure (HCl) hinzu, um die Wasserstoff-Entwicklung zu beobachten!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        // 1. Reagenzglas Visualisierung
        TestTubeVisualizer(state = state)

        // 2. Info-Panel (Temperatur & Gasmenge)
        ExperimentStatsRow(state = state)

        // 3. Knallgasprobe Banner (falls Gas vorhanden)
        if (state.hydrogenGasAmount > 5.0f && !state.popTestDone) {
            PopTestBanner(
                onPerformPopTest = {
                    state = state.copy(
                        popTestDone = true,
                        popTestSuccess = true
                    )
                    onXpEarned(50)
                }
            )
        }

        if (state.popTestDone) {
            PopTestResultCard(success = state.popTestSuccess)
        }

        // 4. Metallauswahl
        MetalSelectionSection(
            selectedMetal = state.selectedMetal,
            enabled = !state.isReacting,
            onMetalSelected = { metal ->
                state = ExperimentState(selectedMetal = metal)
            }
        )

        // 5. Steuerungs-Buttons
        ControlButtons(
            state = state,
            onStartReaction = {
                state = state.copy(isReacting = true)
            },
            onReset = {
                state = ExperimentState(selectedMetal = state.selectedMetal)
            }
        )

        // 6. Reaktionsgleichung & Didaktik
        ReactionFormulaCard(metal = state.selectedMetal)
    }
}

@Composable
private fun TestTubeVisualizer(state: ExperimentState) {
    // Bläschen Partikel-Animation
    val transition = rememberInfiniteTransition(label = "bubbleTransition")
    val offsetY by transition.animateFloat(
        initialValue = 0f,
        targetValue = -100f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "bubbleY"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Reagenzglas Kontur
            Box(
                modifier = Modifier
                    .width(70.dp)
                    .height(200.dp)
                    .border(
                        width = 4.dp,
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(bottomStart = 35.dp, bottomEnd = 35.dp)
                    )
                    .clip(RoundedCornerShape(bottomStart = 35.dp, bottomEnd = 35.dp))
                    .background(Color.White.copy(alpha = 0.3f)),
                contentAlignment = Alignment.BottomCenter
            ) {
                // Flüssigkeit (Salzsäure)
                val acidColor = ChemistryColors.AcidRed.copy(alpha = 0.35f)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(acidColor, acidColor.copy(alpha = 0.6f))
                            )
                        )
                )

                // Metallstück am Boden
                Box(
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                        .size(28.dp, 16.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            if (state.selectedMetal == Metal.COPPER) Color(0xFFB87333) else Color.LightGray
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.selectedMetal.symbol,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                // Wasserstoff-Bläschen (Animation)
                if (state.isReacting && state.selectedMetal.isBaseMetal && state.reactionProgress < 1.0f) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 30.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        repeat(3) { index ->
                            Box(
                                modifier = Modifier
                                    .offset(y = (offsetY + (index * 25)).dp)
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(ChemistryColors.HydrogenCyan)
                            )
                        }
                    }
                }
            }

            // Gas-Anzeige oben am Reagenzglas
            if (state.hydrogenGasAmount > 0f) {
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 12.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = ChemistryColors.HydrogenCyan.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = "💨 H₂ Gas gesammelt: ${(state.hydrogenGasAmount * 10).toInt() / 10.0} ml",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ExperimentStatsRow(state: ExperimentState) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Temperatur
        Card(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("🌡️", fontSize = 20.sp)
                Column {
                    Text(
                        text = "Temperatur",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${state.temperatureCelsius.toInt()} °C",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Reaktionstyp
        Card(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("⚡", fontSize = 20.sp)
                Column {
                    Text(
                        text = "Typ",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = if (state.selectedMetal.isBaseMetal) "Exotherm" else "Keine Reakti.",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun PopTestBanner(onPerformPopTest: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onPerformPopTest() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = ChemistryColors.ReactionHeat
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("🔥", fontSize = 28.sp)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Knallgasprobe bereit!",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Klicke hier, um ein brennendes Spanholz hinzuzuhalten.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color.White
            ) {
                Text(
                    text = "Testen",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = ChemistryColors.ReactionHeat,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
private fun PopTestResultCard(success: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (success) ChemistryColors.NeutralGreen.copy(alpha = 0.2f) else MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(if (success) "💥" else "❓", fontSize = 28.sp)
            Column {
                Text(
                    text = if (success) "PLOPP! Knallgasprobe positiv! 🎉" else "Kein Knallgeräusch.",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (success) "Der Wasserstoff (H₂) hat pfeifend mit dem Sauerstoff reagiert! (+50 XP)" else "Es hat sich noch nicht genügend Gas gesammelt.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun MetalSelectionSection(
    selectedMetal: Metal,
    enabled: Boolean,
    onMetalSelected: (Metal) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Metall auswählen:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Metal.entries.forEach { metal ->
                val isSelected = metal == selectedMetal
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(enabled = enabled) { onMetalSelected(metal) },
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = metal.symbol,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = metal.displayName,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ControlButtons(
    state: ExperimentState,
    onStartReaction: () -> Unit,
    onReset: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = onStartReaction,
            enabled = !state.isReacting,
            modifier = Modifier
                .weight(1f)
                .height(50.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "🧪 Säure hinzugeben",
                fontWeight = FontWeight.Bold
            )
        }

        OutlinedButton(
            onClick = onReset,
            modifier = Modifier
                .height(50.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = "🔄 Reset")
        }
    }
}

@Composable
private fun ReactionFormulaCard(metal: Metal) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Reaktionsgleichung:",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(
                text = metal.reactionFormula,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(
                text = metal.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.9f)
            )
        }
    }
}
