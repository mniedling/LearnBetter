package org.learnbetter.project.ui.driving

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import learnbetter.shared.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.learnbetter.project.model.DrivingImageType
import org.learnbetter.project.model.DrivingQuestion
import org.learnbetter.project.model.DrivingSchoolData
import org.learnbetter.project.model.Subject
import org.learnbetter.project.ui.theme.ChemistryColors

@Composable
fun DrivingSchoolScreen(
    onXpEarned: (Int) -> Unit = {}
) {
    var selectedBogenNumber by remember { mutableIntStateOf(1) }
    var isRandomOrder by remember { mutableStateOf(false) }

    val questions = remember(selectedBogenNumber, isRandomOrder) {
        DrivingSchoolData.getQuestionsForBogen(selectedBogenNumber, isRandomOrder)
    }

    var currentIndex by remember(selectedBogenNumber, isRandomOrder) { mutableIntStateOf(0) }
    var userSelectedIndices by remember(selectedBogenNumber, isRandomOrder, currentIndex) { mutableStateOf(setOf<Int>()) }
    var isSubmitted by remember(selectedBogenNumber, isRandomOrder, currentIndex) { mutableStateOf(false) }
    var showVideoEndFrame by remember(selectedBogenNumber, isRandomOrder, currentIndex) { mutableStateOf(false) }

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
                    onXpEarned(17)
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

            // Header: Testbogen & Kompakte Fehlerpunkte & Wuerfel-Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = if (selectedBogenNumber == 0) "🛵 Gesamtpool • Klasse AM" else "🛵 Testbogen Nr. $selectedBogenNumber",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Subject.DRIVING_SCHOOL.primaryColor
                        )

                        if (!currentQuestion.videoEndImageName.isNullOrEmpty()) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Text(
                                    text = "🎬 Video",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Text(
                        text = "Frage ${currentIndex + 1} von ${questions.size}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Wuerfel-Toggle Button (Random vs Sequential)
                    Surface(
                        modifier = Modifier.clickable { isRandomOrder = !isRandomOrder },
                        shape = RoundedCornerShape(12.dp),
                        color = if (isRandomOrder) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Box(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("🎲", fontSize = 16.sp)
                        }
                    }

                    // Kompakte Fehlerpunkte-Anzeige
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.errorContainer
                    ) {
                        Text(
                            text = "${currentQuestion.errorPoints} FP",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            LinearProgressIndicator(
                progress = { (currentIndex + 1) / questions.size.toFloat() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = Subject.DRIVING_SCHOOL.primaryColor
            )

            // NATIVE LOKALE BILDEINBINDUNG MIT VIDEO-WECHSEL (Startbild -> Endbild)
            val activeImageName = if (showVideoEndFrame && !currentQuestion.videoEndImageName.isNullOrEmpty()) {
                currentQuestion.videoEndImageName
            } else {
                currentQuestion.localImageName
            }

            val localDrawable = getLocalDrawableResource(activeImageName)

            if (localDrawable != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(210.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Crossfade(targetState = localDrawable, label = "videoSwitch") { drawable ->
                            Image(
                                painter = painterResource(drawable),
                                contentDescription = "Amtliches Fahrschul-Originalbild",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(20.dp)),
                                contentScale = ContentScale.Fit
                            )
                        }

                        // VIDEO CONTROL OVERLAY BUTTON
                        if (!currentQuestion.videoEndImageName.isNullOrEmpty()) {
                            Surface(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(12.dp)
                                    .clickable { showVideoEndFrame = !showVideoEndFrame },
                                shape = RoundedCornerShape(14.dp),
                                color = Subject.DRIVING_SCHOOL.primaryColor
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(if (showVideoEndFrame) "⏪ Szene zurücksetzen" else "▶️ Szene abspielen", fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            } else if (!currentQuestion.imageUrl.isNullOrEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
                ) {
                    AsyncImage(
                        model = currentQuestion.imageUrl,
                        contentDescription = "Amtliches Fahrschul-Originalbild",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Fit
                    )
                }
            } else if (currentQuestion.imageType != DrivingImageType.NONE) {
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
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = currentQuestion.questionText,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    if (!currentQuestion.introduction.isNullOrBlank()) {
                        Text(
                            text = currentQuestion.introduction,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                }
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
                            showVideoEndFrame = false
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

private fun getLocalDrawableResource(localImageName: String?): DrawableResource? {
    if (localImageName == null) return null
    return when (localImageName) {
        "kalaiwa_1107159m.webp" -> Res.drawable.kalaiwa_1107159m
        "kalaiwa_1107171a.webp" -> Res.drawable.kalaiwa_1107171a
        "kalaiwa_1107171e.webp" -> Res.drawable.kalaiwa_1107171e
        "kalaiwa_1107155ma.webp" -> Res.drawable.kalaiwa_1107155ma
        "kalaiwa_1107155me.webp" -> Res.drawable.kalaiwa_1107155me
        "kalaiwa_1226114.webp" -> Res.drawable.kalaiwa_1226114
        "kalaiwa_1207105ma.webp" -> Res.drawable.kalaiwa_1207105ma
        "kalaiwa_1207105me.webp" -> Res.drawable.kalaiwa_1207105me
        "kalaiwa_1220107ma.webp" -> Res.drawable.kalaiwa_1220107ma
        "kalaiwa_1220107me.webp" -> Res.drawable.kalaiwa_1220107me
        "kalaiwa_1210006ma.webp" -> Res.drawable.kalaiwa_1210006ma
        "kalaiwa_1210006me.webp" -> Res.drawable.kalaiwa_1210006me
        "kalaiwa_1301125m.webp" -> Res.drawable.kalaiwa_1301125m
        "kalaiwa_1301128m.webp" -> Res.drawable.kalaiwa_1301128m
        "kalaiwa_1440105.webp" -> Res.drawable.kalaiwa_1440105
        "kalaiwa_1440116.webp" -> Res.drawable.kalaiwa_1440116
        "kalaiwa_1440118.webp" -> Res.drawable.kalaiwa_1440118
        "kalaiwa_1441136.webp" -> Res.drawable.kalaiwa_1441136
        "kalaiwa_1442145.webp" -> Res.drawable.kalaiwa_1442145
        "kalaiwa_1441029.webp" -> Res.drawable.kalaiwa_1441029
        else -> null
    }
}

@Composable
private fun TrafficImageCanvas(imageType: DrivingImageType) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                val width = size.width
                val height = size.height
                val centerX = width / 2f
                val centerY = height / 2f

                when (imageType) {
                    DrivingImageType.STOP_SIGN -> {
                        val octPath = Path()
                        val r = 68f
                        for (i in 0 until 8) {
                            val angle = (i * 45f + 22.5f) * (3.14159f / 180f)
                            val x = centerX + r * kotlin.math.cos(angle)
                            val y = centerY + r * kotlin.math.sin(angle)
                            if (i == 0) octPath.moveTo(x, y) else octPath.lineTo(x, y)
                        }
                        octPath.close()
                        drawPath(octPath, Color(0xFFDC2626))
                        drawPath(octPath, Color.White, style = Stroke(6f))

                        val innerOct = Path()
                        val rInner = 60f
                        for (i in 0 until 8) {
                            val angle = (i * 45f + 22.5f) * (3.14159f / 180f)
                            val x = centerX + rInner * kotlin.math.cos(angle)
                            val y = centerY + rInner * kotlin.math.sin(angle)
                            if (i == 0) innerOct.moveTo(x, y) else innerOct.lineTo(x, y)
                        }
                        innerOct.close()
                        drawPath(innerOct, Color.White, style = Stroke(2f))
                    }
                    DrivingImageType.YIELD_SIGN -> {
                        val triPath = Path()
                        triPath.moveTo(centerX - 70f, centerY - 55f)
                        triPath.lineTo(centerX + 70f, centerY - 55f)
                        triPath.lineTo(centerX, centerY + 65f)
                        triPath.close()
                        drawPath(triPath, Color.White)
                        drawPath(triPath, Color(0xFFDC2626), style = Stroke(14f))
                    }
                    DrivingImageType.PRIORITY_ROAD -> {
                        val diamondPath = Path()
                        diamondPath.moveTo(centerX, centerY - 65f)
                        diamondPath.lineTo(centerX + 65f, centerY)
                        diamondPath.lineTo(centerX, centerY + 65f)
                        diamondPath.lineTo(centerX - 65f, centerY)
                        diamondPath.close()

                        drawPath(diamondPath, Color.White)
                        drawPath(diamondPath, Color.Black, style = Stroke(4f))

                        val innerPath = Path()
                        innerPath.moveTo(centerX, centerY - 40f)
                        innerPath.lineTo(centerX + 40f, centerY)
                        innerPath.lineTo(centerX, centerY + 40f)
                        innerPath.lineTo(centerX - 40f, centerY)
                        innerPath.close()
                        drawPath(innerPath, Color(0xFFFFD700))
                    }
                    DrivingImageType.RIGHT_OF_WAY_CROSSING -> {
                        drawRect(Color(0xFF475569), topLeft = Offset(centerX - 45f, 0f), size = Size(90f, height))
                        drawRect(Color(0xFF475569), topLeft = Offset(0f, centerY - 45f), size = Size(width, 90f))

                        drawLine(Color.White, start = Offset(centerX, 0f), end = Offset(centerX, centerY - 45f), strokeWidth = 3f)
                        drawLine(Color.White, start = Offset(centerX, centerY + 45f), end = Offset(centerX, height), strokeWidth = 3f)

                        drawRoundRect(Color(0xFFEF4444), topLeft = Offset(centerX + 65f, centerY + 10f), size = Size(40f, 24f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(6f, 6f))

                        drawCircle(Color(0xFF3B82F6), radius = 16f, center = Offset(centerX - 20f, centerY + 75f))
                    }
                    DrivingImageType.NO_ENTRY -> {
                        drawCircle(Color(0xFFDC2626), radius = 65f, center = Offset(centerX, centerY))
                        drawRect(Color.White, topLeft = Offset(centerX - 48f, centerY - 12f), size = Size(96f, 24f))
                    }
                    DrivingImageType.SPEED_45 -> {
                        drawCircle(Color.White, radius = 65f, center = Offset(centerX, centerY))
                        drawCircle(Color(0xFFDC2626), radius = 65f, center = Offset(centerX, centerY), style = Stroke(12f))
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
                    text = "⭐ +17 XP Belohnung erhalten!",
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
