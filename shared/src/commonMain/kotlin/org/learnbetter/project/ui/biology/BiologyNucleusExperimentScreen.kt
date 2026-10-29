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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.learnbetter.project.model.Subject

@Composable
fun BiologyNucleusExperimentScreen(
    onXpEarned: (Int) -> Unit = {},
    onBackToHub: (() -> Unit)? = null
) {
    var animStep by remember { mutableIntStateOf(0) } // 0 = Start, 1 = Kappen, 2 = Transplantieren, 3 = Wuchs
    var isAnimating by remember { mutableStateOf(false) }
    var selectedNucleusPart by remember { mutableStateOf<String?>(null) }

    // Sequential animation coroutine
    LaunchedEffect(isAnimating) {
        if (isAnimating) {
            animStep = 1 // Skalpell schneidet Hut A
            delay(1500)
            animStep = 2 // Pinzette verpflanzt Kern B
            delay(2000)
            animStep = 3 // Neuer Hut wächst nach
            delay(2000)
            isAnimating = false
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
                    Text("◀️ Zurück zur Übersicht")
                }
            }
        }

        Text(
            text = "🔬 Hutalgen-Experiment & Zellkern",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Subject.BIOLOGY.primaryColor,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Beobachte live die Skalpell- und Pinzetten-Transplantation im Hämmerling-Experiment!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        // 1. Animiertes Hutalgen Canvas Experiment
        HutalgenAnimationCanvas(animStep = animStep)

        // Steuerung Button
        Button(
            onClick = {
                if (animStep == 3) {
                    animStep = 0
                } else {
                    isAnimating = true
                }
            },
            enabled = !isAnimating,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Subject.BIOLOGY.primaryColor)
        ) {
            Text(
                text = when {
                    isAnimating -> "✂️ Skalpell & Pinzette arbeiten..."
                    animStep == 3 -> "🔄 Experiment erneut starten"
                    else -> "✂️ 1. Skalpell & Pinzetten-Transplantation starten!"
                },
                fontWeight = FontWeight.Bold
            )
        }

        // Experiment Phasen Beschreibung
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = when (animStep) {
                        1 -> "✂️ Skalpell kappt den Schirmhut von Hutalge A!"
                        2 -> "🤏 Pinzette entnimmt Kern B (Strahlenhut) und verpflanzt ihn in Fuß A!"
                        3 -> "🎉 Der neue Hut wächst nach! Es ist die Strahlenform von Alge B!"
                        else -> "Ausgangslage: Hutalge A (Schirmhut) und Hutalge B (Strahlenhut)."
                    },
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Subject.BIOLOGY.primaryColor
                )
                Text(
                    text = "Beweis: Der Zellkern enthält die genetische Bauanleitung für die Form der Hutalge (Acetabularia).",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        // 2. Zellkern Detail Explorer (Kernporen, Chromatin, Nucleolus)
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
private fun HutalgenAnimationCanvas(animStep: Int) {
    val stepProgress by animateFloatAsState(
        targetValue = animStep.toFloat(),
        animationSpec = tween(durationMillis = 1200)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
    ) {
        Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            val width = size.width
            val height = size.height

            val algeAX = width * 0.3f
            val algeBX = width * 0.7f
            val footY = height * 0.85f
            val capY = height * 0.25f

            // --- 1. HUTALGE A (Schirmhut) ---
            // Rhizoid / Wurzelfuss A
            drawCircle(Color(0xFF22C55E), radius = 25f, center = Offset(algeAX, footY))

            // Zellkern A (wird in Step 2 entfernt)
            if (animStep < 2) {
                drawCircle(Color(0xFF8B5CF6), radius = 12f, center = Offset(algeAX, footY))
            } else {
                // Transplantierter Kern B!
                drawCircle(Color(0xFFF59E0B), radius = 14f, center = Offset(algeAX, footY))
            }

            // Stiel A
            drawLine(Color(0xFF4ADE80), start = Offset(algeAX, footY - 20f), end = Offset(algeAX, capY), strokeWidth = 8f)

            // Hut A (Schirmform) - wird ab Step 1 abgeschnitten!
            if (animStep == 0) {
                drawArc(
                    color = Color(0xFF10B981),
                    startAngle = 180f,
                    sweepAngle = 180f,
                    useCenter = true,
                    topLeft = Offset(algeAX - 40f, capY - 30f),
                    size = Size(80f, 40f)
                )
            } else if (animStep == 3) {
                // Neuer nachgewachsener Strahlenhut B!
                val starPath = Path()
                val rays = 8
                for (i in 0 until rays) {
                    val angle = (i * 360f / rays) * (3.14159f / 180f)
                    val rx = algeAX + kotlin.math.cos(angle) * 35f
                    val ry = capY - 10f + kotlin.math.sin(angle) * 20f
                    if (i == 0) starPath.moveTo(rx, ry) else starPath.lineTo(rx, ry)
                }
                drawPath(starPath, Color(0xFFF59E0B), style = Stroke(4f))
                drawCircle(Color(0xFFF59E0B), radius = 15f, center = Offset(algeAX, capY - 10f))
            }


            // --- 2. HUTALGE B (Strahlenhut) ---
            drawCircle(Color(0xFF22C55E), radius = 25f, center = Offset(algeBX, footY))

            // Zellkern B (Gelb)
            if (animStep < 2) {
                drawCircle(Color(0xFFF59E0B), radius = 12f, center = Offset(algeBX, footY))
            }

            // Stiel B
            drawLine(Color(0xFF4ADE80), start = Offset(algeBX, footY - 20f), end = Offset(algeBX, capY), strokeWidth = 8f)

            // Hut B (Strahlenform)
            drawCircle(Color(0xFFF59E0B), radius = 20f, center = Offset(algeBX, capY - 10f))


            // --- 3. ANIMIERTES SKALPELL ✂️ & PINZETTE 🤏 ---
            if (animStep == 1) {
                // Skalpell schneidet Hut A ab
                drawLine(Color.Red, start = Offset(algeAX - 50f, capY), end = Offset(algeAX + 50f, capY), strokeWidth = 4f)
            } else if (animStep == 2) {
                // Pinzette bringt Kern B zu Fuß A
                val curveX = algeBX - (algeBX - algeAX) * (stepProgress - 1f)
                val curveY = footY - (kotlin.math.sin((stepProgress - 1f) * 3.14159f) * 60f)

                drawCircle(Color(0xFFFFD700), radius = 14f, center = Offset(curveX, curveY))
            }
        }
    }
}
