package org.learnbetter.project.ui.phtest

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
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
import org.learnbetter.project.model.TestSolution
import org.learnbetter.project.model.TestSolutionsData
import org.learnbetter.project.ui.theme.getPhColor
import org.learnbetter.project.ui.theme.getPhLabel

@Composable
fun PhTestScreen(
    onXpEarned: (Int) -> Unit = {}
) {
    val solutions = TestSolutionsData.sampleSolutions
    var selectedSolution by remember { mutableStateOf(solutions.first()) }
    var isDipped by remember { mutableStateOf(false) }

    // Strip dipping animation & color transition
    val stripOffset by animateDpAsState(
        targetValue = if (isDipped) 60.dp else 0.dp,
        animationSpec = tween(durationMillis = 800)
    )

    val defaultStripColor = Color(0xFFFFF1C5) // Gelblicher Roh-Teststreifen
    val targetStripColor = getPhColor(selectedSolution.phValue)
    val animatedStripColor by animateColorAsState(
        targetValue = if (isDipped) targetStripColor else defaultStripColor,
        animationSpec = tween(durationMillis = 800)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🎨 Interaktiver pH-Test",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Tauche den Universalindikator-Teststreifen in verschiedene Lösungen und vergleiche die Verfärbung mit der pH-Skala!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        // 1. Becherglas & Indikatorstreifen-Visualisierung
        BeakerVisualizer(
            solution = selectedSolution,
            stripOffset = stripOffset,
            stripColor = animatedStripColor
        )

        // 2. Aktion Button
        Button(
            onClick = {
                isDipped = !isDipped
                if (isDipped) {
                    onXpEarned(25)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = if (isDipped) "🔄 Streifen herausziehen" else "🧪 Teststreifen eintauchen!",
                fontWeight = FontWeight.Bold
            )
        }

        // 3. Messergebnis & Indikatorskala
        if (isDipped) {
            MeasurementResultCard(solution = selectedSolution)
        }

        // 4. Lösungsauswahl
        SolutionSelectionGrid(
            solutions = solutions,
            selectedSolution = selectedSolution,
            onSolutionSelected = {
                selectedSolution = it
                isDipped = false
            }
        )
    }
}

@Composable
private fun BeakerVisualizer(
    solution: TestSolution,
    stripOffset: androidx.compose.ui.unit.Dp,
    stripColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Indikatorstreifen
            Box(
                modifier = Modifier
                    .offset(y = (-30).dp + stripOffset)
                    .width(28.dp)
                    .height(130.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFFE8D3A7))
                    .border(1.dp, Color.Gray.copy(alpha = 0.5f), RoundedCornerShape(6.dp)),
                contentAlignment = Alignment.BottomCenter
            ) {
                // Das eintauchende Ende des Teststreifens
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .background(stripColor)
                )
            }

            // Becherglas Kontur
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp)
                    .width(110.dp)
                    .height(120.dp)
                    .border(
                        width = 4.dp,
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                    )
                    .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                    .background(Color.White.copy(alpha = 0.3f)),
                contentAlignment = Alignment.BottomCenter
            ) {
                // Flüssigkeit
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(85.dp)
                        .background(getPhColor(solution.phValue).copy(alpha = 0.4f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = solution.icon,
                        fontSize = 32.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun MeasurementResultCard(solution: TestSolution) {
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
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(getPhColor(solution.phValue)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${solution.phValue}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Gemessener pH-Wert: ${solution.phValue}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = getPhLabel(solution.phValue),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                    )
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))

            Text(
                text = solution.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun SolutionSelectionGrid(
    solutions: List<TestSolution>,
    selectedSolution: TestSolution,
    onSolutionSelected: (TestSolution) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Prüfflüssigkeit wählen:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            solutions.forEach { sol ->
                val isSelected = sol == selectedSolution
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSolutionSelected(sol) },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(sol.icon, fontSize = 24.sp)
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = sol.name,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = sol.category,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isSelected) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(getPhColor(sol.phValue)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${sol.phValue.toInt()}",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
