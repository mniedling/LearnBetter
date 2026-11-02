package org.learnbetter.project.ui.driving

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.learnbetter.project.model.DrivingQuestion
import org.learnbetter.project.model.DrivingSchoolData
import org.learnbetter.project.model.Subject
import org.learnbetter.project.ui.theme.ChemistryColors

@Composable
fun DrivingSchoolScreen(
    onXpEarned: (Int) -> Unit = {}
) {
    val questions = DrivingSchoolData.testbogenAM1
    var currentIndex by remember { mutableIntStateOf(0) }
    var userSelectedIndices by remember { mutableStateOf(setOf<Int>()) }
    var isSubmitted by remember { mutableStateOf(false) }

    var totalErrorPointsIncurred by remember { mutableIntStateOf(0) }
    var examFinished by remember { mutableStateOf(false) }

    val currentQuestion = questions.getOrNull(currentIndex)

    if (examFinished || currentQuestion == null) {
        DrivingExamResultScreen(
            totalErrorPoints = totalErrorPointsIncurred,
            maxAllowedErrorPoints = 10,
            totalQuestions = questions.size,
            onRestart = {
                currentIndex = 0
                userSelectedIndices = emptySet()
                isSubmitted = false
                totalErrorPointsIncurred = 0
                examFinished = false
            },
            onAwardXp = {
                if (totalErrorPointsIncurred <= 10) {
                    onXpEarned(50)
                }
            }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header: Testbogen & Fehlerpunkte
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "🛵 Testbogen Nr. 1 • Klasse AM",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Subject.DRIVING_SCHOOL.primaryColor
                    )
                    Text(
                        text = "Frage ${currentIndex + 1} von ${questions.size}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.errorContainer
                ) {
                    Text(
                        text = "Wertigkeit: ${currentQuestion.errorPoints} FP",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }

            LinearProgressIndicator(
                progress = { (currentIndex + 1) / questions.size.toFloat() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = Subject.DRIVING_SCHOOL.primaryColor
            )

            // Amtliche Fragestellung
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = currentQuestion.questionText,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(20.dp)
                )
            }

            // Mehrfachauswahl Checkboxen
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                currentQuestion.options.forEachIndexed { index, optionText ->
                    val isChecked = userSelectedIndices.contains(index)
                    val isCorrectOption = currentQuestion.correctOptionIndices.contains(index)

                    val backgroundColor = when {
                        isSubmitted && isCorrectOption -> ChemistryColors.NeutralGreen.copy(alpha = 0.25f)
                        isSubmitted && isChecked && !isCorrectOption -> MaterialTheme.colorScheme.errorContainer
                        else -> MaterialTheme.colorScheme.surface
                    }

                    val borderColor = when {
                        isSubmitted && isCorrectOption -> ChemistryColors.NeutralGreen
                        isSubmitted && isChecked && !isCorrectOption -> MaterialTheme.colorScheme.error
                        isChecked -> Subject.DRIVING_SCHOOL.primaryColor
                        else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(width = 2.dp, color = borderColor, shape = RoundedCornerShape(16.dp))
                            .clickable(enabled = !isSubmitted) {
                                userSelectedIndices = if (isChecked) {
                                    userSelectedIndices - index
                                } else {
                                    userSelectedIndices + index
                                }
                            },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = backgroundColor)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = {
                                    if (!isSubmitted) {
                                        userSelectedIndices = if (isChecked) {
                                            userSelectedIndices - index
                                        } else {
                                            userSelectedIndices + index
                                        }
                                    }
                                },
                                enabled = !isSubmitted
                            )

                            Text(
                                text = optionText,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            // Abgeben / Nächste Frage
            if (!isSubmitted) {
                Button(
                    onClick = {
                        isSubmitted = true
                        val isQuestionPerfect = userSelectedIndices == currentQuestion.correctOptionIndices
                        if (!isQuestionPerfect) {
                            totalErrorPointsIncurred += currentQuestion.errorPoints
                        }
                    },
                    enabled = userSelectedIndices.isNotEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Subject.DRIVING_SCHOOL.primaryColor)
                ) {
                    Text("Antwort abgeben", fontWeight = FontWeight.Bold, color = Color.Black)
                }
            } else {
                // Erklärungs-Kasten
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "💡 StVO-Erläuterung:",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = Subject.DRIVING_SCHOOL.primaryColor
                        )
                        Text(
                            text = currentQuestion.explanation,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Button(
                    onClick = {
                        if (currentIndex + 1 < questions.size) {
                            currentIndex++
                            userSelectedIndices = emptySet()
                            isSubmitted = false
                        } else {
                            examFinished = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Subject.DRIVING_SCHOOL.primaryColor)
                ) {
                    Text(
                        text = if (currentIndex + 1 < questions.size) "Nächste Frage ➔" else "Prüfung auswerten 🏆",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
private fun DrivingExamResultScreen(
    totalErrorPoints: Int,
    maxAllowedErrorPoints: Int,
    totalQuestions: Int,
    onRestart: () -> Unit,
    onAwardXp: () -> Unit
) {
    val passed = totalErrorPoints <= maxAllowedErrorPoints

    LaunchedEffect(Unit) {
        onAwardXp()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(if (passed) "🎉" else "❌", fontSize = 64.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (passed) "BESTANDEN!" else "NICHT BESTANDEN",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = if (passed) ChemistryColors.NeutralGreen else MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Testbogen 1 • Klasse AM (Moped)",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (passed) ChemistryColors.NeutralGreen.copy(alpha = 0.15f) else MaterialTheme.colorScheme.errorContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "$totalErrorPoints Fehlerpunkte",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Maximal erlaubt: $maxAllowedErrorPoints Fehlerpunkte",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (passed) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.tertiaryContainer
            ) {
                Text(
                    text = "⭐ +50 XP Belohnung erhalten!",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        Button(
            onClick = onRestart,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Subject.DRIVING_SCHOOL.primaryColor)
        ) {
            Text(
                text = "Testbogen nochmal versuchen 🔄",
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}
