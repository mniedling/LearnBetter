package org.learnbetter.project.ui.police

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
import org.learnbetter.project.model.PoliceData
import org.learnbetter.project.model.PoliceEquipment
import org.learnbetter.project.model.Subject
import org.learnbetter.project.ui.theme.ChemistryColors

@Composable
fun PoliceEquipmentScreen(
    onXpEarned: (Int) -> Unit = {}
) {
    val items = remember { PoliceData.equipment }
    var selectedItemId by remember { mutableStateOf(items.first().id) }
    val currentItem = items.first { it.id == selectedItemId }

    var exploredItems by remember { mutableStateOf(setOf<String>()) }

    LaunchedEffect(exploredItems.size) {
        if (exploredItems.size == items.size) {
            onXpEarned(15)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🛡️ Einsatzmittel & Ausrüstung",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Subject.POLICE_TRAINING.primaryColor,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Lerne die Führungs- und Einsatzmittel der Bundespolizei sowie die Sicherheitsregeln kennen!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        // Equipment Grid / Selector
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items.forEach { item ->
                val isSelected = item.id == selectedItemId
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 2.dp,
                            color = if (isSelected) Subject.POLICE_TRAINING.primaryColor else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable {
                            selectedItemId = item.id
                            exploredItems = exploredItems + item.id
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) Subject.POLICE_TRAINING.primaryColor.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(item.icon, fontSize = 28.sp)
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text(item.category, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        if (exploredItems.contains(item.id)) {
                            Text("✓", fontWeight = FontWeight.Bold, color = ChemistryColors.NeutralGreen)
                        }
                    }
                }
            }
        }

        // Detailansicht
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(currentItem.icon, fontSize = 32.sp)
                    Column {
                        Text(currentItem.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        Text(currentItem.category, style = MaterialTheme.typography.labelMedium, color = Subject.POLICE_TRAINING.primaryColor)
                    }
                }

                HorizontalDivider()

                Text("📖 Beschreibung & Einsatz:", fontWeight = FontWeight.Bold)
                Text(currentItem.description, style = MaterialTheme.typography.bodyMedium)

                HorizontalDivider()

                Text("⚠️ Wichtige Sicherheitsregel:", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error)
                Text(currentItem.safetyRule, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
            }
        }

        if (exploredItems.size == items.size) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("🎉 Alle Ausrüstungsgegenstände erkundet! (+15 XP)", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onTertiaryContainer, textAlign = TextAlign.Center)
                }
            }
        }
    }
}
