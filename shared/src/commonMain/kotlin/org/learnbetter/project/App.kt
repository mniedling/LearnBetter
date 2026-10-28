package org.learnbetter.project

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import org.learnbetter.project.model.RewardState
import org.learnbetter.project.model.Subject
import org.learnbetter.project.ui.lab.LabHubScreen
import org.learnbetter.project.ui.quiz.QuizScreen
import org.learnbetter.project.ui.rewards.RewardCard
import org.learnbetter.project.ui.theme.ChemistryColors
import org.learnbetter.project.ui.theme.LearnBetterTheme
import org.learnbetter.project.ui.theme.getPhColor
import org.learnbetter.project.ui.theme.getPhLabel

enum class NavigationTab(val label: String, val icon: String) {
    DASHBOARD("Übersicht", "🏠"),
    LAB("Werkstatt", "🧪"),
    QUIZ("Quiz", "📝")
}

@Composable
@Preview
fun App() {
    var selectedSubject by remember { mutableStateOf(Subject.CHEMISTRY) }
    var selectedTab by remember { mutableStateOf(NavigationTab.DASHBOARD) }
    var totalXp by remember { mutableIntStateOf(250) }
    var streakDays by remember { mutableIntStateOf(3) }
    var redeemedMinutes by remember { mutableIntStateOf(0) }

    val rewardState = remember(totalXp, redeemedMinutes) {
        RewardState(totalXp = totalXp, redeemedMinutes = redeemedMinutes)
    }

    LearnBetterTheme {
        Scaffold(
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    NavigationTab.entries.forEach { tab ->
                        NavigationBarItem(
                            selected = selectedTab == tab,
                            onClick = { selectedTab = tab },
                            icon = { Text(tab.icon, fontSize = 20.sp) },
                            label = { Text(tab.label) }
                        )
                    }
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (selectedTab) {
                    NavigationTab.DASHBOARD -> DashboardContent(
                        selectedSubject = selectedSubject,
                        rewardState = rewardState,
                        streakDays = streakDays,
                        onSubjectSelected = { selectedSubject = it },
                        onNavigateToLab = { selectedTab = NavigationTab.LAB },
                        onNavigateToQuiz = { selectedTab = NavigationTab.QUIZ },
                        onRedeemMinutes = { mins -> redeemedMinutes += mins }
                    )
                    NavigationTab.LAB -> LabHubScreen(
                        currentSubject = selectedSubject,
                        onXpEarned = { earned -> totalXp += earned }
                    )
                    NavigationTab.QUIZ -> QuizScreen(
                        currentSubject = selectedSubject,
                        onXpEarned = { earned -> totalXp += earned }
                    )
                }
            }
        }
    }
}

@Composable
private fun DashboardContent(
    selectedSubject: Subject,
    rewardState: RewardState,
    streakDays: Int,
    onSubjectSelected: (Subject) -> Unit,
    onNavigateToLab: () -> Unit,
    onNavigateToQuiz: () -> Unit,
    onRedeemMinutes: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HeaderSection(rewardState = rewardState, streakDays = streakDays)

        // Fach-Auswahl Pills Row
        SubjectSelectorRow(
            selectedSubject = selectedSubject,
            onSubjectSelected = onSubjectSelected
        )

        // PC-Zeit Belohnungs-System Card
        RewardCard(
            rewardState = rewardState,
            onRedeemMinutes = onRedeemMinutes
        )

        SubjectWelcomeCard(subject = selectedSubject)

        QuickLabCard(subject = selectedSubject, onNavigateToLab = onNavigateToLab)

        PhScalePreviewCard()

        ModulesSection(subject = selectedSubject, onNavigateToQuiz = onNavigateToQuiz)
    }
}

@Composable
private fun HeaderSection(rewardState: RewardState, streakDays: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "LearnBetter",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Gesamtschule • 10. Klasse",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("🔥", fontSize = 12.sp)
                    Text(
                        text = "$streakDays T.",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.tertiaryContainer
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("⭐", fontSize = 12.sp)
                    Text(
                        text = "${rewardState.totalXp} XP",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(14.dp),
                color = ChemistryColors.AchievementGold
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("💻", fontSize = 12.sp)
                    Text(
                        text = "${rewardState.availableMinutes}m",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
private fun SubjectSelectorRow(
    selectedSubject: Subject,
    onSubjectSelected: (Subject) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = "Fach auswählen:",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Subject.entries.forEach { subject ->
                val isSelected = subject == selectedSubject
                Surface(
                    modifier = Modifier.clickable { onSubjectSelected(subject) },
                    shape = RoundedCornerShape(16.dp),
                    color = if (isSelected) subject.primaryColor else MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(subject.icon, fontSize = 16.sp)
                        Text(
                            text = subject.displayName,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SubjectWelcomeCard(subject: Subject) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = subject.primaryColor.copy(alpha = 0.15f)
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "${subject.icon} ${subject.displayName}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = subject.primaryColor
            )
            Text(
                text = subject.subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun QuickLabCard(subject: Subject, onNavigateToLab: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(subject.icon, fontSize = 36.sp)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${subject.displayName}-Werkstatt",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = "Starte die interaktive Werkstatt für ${subject.displayName}!",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
                )
            }
            Button(
                onClick = onNavigateToLab,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = subject.primaryColor)
            ) {
                Text("Starten", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun PhScalePreviewCard() {
    var phSliderValue by remember { mutableFloatStateOf(1.0f) }
    val phColor by animateColorAsState(targetValue = getPhColor(phSliderValue))

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "📊 Interaktive pH-Skala",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(phColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${(phSliderValue * 10).toInt() / 10.0}",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "pH-Wert: ${((phSliderValue * 10).toInt() / 10.0)}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = getPhLabel(phSliderValue),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Slider(
                value = phSliderValue,
                onValueChange = { phSliderValue = it },
                valueRange = 0f..14f,
                steps = 27,
                colors = SliderDefaults.colors(
                    thumbColor = phColor,
                    activeTrackColor = phColor
                )
            )
        }
    }
}

@Composable
private fun ModulesSection(subject: Subject, onNavigateToQuiz: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Lerneinheiten (${subject.displayName})",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            TextButton(onClick = onNavigateToQuiz) {
                Text("Quiz starten ➔")
            }
        }

        ModuleItem(
            icon = subject.icon,
            title = "1. Grundlagen ${subject.displayName}",
            subtitle = "Kernkonzepte & Grundwissen",
            progress = 0.85f,
            accentColor = subject.primaryColor
        )

        ModuleItem(
            icon = "⚡",
            title = "2. Interaktive Werkstatt",
            subtitle = "Aufgaben & Simulationen",
            progress = 0.5f,
            accentColor = subject.primaryColor
        )
    }
}

@Composable
private fun ModuleItem(
    icon: String,
    title: String,
    subtitle: String,
    progress: Float,
    accentColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(accentColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = icon, fontSize = 22.sp)
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .weight(1f)
                            .height(6.dp)
                            .clip(CircleShape),
                        color = accentColor,
                        trackColor = accentColor.copy(alpha = 0.2f)
                    )
                    Text(
                        text = "${(progress * 100).toInt()}%",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
