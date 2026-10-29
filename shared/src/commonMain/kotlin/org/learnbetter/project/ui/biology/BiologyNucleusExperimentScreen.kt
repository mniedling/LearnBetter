package org.learnbetter.project.ui.biology

import androidx.compose.foundation.background
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

@Composable
fun BiologyNucleusExperimentScreen(
    onXpEarned: (Int) -> Unit = {},
    onBackToHub: (() -> Unit)? = null
) {
    var expStep by remember { mutableIntStateOf(1) } // 1 = Ausgang, 2 = Schneiden & Transplantieren, 3 = Wuchs
    var selectedNucleusPart by remember { mutableStateOf<String?>(null) }

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
            text = "🔬 Hutalgen-Experiment & Zellkern",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Subject.BIOLOGY.primaryColor,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Erforsche das berühmte Hämmerling-Experiment zur Funktion des Zellkerns und untersuche seine Bausteine!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        // 1. Hämmerling Experiment Visualizer
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("🧪 Das Hämmerling-Experiment (Acetabularia):", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF1F2937)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        when (expStep) {
                            1 -> {
                                Text("☂️ Hutalge A (Schirmhut)  |  ☀️ Hutalge B (Strahlenhut)", color = Color.White, fontWeight = FontWeight.Bold)
                                Text("Beide Algen besitzen ihren Zellkern unten im Fuß.", color = Color.LightGray, fontSize = 12.sp)
                            }
                            2 -> {
                                Text("✂️ Hut A abgeschnitten! Kern B in Fuß A eingesetzt!", color = Color(0xFFFFD700), fontWeight = FontWeight.Bold)
                                Text("Der Zellkern von Hutalge B wird transplantiert...", color = Color.LightGray, fontSize = 12.sp)
                            }
                            else -> {
                                Text("☀️ Neuer Hut wächst nach! Er hat die Form von Hutalge B!", color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
                                Text("Beweis: Der Zellkern steuert die Ausbildung aller Merkmale!", color = Color.White, fontSize = 12.sp)
                            }
                        }
                    }
                }

                if (expStep < 3) {
                    Button(
                        onClick = {
                            expStep++
                            if (expStep == 3) onXpEarned(20)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Subject.BIOLOGY.primaryColor)
                    ) {
                        Text(if (expStep == 1) "✂️ 1. Hut A kappen & Kern B einsetzen" else "🌱 2. Hut nachwachsen lassen!", fontWeight = FontWeight.Bold)
                    }
                } else {
                    OutlinedButton(
                        onClick = { expStep = 1 },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("🔄 Experiment wiederholen")
                    }
                }
            }
        }

        // 2. Zellkern Detail Explorer (Kernporen, Chromatin, Nucleolus)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("🟣 Zellkern Detailansicht (Explorer):", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)

                // Interactive Parts
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedNucleusPart = "poren" },
                        shape = RoundedCornerShape(12.dp),
                        color = if (selectedNucleusPart == "poren") Color(0xFF6A1B9A) else MaterialTheme.colorScheme.surface
                    ) {
                        Text("🕳️ Kernporen", fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp), textAlign = TextAlign.Center, color = if (selectedNucleusPart == "poren") Color.White else MaterialTheme.colorScheme.onSurface)
                    }

                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedNucleusPart = "chromatin" },
                        shape = RoundedCornerShape(12.dp),
                        color = if (selectedNucleusPart == "chromatin") Color(0xFF6A1B9A) else MaterialTheme.colorScheme.surface
                    ) {
                        Text("🧬 Chromatin (DNA)", fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp), textAlign = TextAlign.Center, color = if (selectedNucleusPart == "chromatin") Color.White else MaterialTheme.colorScheme.onSurface)
                    }

                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedNucleusPart = "nucleolus" },
                        shape = RoundedCornerShape(12.dp),
                        color = if (selectedNucleusPart == "nucleolus") Color(0xFF6A1B9A) else MaterialTheme.colorScheme.surface
                    ) {
                        Text("🟡 Nucleolus", fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp), textAlign = TextAlign.Center, color = if (selectedNucleusPart == "nucleolus") Color.White else MaterialTheme.colorScheme.onSurface)
                    }
                }

                if (selectedNucleusPart != null) {
                    val desc = when (selectedNucleusPart) {
                        "poren" -> "Kernporen sind Mikrotunnel in der doppelten Kernhülle. Sie steuern den gezielten Stoffaustausch von RNA und Proteinen zwischen Zellkern und Zellplasma!"
                        "chromatin" -> "Chromatin besteht aus fädigen DNA-Strängen und Eiweißen. Hier sind alle Erbinformationen (Gene) der Zelle gespeichert!"
                        else -> "Der Nucleolus (Kernkörperchen) ist ein rundliches Gebilde im Zentrum des Zellkerns. Er baut die Bausteine für die Ribosomen auf!"
                    }
                    Text(desc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }
        }
    }
}
