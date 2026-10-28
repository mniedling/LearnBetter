package org.learnbetter.project.ui.physics

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Composable
fun PhysicsHookeScreen(
    onXpEarned: (Int) -> Unit = {},
    onBackToHub: (() -> Unit)? = null
) {
    var forceNewton by remember { mutableFloatStateOf(2.0f) } // 0N bis 10N
    var springConstant by remember { mutableFloatStateOf(50.0f) } // 50 N/m

    val extensionMeters = forceNewton / springConstant
    val extensionCm = extensionMeters * 100.0f

    val animatedExtension by animateFloatAsState(
        targetValue = extensionCm,
        animationSpec = tween(durationMillis = 600)
    )

    var hasTested by remember { mutableStateOf(false) }

    LaunchedEffect(forceNewton) {
        if (!hasTested && forceNewton > 0f) {
            hasTested = true
            onXpEarned(15)
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
            text = "⚙️ Hookesches Gesetz & Federkraft",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Subject.PHYSICS.primaryColor,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Hänge Gewichte an die Spiralfeder und beobachte, wie sich die Verlängerung Δs proportional zur Kraft F verhält!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        // 1. Großes Visuelles Feder Canvas (300dp)
        SpringVisualizerCanvas(
            forceNewton = forceNewton,
            extensionCm = animatedExtension
        )

        // 2. Werte & Formel Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Formel:  F = D · Δs",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Angewendete Kraft F:", style = MaterialTheme.typography.bodyMedium)
                    Text("${formatFloat(forceNewton)} N  (${(forceNewton * 100).toInt()} g)", fontWeight = FontWeight.Bold)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Federkonstante D:", style = MaterialTheme.typography.bodyMedium)
                    Text("${springConstant.toInt()} N/m", fontWeight = FontWeight.Bold)
                }

                HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Berechnete Auslenkung Δs:", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Text("${formatFloat(extensionCm)} cm", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = Subject.PHYSICS.primaryColor)
                }
            }
        }

        // 3. Schieberegler Kraft F
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Gewichte / Kraft F anpassen:", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)

                Column {
                    Text("Gewichtskraft F = ${formatFloat(forceNewton)} N", style = MaterialTheme.typography.labelMedium)
                    Slider(
                        value = forceNewton,
                        onValueChange = { forceNewton = (it * 10).toInt() / 10.0f },
                        valueRange = 0.0f..10.0f,
                        steps = 99,
                        colors = SliderDefaults.colors(thumbColor = Subject.PHYSICS.primaryColor, activeTrackColor = Subject.PHYSICS.primaryColor)
                    )
                }

                Column {
                    Text("Federkonstante D = ${springConstant.toInt()} N/m", style = MaterialTheme.typography.labelMedium)
                    Slider(
                        value = springConstant,
                        onValueChange = { springConstant = it },
                        valueRange = 20.0f..100.0f,
                        steps = 7,
                        colors = SliderDefaults.colors(thumbColor = Subject.PHYSICS.primaryColor, activeTrackColor = Subject.PHYSICS.primaryColor)
                    )
                }
            }
        }
    }
}

@Composable
private fun SpringVisualizerCanvas(
    forceNewton: Float,
    extensionCm: Float
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1F2937))
    ) {
        Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            val width = size.width
            val height = size.height
            val springX = width * 0.35f
            val startY = 25f

            // Größere Dehnungsskalierung (12px pro cm)
            val springLength = 80f + (extensionCm * 11f).coerceAtMost(180f)

            // Aufhängung oben
            drawLine(Color.Gray, start = Offset(springX - 45f, startY), end = Offset(springX + 45f, startY), strokeWidth = 8f)

            // Zickzack Spiralfeder (Größer & Breiter)
            val path = Path()
            path.moveTo(springX, startY)

            val coils = 12
            val segmentHeight = springLength / coils
            for (i in 0 until coils) {
                val y1 = startY + (i * segmentHeight) + (segmentHeight / 2f)
                val x1 = if (i % 2 == 0) springX - 32f else springX + 32f
                path.lineTo(x1, y1)
            }
            path.lineTo(springX, startY + springLength)

            drawPath(
                path = path,
                color = Color(0xFF60A5FA),
                style = Stroke(width = 7f)
            )

            // Größeres angehängtes Gewicht
            val weightY = startY + springLength
            val weightSize = (40f + (forceNewton * 3f)).coerceIn(40f, 75f)
            drawRect(
                color = if (forceNewton > 0f) Color(0xFFEF4444) else Color.Gray,
                topLeft = Offset(springX - (weightSize / 2f), weightY),
                size = Size(weightSize, weightSize)
            )

            // Messskala / Lineal daneben (Rechts)
            val rulerX = width * 0.78f
            drawLine(Color.White.copy(alpha = 0.6f), start = Offset(rulerX, startY), end = Offset(rulerX, height - 10f), strokeWidth = 3f)

            for (cm in 0..20 step 2) {
                val tickY = startY + (cm * 11f)
                if (tickY <= height - 10f) {
                    val isMajor = cm % 10 == 0
                    val tickWidth = if (isMajor) 20f else 10f
                    drawLine(Color.White.copy(alpha = 0.8f), start = Offset(rulerX - tickWidth, tickY), end = Offset(rulerX + tickWidth, tickY), strokeWidth = if (isMajor) 3f else 2f)
                }
            }
        }
    }
}

private fun formatFloat(value: Float): String {
    return ((value * 10).toInt() / 10.0).toString()
}
