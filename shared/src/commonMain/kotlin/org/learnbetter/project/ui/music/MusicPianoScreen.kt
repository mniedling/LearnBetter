package org.learnbetter.project.ui.music

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.learnbetter.project.model.Subject

data class PianoKey(val note: String, val isBlack: Boolean = false)

@Composable
fun MusicPianoScreen(
    onXpEarned: (Int) -> Unit = {},
    onBackToHub: (() -> Unit)? = null
) {
    var playedNotes by remember { mutableStateOf(listOf<String>()) }
    var activeChord by remember { mutableStateOf<String?>(null) }

    val whiteKeys = listOf("C", "D", "E", "F", "G", "A", "H", "C'")

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
            text = "🎵 Virtuelles Klavier & Dreiklänge",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Subject.MUSIC.primaryColor,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Tippe auf die Tasten und baue Dur- und Moll-Dreiklänge auf der Klaviatur!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        // Piano Visualizer Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1F1B24))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                whiteKeys.forEach { note ->
                    val isHighlighted = playedNotes.contains(note)
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable {
                                playedNotes = (playedNotes + note).takeLast(3)
                                if (playedNotes.size == 3) {
                                    val chord = playedNotes.joinToString(" - ")
                                    if (chord == "C - E - G") {
                                        activeChord = "C-Dur Dreiklang (Große Terz + Kleine Terz) 🎉"
                                        onXpEarned(10)
                                    } else if (chord == "A - C - E") {
                                        activeChord = "A-Moll Dreiklang (Kleine Terz + Große Terz) 🎉"
                                        onXpEarned(10)
                                    } else {
                                        activeChord = "Akkord: $chord"
                                    }
                                }
                            },
                        shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp),
                        color = if (isHighlighted) Subject.MUSIC.primaryColor else Color.White
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize().padding(bottom = 8.dp),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Text(
                                text = note,
                                fontWeight = FontWeight.Bold,
                                color = if (isHighlighted) Color.White else Color.Black
                            )
                        }
                    }
                }
            }
        }

        // Display Active Notes
        if (playedNotes.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Gespielte Töne: ${playedNotes.joinToString("  •  ")}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    if (activeChord != null) {
                        Text(
                            text = activeChord!!,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Subject.MUSIC.primaryColor
                        )
                    }

                    Button(
                        onClick = {
                            playedNotes = emptyList()
                            activeChord = null
                        },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("🔄 Zurücksetzen")
                    }
                }
            }
        }

        // Dreiklang-Aufgaben Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("🎼 Aufgabe: Baue einen C-Dur Dreiklang!", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                Text("Tippe nacheinander auf die Töne: C  ➔  E  ➔  G", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
