package org.learnbetter.project.ui.lab

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
fun ChemicalBondGameScreen(
    onXpEarned: (Int) -> Unit = {},
    onBackToHub: (() -> Unit)? = null
) {
    var bondMode by remember { mutableIntStateOf(1) } // 1 = Ionenbindung, 2 = Elektronenpaarbindung
    var ionBondDone by remember { mutableStateOf(false) }
    var covalentBondDone by remember { mutableStateOf(false) }

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
            text = "⚛️ Atombinder - Das Bindungsspiel",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Subject.CHEMISTRY.primaryColor,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Lerne wie lustige Atome Elektronen abgeben, ausleihen oder gemeinsam nutzen!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        // Mode Switcher
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { bondMode = 1 },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (bondMode == 1) Subject.CHEMISTRY.primaryColor else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text("1. Ionenbindung (NaCl)", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = if (bondMode == 1) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Button(
                onClick = { bondMode = 2 },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (bondMode == 2) Subject.CHEMISTRY.primaryColor else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text("2. Atombindung (H₂O)", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = if (bondMode == 2) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        if (bondMode == 1) {
            IonicBondGame(
                isDone = ionBondDone,
                onComplete = {
                    if (!ionBondDone) {
                        ionBondDone = true
                        onXpEarned(15)
                    }
                }
            )
        } else {
            CovalentBondGame(
                isDone = covalentBondDone,
                onComplete = {
                    if (!covalentBondDone) {
                        covalentBondDone = true
                        onXpEarned(15)
                    }
                }
            )
        }
    }
}

@Composable
private fun IonicBondGame(
    isDone: Boolean,
    onComplete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = if (isDone) "🎉 Kochsalz (NaCl) entstanden!" else "1. Ionenbindung: Natrium (Na) & Chlor (Cl)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Natrium Atom
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .background(if (isDone) Color(0xFF4CAF50) else Color(0xFFFFB74D)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(if (isDone) "Na⁺ 😄" else "Na 😃", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                    Text("Natrium", style = MaterialTheme.typography.labelSmall)
                }

                Text(if (isDone) "⚡ ➕ ⚡" else "➔ 🟡 ➔", fontSize = 24.sp)

                // Chlor Atom
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(if (isDone) Color(0xFF1E88E5) else Color(0xFF81C784)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(if (isDone) "Cl⁻ 😍" else "Cl 😮", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                    Text("Chlor", style = MaterialTheme.typography.labelSmall)
                }
            }

            if (!isDone) {
                Button(
                    onClick = onComplete,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("🟡 Elektron von Na an Cl abgeben!", fontWeight = FontWeight.Bold)
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("💡 Erklärung Ionenbindung:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                        Text("Natrium (Na) gibt sein 1 Außenelektron vollständig an Chlor (Cl) ab. Es entstehen positiv geladenes Na⁺ und negativ geladenes Cl⁻. Die gegenseitige Anziehung bildet ein Kochsalz-Ionengitter!", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

@Composable
private fun CovalentBondGame(
    isDone: Boolean,
    onComplete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = if (isDone) "🎉 Wasser-Molekül (H₂O) entstanden!" else "2. Elektronenpaarbindung: 2x H & 1x O",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Wasserstoff H
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF64B5F6)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("H 😊", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }

                Text(if (isDone) "🤝 🤝" else "➕", fontSize = 20.sp)

                // Sauerstoff O
                Box(
                    modifier = Modifier
                        .size(75.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE57373)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("O 🤔", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }

                Text(if (isDone) "🤝 🤝" else "➕", fontSize = 20.sp)

                // Wasserstoff H
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF64B5F6)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("H 😊", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }

            if (!isDone) {
                Button(
                    onClick = onComplete,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("🤝 Elektronenpaare gemeinsam nutzen!", fontWeight = FontWeight.Bold)
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("💡 Erklärung Atombindung / Elektronenpaarbindung:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                        Text("Bei Nichtmetallen werden Elektronen nicht abgegeben, sondern gemeinsam genutzt. Jedes H-Atom teilt sich ein Elektronenpaar mit dem O-Atom, um die Edelgaskonfiguration zu erreichen!", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
