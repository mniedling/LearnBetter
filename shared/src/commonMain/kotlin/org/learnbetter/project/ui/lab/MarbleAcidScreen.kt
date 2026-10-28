package org.learnbetter.project.ui.lab

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import org.learnbetter.project.ui.theme.ChemistryColors

@Composable
fun MarbleAcidScreen(
    onXpEarned: (Int) -> Unit = {},
    onBackToHub: (() -> Unit)? = null
) {
    var selectedAcidName by remember { mutableStateOf("Zitronensäure") }
    var isPoured by remember { mutableStateOf(false) }
    var reactionDone by remember { mutableStateOf(false) }

    LaunchedEffect(isPoured) {
        if (isPoured) {
            kotlinx.coroutines.delay(2000)
            reactionDone = true
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
            text = "🏛️ Säure vs. Marmorfliesen",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Subject.CHEMISTRY.primaryColor,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Erforsche, was passiert, wenn Säure (wie Zitronensäure oder Essig) auf edle Marmorfliesen trifft!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        // Visualizer Card
        MarbleVisualizerCard(
            selectedAcidName = selectedAcidName,
            isPoured = isPoured,
            reactionDone = reactionDone
        )

        // Säure Auswahl
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Zitronensäure", "Essig", "Salzsäure").forEach { acid ->
                val isSelected = acid == selectedAcidName
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) Subject.CHEMISTRY.primaryColor else MaterialTheme.colorScheme.surfaceVariant,
                    onClick = {
                        if (!isPoured) selectedAcidName = acid
                    }
                ) {
                    Text(
                        text = acid,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(10.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Action Button
        Button(
            onClick = {
                if (!isPoured) {
                    isPoured = true
                } else {
                    isPoured = false
                    reactionDone = false
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Subject.CHEMISTRY.primaryColor)
        ) {
            Text(
                text = if (isPoured) "🔄 Fliese reinigen / Zurücksetzen" else "🧪 $selectedAcidName auf Marmor tröpfeln!",
                fontWeight = FontWeight.Bold
            )
        }

        // Fazit Card
        if (reactionDone) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("⚠️ WICHTIGES ALLTAGS-FAZIT:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onErrorContainer)
                    Text("Darf man Marmorfliesen mit sauren Reinigern (Essig, Zitronensäure, Badreiniger) putzen?", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onErrorContainer)
                    Text("NEIN! Marmor besteht aus Calciumcarbonat (CaCO₃). Säuren lösen den Kalk auf. Es entsteht Kohlendioxid-Gas (CO₂), der Marmor wird rau, mattscheckig und dauerhaft zerstört!", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onErrorContainer)

                    HorizontalDivider(color = MaterialTheme.colorScheme.onErrorContainer.copy(alpha = 0.2f))

                    Text("Reaktionsgleichung:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onErrorContainer)
                    Text("CaCO₃  +  2 H⁺  ➔  Ca²⁺  +  H₂O  +  CO₂ ↑ (Gasbläschen)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onErrorContainer)
                }
            }
        }
    }
}

@Composable
private fun MarbleVisualizerCard(
    selectedAcidName: String,
    isPoured: Boolean,
    reactionDone: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2C3E50))
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Marmorfliese
            Box(
                modifier = Modifier
                    .size(160.dp, 100.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (reactionDone) Color(0xFFB0BEC5) else Color(0xFFECEFF1))
                    .border(3.dp, Color.Gray, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("🏛️ Marmorfliese", fontWeight = FontWeight.Bold, color = Color.DarkGray)
                    Text("(Calciumcarbonat CaCO₃)", fontSize = 10.sp, color = Color.Gray)

                    if (isPoured && !reactionDone) {
                        Text("🫧 Schäumt heftig! (CO₂ 🫧)", color = ChemistryColors.AcidOrange, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    } else if (reactionDone) {
                        Text("💥 Ätzspuren & Rauheiten!", color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
