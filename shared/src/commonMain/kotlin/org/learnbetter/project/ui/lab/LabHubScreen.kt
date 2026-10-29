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
import org.learnbetter.project.model.Subject
import org.learnbetter.project.ui.biology.BiologyCellPuzzleScreen
import org.learnbetter.project.ui.biology.BiologyMicroscopeScreen
import org.learnbetter.project.ui.biology.BiologyNucleusExperimentScreen
import org.learnbetter.project.ui.german.GermanRhetoricScreen
import org.learnbetter.project.ui.math.MathGeometryScreen
import org.learnbetter.project.ui.math.MathParabolaScreen
import org.learnbetter.project.ui.music.MusicPianoScreen
import org.learnbetter.project.ui.physics.PhysicsHookeScreen
import org.learnbetter.project.ui.phtest.PhTestScreen
import org.learnbetter.project.ui.socialstudies.SocialStudiesTimelineScreen

@Composable
fun LabHubScreen(
    currentSubject: Subject = Subject.CHEMISTRY,
    onXpEarned: (Int) -> Unit = {}
) {
    var activeExperimentId by remember(currentSubject) { mutableStateOf<String?>(null) }
    var bioToolMode by remember { mutableIntStateOf(1) } // 1 = Mikroskop, 2 = Zell-Puzzle, 3 = Hutalgen

    var mathToolMode by remember { mutableIntStateOf(1) }

    when {
        currentSubject == Subject.PHYSICS -> PhysicsHookeScreen(
            onXpEarned = onXpEarned
        )
        currentSubject == Subject.BIOLOGY -> Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Button(
                    onClick = { bioToolMode = 1 },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (bioToolMode == 1) Subject.BIOLOGY.primaryColor else MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text("🔬 Mikroskop", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = if (bioToolMode == 1) androidx.compose.ui.graphics.Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
                }

                Button(
                    onClick = { bioToolMode = 2 },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (bioToolMode == 2) Subject.BIOLOGY.primaryColor else MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text("🧩 Puzzle & Turgor", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = if (bioToolMode == 2) androidx.compose.ui.graphics.Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
                }

                Button(
                    onClick = { bioToolMode = 3 },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (bioToolMode == 3) Subject.BIOLOGY.primaryColor else MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text("🧪 Hutalgen & Kern", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = if (bioToolMode == 3) androidx.compose.ui.graphics.Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            when (bioToolMode) {
                1 -> BiologyMicroscopeScreen(onXpEarned = onXpEarned)
                2 -> BiologyCellPuzzleScreen(onXpEarned = onXpEarned)
                else -> BiologyNucleusExperimentScreen(onXpEarned = onXpEarned)
            }
        }
        currentSubject == Subject.MATH -> Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { mathToolMode = 1 },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (mathToolMode == 1) Subject.MATH.primaryColor else MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text("📉 Parabeln", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = if (mathToolMode == 1) androidx.compose.ui.graphics.Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
                }

                Button(
                    onClick = { mathToolMode = 2 },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (mathToolMode == 2) Subject.MATH.primaryColor else MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text("📐 Zylinder & Pyramide", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = if (mathToolMode == 2) androidx.compose.ui.graphics.Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            if (mathToolMode == 1) {
                MathParabolaScreen(onXpEarned = onXpEarned)
            } else {
                MathGeometryScreen(onXpEarned = onXpEarned)
            }
        }
        currentSubject == Subject.SOCIAL_STUDIES -> SocialStudiesTimelineScreen(
            onXpEarned = onXpEarned
        )
        currentSubject == Subject.MUSIC -> MusicPianoScreen(
            onXpEarned = onXpEarned
        )
        currentSubject == Subject.GERMAN -> GermanRhetoricScreen(
            onXpEarned = onXpEarned
        )
        activeExperimentId == "acid_ions" -> AcidIonsTrainerScreen(
            onXpEarned = onXpEarned,
            onBackToHub = { activeExperimentId = null }
        )
        activeExperimentId == "chemical_bond" -> ChemicalBondGameScreen(
            onXpEarned = onXpEarned,
            onBackToHub = { activeExperimentId = null }
        )
        activeExperimentId == "sulfuric_acid" -> SulfuricAcidLabScreen(
            onXpEarned = onXpEarned,
            onBackToHub = { activeExperimentId = null }
        )
        activeExperimentId == "marble_acid" -> MarbleAcidScreen(
            onXpEarned = onXpEarned,
            onBackToHub = { activeExperimentId = null }
        )
        activeExperimentId == "acid_metal" -> LabScreen(
            onXpEarned = onXpEarned,
            onBackToHub = { activeExperimentId = null }
        )
        activeExperimentId == "ph_indicator" -> PhTestScreen(
            onXpEarned = onXpEarned,
            onBackToHub = { activeExperimentId = null }
        )
        activeExperimentId == "magnesium_burn" -> MagnesiumBurnScreen(
            onXpEarned = onXpEarned,
            onBackToHub = { activeExperimentId = null }
        )
        else -> ExperimentSelectionGrid(
            subject = currentSubject,
            experiments = LabExperimentsData.experiments,
            onExperimentSelected = { activeExperimentId = it.id }
        )
    }
}

@Composable
private fun ExperimentSelectionGrid(
    subject: Subject,
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
            text = "🧪 ${subject.displayName}-Werkstatt",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = subject.primaryColor,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Wähle ein interaktives Experiment für ${subject.displayName} aus:",
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
                                color = subject.primaryColor,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = exp.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        if (exp.isAvailable) {
                            Text("➔", fontSize = 20.sp, color = subject.primaryColor)
                        }
                    }
                }
            }
        }
    }
}
