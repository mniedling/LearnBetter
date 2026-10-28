package org.learnbetter.project.ui.lab

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import kotlinx.coroutines.delay
import org.learnbetter.project.model.Subject
import org.learnbetter.project.ui.theme.ChemistryColors

@Composable
fun SulfuricAcidLabScreen(
    onXpEarned: (Int) -> Unit = {},
    onBackToHub: (() -> Unit)? = null
) {
    var isSugarExperimentActive by remember { mutableStateOf(false) }
    var isSugarCharred by remember { mutableStateOf(false) }

    LaunchedEffect(isSugarExperimentActive) {
        if (isSugarExperimentActive && !isSugarCharred) {
            delay(2000)
            isSugarCharred = true
            onXpEarned(20)
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
        if (onBackToHub != null) {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = onBackToHub) {
                    Text("◀️ Zurück zum Labor-Zentrum")
                }
            }
        }

        Text(
            text = "🧪 Schwefelsäure (H₂SO₄) & Kontaktverfahren",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Subject.CHEMISTRY.primaryColor,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Lerne das Kontaktverfahren zur Herstellung und die stark wasserentziehende Wirkung auf Zucker kennen!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        // 1. Kontaktverfahren Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("⚙️ Das Kontaktverfahren (3 Schritte):", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                Text("1. Schwefel verbrennen:  S  +  O₂  ➔  SO₂ (Schwefeldioxid)", style = MaterialTheme.typography.bodySmall)
                Text("2. Katalytische Oxidation:  2 SO₂  +  O₂  ➔  2 SO₃ (Schwefeltrioxid)", style = MaterialTheme.typography.bodySmall)
                Text("3. Reaktion mit Wasser:  SO₃  +  H₂O  ➔  H₂SO₄ (Schwefelsäure)", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
            }
        }

        // 2. Experiment Visualizer
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF212121))
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (isSugarCharred) {
                        // Schwarze Kohlenstoff-Säule
                        Box(
                            modifier = Modifier
                                .width(40.dp)
                                .height(110.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Black)
                                .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
                        )
                        Text("💨 Schwarzer Kohlenstoff-Turm (C) + H₂O Dampf!", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    } else if (isSugarExperimentActive) {
                        Text("🧪 Schwefelsäure reagiert mit Zucker...", color = ChemistryColors.AcidOrange, fontWeight = FontWeight.Bold)
                    } else {
                        Box(
                            modifier = Modifier
                                .width(50.dp)
                                .height(40.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color.White)
                        )
                        Text("⚪ Weißer Haushaltszucker (Saccharose)", color = Color.LightGray, fontSize = 12.sp)
                    }
                }
            }
        }

        Button(
            onClick = {
                if (!isSugarExperimentActive) {
                    isSugarExperimentActive = true
                } else {
                    isSugarExperimentActive = false
                    isSugarCharred = false
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Subject.CHEMISTRY.primaryColor)
        ) {
            Text(
                text = if (isSugarExperimentActive) "🔄 Becherglas reinigen" else "🧪 Konz. H₂SO₄ auf Zucker gießen!",
                fontWeight = FontWeight.Bold
            )
        }

        if (isSugarCharred) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("💡 Erklärung Wasserentzug:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = Subject.CHEMISTRY.primaryColor)
                    Text("Konzentrierte Schwefelsäure (H₂SO₄) ist extrem hygroskopisch (wasserentziehend). Sie entzieht dem Zucker (C₁₂H₂₂O₁₁) das gesamte enthaltene Wasser (H₂O). Zurück bleibt reiner, schwarzer Kohlenstoff (C)!", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
