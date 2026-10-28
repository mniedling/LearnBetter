package org.learnbetter.project.ui.quiz

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.learnbetter.project.model.QuizData
import org.learnbetter.project.model.Subject
import org.learnbetter.project.ui.theme.ChemistryColors

@Composable
fun QuizScreen(
    currentSubject: Subject = Subject.CHEMISTRY,
    onXpEarned: (Int) -> Unit = {}
) {
    val questions = remember(currentSubject) {
        QuizData.getQuestionsForSubject(currentSubject.id)
    }

    var currentIndex by remember(currentSubject) { mutableIntStateOf(0) }
    var selectedAnswerIndex by remember(currentSubject) { mutableStateOf<Int?>(null) }
    var score by remember(currentSubject) { mutableIntStateOf(0) }
    var quizFinished by remember(currentSubject) { mutableStateOf(false) }

    val currentQuestion = questions.getOrNull(currentIndex)

    if (quizFinished || currentQuestion == null) {
        QuizResultScreen(
            score = score,
            totalQuestions = questions.size,
            subject = currentSubject,
            onRestart = {
                currentIndex = 0
                selectedAnswerIndex = null
                score = 0
                quizFinished = false
            }
        ) {
            onXpEarned(score * 20)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header: Fortschritt
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📝 ${currentSubject.displayName}-Quiz",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = currentSubject.primaryColor
                )
                Text(
                    text = "Frage ${currentIndex + 1} von ${questions.size}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            LinearProgressIndicator(
                progress = { (currentIndex + 1) / questions.size.toFloat() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = currentSubject.primaryColor
            )

            // Frage Card
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

            // Antwortoptionen
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                currentQuestion.options.forEachIndexed { index, optionText ->
                    val isSelected = selectedAnswerIndex == index
                    val isCorrect = index == currentQuestion.correctAnswerIndex
                    val hasAnswered = selectedAnswerIndex != null

                    val backgroundColor = when {
                        hasAnswered && isCorrect -> ChemistryColors.NeutralGreen.copy(alpha = 0.25f)
                        hasAnswered && isSelected -> MaterialTheme.colorScheme.errorContainer
                        else -> MaterialTheme.colorScheme.surface
                    }

                    val borderColor = when {
                        hasAnswered && isCorrect -> ChemistryColors.NeutralGreen
                        hasAnswered && isSelected -> MaterialTheme.colorScheme.error
                        else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(width = 2.dp, color = borderColor, shape = RoundedCornerShape(16.dp))
                            .clickable(enabled = !hasAnswered) {
                                selectedAnswerIndex = index
                                if (index == currentQuestion.correctAnswerIndex) {
                                    score++
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
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = "${('A' + index)}",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }

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

            // Erklärungstext nach Antwort
            if (selectedAnswerIndex != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "💡 Erklärung:",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = currentSubject.primaryColor
                        )
                        Text(
                            text = currentQuestion.explanation,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Nächste Frage Button
                Button(
                    onClick = {
                        if (currentIndex + 1 < questions.size) {
                            currentIndex++
                            selectedAnswerIndex = null
                        } else {
                            quizFinished = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = currentSubject.primaryColor)
                ) {
                    Text(
                        text = if (currentIndex + 1 < questions.size) "Nächste Frage ➔" else "Ergebnis anzeigen 🎉",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun QuizResultScreen(
    score: Int,
    totalQuestions: Int,
    subject: Subject,
    onRestart: () -> Unit,
    onAwardXp: () -> Unit
) {
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
        Text("🏆", fontSize = 64.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "${subject.displayName}-Quiz geschafft!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = subject.primaryColor
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Du hast $score von $totalQuestions Fragen richtig beantwortet.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.tertiaryContainer
        ) {
            Text(
                text = "⭐ +${score * 20} XP erhalten!",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onRestart,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = subject.primaryColor)
        ) {
            Text(
                text = "Quiz nochmal versuchen 🔄",
                fontWeight = FontWeight.Bold
            )
        }
    }
}
