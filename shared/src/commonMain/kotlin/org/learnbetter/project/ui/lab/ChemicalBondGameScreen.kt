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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
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
            text = "Schau, wie die traurigen Atom-Strichmännchen glücklich werden und jubeln!",
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
                text = if (isDone) "🎉 Beide Atom-Männchen jubeln!" else "1. Ionenbindung: Elektron von Na ➔ Cl",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Canvas mit doppelt so großen Atom-Strichmännchen & Gesichtern
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF1B2631))
            ) {
                val width = size.width
                val height = size.height
                val naCenterX = width * 0.28f
                val clCenterX = width * 0.72f
                val centerY = height * 0.45f

                val isHappy = electronProgress > 0.8f

                // Doppelt so große Radien
                val naRadius = 85f
                val clRadius = 100f

                // --- 1. NATRIUM (Na) STRICHMÄNNCHEN ---
                // Strichmännchen Beine Na
                drawLine(Color.White, start = Offset(naCenterX - 20f, centerY + naRadius), end = Offset(naCenterX - 45f, centerY + naRadius + 60f), strokeWidth = 5f)
                drawLine(Color.White, start = Offset(naCenterX + 20f, centerY + naRadius), end = Offset(naCenterX + 45f, centerY + naRadius + 60f), strokeWidth = 5f)

                // Strichmännchen Arme Na
                if (isHappy) {
                    // Arme jubelnd nach oben!
                    drawLine(Color.White, start = Offset(naCenterX - naRadius, centerY), end = Offset(naCenterX - naRadius - 35f, centerY - 45f), strokeWidth = 5f)
                    drawLine(Color.White, start = Offset(naCenterX + naRadius, centerY), end = Offset(naCenterX + naRadius + 35f, centerY - 45f), strokeWidth = 5f)
                } else {
                    // Arme traurig nach unten
                    drawLine(Color.White, start = Offset(naCenterX - naRadius, centerY), end = Offset(naCenterX - naRadius - 30f, centerY + 30f), strokeWidth = 5f)
                    drawLine(Color.White, start = Offset(naCenterX + naRadius, centerY), end = Offset(naCenterX + naRadius + 30f, centerY + 30f), strokeWidth = 5f)
                }

                // Natrium Körper/Kopf
                drawCircle(color = Color(0xFFFFB74D), radius = naRadius, center = Offset(naCenterX, centerY))
                drawCircle(color = Color(0xFFFF9800), radius = naRadius + 22f, center = Offset(naCenterX, centerY), style = Stroke(4f))

                // Natrium Gesicht (Augen & Mund)
                drawCircle(Color.Black, radius = 7f, center = Offset(naCenterX - 25f, centerY - 20f))
                drawCircle(Color.Black, radius = 7f, center = Offset(naCenterX + 25f, centerY - 20f))

                val naMouthPath = Path()
                if (isHappy) {
                    // Lachelnder Mund 😄
                    naMouthPath.moveTo(naCenterX - 25f, centerY + 15f)
                    naMouthPath.quadraticTo(naCenterX, centerY + 45f, naCenterX + 25f, centerY + 15f)
                } else {
                    // Trauriger Mund 🥺
                    naMouthPath.moveTo(naCenterX - 25f, centerY + 30f)
                    naMouthPath.quadraticTo(naCenterX, centerY + 10f, naCenterX + 25f, centerY + 30f)
                }
                drawPath(naMouthPath, Color.Black, style = Stroke(5f))


                // --- 2. CHLOR (Cl) STRICHMÄNNCHEN ---
                // Strichmännchen Beine Cl
                drawLine(Color.White, start = Offset(clCenterX - 25f, centerY + clRadius), end = Offset(clCenterX - 50f, centerY + clRadius + 60f), strokeWidth = 5f)
                drawLine(Color.White, start = Offset(clCenterX + 25f, centerY + clRadius), end = Offset(clCenterX + 50f, centerY + clRadius + 60f), strokeWidth = 5f)

                // Strichmännchen Arme Cl
                if (isHappy) {
                    // Arme jubelnd nach oben!
                    drawLine(Color.White, start = Offset(clCenterX - clRadius, centerY), end = Offset(clCenterX - clRadius - 35f, centerY - 45f), strokeWidth = 5f)
                    drawLine(Color.White, start = Offset(clCenterX + clRadius, centerY), end = Offset(clCenterX + clRadius + 35f, centerY - 45f), strokeWidth = 5f)
                } else {
                    // Arme traurig nach unten
                    drawLine(Color.White, start = Offset(clCenterX - clRadius, centerY), end = Offset(clCenterX - clRadius - 30f, centerY + 30f), strokeWidth = 5f)
                    drawLine(Color.White, start = Offset(clCenterX + clRadius, centerY), end = Offset(clCenterX + clRadius + 30f, centerY + 30f), strokeWidth = 5f)
                }

                // Chlor Körper/Kopf
                drawCircle(color = Color(0xFF81C784), radius = clRadius, center = Offset(clCenterX, centerY))
                drawCircle(color = Color(0xFF4CAF50), radius = clRadius + 22f, center = Offset(clCenterX, centerY), style = Stroke(4f))

                // Chlor Gesicht (Augen & Mund)
                drawCircle(Color.Black, radius = 8f, center = Offset(clCenterX - 30f, centerY - 25f))
                drawCircle(Color.Black, radius = 8f, center = Offset(clCenterX + 30f, centerY - 25f))

                val clMouthPath = Path()
                if (isHappy) {
                    // Lachender Mund 😄
                    clMouthPath.moveTo(clCenterX - 30f, centerY + 20f)
                    clMouthPath.quadraticTo(clCenterX, centerY + 55f, clCenterX + 30f, centerY + 20f)
                } else {
                    // Trauriger Mund 🥺
                    clMouthPath.moveTo(clCenterX - 30f, centerY + 35f)
                    clMouthPath.quadraticTo(clCenterX, centerY + 12f, clCenterX + 30f, centerY + 35f)
                }
                drawPath(clMouthPath, Color.Black, style = Stroke(5f))


                // --- ELEKTRONENPAARE BEI CHLOR ---
                val r = clRadius + 22f
                val eRadius = 12f
                val pairColor = Color(0xFF00E5FF)

                // Paar 1: Oben
                drawCircle(color = pairColor, radius = eRadius, center = Offset(clCenterX - 18f, centerY - r))
                drawCircle(color = pairColor, radius = eRadius, center = Offset(clCenterX + 18f, centerY - r))

                // Paar 2: Unten
                drawCircle(color = pairColor, radius = eRadius, center = Offset(clCenterX - 18f, centerY + r))
                drawCircle(color = pairColor, radius = eRadius, center = Offset(clCenterX + 18f, centerY + r))

                // Paar 3: Rechts
                drawCircle(color = pairColor, radius = eRadius, center = Offset(clCenterX + r, centerY - 18f))
                drawCircle(color = pairColor, radius = eRadius, center = Offset(clCenterX + r, centerY + 18f))

                // Einzelnes 7. Elektron bei Chlor (Links, wartet auf Partner)
                if (electronProgress < 0.9f) {
                    drawCircle(color = pairColor, radius = eRadius, center = Offset(clCenterX - r, centerY + 18f))
                } else {
                    // Sobald da, bilden sie ein volles Paar links!
                    drawCircle(color = pairColor, radius = eRadius, center = Offset(clCenterX - r, centerY - 15f))
                    drawCircle(color = Color(0xFFFFD700), radius = eRadius + 2f, center = Offset(clCenterX - r, centerY + 15f))
                }

                // --- WANDERNDES AUSSENELEKTRON VON NATRIUM ---
                val startX = naCenterX + naRadius + 22f
                val targetX = clCenterX - r
                val currentX = startX + (targetX - startX) * electronProgress
                val currentY = centerY - (kotlin.math.sin(electronProgress * 3.14159f) * 45f)

                if (electronProgress < 0.9f) {
                    drawCircle(color = Color(0xFFFFD700), radius = eRadius + 3f, center = Offset(currentX, currentY))
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = if (isDone) "Na⁺ (Überglücklich! 😄)" else "Na (Traurig 🥺 - 1 Elektron zu viel)",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isDone) ChemistryColors.NeutralGreen else MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = if (isDone) "Cl⁻ (Überglücklich! 😄)" else "Cl (Traurig 🥺 - sucht 1 Elektron)",
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
                    Text("⚡ Elektron übertragen & Strichmännchen glücklich machen!", fontWeight = FontWeight.Bold)
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("💡 Chlor hat jetzt 4 volle Elektronenpaare (Edelgaszustand)! 🎉", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                        Text("Sowohl Na⁺ als auch Cl⁻ sind jetzt voll besetzt und strahlend glücklich!", style = MaterialTheme.typography.bodySmall)
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
                text = if (isDone) "🎉 Wasser-Molekül (H₂O) jubelt!" else "2. Atombindung: Gemeinsame Elektronenpaare",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF1B2631))
            ) {
                val width = size.width
                val height = size.height
                val oCenterX = width * 0.5f
                val oCenterY = height * 0.45f

                val isHappy = covalentProgress > 0.8f

                val h1TargetX = oCenterX - 80f
                val h2TargetX = oCenterX + 80f

                val h1StartX = width * 0.16f
                val h2StartX = width * 0.84f

                val h1CurrentX = h1StartX + (h1TargetX - h1StartX) * covalentProgress
                val h2CurrentX = h2StartX + (h2TargetX - h2StartX) * covalentProgress

                val eRadius = 12f
                val oRadius = 85f
                val hRadius = 55f

                // --- SAUERSTOFF (O) STRICHMÄNNCHEN ---
                drawLine(Color.White, start = Offset(oCenterX - 25f, oCenterY + oRadius), end = Offset(oCenterX - 50f, oCenterY + oRadius + 50f), strokeWidth = 5f)
                drawLine(Color.White, start = Offset(oCenterX + 25f, oCenterY + oRadius), end = Offset(oCenterX + 50f, oCenterY + oRadius + 50f), strokeWidth = 5f)

                drawCircle(color = Color(0xFFE57373), radius = oRadius, center = Offset(oCenterX, oCenterY))
                drawCircle(color = Color(0xFFEF5350), radius = oRadius + 22f, center = Offset(oCenterX, oCenterY), style = Stroke(4f))

                // O Augen & Mund
                drawCircle(Color.Black, radius = 8f, center = Offset(oCenterX - 30f, oCenterY - 25f))
                drawCircle(Color.Black, radius = 8f, center = Offset(oCenterX + 30f, oCenterY - 25f))

                val oMouthPath = Path()
                if (isHappy) {
                    oMouthPath.moveTo(oCenterX - 30f, oCenterY + 20f)
                    oMouthPath.quadraticTo(oCenterX, oCenterY + 55f, oCenterX + 30f, oCenterY + 20f)
                } else {
                    oMouthPath.moveTo(oCenterX - 30f, oCenterY + 35f)
                    oMouthPath.quadraticTo(oCenterX, oCenterY + 12f, oCenterX + 30f, oCenterY + 35f)
                }
                drawPath(oMouthPath, Color.Black, style = Stroke(5f))


                // Sauerstoff 2 freie Elektronenpaare (oben und unten)
                drawCircle(color = Color(0xFF00E5FF), radius = eRadius, center = Offset(oCenterX - 18f, oCenterY - (oRadius + 22f)))
                drawCircle(color = Color(0xFF00E5FF), radius = eRadius, center = Offset(oCenterX + 18f, oCenterY - (oRadius + 22f)))

                drawCircle(color = Color(0xFF00E5FF), radius = eRadius, center = Offset(oCenterX - 18f, oCenterY + (oRadius + 22f)))
                drawCircle(color = Color(0xFF00E5FF), radius = eRadius, center = Offset(oCenterX + 18f, oCenterY + (oRadius + 22f)))


                // --- WASSERSTOFF (H1) STRICHMÄNNCHEN ---
                drawLine(Color.White, start = Offset(h1CurrentX - 15f, oCenterY + hRadius), end = Offset(h1CurrentX - 30f, oCenterY + hRadius + 45f), strokeWidth = 4f)
                drawLine(Color.White, start = Offset(h1CurrentX + 15f, oCenterY + hRadius), end = Offset(h1CurrentX + 30f, oCenterY + hRadius + 45f), strokeWidth = 4f)

                drawCircle(color = Color(0xFF64B5F6), radius = hRadius, center = Offset(h1CurrentX, oCenterY))

                // H1 Gesicht
                drawCircle(Color.Black, radius = 6f, center = Offset(h1CurrentX - 18f, oCenterY - 15f))
                drawCircle(Color.Black, radius = 6f, center = Offset(h1CurrentX + 18f, oCenterY - 15f))

                val h1MouthPath = Path()
                if (isHappy) {
                    h1MouthPath.moveTo(h1CurrentX - 18f, oCenterY + 12f)
                    h1MouthPath.quadraticTo(h1CurrentX, oCenterY + 32f, h1CurrentX + 18f, oCenterY + 12f)
                } else {
                    h1MouthPath.moveTo(h1CurrentX - 18f, oCenterY + 22f)
                    h1MouthPath.quadraticTo(h1CurrentX, oCenterY + 8f, h1CurrentX + 18f, oCenterY + 22f)
                }
                drawPath(h1MouthPath, Color.Black, style = Stroke(4f))


                // --- WASSERSTOFF (H2) STRICHMÄNNCHEN ---
                drawLine(Color.White, start = Offset(h2CurrentX - 15f, oCenterY + hRadius), end = Offset(h2CurrentX - 30f, oCenterY + hRadius + 45f), strokeWidth = 4f)
                drawLine(Color.White, start = Offset(h2CurrentX + 15f, oCenterY + hRadius), end = Offset(h2CurrentX + 30f, oCenterY + hRadius + 45f), strokeWidth = 4f)

                drawCircle(color = Color(0xFF64B5F6), radius = hRadius, center = Offset(h2CurrentX, oCenterY))

                // H2 Gesicht
                drawCircle(Color.Black, radius = 6f, center = Offset(h2CurrentX - 18f, oCenterY - 15f))
                drawCircle(Color.Black, radius = 6f, center = Offset(h2CurrentX + 18f, oCenterY - 15f))

                val h2MouthPath = Path()
                if (isHappy) {
                    h2MouthPath.moveTo(h2CurrentX - 18f, oCenterY + 12f)
                    h2MouthPath.quadraticTo(h2CurrentX, oCenterY + 32f, h2CurrentX + 18f, oCenterY + 12f)
                } else {
                    h2MouthPath.moveTo(h2CurrentX - 18f, oCenterY + 22f)
                    h2MouthPath.quadraticTo(h2CurrentX, oCenterY + 8f, h2CurrentX + 18f, oCenterY + 22f)
                }
                drawPath(h2MouthPath, Color.Black, style = Stroke(4f))


                // Gemeinsame Elektronenpaare im Überlappungsbereich
                if (covalentProgress > 0.5f) {
                    drawCircle(color = Color(0xFFFFD700), radius = eRadius, center = Offset((h1CurrentX + oCenterX) / 2f, oCenterY - 15f))
                    drawCircle(color = Color(0xFFFFD700), radius = eRadius, center = Offset((h1CurrentX + oCenterX) / 2f, oCenterY + 15f))

                    drawCircle(color = Color(0xFFFFD700), radius = eRadius, center = Offset((h2CurrentX + oCenterX) / 2f, oCenterY - 15f))
                    drawCircle(color = Color(0xFFFFD700), radius = eRadius, center = Offset((h2CurrentX + oCenterX) / 2f, oCenterY + 15f))
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
                        Text("Alle 3 Atom-Strichmännchen sind nun glücklich und haben ihre volle Elektronen-Aussenschale erreicht!", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
