package org.learnbetter.project.ui.driving

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.learnbetter.project.model.DrivingImageType
import org.learnbetter.project.model.DrivingQuestion
import org.learnbetter.project.model.DrivingSchoolData
import org.learnbetter.project.model.Subject
import org.learnbetter.project.ui.theme.ChemistryColors

@Composable
fun DrivingSchoolScreen(
    onXpEarned: (Int) -> Unit = {}
) {
    var selectedBogenNumber by remember { mutableIntStateOf(1) } // 1, 2, 3 oder 0 = Alle
    var isRandomOrder by remember { mutableStateOf(false) }       // false = Der Reihe nach, true = Zufall

    val questions = remember(selectedBogenNumber, isRandomOrder) {
        DrivingSchoolData.getQuestionsForBogen(selectedBogenNumber, isRandomOrder)
    }

    var currentIndex by remember(selectedBogenNumber, isRandomOrder) { mutableIntStateOf(0) }
    var userSelectedIndices by remember(selectedBogenNumber, isRandomOrder, currentIndex) { mutableStateOf(setOf<Int>()) }
    var isSubmitted by remember(selectedBogenNumber, isRandomOrder, currentIndex) { mutableStateOf(false) }

    var totalErrorPointsIncurred by remember(selectedBogenNumber, isRandomOrder) { mutableIntStateOf(0) }
    var examFinished by remember(selectedBogenNumber, isRandomOrder) { mutableStateOf(false) }

    val currentQuestion = questions.getOrNull(currentIndex)

    if (examFinished || currentQuestion == null) {
        DrivingExamResultScreen(
            totalErrorPoints = totalErrorPointsIncurred,
            selectedBogen = selectedBogenNumber,
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
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Filter Bogen-Auswahl & Reihenfolge Switcher
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(1, 2, 3, 0).forEach { bogen ->
                    val isSelected = selectedBogenNumber == bogen
                    val label = if (bogen == 0) "🎲 Alle Bögen" else "Testbogen $bogen"
                    Surface(
                        modifier = Modifier.clickable {
                            selectedBogenNumber = bogen
                        },
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) Subject.DRIVING_SCHOOL.primaryColor else MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }

                // Order Switcher (Der Reihe nach vs. Zufall)
                Surface(
                    modifier = Modifier.clickable {
                        isRandomOrder = !isRandomOrder
                    },
                    shape = RoundedCornerShape(12.dp),
                    color = if (isRandomOrder) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = if (isRandomOrder) "🔀 Zufällig" else "🔢 Der Reihe nach",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            // Header: Testbogen & Fehlerpunkte
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (selectedBogenNumber == 0) "🛵 Gesamtpool • Klasse AM" else "🛵 Testbogen Nr. $selectedBogenNumber • Klasse AM",
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

            // Falls Bild/Schild vorhanden, hier grafisch rendern!
            if (currentQuestion.imageType != DrivingImageType.NONE) {
                TrafficImageCanvas(imageType = currentQuestion.imageType)
            }

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
                        isSubmitted && isChecked -> MaterialTheme.colorScheme.errorContainer
                        else -> MaterialTheme.colorScheme.surface
                    }

                    val borderColor = when {
                        isSubmitted && isCorrectOption -> ChemistryColors.NeutralGreen
                        isSubmitted && isChecked -> MaterialTheme.colorScheme.error
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
private fun TrafficImageCanvas(imageType: DrivingImageType) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                val width = size.width
                val height = size.height
                val centerX = width / 2f
                val centerY = height / 2f

                when (imageType) {
                    DrivingImageType.STOP_SIGN -> {
                        // Stoppschild 🛑
                        val octPath = Path()
                        val r = 60f
                        for (i in 0 until 8) {
                            val angle = (i * 45f + 22.5f) * (3.14159f / 180f)
                            val x = centerX + r * kotlin.math.cos(angle)
                            val y = centerY + r * kotlin.math.sin(angle)
                            if (i == 0) octPath.moveTo(x, y) else octPath.lineTo(x, y)
                        }
                        octPath.close()
                        drawPath(octPath, Color(0xFFDC2626))
                        drawPath(octPath, Color.White, style = Stroke(4f))
                    }
                    DrivingImageType.YIELD_SIGN -> {
                        // Vorfahrt gewähren 🔻
                        val triPath = Path()
                        triPath.moveTo(centerX - 60f, centerY - 45f)
                        triPath.lineTo(centerX + 60f, centerY - 45f)
                        triPath.lineTo(centerX, centerY + 55f)
                        triPath.close()
                        drawPath(triPath, Color.White)
                        drawPath(triPath, Color(0xFFDC2626), style = Stroke(12f))
                    }
                    DrivingImageType.PRIORITY_ROAD -> {
                        // Vorfahrtsstraße 🟡
                        val diamondPath = Path()
                        diamondPath.moveTo(centerX, centerY - 55f)
                        diamondPath.lineTo(centerX + 55f, centerY)
                        diamondPath.lineTo(centerX, centerY + 55f)
                        diamondPath.lineTo(centerX - 55f, centerY)
                        diamondPath.close()

                        drawPath(diamondPath, Color.White)
                        drawPath(diamondPath, Color(0xFFDC2626), style = Stroke(6f))

                        val innerPath = Path()
                        innerPath.moveTo(centerX, centerY - 35f)
                        innerPath.lineTo(centerX + 35f, centerY)
                        innerPath.lineTo(centerX, centerY + 35f)
                        innerPath.lineTo(centerX - 35f, centerY)
                        innerPath.close()
                        drawPath(innerPath, Color(0xFFFFD700))
                    }
                    DrivingImageType.RIGHT_OF_WAY_CROSSING -> {
                        // 2D Kreuzung (Rechts vor Links)
                        drawRect(Color(0xFF475569), topLeft = Offset(centerX - 40f, 0f), size = Size(80f, height))
                        drawRect(Color(0xFF475569), topLeft = Offset(0f, centerY - 40f), size = Size(width, 80f))

                        // Rotes Auto A (von Rechts)
                        drawCircle(Color(0xFFEF4444), radius = 18f, center = Offset(centerX + 70f, centerY + 18f))

                        // Blaues Moped B (von unten)
                        drawCircle(Color(0xFF3B82F6), radius = 14f, center = Offset(centerX - 18f, centerY + 70f))
                    }
                    DrivingImageType.NO_ENTRY -> {
                        // Verbot der Einfahrt ⛔
                        drawCircle(Color(0xFFDC2626), radius = 55f, center = Offset(centerX, centerY))
                        drawRect(Color.White, topLeft = Offset(centerX - 40f, centerY - 10f), size = Size(80f, 20f))
                    }
                    DrivingImageType.SPEED_45 -> {
                        // Höchstgeschwindigkeit 45 km/h
                        drawCircle(Color.White, radius = 55f, center = Offset(centerX, centerY))
                        drawCircle(Color(0xFFDC2626), radius = 55f, center = Offset(centerX, centerY), style = Stroke(10f))
                    }
                    else -> {}
                }
            }
        }
    }
}

@Composable
private fun DrivingExamResultScreen(
    totalErrorPoints: Int,
    selectedBogen: Int,
    onRestart: () -> Unit,
    onAwardXp: () -> Unit
) {
    val passed = totalErrorPoints <= 10

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
            text = if (selectedBogen == 0) "Klasse AM • Alle Bögen gemischt" else "Klasse AM • Testbogen Nr. $selectedBogen",
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
                    text = "Maximal erlaubt: 10 Fehlerpunkte",
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
