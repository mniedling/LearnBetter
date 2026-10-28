package org.learnbetter.project.ui.math

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.learnbetter.project.model.Subject
import kotlin.math.sqrt

@Composable
fun MathParabolaScreen(
    onXpEarned: (Int) -> Unit = {},
    onBackToHub: (() -> Unit)? = null
) {
    var parameterA by remember { mutableFloatStateOf(1.0f) }
    var parameterC by remember { mutableFloatStateOf(0.0f) }

    val vertexY = parameterC
    val hasNullstelle = (parameterA > 0 && parameterC <= 0) || (parameterA < 0 && parameterC >= 0)
    val nullstelleValue = if (hasNullstelle && parameterA != 0f) sqrt((-parameterC / parameterA).toDouble()).toFloat() else null

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
            text = "📐 Interaktiver Parabel-Plotter",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Subject.MATH.primaryColor,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Bewege die Schieberegler für a und c und beobachte, wie sich die Parabel f(x) = a·x² + c verändert!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        // 1. Canvas Koordinatensystem Parabel Graph
        ParabolaCanvas(
            a = parameterA,
            c = parameterC
        )

        // 2. Mathematische Eigenschaften Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Funktion: f(x) = ${formatFloat(parameterA)}·x² ${if (parameterC >= 0) "+ " + formatFloat(parameterC) else "- " + formatFloat(-parameterC)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Scheitelpunkt S:", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                    Text("S( 0 | ${formatFloat(vertexY)} )", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Nullstellen N:", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                    Text(
                        text = if (nullstelleValue != null) "N₁(-${formatFloat(nullstelleValue)}|0), N₂(${formatFloat(nullstelleValue)}|0)" else "Keine reellen Nullstellen",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = getParabolaDescription(parameterA, parameterC),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                )
            }
        }

        // 3. Schieberegler für Parameter a und c
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Parameter anpassen:", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)

                Column {
                    Text("Öffnungsfaktor a = ${formatFloat(parameterA)}", style = MaterialTheme.typography.labelMedium)
                    Slider(
                        value = parameterA,
                        onValueChange = { parameterA = (it * 10).toInt() / 10.0f },
                        valueRange = -2.5f..2.5f,
                        steps = 49,
                        colors = SliderDefaults.colors(thumbColor = Subject.MATH.primaryColor, activeTrackColor = Subject.MATH.primaryColor)
                    )
                }

                Column {
                    Text("Verschiebung y-Achse c = ${formatFloat(parameterC)}", style = MaterialTheme.typography.labelMedium)
                    Slider(
                        value = parameterC,
                        onValueChange = { parameterC = (it * 10).toInt() / 10.0f },
                        valueRange = -5.0f..5.0f,
                        steps = 99,
                        colors = SliderDefaults.colors(thumbColor = Subject.MATH.primaryColor, activeTrackColor = Subject.MATH.primaryColor)
                    )
                }
            }
        }
    }
}

@Composable
private fun ParabolaCanvas(a: Float, c: Float) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF121B22))
    ) {
        Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            val width = size.width
            val height = size.height
            val centerX = width / 2f
            val centerY = height / 2f
            val scale = 25f // 25 Pixel pro Mathematische Einheit

            // Raster zeichnen
            val gridColor = Color.Gray.copy(alpha = 0.25f)
            for (x in -10..10) {
                val posX = centerX + (x * scale)
                drawLine(gridColor, start = Offset(posX, 0f), end = Offset(posX, height), strokeWidth = 1f)
            }
            for (y in -10..10) {
                val posY = centerY - (y * scale)
                drawLine(gridColor, start = Offset(0f, posY), end = Offset(width, posY), strokeWidth = 1f)
            }

            // Achsen zeichnen
            val axisColor = Color.White.copy(alpha = 0.8f)
            drawLine(axisColor, start = Offset(0f, centerY), end = Offset(width, centerY), strokeWidth = 2f) // x-Achse
            drawLine(axisColor, start = Offset(centerX, 0f), end = Offset(centerX, height), strokeWidth = 2f) // y-Achse

            // Parabel Graph zeichnen
            val path = Path()
            var first = true

            val stepX = 0.1f
            var x = -8.0f
            while (x <= 8.0f) {
                val y = a * (x * x) + c
                val canvasX = centerX + (x * scale)
                val canvasY = centerY - (y * scale)

                if (first) {
                    path.moveTo(canvasX, canvasY)
                    first = false
                } else {
                    path.lineTo(canvasX, canvasY)
                }
                x += stepX
            }

            drawPath(
                path = path,
                color = Color(0xFFAB47BC),
                style = Stroke(width = 4f)
            )

            // Scheitelpunkt S
            val vertexCanvasY = centerY - (c * scale)
            drawCircle(
                color = Color(0xFFFFD700),
                radius = 6f,
                center = Offset(centerX, vertexCanvasY)
            )
        }
    }
}

private fun formatFloat(value: Float): String {
    return ((value * 10).toInt() / 10.0).toString()
}

private fun getParabolaDescription(a: Float, c: Float): String {
    val form = when {
        a > 1.0f -> "Gestreckt (steil) und nach oben geöffnet."
        a > 0.0f && a < 1.0f -> "Gestaucht (breiter) und nach oben geöffnet."
        a < -1.0f -> "Gestreckt (steil) und nach unten geöffnet."
        a < 0.0f -> "Gestaucht (breiter) und nach unten geöffnet."
        else -> "Lineare Gerade (a = 0)."
    }
    val pos = if (c > 0) "Um $c Einheiten nach oben verschoben." else if (c < 0) "Um ${-c} Einheiten nach unten verschoben." else "Der Scheitelpunkt liegt im Ursprung (0|0)."
    return "$form $pos"
}
