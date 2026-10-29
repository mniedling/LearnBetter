package org.learnbetter.project.ui.biology

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
    onXpEarned: (Int) -> Unit = {},
    onBackToHub: (() -> Unit)? = null
) {
    var selectedTool by remember { mutableStateOf(LabTool.NONE) }

    // Interaktions-Zustände
    var kernelsExtracted by remember { mutableStateOf(false) } // Step 1: Kerne auf dem Tisch
    var capCutOff by remember { mutableStateOf(false) }         // Step 2: Hut A abgetrennt
    var kernelTransplanted by remember { mutableStateOf(false) }// Step 3: Kern B eingesetzt
    var capGrown by remember { mutableStateOf(false) }         // Step 4: Neuer Hut gewachsen

    var selectedNucleusPart by remember { mutableStateOf<String?>(null) }

    // Wuchs Animation sobald Kern verpflanzt
    LaunchedEffect(kernelTransplanted) {
        if (kernelTransplanted && !capGrown) {
            kotlinx.coroutines.delay(1500)
            capGrown = true
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
            text = "🔬 Interaktives Hutalgen-Labor",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Subject.BIOLOGY.primaryColor,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Wähle Skalpell ✂️ und Pinzette 🤏 rechts aus der Leiste und führe das Hämmerling-Experiment selbst durch!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        // Experiment Canvas mit rechter Werkzeugleiste
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Main Interactive Canvas
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
                        kernelTransplanted = kernelTransplanted,
                        capGrown = capGrown,
                        onExtractKernels = {
                            if (selectedTool == LabTool.SCALPEL) kernelsExtracted = true
                        },
                        onCutCap = {
                            if (selectedTool == LabTool.SCALPEL && kernelsExtracted) capCutOff = true
                        },
                        onTransplantKernel = {
                            if (selectedTool == LabTool.TWEEZER && capCutOff) kernelTransplanted = true
                        }
                    )
                }
            }

            // Rechte Werkzeugleiste (Skalpell & Pinzette)
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
                    // Skalpell Tool
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

                    // Pinzette Tool
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

        // Anleitung / Status
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
                        !kernelTransplanted -> "3️⃣ Wähle die Pinzette 🤏 und setze den gelben Kern B in den Fuß von Alge A ein!"
                        !capGrown -> "🌱 Der Zellkern B arbeitet... Der neue Hut wächst..."
                        else -> "🎉 WUNDERSCHÖN! Ein neuer Strahlenhut B ist an Alge A gewachsen! (+25 XP)"
                    },
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Subject.BIOLOGY.primaryColor
                )

                if (capGrown) {
                    Button(
                        onClick = {
                            selectedTool = LabTool.NONE
                            kernelsExtracted = false
                            capCutOff = false
                            kernelTransplanted = false
                            capGrown = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Subject.BIOLOGY.primaryColor)
                    ) {
                        Text("🔄 Experiment zurücksetzen & nochmal versuchen")
                    }
                }
            }
        }

        // 2. Zellkern Detail Explorer
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

@Composable
private fun InteractiveHutalgenCanvas(
    selectedTool: LabTool,
    kernelsExtracted: Boolean,
    capCutOff: Boolean,
    kernelTransplanted: Boolean,
    capGrown: Boolean,
    onExtractKernels: () -> Unit,
    onCutCap: () -> Unit,
    onTransplantKernel: () -> Unit
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                if (selectedTool == LabTool.SCALPEL) {
                    if (!kernelsExtracted) onExtractKernels() else if (!capCutOff) onCutCap()
                } else if (selectedTool == LabTool.TWEEZER) {
                    if (capCutOff && !kernelTransplanted) onTransplantKernel()
                }
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

        // Labortisch Untergrund
        drawLine(Color.Gray.copy(alpha = 0.5f), start = Offset(10f, tableY), end = Offset(width - 10f, tableY), strokeWidth = 4f)

        // --- 1. HUTALGE A ---
        drawCircle(Color(0xFF22C55E), radius = 24f, center = Offset(algeAX, footY))

        // Stiel A
        drawLine(Color(0xFF4ADE80), start = Offset(algeAX, footY - 18f), end = Offset(algeAX, capY), strokeWidth = 7f)

        // Kern im Fuß A (falls noch nicht entnommen)
        if (!kernelsExtracted) {
            drawCircle(Color(0xFF8B5CF6), radius = 11f, center = Offset(algeAX, footY))
        }

        // Kern in Fuß A eingesetzt (Transplantation)
        if (kernelTransplanted) {
            drawCircle(Color(0xFFF59E0B), radius = 13f, center = Offset(algeAX, footY))
        }

        // Hut A (Schirmhut) - falls noch nicht abgeschnitten
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

        // Neuer nachgewachsener Strahlenhut B bei Step 4!
        if (capGrown) {
            drawCircle(Color(0xFFF59E0B), radius = 18f, center = Offset(algeAX, capY - 10f))
        }


        // --- 2. HUTALGE B ---
        drawCircle(Color(0xFF22C55E), radius = 24f, center = Offset(algeBX, footY))

        // Stiel B
        drawLine(Color(0xFF4ADE80), start = Offset(algeBX, footY - 18f), end = Offset(algeBX, capY), strokeWidth = 7f)

        // Kern im Fuß B (falls noch nicht entnommen)
        if (!kernelsExtracted) {
            drawCircle(Color(0xFFF59E0B), radius = 11f, center = Offset(algeBX, footY))
        }

        // Hut B (Strahlenhut)
        drawCircle(Color(0xFFF59E0B), radius = 18f, center = Offset(algeBX, capY - 10f))


        // --- 3. DIE KERNE AUF DEM LABORTISCH ---
        if (kernelsExtracted && !kernelTransplanted) {
            // Kern A liegt links auf dem Tisch
            drawCircle(Color(0xFF8B5CF6), radius = 12f, center = Offset(algeAX - 40f, tableY - 12f))

            // Kern B liegt rechts auf dem Tisch (zum Transplantieren!)
            drawCircle(Color(0xFFF59E0B), radius = 14f, center = Offset(algeBX + 40f, tableY - 12f))
        }
    }
}
