package org.learnbetter.project

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
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
import org.learnbetter.project.ui.lab.LabHubScreen
import org.learnbetter.project.ui.quiz.QuizScreen
import org.learnbetter.project.ui.theme.ChemistryColors
import org.learnbetter.project.ui.theme.LearnBetterTheme
import org.learnbetter.project.ui.theme.getPhColor
import org.learnbetter.project.ui.theme.getPhLabel

enum class NavigationTab(val label: String, val icon: String) {
    DASHBOARD("Übersicht", "🏠"),
    LAB("Virtuelles Labor", "🧪"),
    QUIZ("Quiz", "📝")
}

@Composable
@Preview
fun App() {
    var selectedTab by remember { mutableStateOf(NavigationTab.DASHBOARD) }
    var totalXp by remember { mutableIntStateOf(250) }
    var streakDays by remember { mutableIntStateOf(3) }

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
                        totalXp = totalXp,
                        streakDays = streakDays,
                        onNavigateToLab = { selectedTab = NavigationTab.LAB },
                        onNavigateToQuiz = { selectedTab = NavigationTab.QUIZ }
                    )
                    NavigationTab.LAB -> LabHubScreen(
                        onXpEarned = { earned -> totalXp += earned }
                    )
                    NavigationTab.QUIZ -> QuizScreen(
                        onXpEarned = { earned -> totalXp += earned }
                    )
                }
            }
        }
    }
}

@Composable
private fun DashboardContent(
    totalXp: Int,
    streakDays: Int,
    onNavigateToLab: () -> Unit,
    onNavigateToQuiz: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HeaderSection(totalXp = totalXp, streakDays = streakDays)

        WelcomeCard()

        QuickLabCard(onNavigateToLab = onNavigateToLab)

        PhScalePreviewCard()

        ModulesSection(onNavigateToQuiz = onNavigateToQuiz)
    }
}

@Composable
private fun HeaderSection(totalXp: Int, streakDays: Int) {
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
                text = "Chemie • 10. Klasse",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("🔥", fontSize = 14.sp)
                    Text(
                        text = "$streakDays Tage",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.tertiaryContainer
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("⭐", fontSize = 14.sp)
                    Text(
                        text = "$totalXp XP",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }
        }
    }
}

@Composable
private fun WelcomeCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "🧪 Säuren, Laugen & Oxidation",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "Erforsche Säure-Metallreaktionen, entzünde Magnesiumbänder und messe den pH-Wert im virtuellen Labor!",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
            )
        }
    }
}

@Composable
private fun QuickLabCard(onNavigateToLab: () -> Unit) {
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
            Text("🥼", fontSize = 36.sp)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Virtuelles Chemie-Labor",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = "Wähle aus verschiedenen Experimenten (Säuren, pH-Test, Magnesium brennen)!",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
                )
            }
            Button(
                onClick = onNavigateToLab,
                shape = RoundedCornerShape(12.dp)
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
private fun ModulesSection(onNavigateToQuiz: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Lerneinheiten",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            TextButton(onClick = onNavigateToQuiz) {
                Text("Quiz starten ➔")
            }
        }

        ModuleItem(
            icon = "🧪",
            title = "1. Säuren & Basen Übersicht",
            subtitle = "HCl, H₂SO₄, HNO₃, CH₃COOH",
            progress = 0.85f,
            accentColor = ChemistryColors.AcidRed
        )

        ModuleItem(
            icon = "💥",
            title = "2. Säuren & Metallreaktionen",
            subtitle = "Magnesium, Wasserstoff & Knallgasprobe",
            progress = 0.5f,
            accentColor = ChemistryColors.HydrogenCyan
        )

        ModuleItem(
            icon = "🔥",
            title = "3. Verbrennung & Oxidation",
            subtitle = "Magnesiumband & Magnesiumoxid (MgO)",
            progress = 0.25f,
            accentColor = ChemistryColors.ReactionHeat
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
