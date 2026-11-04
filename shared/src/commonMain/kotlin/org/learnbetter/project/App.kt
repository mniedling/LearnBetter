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
import kotlinx.coroutines.launch
import org.learnbetter.project.model.RewardExchangeRate
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    var selectedSubject by remember { mutableStateOf(Subject.CHEMISTRY) }
    var selectedTab by remember { mutableStateOf(NavigationTab.DASHBOARD) }
    var totalXp by remember { mutableIntStateOf(0) }
    var streakDays by remember { mutableIntStateOf(1) }
    var redeemedMinutes by remember { mutableIntStateOf(0) }
    var exchangeRate by remember { mutableStateOf(RewardExchangeRate.BALANCED) }

    val rewardState = remember(totalXp, redeemedMinutes, exchangeRate) {
        RewardState(totalXp = totalXp, redeemedMinutes = redeemedMinutes, exchangeRate = exchangeRate)
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val schoolSubjects = remember { Subject.entries.filter { it.isSchoolSubject } }
    val otherCategories = remember { Subject.entries.filter { !it.isSchoolSubject } }

    LearnBetterTheme {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    drawerContainerColor = MaterialTheme.colorScheme.surface
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "LearnBetter",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        HorizontalDivider()

                        // --- 1. SCHUL-FÄCHER ---
                        Text(
                            text = "📚 Fächer",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        schoolSubjects.forEach { subject ->
                            val isSelected = subject == selectedSubject
                            NavigationDrawerItem(
                                icon = { Text(subject.icon, fontSize = 20.sp) },
                                label = {
                                    Text(
                                        text = subject.displayName,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                    )
                                },
                                selected = isSelected,
                                onClick = {
                                    selectedSubject = subject
                                    scope.launch { drawerState.close() }
                                },
                                shape = RoundedCornerShape(16.dp),
                                colors = NavigationDrawerItemDefaults.colors(
                                    selectedContainerColor = subject.primaryColor
                                )
                            )
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                        // --- 2. ANDERE KATEGORIEN ---
                        Text(
                            text = "⭐ Andere Kategorien",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Subject.DRIVING_SCHOOL.primaryColor
                        )

                        otherCategories.forEach { category ->
                            val isSelected = category == selectedSubject
                            NavigationDrawerItem(
                                icon = { Text(category.icon, fontSize = 20.sp) },
                                label = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = category.displayName,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurface
                                        )

                                        if (!category.isAvailable) {
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
                                },
                                selected = isSelected,
                                onClick = {
                                    if (category.isAvailable) {
                                        selectedSubject = category
                                        scope.launch { drawerState.close() }
                                    }
                                },
                                shape = RoundedCornerShape(16.dp),
                                colors = NavigationDrawerItemDefaults.colors(
                                    selectedContainerColor = category.primaryColor
                                )
                            )
                        }
                    }
                }
            }
        ) {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text("LearnBetter", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
                        },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Text("☰", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            }
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                },
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
                            onRateChanged = { exchangeRate = it },
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
}

@Composable
private fun DashboardContent(
    selectedSubject: Subject,
    rewardState: RewardState,
    streakDays: Int,
    onRateChanged: (RewardExchangeRate) -> Unit,
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

        // HAUPT-FOKUS AUF DEM STARTBILDSCHIRM: PC-ZEIT BELOHNUNGS-KARTE
        RewardCard(
            rewardState = rewardState,
            onRateChanged = onRateChanged,
            onRedeemMinutes = onRedeemMinutes
        )

        ActiveCategorySummaryCard(
            subject = selectedSubject,
            onNavigateToLab = onNavigateToLab,
            onNavigateToQuiz = onNavigateToQuiz
        )

        if (selectedSubject == Subject.CHEMISTRY) {
            PhScalePreviewCard()
        }
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
                text = "Willkommen zurück! 👋",
                style = MaterialTheme.typography.titleMedium,
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
private fun ActiveCategorySummaryCard(
    subject: Subject,
    onNavigateToLab: () -> Unit,
    onNavigateToQuiz: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = subject.primaryColor.copy(alpha = 0.12f)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(subject.icon, fontSize = 28.sp)
                Column {
                    Text(
                        text = "Aktive Kategorie: ${subject.displayName}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = subject.primaryColor
                    )
                    Text(
                        text = subject.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onNavigateToLab,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = subject.primaryColor)
                ) {
                    Text("🧪 Werkstatt", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = if (subject == Subject.DRIVING_SCHOOL) Color.Black else Color.White)
                }

                Button(
                    onClick = onNavigateToQuiz,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = subject.primaryColor)
                ) {
                    Text("📝 Quiz / Test", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = if (subject == Subject.DRIVING_SCHOOL) Color.Black else Color.White)
                }
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
