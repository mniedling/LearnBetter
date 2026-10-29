package org.learnbetter.project.ui.biology

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.learnbetter.project.model.Subject

enum class LabTool {
    NONE, SCALPEL, TWEEZER
}

@Composable
fun BiologyNucleusExperimentScreen(
    showExplorerOnly: Boolean = false,
    onXpEarned: (Int) -> Unit = {},
    onBackToHub: (() -> Unit)? = null
) {
    var selectedTool by remember { mutableStateOf(LabTool.NONE) }

    // Interaktions-Zustände Hutalge
    var kernelsExtracted by remember { mutableStateOf(false) }
    var capCutOff by remember { mutableStateOf(false) }
    var heldKernel by remember { mutableStateOf<String?>(null) }
    var insertedKernel by remember { mutableStateOf<String?>(null) }
    var capGrownType by remember { mutableStateOf<String?>(null) }

    var selectedNucleusPart by remember { mutableStateOf("poren") }

    LaunchedEffect(insertedKernel) {
        if (insertedKernel != null && capGrownType == null) {
            kotlinx.coroutines.delay(1500)
            capGrownType = insertedKernel
            onXpEarned(25)
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
                    Text("◀️ Zurück zur Übersicht")
                }
            }
        }

        Text(
            text = if (showExplorerOnly) "🟣 3D-Zellkern Explorer" else "✂️ Hutalgen-Transplantation",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Subject.BIOLOGY.primaryColor,
            textAlign = TextAlign.Center
        )

        Text(
            text = if (showExplorerOnly) "Beobachte den animierten Stoffaustausch durch die 3D-Kernporen und schwebenden DNA-Fäden!" else "Tippe auf das Skalpell ✂️ oder die Pinzette 🤏 oder nutze die Aktionsbuttons unten!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        if (!showExplorerOnly) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(260.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        InteractiveHutalgenCanvas(
                            selectedTool = selectedTool,
                            kernelsExtracted = kernelsExtracted,
                            capCutOff = capCutOff,
                            heldKernel = heldKernel,
                            insertedKernel = insertedKernel,
                            capGrownType = capGrownType,
                            onTargetTapped = { target ->
                                when (target) {
                                    "foot" -> if (selectedTool == LabTool.SCALPEL && !kernelsExtracted) kernelsExtracted = true
                                    "capA" -> if (selectedTool == LabTool.SCALPEL && kernelsExtracted && !capCutOff) capCutOff = true
                                    "kernelA" -> if (selectedTool == LabTool.TWEEZER && capCutOff && insertedKernel == null) heldKernel = "A"
                                    "kernelB" -> if (selectedTool == LabTool.TWEEZER && capCutOff && insertedKernel == null) heldKernel = "B"
                                    "footA" -> if (selectedTool == LabTool.TWEEZER && heldKernel != null) {
                                        insertedKernel = heldKernel
                                        heldKernel = null
                                    }
                                }
                            }
                        )
                    }
                }

                // Rechte Werkzeugleiste
                Card(
                    modifier = Modifier.width(60.dp).height(260.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(vertical = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Surface(
                            modifier = Modifier
                                .size(48.dp)
                                .clickable { selectedTool = LabTool.SCALPEL },
                            shape = CircleShape,
                            color = if (selectedTool == LabTool.SCALPEL) Subject.BIOLOGY.primaryColor else MaterialTheme.colorScheme.surface
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("✂️", fontSize = 22.sp)
                            }
                        }

                        Surface(
                            modifier = Modifier
                                .size(48.dp)
                                .clickable { selectedTool = LabTool.TWEEZER },
                            shape = CircleShape,
                            color = if (selectedTool == LabTool.TWEEZER) Subject.BIOLOGY.primaryColor else MaterialTheme.colorScheme.surface
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("🤏", fontSize = 22.sp)
                            }
                        }
                    }
                }
            }

            // Komfort-Aktionsbuttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (!kernelsExtracted) {
                    Button(
                        onClick = {
                            selectedTool = LabTool.SCALPEL
                            kernelsExtracted = true
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("✂️ 1. Skalpell: Beide Zellkerne entnehmen", fontWeight = FontWeight.Bold)
                    }
                } else if (!capCutOff) {
                    Button(
                        onClick = {
                            selectedTool = LabTool.SCALPEL
                            capCutOff = true
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("✂️ 2. Skalpell: Schirmhut A abtrennen", fontWeight = FontWeight.Bold)
                    }
                } else if (insertedKernel == null) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                selectedTool = LabTool.TWEEZER
                                heldKernel = "A"
                                insertedKernel = "A"
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5CF6))
                        ) {
                            Text("🤏 Kern A einsetzen", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        }

                        Button(
                            onClick = {
                                selectedTool = LabTool.TWEEZER
                                heldKernel = "B"
                                insertedKernel = "B"
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B))
                        ) {
                            Text("🤏 Kern B verpflanzen", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        }
                    }
                }
            }
        }

        if (showExplorerOnly) {
            // 3D-Zellkern Canvas Animation
            Animated3DNucleusCanvas(selectedPart = selectedNucleusPart)

            // 3D-Zellkern Detail Explorer Tabs
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("🟣 3D-Zellkern Bestandteile tippen:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)

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

                    val desc = when (selectedNucleusPart) {
                        "poren" -> "🕳️ Kernporen (Porenkomplexe): Bieten Tunnel-Durchlässe in der doppelten Kernhülle. Siehst du die gelben mRNA-Partikel nach außen ins Zytoplasma wandern?"
                        "chromatin" -> "🧬 Chromatin (DNA-Stränge): Fädige Netzwerke im Kerninneren. Hier lagern alle Erbinformationen (Gene) der Pflanzen- und Tierzelle!"
                        else -> "🟡 Nucleolus (Kernkörperchen): Die runde Steuer-Kugel im Zentrum. Hier werden die Bausteine für die Protein-Fabriken (Ribosomen) hergestellt!"
                    }
                    Text(desc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }
        }
    }
}

@Composable
private fun Animated3DNucleusCanvas(selectedPart: String) {
    val transition = rememberInfiniteTransition(label = "nucleus3d")

    // Pulsierender Transport-Partikel
    val particleOffset by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "particle"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF111827))
    ) {
        Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            val width = size.width
            val height = size.height
            val centerX = width * 0.5f
            val centerY = height * 0.5f
            val nRadius = 85f

            // 1. Doppelte 3D-Kernhülle (Membran mit Hohlraum)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFF8B5CF6), Color(0xFF4C1D95)),
                    center = Offset(centerX - 30f, centerY - 30f),
                    radius = nRadius + 25f
                ),
                radius = nRadius + 18f,
                center = Offset(centerX, centerY)
            )

            // Innere Kernhülle
            drawCircle(
                color = Color(0xFF2E1065),
                radius = nRadius,
                center = Offset(centerX, centerY)
            )

            // 2. 8 Plastische Kernporen Tunnels auf der Membran
            val poresCount = 8
            for (i in 0 until poresCount) {
                val angle = (i * 45f) * (3.14159f / 180f)
                val px = centerX + (nRadius + 9f) * kotlin.math.cos(angle)
                val py = centerY + (nRadius + 9f) * kotlin.math.sin(angle)

                val poreColor = if (selectedPart == "poren") Color(0xFF00E5FF) else Color(0xFFA78BFA)
                drawCircle(color = poreColor, radius = 9f, center = Offset(px, py))

                // Animierter mRNA Partikel wandernd durch die Pore nach außen!
                if (selectedPart == "poren" || selectedPart == "chromatin") {
                    val particleDist = nRadius - 20f + (50f * particleOffset)
                    val particleX = centerX + particleDist * kotlin.math.cos(angle)
                    val particleY = centerY + particleDist * kotlin.math.sin(angle)
                    drawCircle(Color(0xFFFFD700), radius = 4f, center = Offset(particleX, particleY))
                }
            }

            // 3. Chromatin Fäden (DNA Netzwerk im Inneren)
            val chromatinColor = if (selectedPart == "chromatin") Color(0xFF38BDF8) else Color(0xFFC084FC)
            val path1 = Path()
            path1.moveTo(centerX - 40f, centerY - 30f)
            path1.quadraticTo(centerX - 10f, centerY - 60f, centerX + 30f, centerY - 20f)
            drawPath(path1, chromatinColor, style = Stroke(width = 4f))

            val path2 = Path()
            path2.moveTo(centerX - 50f, centerY + 20f)
            path2.quadraticTo(centerX, centerY + 50f, centerX + 40f, centerY + 30f)
            drawPath(path2, chromatinColor, style = Stroke(width = 4f))

            // 4. Nucleolus (Kernkörperchen im Zentrum)
            val nucleolusColor = if (selectedPart == "nucleolus") Color(0xFFFFD700) else Color(0xFFF59E0B)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFFFCD34D), nucleolusColor),
                    center = Offset(centerX - 10f, centerY - 10f),
                    radius = 35f
                ),
                radius = 28f,
                center = Offset(centerX, centerY)
            )
        }
    }
}

@Composable
private fun InteractiveHutalgenCanvas(
    selectedTool: LabTool,
    kernelsExtracted: Boolean,
    capCutOff: Boolean,
    heldKernel: String?,
    insertedKernel: String?,
    capGrownType: String?,
    onTargetTapped: (String) -> Unit
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onTargetTapped("foot")
            }
            .padding(12.dp)
    ) {
        val width = size.width
        val height = size.height

        val algeAX = width * 0.32f
        val algeBX = width * 0.68f
        val footY = height * 0.72f
        val capY = height * 0.22f
        val tableY = height * 0.88f

        drawLine(Color.Gray.copy(alpha = 0.5f), start = Offset(10f, tableY), end = Offset(width - 10f, tableY), strokeWidth = 4f)

        // Hutalge A
        drawCircle(Color(0xFF22C55E), radius = 24f, center = Offset(algeAX, footY))
        drawLine(Color(0xFF4ADE80), start = Offset(algeAX, footY - 18f), end = Offset(algeAX, capY), strokeWidth = 7f)

        if (!kernelsExtracted) {
            drawCircle(Color(0xFF8B5CF6), radius = 11f, center = Offset(algeAX, footY))
        }

        if (insertedKernel != null) {
            val color = if (insertedKernel == "B") Color(0xFFF59E0B) else Color(0xFF8B5CF6)
            drawCircle(color, radius = 13f, center = Offset(algeAX, footY))
        }

        if (!capCutOff) {
            drawArc(
                color = Color(0xFF10B981),
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = true,
                topLeft = Offset(algeAX - 36f, capY - 28f),
                size = Size(72f, 36f)
            )
        }

        if (capGrownType == "B") {
            drawStarCap(algeAX, capY)
        } else if (capGrownType == "A") {
            drawArc(
                color = Color(0xFF10B981),
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = true,
                topLeft = Offset(algeAX - 36f, capY - 28f),
                size = Size(72f, 36f)
            )
        }

        // Hutalge B
        drawCircle(Color(0xFF22C55E), radius = 24f, center = Offset(algeBX, footY))
        drawLine(Color(0xFF4ADE80), start = Offset(algeBX, footY - 18f), end = Offset(algeBX, capY), strokeWidth = 7f)

        if (!kernelsExtracted) {
            drawCircle(Color(0xFFF59E0B), radius = 11f, center = Offset(algeBX, footY))
        }

        drawStarCap(algeBX, capY)

        // Kerne auf dem Tisch
        val kernAX = algeAX - 45f
        val kernBX = algeBX + 45f

        if (kernelsExtracted) {
            if (heldKernel != "A" && insertedKernel != "A") {
                drawCircle(Color(0xFF8B5CF6), radius = 13f, center = Offset(kernAX, tableY - 14f))
            }

            if (heldKernel != "B" && insertedKernel != "B") {
                drawCircle(Color(0xFFF59E0B), radius = 14f, center = Offset(kernBX, tableY - 14f))
            }
        }

        // Gehaltener Kern
        if (heldKernel != null) {
            val heldColor = if (heldKernel == "B") Color(0xFFF59E0B) else Color(0xFF8B5CF6)
            val tweezerX = width * 0.5f
            val tweezerY = height * 0.45f

            drawCircle(heldColor, radius = 14f, center = Offset(tweezerX, tweezerY))
            drawLine(Color.LightGray, start = Offset(tweezerX - 15f, tweezerY - 25f), end = Offset(tweezerX - 3f, tweezerY - 5f), strokeWidth = 4f)
            drawLine(Color.LightGray, start = Offset(tweezerX + 15f, tweezerY - 25f), end = Offset(tweezerX + 3f, tweezerY - 5f), strokeWidth = 4f)
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawStarCap(centerX: Float, centerY: Float) {
    val rayColor = Color(0xFFF59E0B)

    drawArc(
        color = rayColor,
        startAngle = 180f,
        sweepAngle = 180f,
        useCenter = true,
        topLeft = Offset(centerX - 36f, centerY - 28f),
        size = Size(72f, 36f)
    )

    val rays = 10
    val rInner = 32f
    val rOuter = 46f
    for (i in 0..rays) {
        val angle = (180f + (i * 180f / rays)) * (3.14159f / 180f)
        val x1 = centerX + rInner * kotlin.math.cos(angle)
        val y1 = centerY - 10f + rInner * kotlin.math.sin(angle)
        val x2 = centerX + rOuter * kotlin.math.cos(angle)
        val y2 = centerY - 10f + rOuter * kotlin.math.sin(angle)
        drawLine(rayColor, start = Offset(x1, y1), end = Offset(x2, y2), strokeWidth = 5f)
    }
}
