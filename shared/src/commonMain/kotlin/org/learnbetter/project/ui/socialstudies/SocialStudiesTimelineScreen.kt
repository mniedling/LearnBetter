package org.learnbetter.project.ui.socialstudies

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
import org.learnbetter.project.model.Subject

data class HistoricalEvent(
    val year: String,
    val title: String,
    val icon: String,
    val description: String,
    val ggArticle: String? = null
)

@Composable
fun SocialStudiesTimelineScreen(
    onXpEarned: (Int) -> Unit = {},
    onBackToHub: (() -> Unit)? = null
) {
    var selectedEvent by remember { mutableStateOf<HistoricalEvent?>(null) }
    var exploredEvents by remember { mutableStateOf(setOf<String>()) }

    val timeline = listOf(
        HistoricalEvent("1914 - 1918", "Erster Weltkrieg", "⚔️", "Der erste globale Krieg der Moderne. Endete mit dem Sturz der Monarchie in Deutschland."),
        HistoricalEvent("1919", "Weimarer Republik", "📜", "Erste parlamentarische Demokratie auf deutschem Boden. Konfrontiert mit Krisen und Radikalisierung."),
        HistoricalEvent("1933 - 1945", "NS-Diktatur & 2. Weltkrieg", "⚠️", "Gewaltherrschaft des Nationalsozialismus, Holocaust und Zerstörung Europas."),
        HistoricalEvent("1949", "Entstehung des Grundgesetzes (GG)", "⚖️", "Verkündung des Grundgesetzes und Gründung der Bundesrepublik Deutschland. Lehre aus der Diktatur.", "Artikel 1: Die Würde des Menschen ist unantastbar."),
        HistoricalEvent("1989", "Mauerfall in Berlin", "🧱", "Friedliche Revolution in der DDR führt zur Öffnung der Berliner Mauer am 9. November."),
        HistoricalEvent("1990", "Deutsche Wiedervereinigung", "🕊️", "Beitritt der DDR zur Bundesrepublik Deutschland am 3. Oktober (Tag der Deutschen Einheit).")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (onBackToHub != null) {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = onBackToHub) {
                    Text("◀️ Zurück zur Übersicht")
                }
            }
        }

        Text(
            text = "🌍 Demokratie & Zeitleiste",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Subject.SOCIAL_STUDIES.primaryColor,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Erforsche die wichtigsten Meilensteine der deutschen Geschichte und das Grundgesetz!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        // Grundgesetz Highlight Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("⚖️ Grundgesetz Art. 1 (1):", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                Text("»Die Würde des Menschen ist unantastbar. Sie zu achten und zu schützen ist Verpflichtung aller staatlichen Gewalt.«", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        }

        // Zeitleiste
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            timeline.forEach { event ->
                val isSelected = selectedEvent == event
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedEvent = event
                            if (!exploredEvents.contains(event.year)) {
                                exploredEvents = exploredEvents + event.year
                                onXpEarned(5)
                            }
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) Subject.SOCIAL_STUDIES.primaryColor else MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) MaterialTheme.colorScheme.surface.copy(alpha = 0.2f) else MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = event.year,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) androidx.compose.ui.graphics.Color.White else MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${event.icon} ${event.title}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) androidx.compose.ui.graphics.Color.White else MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = event.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = if (isSelected) androidx.compose.ui.graphics.Color.White.copy(alpha = 0.85f) else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}
