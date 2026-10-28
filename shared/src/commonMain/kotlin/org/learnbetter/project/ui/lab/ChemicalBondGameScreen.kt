package org.learnbetter.project.ui.lab

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
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
            text = "Schau, wie die traurigen Atome glücklich werden, wenn sie Elektronen paaren!",
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
            IonicBondGameCanvas(
                isDone = ionBondDone,
                onComplete = {
                    if (!ionBondDone) {
                        ionBondDone = true
                        onXpEarned(15)
                    }
                }
            )
        } else {
            CovalentBondGameCanvas(
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
private fun IonicBondGameCanvas(
    isDone: Boolean,
    onComplete: () -> Unit
) {
    var animateElectron by remember { mutableStateOf(false) }

    val electronProgress by animateFloatAsState(
        targetValue = if (animateElectron || isDone) 1.0f else 0.0f,
        animationSpec = tween(durationMillis = 1800, easing = FastOutSlowInEasing)
    )

    LaunchedEffect(electronProgress) {
        if (electronProgress == 1.0f && animateElectron) {
            onComplete()
        }
    }

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
                text = if (isDone) "🎉 Beide Atome sind jetzt glücklich!" else "1. Ionenbindung: Trauriges Na ➔ Trauriges Cl",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Canvas für große Atome mit Gesichtern & Paarelektronen
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF1B2631))
            ) {
                val width = size.width
                val height = size.height
                val naCenterX = width * 0.28f
                val clCenterX = width * 0.72f
                val centerY = height * 0.5f

                // Große Atomkerne
                val naRadius = 60f
                val clRadius = 75f

                // 1. Natrium Atom (Na)
                drawCircle(color = Color(0xFFFFB74D), radius = naRadius, center = Offset(naCenterX, centerY))
                drawCircle(color = Color(0xFFFF9800), radius = naRadius + 18f, center = Offset(naCenterX, centerY), style = androidx.compose.ui.graphics.drawscope.Stroke(3f))

                // 2. Chlor Atom (Cl)
                drawCircle(color = Color(0xFF81C784), radius = clRadius, center = Offset(clCenterX, centerY))
                drawCircle(color = Color(0xFF4CAF50), radius = clRadius + 18f, center = Offset(clCenterX, centerY), style = androidx.compose.ui.graphics.drawscope.Stroke(3f))

                // --- 6 Paarelektronen bei Chlor (3 Paare: oben, unten, rechts) ---
                val r = clRadius + 18f
                val eRadius = 10f
                val pairColor = Color(0xFF00E5FF)

                // Paar 1: Oben
                drawCircle(color = pairColor, radius = eRadius, center = Offset(clCenterX - 12f, centerY - r))
                drawCircle(color = pairColor, radius = eRadius, center = Offset(clCenterX + 12f, centerY - r))

                // Paar 2: Unten
                drawCircle(color = pairColor, radius = eRadius, center = Offset(clCenterX - 12f, centerY + r))
                drawCircle(color = pairColor, radius = eRadius, center = Offset(clCenterX + 12f, centerY + r))

                // Paar 3: Rechts
                drawCircle(color = pairColor, radius = eRadius, center = Offset(clCenterX + r, centerY - 12f))
                drawCircle(color = pairColor, radius = eRadius, center = Offset(clCenterX + r, centerY + 12f))

                // Einzelnes 7. Elektron bei Chlor (Links, wartet auf Partner)
                if (electronProgress < 0.9f) {
                    drawCircle(color = pairColor, radius = eRadius, center = Offset(clCenterX - r, centerY + 15f))
                } else {
                    // Sobald da, bilden sie ein volles Paar links!
                    drawCircle(color = pairColor, radius = eRadius, center = Offset(clCenterX - r, centerY - 10f))
                    drawCircle(color = Color(0xFFFFD700), radius = eRadius, center = Offset(clCenterX - r, centerY + 10f))
                }

                // --- Wandernes Außenelektron von Natrium ---
                val startX = naCenterX + naRadius + 18f
                val targetX = clCenterX - r
                val currentX = startX + (targetX - startX) * electronProgress
                val currentY = centerY - (kotlin.math.sin(electronProgress * 3.14159f) * 35f)

                if (electronProgress < 0.9f) {
                    drawCircle(color = Color(0xFFFFD700), radius = eRadius + 2f, center = Offset(currentX, currentY))
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = if (isDone) "Na⁺ (Glücklich 😄)" else "Na (Einsam 🥺 - 1 Elektron zu viel)",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isDone) ChemistryColors.NeutralGreen else MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = if (isDone) "Cl⁻ (Glücklich 😄)" else "Cl (Traurig 🥺 - sucht 1 Elektron)",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isDone) ChemistryColors.NeutralGreen else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (!isDone) {
                Button(
                    onClick = { animateElectron = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("⚡ Einzel-Elektron übertragen & Atome glücklich machen!", fontWeight = FontWeight.Bold)
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("💡 Chlor hat jetzt 4 volle Elektronenpaare (Edelgaszustand)! 🎉", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                        Text("Chlor besaß 3 Paare (6 Elektronen) + 1 einzelnes Elektron. Durch die Übertragung von Natrium hat Chlor nun 8 Außenelektronen (Edelgaskonfiguration)!", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

@Composable
private fun CovalentBondGameCanvas(
    isDone: Boolean,
    onComplete: () -> Unit
) {
    var animateCovalent by remember { mutableStateOf(false) }

    val covalentProgress by animateFloatAsState(
        targetValue = if (animateCovalent || isDone) 1.0f else 0.0f,
        animationSpec = tween(durationMillis = 1800, easing = FastOutSlowInEasing)
    )

    LaunchedEffect(covalentProgress) {
        if (covalentProgress == 1.0f && animateCovalent) {
            onComplete()
        }
    }

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
                text = if (isDone) "🎉 Wasser-Molekül (H₂O) ist glücklich!" else "2. Atombindung: H-Atome teilen Elektronen mit O",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF1B2631))
            ) {
                val width = size.width
                val height = size.height
                val oCenterX = width * 0.5f
                val oCenterY = height * 0.5f

                val h1TargetX = oCenterX - 65f
                val h2TargetX = oCenterX + 65f

                val h1StartX = width * 0.18f
                val h2StartX = width * 0.82f

                val h1CurrentX = h1StartX + (h1TargetX - h1StartX) * covalentProgress
                val h2CurrentX = h2StartX + (h2TargetX - h2StartX) * covalentProgress

                val eRadius = 10f

                // Sauerstoff Atom O
                drawCircle(color = Color(0xFFE57373), radius = 65f, center = Offset(oCenterX, oCenterY))
                drawCircle(color = Color(0xFFEF5350), radius = 83f, center = Offset(oCenterX, oCenterY), style = androidx.compose.ui.graphics.drawscope.Stroke(3f))

                // Sauerstoff 2 freie Elektronenpaare (oben und unten)
                drawCircle(color = Color(0xFF00E5FF), radius = eRadius, center = Offset(oCenterX - 12f, oCenterY - 83f))
                drawCircle(color = Color(0xFF00E5FF), radius = eRadius, center = Offset(oCenterX + 12f, oCenterY - 83f))

                drawCircle(color = Color(0xFF00E5FF), radius = eRadius, center = Offset(oCenterX - 12f, oCenterY + 83f))
                drawCircle(color = Color(0xFF00E5FF), radius = eRadius, center = Offset(oCenterX + 12f, oCenterY + 83f))

                // Wasserstoff Atome H1 und H2
                drawCircle(color = Color(0xFF64B5F6), radius = 45f, center = Offset(h1CurrentX, oCenterY))
                drawCircle(color = Color(0xFF64B5F6), radius = 45f, center = Offset(h2CurrentX, oCenterY))

                // Gemeinsame Elektronenpaare im Überlappungsbereich
                if (covalentProgress > 0.5f) {
                    drawCircle(color = Color(0xFFFFD700), radius = eRadius, center = Offset((h1CurrentX + oCenterX) / 2f, oCenterY - 12f))
                    drawCircle(color = Color(0xFFFFD700), radius = eRadius, center = Offset((h1CurrentX + oCenterX) / 2f, oCenterY + 12f))

                    drawCircle(color = Color(0xFFFFD700), radius = eRadius, center = Offset((h2CurrentX + oCenterX) / 2f, oCenterY - 12f))
                    drawCircle(color = Color(0xFFFFD700), radius = eRadius, center = Offset((h2CurrentX + oCenterX) / 2f, oCenterY + 12f))
                }
            }

            if (!isDone) {
                Button(
                    onClick = { animateCovalent = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("🤝 H-Atome teilen Elektronenpaare mit O!", fontWeight = FontWeight.Bold)
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("💡 Erklärung Atombindung (H₂O):", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                        Text("Sauerstoff hatte 2 freie Elektronenpaare und 2 einzelne Elektronen. Durch das Teilen mit den zwei H-Atomen entstehen 2 gemeinsame Elektronenpaare!", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
