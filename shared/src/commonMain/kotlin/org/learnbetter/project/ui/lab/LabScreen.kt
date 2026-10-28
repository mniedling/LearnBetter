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
import org.learnbetter.project.model.Acid
import org.learnbetter.project.model.ExperimentState
import org.learnbetter.project.model.Metal
import org.learnbetter.project.ui.theme.ChemistryColors

@Composable
fun LabScreen(
    onXpEarned: (Int) -> Unit = {}
) {
    var state by remember { mutableStateOf(ExperimentState()) }

    // Coroutine für die chemische Reaktion basierend auf Metall AND Säure
    LaunchedEffect(state.isReacting, state.selectedMetal, state.selectedAcid) {
        if (state.isReacting && state.selectedMetal.isBaseMetal) {
            val acidSpeedFactor = if (state.selectedAcid.isStrong) 1.0f else 0.3f
            val speed = state.selectedMetal.reactivitySpeed * acidSpeedFactor

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
            text = "🧪 Säure & Metall-Labor",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Wähle eine Säure und ein Metall, um deren Reaktion zu erforschen!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        // 1. Reagenzglas Visualisierung
        TestTubeVisualizer(state = state)

        // 2. Info-Panel (Temperatur & Gasmenge)
        ExperimentStatsRow(state = state)

        // 3. Knallgasprobe Banner
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

        // 4. Säure-Auswahl
        AcidSelectionSection(
            selectedAcid = state.selectedAcid,
            enabled = !state.isReacting,
            onAcidSelected = { acid ->
                state = ExperimentState(selectedAcid = acid, selectedMetal = state.selectedMetal)
            }
        )

        // 5. Metall-Auswahl
        MetalSelectionSection(
            selectedMetal = state.selectedMetal,
            enabled = !state.isReacting,
            onMetalSelected = { metal ->
                state = ExperimentState(selectedAcid = state.selectedAcid, selectedMetal = metal)
            }
        )

        // 6. Steuerungs-Buttons
        ControlButtons(
            state = state,
            onStartReaction = {
                state = state.copy(isReacting = true)
            },
            onReset = {
                state = ExperimentState(selectedAcid = state.selectedAcid, selectedMetal = state.selectedMetal)
            }
        )

        // 7. Reaktionsgleichung & Didaktik
        ReactionFormulaCard(acid = state.selectedAcid, metal = state.selectedMetal)
    }
}

@Composable
private fun TestTubeVisualizer(state: ExperimentState) {
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
                // Säure-Flüssigkeit
                val acidColor = if (state.selectedAcid.isStrong) ChemistryColors.AcidRed.copy(alpha = 0.35f) else ChemistryColors.AcidOrange.copy(alpha = 0.35f)
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

                // Wasserstoff-Bläschen
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

            if (state.hydrogenGasAmount > 0f) {
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 12.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = ChemistryColors.HydrogenCyan.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = "💨 H₂ Gas: ${(state.hydrogenGasAmount * 10).toInt() / 10.0} ml",
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
        Card(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("🌡️", fontSize = 20.sp)
                Column {
                    Text("Temperatur", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("${state.temperatureCelsius.toInt()} °C", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
            }
        }

        Card(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("⚡", fontSize = 20.sp)
                Column {
                    Text("Säuretyp", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(if (state.selectedAcid.isStrong) "Stark" else "Schwach", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
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
        colors = CardDefaults.cardColors(containerColor = ChemistryColors.ReactionHeat)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("🔥", fontSize = 28.sp)
            Column(modifier = Modifier.weight(1f)) {
                Text("Knallgasprobe bereit!", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
                Text("Klicke hier für die Wasserstoff-Probe.", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.9f))
            }
            Surface(shape = RoundedCornerShape(12.dp), color = Color.White) {
                Text("Testen", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = ChemistryColors.ReactionHeat, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp))
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
                Text(if (success) "PLOPP! Knallgasprobe positiv! 🎉" else "Kein Knallgeräusch.", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(if (success) "Wasserstoff (H₂) hat gepufft! (+50 XP)" else "Nicht genügend Gas vorhanden.", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
private fun AcidSelectionSection(
    selectedAcid: Acid,
    enabled: Boolean,
    onAcidSelected: (Acid) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Säure auswählen:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Acid.entries.take(4).forEach { acid ->
                val isSelected = acid == selectedAcid
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(enabled = enabled) { onAcidSelected(acid) },
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 2.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(acid.formula, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = if (isSelected) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(acid.displayName, style = MaterialTheme.typography.labelSmall, fontSize = 10.sp, color = if (isSelected) MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
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
        Text("Metall auswählen:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

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
                        Text(metal.symbol, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(metal.displayName, style = MaterialTheme.typography.labelSmall, color = if (isSelected) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant)
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
            Text("🧪 Reaktion starten", fontWeight = FontWeight.Bold)
        }

        OutlinedButton(
            onClick = onReset,
            modifier = Modifier.height(50.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("🔄 Reset")
        }
    }
}

@Composable
private fun ReactionFormulaCard(acid: Acid, metal: Metal) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Gekoppelte Reaktion (${acid.displayName} + ${metal.displayName}):", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSecondaryContainer)
            Text(
                text = if (acid == Acid.HYDROCHLORIC) metal.reactionFormula else "${metal.symbol} + ${acid.formula} -> Salze + H₂ ↑",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(acid.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.9f))
        }
    }
}
