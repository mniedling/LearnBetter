package org.learnbetter.project.ui.lab

import androidx.compose.animation.animateColorAsState
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
import org.learnbetter.project.model.Acid
import org.learnbetter.project.model.Subject
import org.learnbetter.project.ui.theme.ChemistryColors

@Composable
fun AcidIonsTrainerScreen(
    onXpEarned: (Int) -> Unit = {},
    onBackToHub: (() -> Unit)? = null
) {
    var isTrainerMode by remember { mutableStateOf(true) }

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
                    Text("◀️ Zurück zum Labor-Zentrum")
                }
            }
        }

        Text(
            text = "🧪 Säuren- & Ion-Laborkolben",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Subject.CHEMISTRY.primaryColor,
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { isTrainerMode = true },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isTrainerMode) Subject.CHEMISTRY.primaryColor else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(
                    text = "🧪 Kolben-Werkstatt",
                    color = if (isTrainerMode) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = { isTrainerMode = false },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isTrainerMode) Subject.CHEMISTRY.primaryColor else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(
                    text = "📖 Übersichtstabelle",
                    color = if (!isTrainerMode) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (isTrainerMode) {
            LabFlaskDissoziationGame(onXpEarned = onXpEarned)
        } else {
            AcidIonsReferenceTable()
        }
    }
}

@Composable
private fun LabFlaskDissoziationGame(onXpEarned: (Int) -> Unit) {
    val acids = Acid.entries
    var currentAcidIndex by remember { mutableIntStateOf(0) }
    var selectedFormula by remember { mutableStateOf<String?>(null) }
    var waterAdded by remember { mutableStateOf(false) }
    var selectedIonName by remember { mutableStateOf<String?>(null) }

    val currentAcid = acids[currentAcidIndex % acids.size]

    val formulaOptions = remember(currentAcidIndex) {
        (acids.map { it.formula }.shuffled().take(3) + currentAcid.formula).distinct().shuffled()
    }

    val ionOptions = remember(currentAcidIndex, waterAdded) {
        (acids.map { it.ionName }.shuffled().take(3) + currentAcid.ionName).distinct().shuffled()
    }

    val isFormulaCorrect = selectedFormula == currentAcid.formula
    val isIonCorrect = selectedIonName == currentAcid.ionName

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Labor-Aufgabe: ${currentAcid.displayName}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Erlenmeyerkolben Visualizer
            FlaskVisualizer(
                acid = currentAcid,
                hasFormula = isFormulaCorrect,
                hasWater = waterAdded,
                hasIon = isIonCorrect
            )

            // Phase 1: Säureformel zuordnen
            if (!isFormulaCorrect) {
                Text("1. Wähle die richtige Formel für ${currentAcid.displayName}:", style = MaterialTheme.typography.bodyMedium)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    formulaOptions.forEach { formula ->
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedFormula = formula },
                            shape = RoundedCornerShape(12.dp),
                            color = if (selectedFormula == formula) Subject.CHEMISTRY.primaryColor else MaterialTheme.colorScheme.surface
                        ) {
                            Text(
                                text = formula,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(12.dp),
                                color = if (selectedFormula == formula) Color.White else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            } else if (!waterAdded) {
                // Phase 2: Wasser dazugießen
                Text("2. Gieße Wasser (H₂O) in den Kolben zur Dissoziation!", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                Button(
                    onClick = { waterAdded = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = ChemistryColors.HydrogenCyan)
                ) {
                    Text("💧 Wasser dazugießen (Dissoziieren)", fontWeight = FontWeight.Bold, color = Color.Black)
                }
            } else if (!isIonCorrect) {
                // Phase 3: Säurerest-Ion zuordnen
                Text("3. Welches Säurerest-Ion (${currentAcid.ionFormula}) ist im Wasser entstanden?", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ionOptions.forEach { ionName ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedIonName = ionName
                                    if (ionName == currentAcid.ionName) {
                                        onXpEarned(10)
                                    }
                                },
                            shape = RoundedCornerShape(12.dp),
                            color = if (selectedIonName == ionName) Subject.CHEMISTRY.primaryColor else MaterialTheme.colorScheme.surface
                        ) {
                            Text(
                                text = ionName,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(12.dp),
                                color = if (selectedIonName == ionName) Color.White else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            } else {
                // Phase 4: Erfolg
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("🎉 Perfekt dissoziiert! (+10 XP)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        Text("${currentAcid.formula} + H₂O  ➔  H₃O⁺  +  ${currentAcid.ionFormula} (${currentAcid.ionName})", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                    }
                }

                Button(
                    onClick = {
                        currentAcidIndex++
                        selectedFormula = null
                        waterAdded = false
                        selectedIonName = null
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Subject.CHEMISTRY.primaryColor)
                ) {
                    Text("Nächster Erlenmeyerkolben ➔", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun FlaskVisualizer(
    acid: Acid,
    hasFormula: Boolean,
    hasWater: Boolean,
    hasIon: Boolean
) {
    val liquidColor by animateColorAsState(
        targetValue = when {
            hasIon -> ChemistryColors.NeutralGreen.copy(alpha = 0.5f)
            hasWater -> ChemistryColors.AcidRed.copy(alpha = 0.4f)
            hasFormula -> ChemistryColors.AcidOrange.copy(alpha = 0.3f)
            else -> Color.Transparent
        }
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1B2631))
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Kolben Kontur
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(140.dp)
                    .border(3.dp, Color.White.copy(alpha = 0.7f), RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
                    .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
                    .background(liquidColor),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (hasFormula) Text(acid.formula, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 18.sp)
                    if (hasWater) Text("+ H₂O 💧", fontSize = 12.sp, color = Color.LightGray)
                    if (hasIon) Text("${acid.ionFormula}\n(${acid.ionName})", fontWeight = FontWeight.Bold, color = Color.Yellow, fontSize = 12.sp, textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Composable
private fun AcidIonsReferenceTable() {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Die 7 wichtigsten Säuren & Säurerest-Ionen:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Acid.entries.forEach { acid ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = acid.displayName,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Subject.CHEMISTRY.primaryColor
                        )

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = "Formel: ${acid.formula}",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }

                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Säurerest-Ion Name:",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = acid.ionName,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.tertiaryContainer
                        ) {
                            Text(
                                text = "Ion: ${acid.ionFormula}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
