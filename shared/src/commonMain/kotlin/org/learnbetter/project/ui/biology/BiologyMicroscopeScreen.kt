package org.learnbetter.project.ui.biology

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.learnbetter.project.model.Subject

data class Organelle(
    val name: String,
    val icon: String,
    val color: Color,
    val function: String
)

@Composable
fun BiologyMicroscopeScreen(
    onXpEarned: (Int) -> Unit = {},
    onBackToHub: (() -> Unit)? = null
) {
    var zoomLevel by remember { mutableIntStateOf(100) } // 10x, 100x, 1000x
    var selectedOrganelle by remember { mutableStateOf<Organelle?>(null) }
    val discoveredOrganelles = remember { mutableStateListOf<String>() }

    val organelles = listOf(
        Organelle("Chloroplast", "🟢", Color(0xFF2E7D32), "Fotosynthese: Wandelt CO₂ + Wasser mit Sonnenlicht in Glucose (Zucker) und Sauerstoff (O₂) um."),
        Organelle("Zellkern", "🟣", Color(0xFF6A1B9A), "Steuerzentrale der Zelle: Enthält die Erbinformation (DNA) und steuert alle Stoffwechselprozesse."),
        Organelle("Mitochondrium", "🔴", Color(0xFFC62828), "Kraftwerk der Zelle: Erzeugt durch Zellatmung Energie (ATP) aus Nährstoffen."),
        Organelle("Vakuole", "💧", Color(0xFF0288D1), "Zellsaftraum: Speichert Wasser, Nährstoffe und sorgt für den Innendruck (Turgor)."),
        Organelle("Zellwand", "🛡️", Color(0xFF8D6E63), "Schutzhülle: Besteht aus Cellulose und verleiht der Pflanzenzelle Stabilität.")
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
            text = "🔬 Virtuelles Mikroskop",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Subject.BIOLOGY.primaryColor,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Erforsche den Aufbau einer Pflanzenzelle! Wähle die Zoomstufe und tippe auf die Organellen.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        // 1. Zoomsteuerelement
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(10, 100, 1000).forEach { level ->
                val isSelected = zoomLevel == level
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { zoomLevel = level },
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) Subject.BIOLOGY.primaryColor else MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = "${level}x Zoom",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 10.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // 2. Mikroskop Okularansicht
        MicroscopeLensVisualizer(
            zoomLevel = zoomLevel,
            organelles = organelles,
            onOrganelleClicked = { organelle ->
                selectedOrganelle = organelle
                if (organelle.name !in discoveredOrganelles) {
                    discoveredOrganelles.add(organelle.name)
                    onXpEarned(10)
                }
            }
        )

        // 3. Organell-Detailkarte
        if (selectedOrganelle != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(selectedOrganelle!!.icon, fontSize = 28.sp)
                        Text(
                            text = selectedOrganelle!!.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))

                    Text(
                        text = selectedOrganelle!!.function,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}

@Composable
private fun MicroscopeLensVisualizer(
    zoomLevel: Int,
    organelles: List<Organelle>,
    onOrganelleClicked: (Organelle) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1B2A1E))
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Runder Mikroskop Okular-Kreis
            Box(
                modifier = Modifier
                    .size(220.dp)
                    .clip(CircleShape)
                    .border(6.dp, Color(0xFF4CAF50), CircleShape)
                    .background(Color(0xFF253B2A)),
                contentAlignment = Alignment.Center
            ) {
                if (zoomLevel == 10) {
                    // 10x: Gewebeübersicht mit mehreren kleinen Zellen
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("🧫 Pflanzengewebe", color = Color.White, fontWeight = FontWeight.Bold)
                        Text("Wechsle auf 100x oder 1000x für Details!", color = Color.LightGray, fontSize = 11.sp)
                    }
                } else {
                    // 100x / 1000x: Einzelne Zelle mit anklickbaren Organellen
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Pflanzenzelle (Elodea)",
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            organelles.take(3).forEach { org ->
                                Surface(
                                    modifier = Modifier.clickable { onOrganelleClicked(org) },
                                    shape = CircleShape,
                                    color = org.color.copy(alpha = 0.85f)
                                ) {
                                    Text(
                                        text = "${org.icon} ${org.name}",
                                        color = Color.White,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            organelles.drop(3).forEach { org ->
                                Surface(
                                    modifier = Modifier.clickable { onOrganelleClicked(org) },
                                    shape = CircleShape,
                                    color = org.color.copy(alpha = 0.85f)
                                ) {
                                    Text(
                                        text = "${org.icon} ${org.name}",
                                        color = Color.White,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
