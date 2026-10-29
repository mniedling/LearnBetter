package org.learnbetter.project.ui.biology

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
import androidx.compose.ui.graphics.Color
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

    // Interaktions-Zustände
    var kernelsExtracted by remember { mutableStateOf(false) } // Step 1: Kerne auf dem Tisch
    var capCutOff by remember { mutableStateOf(false) }         // Step 2: Hut A abgetrennt
    var heldKernel by remember { mutableStateOf<String?>(null) }// "A" oder "B" in der Pinzette
    var insertedKernel by remember { mutableStateOf<String?>(null) } // "A" oder "B" in Fuß A
    var capGrownType by remember { mutableStateOf<String?>(null) }   // "A" (Schirm) oder "B" (Strahlen)

    var selectedNucleusPart by remember { mutableStateOf<String?>(null) }

    // Wuchs Animation sobald Kern eingesetzt
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
            text = if (showExplorerOnly) "Erforsche Kernporen, Chromatin (DNA) und Nucleolus im Zellkern!" else "Führe das Experiment durch! Teste auch, welcher Hut nachwächst, wenn du Kern A wiedereinsetzt!",
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
                            onCanvasClicked = { clickPart ->
                                if (selectedTool == LabTool.SCALPEL) {
                                    if (!kernelsExtracted && clickPart == "foot") kernelsExtracted = true
                                    else if (kernelsExtracted && !capCutOff && clickPart == "capA") capCutOff = true
                                } else if (selectedTool == LabTool.TWEEZER) {
                                    if (capCutOff && insertedKernel == null) {
                                        if (heldKernel == null) {
                                            if (clickPart == "kernelA") heldKernel = "A"
                                            else if (clickPart == "kernelB") heldKernel = "B"
                                        } else if (clickPart == "footA" || clickPart == "foot") {
                                            insertedKernel = heldKernel
                                            heldKernel = null
                                        }
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

            // Anleitung & Status
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = when {
                            !kernelsExtracted -> "1️⃣ Wähle das Skalpell ✂️ und klicke auf die Füße, um die Zellkerne zu entnehmen!"
                            !capCutOff -> "2️⃣ Klicke mit dem Skalpell ✂️ oben auf Hut A, um ihn abzutrennen!"
                            heldKernel != null -> "🤏 Kern $heldKernel in der Pinzette! Klicke jetzt auf den Fuß von Alge A, um ihn einzusetzen!"
                            insertedKernel == null -> "3️⃣ Wähle die Pinzette 🤏 und klicke auf Kern A (Lila) oder Kern B (Gelb) auf dem Tisch!"
                            capGrownType == null -> "🌱 Zellkern $insertedKernel arbeitet im Fuß... Neuer Hut wächst heran..."
                            capGrownType == "B" -> "🎉 ERFOLG! Mit Kern B ist ein neuer Strahlenhut B nachgewachsen! (+25 XP)"
                            else -> "🎉 KONTROLL-EXPERIMENT: Mit dem eigenen Kern A wächst wieder der alte Schirmhut A nach! (+25 XP)"
                        },
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Subject.BIOLOGY.primaryColor
                    )

                    if (capGrownType != null) {
                        Button(
                            onClick = {
                                selectedTool = LabTool.NONE
                                kernelsExtracted = false
                                capCutOff = false
                                heldKernel = null
                                insertedKernel = null
                                capGrownType = null
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Subject.BIOLOGY.primaryColor)
                        ) {
                            Text("🔄 Experiment nochmal versuchen (Anderen Kern testen)")
                        }
                    }
                }
            }
        }

        if (showExplorerOnly) {
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
}

@Composable
private fun InteractiveHutalgenCanvas(
    selectedTool: LabTool,
    kernelsExtracted: Boolean,
    capCutOff: Boolean,
    heldKernel: String?,
    insertedKernel: String?,
    capGrownType: String?,
    onCanvasClicked: (String) -> Unit
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                // Generische Klick-Auswertung
                onCanvasClicked("foot")
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

        // --- HUTALGE A ---
        drawCircle(Color(0xFF22C55E), radius = 24f, center = Offset(algeAX, footY))
        drawLine(Color(0xFF4ADE80), start = Offset(algeAX, footY - 18f), end = Offset(algeAX, capY), strokeWidth = 7f)

        // Kern im Fuß A (Anfang)
        if (!kernelsExtracted) {
            drawCircle(Color(0xFF8B5CF6), radius = 11f, center = Offset(algeAX, footY))
        }

        // Kern in Fuß A eingesetzt
        if (insertedKernel != null) {
            val color = if (insertedKernel == "B") Color(0xFFF59E0B) else Color(0xFF8B5CF6)
            drawCircle(color, radius = 13f, center = Offset(algeAX, footY))
        }

        // Hut A (Schirm)
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

        // Nachgewachsener Hut A oder B
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

        // --- HUTALGE B ---
        drawCircle(Color(0xFF22C55E), radius = 24f, center = Offset(algeBX, footY))
        drawLine(Color(0xFF4ADE80), start = Offset(algeBX, footY - 18f), end = Offset(algeBX, capY), strokeWidth = 7f)

        if (!kernelsExtracted) {
            drawCircle(Color(0xFFF59E0B), radius = 11f, center = Offset(algeBX, footY))
        }

        drawStarCap(algeBX, capY)

        // --- KERNE AUF DEM TISCH ---
        val kernAX = algeAX - 45f
        val kernBX = algeBX + 45f

        if (kernelsExtracted) {
            // Kern A (Lila) auf dem Tisch, falls nicht gehalten oder eingesetzt
            if (heldKernel != "A" && insertedKernel != "A") {
                drawCircle(Color(0xFF8B5CF6), radius = 13f, center = Offset(kernAX, tableY - 14f))
            }

            // Kern B (Gelb) auf dem Tisch, falls nicht gehalten oder eingesetzt
            if (heldKernel != "B" && insertedKernel != "B") {
                drawCircle(Color(0xFFF59E0B), radius = 14f, center = Offset(kernBX, tableY - 14f))
            }
        }

        // --- GEHALTENER KERN IN DER PINZETTE ---
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
