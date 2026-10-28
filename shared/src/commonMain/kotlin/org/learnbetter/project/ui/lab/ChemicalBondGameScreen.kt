package org.learnbetter.project.ui.lab

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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

data class BondExample(
    val id: String,
    val title: String,
    val formula: String,
    val type: String, // "ion" or "covalent"
    val description: String
)

@Composable
fun ChemicalBondGameScreen(
    onXpEarned: (Int) -> Unit = {},
    onBackToHub: (() -> Unit)? = null
) {
    var bondTypeMode by remember { mutableIntStateOf(1) } // 1 = Ionenbindung, 2 = Atombindung
    var selectedExampleId by remember(bondTypeMode) {
        mutableStateOf(if (bondTypeMode == 1) "nacl" else "h2o")
    }

    val ionicExamples = listOf(
        BondExample("nacl", "Kochsalz", "NaCl", "ion", "Natrium gibt 1 Elektron an Chlor ab."),
        BondExample("mgo", "Magnesiumoxid", "MgO", "ion", "Magnesium gibt 2 Elektronen an Sauerstoff ab!"),
        BondExample("lif", "Lithiumfluorid", "LiF", "ion", "Lithium gibt 1 Elektron an Fluor ab.")
    )

    val covalentExamples = listOf(
        BondExample("h2o", "Wasser", "H₂O", "covalent", "Sauerstoff teilt Elektronenpaare mit 2x H."),
        BondExample("ch4", "Methan", "CH₄", "covalent", "Kohlenstoff teilt Elektronenpaare mit 4x H!"),
        BondExample("o2", "Sauerstoff", "O₂", "covalent", "Zwei O-Atome bilden eine Doppelbindung!")
    )

    val currentExamples = if (bondTypeMode == 1) ionicExamples else covalentExamples
    val activeExample = currentExamples.firstOrNull { it.id == selectedExampleId } ?: currentExamples.first()

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
            text = "Wähle Bindungsart und Molekül aus, um die Atom-Strichmännchen jubeln zu lassen!",
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
                onClick = { bondTypeMode = 1; selectedExampleId = "nacl" },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (bondTypeMode == 1) Subject.CHEMISTRY.primaryColor else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text("1. Ionenbindungen (Salze)", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = if (bondTypeMode == 1) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Button(
                onClick = { bondTypeMode = 2; selectedExampleId = "h2o" },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (bondTypeMode == 2) Subject.CHEMISTRY.primaryColor else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text("2. Atombindungen (Moleküle)", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = if (bondTypeMode == 2) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        // Beispiel Auswahl Carousel
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            currentExamples.forEach { ex ->
                val isSelected = ex.id == activeExample.id
                Surface(
                    modifier = Modifier.clickable { selectedExampleId = ex.id },
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) Subject.CHEMISTRY.primaryColor else MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = "${ex.title} (${ex.formula})",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
            }
        }

        // Active Interactive Canvas
        if (bondTypeMode == 1) {
            IonicBondGameCanvas(
                example = activeExample,
                onXpEarned = onXpEarned
            )
        } else {
            CovalentBondGameCanvas(
                example = activeExample,
                onXpEarned = onXpEarned
            )
        }
    }
}

@Composable
private fun IonicBondGameCanvas(
    example: BondExample,
    onXpEarned: (Int) -> Unit
) {
    var isDone by remember(example.id) { mutableStateOf(false) }
    var animateElectron by remember(example.id) { mutableStateOf(false) }

    val electronProgress by animateFloatAsState(
        targetValue = if (animateElectron || isDone) 1.0f else 0.0f,
        animationSpec = tween(durationMillis = 1800, easing = FastOutSlowInEasing)
    )

    LaunchedEffect(electronProgress) {
        if (electronProgress == 1.0f && animateElectron && !isDone) {
            isDone = true
            onXpEarned(15)
        }
    }

    val atom1Name = when (example.id) {
        "mgo" -> "Mg (Magnesium)"
        "lif" -> "Li (Lithium)"
        else -> "Na (Natrium)"
    }

    val atom2Name = when (example.id) {
        "mgo" -> "O (Sauerstoff)"
        "lif" -> "F (Fluor)"
        else -> "Cl (Chlor)"
    }

    val atom1Symbol = when (example.id) {
        "mgo" -> "Mg"
        "lif" -> "Li"
        else -> "Na"
    }

    val atom2Symbol = when (example.id) {
        "mgo" -> "O"
        "lif" -> "F"
        else -> "Cl"
    }

    val atom1Color = when (example.id) {
        "mgo" -> Color(0xFFAB47BC)
        "lif" -> Color(0xFFFF7043)
        else -> Color(0xFFFFB74D)
    }

    val atom2Color = when (example.id) {
        "mgo" -> Color(0xFFEF5350)
        "lif" -> Color(0xFF26A69A)
        else -> Color(0xFF81C784)
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
                text = if (isDone) "🎉 ${example.title} (${example.formula}) ist entstanden!" else "Ionenbindung: $atom1Symbol ➔ $atom2Symbol",
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
                val naCenterX = width * 0.28f
                val clCenterX = width * 0.72f
                val centerY = height * 0.45f

                val isHappy = electronProgress > 0.8f

                val naRadius = 85f
                val clRadius = 100f

                // Strichmännchen Beine Atom 1
                drawLine(Color.White, start = Offset(naCenterX - 20f, centerY + naRadius), end = Offset(naCenterX - 45f, centerY + naRadius + 60f), strokeWidth = 5f)
                drawLine(Color.White, start = Offset(naCenterX + 20f, centerY + naRadius), end = Offset(naCenterX + 45f, centerY + naRadius + 60f), strokeWidth = 5f)

                // Strichmännchen Arme Atom 1
                if (isHappy) {
                    drawLine(Color.White, start = Offset(naCenterX - naRadius, centerY), end = Offset(naCenterX - naRadius - 35f, centerY - 45f), strokeWidth = 5f)
                    drawLine(Color.White, start = Offset(naCenterX + naRadius, centerY), end = Offset(naCenterX + naRadius + 35f, centerY - 45f), strokeWidth = 5f)
                } else {
                    drawLine(Color.White, start = Offset(naCenterX - naRadius, centerY), end = Offset(naCenterX - naRadius - 30f, centerY + 30f), strokeWidth = 5f)
                    drawLine(Color.White, start = Offset(naCenterX + naRadius, centerY), end = Offset(naCenterX + naRadius + 30f, centerY + 30f), strokeWidth = 5f)
                }

                // Atom 1 Körper
                drawCircle(color = atom1Color, radius = naRadius, center = Offset(naCenterX, centerY))
                drawCircle(color = Color.White.copy(alpha = 0.5f), radius = naRadius + 22f, center = Offset(naCenterX, centerY), style = Stroke(3f))

                // Atom 1 Gesicht
                drawCircle(Color.Black, radius = 7f, center = Offset(naCenterX - 25f, centerY - 20f))
                drawCircle(Color.Black, radius = 7f, center = Offset(naCenterX + 25f, centerY - 20f))

                val naMouthPath = Path()
                if (isHappy) {
                    naMouthPath.moveTo(naCenterX - 25f, centerY + 15f)
                    naMouthPath.quadraticTo(naCenterX, centerY + 45f, naCenterX + 25f, centerY + 15f)
                } else {
                    naMouthPath.moveTo(naCenterX - 25f, centerY + 30f)
                    naMouthPath.quadraticTo(naCenterX, centerY + 10f, naCenterX + 25f, centerY + 30f)
                }
                drawPath(naMouthPath, Color.Black, style = Stroke(5f))


                // Strichmännchen Beine Atom 2
                drawLine(Color.White, start = Offset(clCenterX - 25f, centerY + clRadius), end = Offset(clCenterX - 50f, centerY + clRadius + 60f), strokeWidth = 5f)
                drawLine(Color.White, start = Offset(clCenterX + 25f, centerY + clRadius), end = Offset(clCenterX + 50f, centerY + clRadius + 60f), strokeWidth = 5f)

                // Strichmännchen Arme Atom 2
                if (isHappy) {
                    drawLine(Color.White, start = Offset(clCenterX - clRadius, centerY), end = Offset(clCenterX - clRadius - 35f, centerY - 45f), strokeWidth = 5f)
                    drawLine(Color.White, start = Offset(clCenterX + clRadius, centerY), end = Offset(clCenterX + clRadius + 35f, centerY - 45f), strokeWidth = 5f)
                } else {
                    drawLine(Color.White, start = Offset(clCenterX - clRadius, centerY), end = Offset(clCenterX - clRadius - 30f, centerY + 30f), strokeWidth = 5f)
                    drawLine(Color.White, start = Offset(clCenterX + clRadius, centerY), end = Offset(clCenterX + clRadius + 30f, centerY + 30f), strokeWidth = 5f)
                }

                // Atom 2 Körper
                drawCircle(color = atom2Color, radius = clRadius, center = Offset(clCenterX, centerY))
                drawCircle(color = Color.White.copy(alpha = 0.5f), radius = clRadius + 22f, center = Offset(clCenterX, centerY), style = Stroke(3f))

                // Atom 2 Gesicht
                drawCircle(Color.Black, radius = 8f, center = Offset(clCenterX - 30f, centerY - 25f))
                drawCircle(Color.Black, radius = 8f, center = Offset(clCenterX + 30f, centerY - 25f))

                val clMouthPath = Path()
                if (isHappy) {
                    clMouthPath.moveTo(clCenterX - 30f, centerY + 20f)
                    clMouthPath.quadraticTo(clCenterX, centerY + 55f, clCenterX + 30f, centerY + 20f)
                } else {
                    clMouthPath.moveTo(clCenterX - 30f, centerY + 35f)
                    clMouthPath.quadraticTo(clCenterX, centerY + 12f, clCenterX + 30f, centerY + 35f)
                }
                drawPath(clMouthPath, Color.Black, style = Stroke(5f))


                // Elektronenpaare bei Atom 2
                val r = clRadius + 22f
                val eRadius = 12f
                val pairColor = Color(0xFF00E5FF)

                drawCircle(color = pairColor, radius = eRadius, center = Offset(clCenterX - 18f, centerY - r))
                drawCircle(color = pairColor, radius = eRadius, center = Offset(clCenterX + 18f, centerY - r))

                drawCircle(color = pairColor, radius = eRadius, center = Offset(clCenterX - 18f, centerY + r))
                drawCircle(color = pairColor, radius = eRadius, center = Offset(clCenterX + 18f, centerY + r))

                drawCircle(color = pairColor, radius = eRadius, center = Offset(clCenterX + r, centerY - 18f))
                drawCircle(color = pairColor, radius = eRadius, center = Offset(clCenterX + r, centerY + 18f))

                if (electronProgress < 0.9f) {
                    drawCircle(color = pairColor, radius = eRadius, center = Offset(clCenterX - r, centerY + 18f))
                } else {
                    drawCircle(color = pairColor, radius = eRadius, center = Offset(clCenterX - r, centerY - 15f))
                    drawCircle(color = Color(0xFFFFD700), radius = eRadius + 2f, center = Offset(clCenterX - r, centerY + 15f))
                }

                // Wanderndes Außenelektron
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
                    text = if (isDone) "$atom1Symbol (Glücklich 😄)" else "$atom1Name (Traurig 🥺)",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isDone) ChemistryColors.NeutralGreen else MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = if (isDone) "$atom2Symbol (Glücklich 😄)" else "$atom2Name (Traurig 🥺)",
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
                    Text("⚡ Elektron übertragen & Atome glücklich machen!", fontWeight = FontWeight.Bold)
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("💡 ${example.title} (${example.formula}):", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                        Text(example.description, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

@Composable
private fun CovalentBondGameCanvas(
    example: BondExample,
    onXpEarned: (Int) -> Unit
) {
    var isDone by remember(example.id) { mutableStateOf(false) }
    var animateCovalent by remember(example.id) { mutableStateOf(false) }

    val covalentProgress by animateFloatAsState(
        targetValue = if (animateCovalent || isDone) 1.0f else 0.0f,
        animationSpec = tween(durationMillis = 1800, easing = FastOutSlowInEasing)
    )

    LaunchedEffect(covalentProgress) {
        if (covalentProgress == 1.0f && animateCovalent && !isDone) {
            isDone = true
            onXpEarned(15)
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
                text = if (isDone) "🎉 ${example.title} (${example.formula}) ist entstanden!" else "Atombindung: ${example.title} (${example.formula})",
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

                // --- HAUPT ATOM STRICHMÄNNCHEN ---
                drawLine(Color.White, start = Offset(oCenterX - 25f, oCenterY + oRadius), end = Offset(oCenterX - 50f, oCenterY + oRadius + 50f), strokeWidth = 5f)
                drawLine(Color.White, start = Offset(oCenterX + 25f, oCenterY + oRadius), end = Offset(oCenterX + 50f, oCenterY + oRadius + 50f), strokeWidth = 5f)

                val mainColor = if (example.id == "ch4") Color(0xFF8D6E63) else Color(0xFFE57373)
                drawCircle(color = mainColor, radius = oRadius, center = Offset(oCenterX, oCenterY))
                drawCircle(color = Color.White.copy(alpha = 0.5f), radius = oRadius + 22f, center = Offset(oCenterX, oCenterY), style = Stroke(4f))

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


                // --- BINDUNGS PARTNER H1 und H2 ---
                drawLine(Color.White, start = Offset(h1CurrentX - 15f, oCenterY + hRadius), end = Offset(h1CurrentX - 30f, oCenterY + hRadius + 45f), strokeWidth = 4f)
                drawLine(Color.White, start = Offset(h1CurrentX + 15f, oCenterY + hRadius), end = Offset(h1CurrentX + 30f, oCenterY + hRadius + 45f), strokeWidth = 4f)

                val partnerColor = if (example.id == "o2") Color(0xFFEF5350) else Color(0xFF64B5F6)
                drawCircle(color = partnerColor, radius = hRadius, center = Offset(h1CurrentX, oCenterY))

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


                drawLine(Color.White, start = Offset(h2CurrentX - 15f, oCenterY + hRadius), end = Offset(h2CurrentX - 30f, oCenterY + hRadius + 45f), strokeWidth = 4f)
                drawLine(Color.White, start = Offset(h2CurrentX + 15f, oCenterY + hRadius), end = Offset(h2CurrentX + 30f, oCenterY + hRadius + 45f), strokeWidth = 4f)

                drawCircle(color = partnerColor, radius = hRadius, center = Offset(h2CurrentX, oCenterY))

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
                    Text("🤝 Atome zusammenführen & Elektronenpaare teilen!", fontWeight = FontWeight.Bold)
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("💡 ${example.title} (${example.formula}):", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                        Text(example.description, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
