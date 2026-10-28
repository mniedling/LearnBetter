package org.learnbetter.project.ui.lab

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.learnbetter.project.model.Subject

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
            text = "Beobachte, wie Außenelektronen wandern, um Ionenbindungen oder gemeinsame Elektronenpaare zu bilden!",
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
        animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing)
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
                text = if (isDone) "🎉 Kochsalz (NaCl) entstanden!" else "1. Ionenbindung: Elektron von Na ➔ Cl",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Canvas for Animated Atoms & Electrons
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF1B2631))
            ) {
                val width = size.width
                val height = size.height
                val naCenterX = width * 0.28f
                val clCenterX = width * 0.72f
                val centerY = height * 0.5f

                // Natrium Atom
                drawCircle(color = Color(0xFFFFB74D), radius = 45f, center = Offset(naCenterX, centerY))
                drawCircle(color = Color(0xFFFF9800), radius = 60f, center = Offset(naCenterX, centerY), style = androidx.compose.ui.graphics.drawscope.Stroke(2f))

                // Chlor Atom
                drawCircle(color = Color(0xFF81C784), radius = 55f, center = Offset(clCenterX, centerY))
                drawCircle(color = Color(0xFF4CAF50), radius = 70f, center = Offset(clCenterX, centerY), style = androidx.compose.ui.graphics.drawscope.Stroke(2f))

                // Chlor 7 Außenelektronen
                val clRadius = 70f
                for (i in 0 until 7) {
                    val angle = (i * 51.4f) * (3.14159f / 180f)
                    val x = clCenterX + clRadius * kotlin.math.cos(angle)
                    val y = centerY + clRadius * kotlin.math.sin(angle)
                    drawCircle(color = Color(0xFF00E5FF), radius = 7f, center = Offset(x, y))
                }

                // Wandernes Außenelektron von Na -> Cl
                val currentElectronX = naCenterX + (clCenterX - clRadius - naCenterX) * electronProgress
                val currentElectronY = centerY - (kotlin.math.sin(electronProgress * 3.14159f) * 30f)
                drawCircle(color = Color(0xFFFFD700), radius = 10f, center = Offset(currentElectronX, currentElectronY))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text("Na (Natrium - 1 Außenelektron)", style = MaterialTheme.typography.labelSmall)
                Text("Cl (Chlor - 7 Außenelektronen)", style = MaterialTheme.typography.labelSmall)
            }

            if (!isDone) {
                Button(
                    onClick = { animateElectron = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("⚡ Elektron von Na an Cl abgeben!", fontWeight = FontWeight.Bold)
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("💡 Erklärung Ionenbindung:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                        Text("Natrium (Na) gibt sein 1 Außenelektron vollständig an Chlor (Cl) ab. Es entstehen Na⁺ (Kation) und Cl⁻ (Anion). Die gegenseitige Anziehung bildet das Kochsalz-Ionengitter!", style = MaterialTheme.typography.bodySmall)
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
        animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing)
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
                text = if (isDone) "🎉 Wasser-Molekül (H₂O) entstanden!" else "2. Atombindung: Gemeinsame Elektronenpaare",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF1B2631))
            ) {
                val width = size.width
                val height = size.height
                val oCenterX = width * 0.5f
                val oCenterY = height * 0.5f

                val h1TargetX = oCenterX - 60f
                val h2TargetX = oCenterX + 60f

                val h1StartX = width * 0.18f
                val h2StartX = width * 0.82f

                val h1CurrentX = h1StartX + (h1TargetX - h1StartX) * covalentProgress
                val h2CurrentX = h2StartX + (h2TargetX - h2StartX) * covalentProgress

                // Sauerstoff Atom O
                drawCircle(color = Color(0xFFE57373), radius = 55f, center = Offset(oCenterX, oCenterY))

                // Wasserstoff Atome H1 und H2
                drawCircle(color = Color(0xFF64B5F6), radius = 35f, center = Offset(h1CurrentX, oCenterY))
                drawCircle(color = Color(0xFF64B5F6), radius = 35f, center = Offset(h2CurrentX, oCenterY))

                // Gemeinsame Elektronenpaare im Überlappungsbereich
                if (covalentProgress > 0.5f) {
                    drawCircle(color = Color(0xFFFFD700), radius = 6f, center = Offset((h1CurrentX + oCenterX) / 2f, oCenterY - 10f))
                    drawCircle(color = Color(0xFFFFD700), radius = 6f, center = Offset((h1CurrentX + oCenterX) / 2f, oCenterY + 10f))

                    drawCircle(color = Color(0xFFFFD700), radius = 6f, center = Offset((h2CurrentX + oCenterX) / 2f, oCenterY - 10f))
                    drawCircle(color = Color(0xFFFFD700), radius = 6f, center = Offset((h2CurrentX + oCenterX) / 2f, oCenterY + 10f))
                }
            }

            if (!isDone) {
                Button(
                    onClick = { animateCovalent = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("🤝 H-Atome annähern & Elektronenpaare teilen!", fontWeight = FontWeight.Bold)
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("💡 Erklärung Elektronenpaarbindung:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                        Text("Bei Nichtmetallen teilen sich die Atome Außenelektronen. Es entstehen gemeinsame Elektronenpaare, sodass jedes Atom die vollbesetzte Edelgasschale erreicht!", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
