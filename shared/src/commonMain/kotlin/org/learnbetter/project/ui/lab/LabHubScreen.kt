package org.learnbetter.project.ui.lab

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.learnbetter.project.model.LabExperiment
import org.learnbetter.project.model.LabExperimentsData
import org.learnbetter.project.ui.phtest.PhTestScreen

@Composable
fun LabHubScreen(
    onXpEarned: (Int) -> Unit = {}
) {
    var activeExperimentId by remember { mutableStateOf<String?>(null) }

    when (activeExperimentId) {
        "acid_metal" -> LabScreen(
            onXpEarned = onXpEarned,
            onBackToHub = { activeExperimentId = null }
        )
        "ph_indicator" -> PhTestScreen(
            onXpEarned = onXpEarned,
            onBackToHub = { activeExperimentId = null }
        )
        "magnesium_burn" -> MagnesiumBurnScreen(
            onXpEarned = onXpEarned,
            onBackToHub = { activeExperimentId = null }
        )
        else -> ExperimentSelectionGrid(
            experiments = LabExperimentsData.experiments,
            onExperimentSelected = { activeExperimentId = it.id }
        )
    }
}

@Composable
private fun ExperimentSelectionGrid(
    experiments: List<LabExperiment>,
    onExperimentSelected: (LabExperiment) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🧪 Virtuelles Chemie-Labor",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Wähle ein Experiment aus, das du im virtuellen Labor durchführen möchtest:",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            experiments.forEach { exp ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = exp.isAvailable) {
                            onExperimentSelected(exp)
                        },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (exp.isAvailable) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = if (exp.isAvailable) 2.dp else 0.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = if (exp.isAvailable) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Box(
                                modifier = Modifier.size(52.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(exp.icon, fontSize = 28.sp)
                            }
                        }

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = exp.title,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )

                                if (!exp.isAvailable) {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                                    ) {
                                        Text(
                                            text = "Demnächst",
                                            style = MaterialTheme.typography.labelSmall,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }

                            Text(
                                text = exp.subtitle,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = exp.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        if (exp.isAvailable) {
                            Text("➔", fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    }
}
