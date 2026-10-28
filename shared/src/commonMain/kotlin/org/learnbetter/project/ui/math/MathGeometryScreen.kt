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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.learnbetter.project.model.Subject
import kotlin.math.PI
import kotlin.math.sqrt

@Composable
fun MathGeometryScreen(
    onXpEarned: (Int) -> Unit = {},
    onBackToHub: (() -> Unit)? = null
) {
    var selectedShapeMode by remember { mutableIntStateOf(1) } // 1 = Zylinder, 2 = Pyramide

    // Zylinder Parameter
    var radius by remember { mutableFloatStateOf(3.0f) }
    var heightCylinder by remember { mutableFloatStateOf(6.0f) }

    // Pyramiden Parameter
    var baseA by remember { mutableFloatStateOf(4.0f) }
    var heightPyramid by remember { mutableFloatStateOf(5.0f) }

    // Berechnungen Zylinder
    val baseAreaCylinder = PI * radius * radius
    val mantleAreaCylinder = 2 * PI * radius * heightCylinder
    val surfaceAreaCylinder = 2 * baseAreaCylinder + mantleAreaCylinder
    val volumeCylinder = baseAreaCylinder * heightCylinder

    // Berechnungen Pyramide
    val slantHeightHs = sqrt((heightPyramid * heightPyramid + (baseA / 2f) * (baseA / 2f)).toDouble()).toFloat()
    val baseAreaPyramid = baseA * baseA
    val mantleAreaPyramid = 2 * baseA * slantHeightHs
    val surfaceAreaPyramid = baseAreaPyramid + mantleAreaPyramid
    val volumePyramid = (1.0 / 3.0) * baseAreaPyramid * heightPyramid

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
            text = "📐 Zylinder & Pyramiden Oberflächenrechner",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Subject.MATH.primaryColor,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Verändere Radius, Grundkante und Höhe, um Grundfläche G, Mantelfläche M und Oberfläche O zu berechnen!",
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
                onClick = { selectedShapeMode = 1 },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedShapeMode == 1) Subject.MATH.primaryColor else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text("🛢️ Zylinder", fontWeight = FontWeight.Bold, color = if (selectedShapeMode == 1) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Button(
                onClick = { selectedShapeMode = 2 },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedShapeMode == 2) Subject.MATH.primaryColor else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text("🔺 Pyramide", fontWeight = FontWeight.Bold, color = if (selectedShapeMode == 2) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        if (selectedShapeMode == 1) {
            // Zylinder Mode
            CylinderVisualizerCanvas(radius = radius, height = heightCylinder)

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("🛢️ Zylinder Formeln:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)

                    Text("Grundfläche (2x): G = π · r² = ${formatDouble(baseAreaCylinder)} cm²", style = MaterialTheme.typography.bodySmall)
                    Text("Mantelfläche: M = 2π · r · h = ${formatDouble(mantleAreaCylinder)} cm²", style = MaterialTheme.typography.bodySmall)

                    HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))

                    Text("Oberfläche O = 2·G + M = ${formatDouble(surfaceAreaCylinder)} cm²", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = Subject.MATH.primaryColor)
                    Text("Volumen V = G · h = ${formatDouble(volumeCylinder)} cm³", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                }
            }

            // Slider Zylinder
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Zylinder Maße anpassen:", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)

                    Column {
                        Text("Radius r = ${formatFloat(radius)} cm", style = MaterialTheme.typography.labelMedium)
                        Slider(
                            value = radius,
                            onValueChange = { radius = (it * 10).toInt() / 10.0f },
                            valueRange = 1.0f..8.0f,
                            steps = 69,
                            colors = SliderDefaults.colors(thumbColor = Subject.MATH.primaryColor, activeTrackColor = Subject.MATH.primaryColor)
                        )
                    }

                    Column {
                        Text("Höhe h = ${formatFloat(heightCylinder)} cm", style = MaterialTheme.typography.labelMedium)
                        Slider(
                            value = heightCylinder,
                            onValueChange = { heightCylinder = (it * 10).toInt() / 10.0f },
                            valueRange = 2.0f..12.0f,
                            steps = 99,
                            colors = SliderDefaults.colors(thumbColor = Subject.MATH.primaryColor, activeTrackColor = Subject.MATH.primaryColor)
                        )
                    }
                }
            }
        } else {
            // Pyramide Mode
            PyramidVisualizerCanvas(baseA = baseA, height = heightPyramid)

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("🔺 Pyramide Formeln (Quadratisch):", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)

                    Text("Grundfläche: G = a² = ${formatFloat(baseAreaPyramid)} cm²", style = MaterialTheme.typography.bodySmall)
                    Text("Seitenhöhe hs = √(h² + (a/2)²) = ${formatFloat(slantHeightHs)} cm", style = MaterialTheme.typography.bodySmall)
                    Text("Mantelfläche (4 Dreiecke): M = 2·a·hs = ${formatFloat(mantleAreaPyramid)} cm²", style = MaterialTheme.typography.bodySmall)

                    HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))

                    Text("Oberfläche O = G + M = ${formatFloat(surfaceAreaPyramid)} cm²", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = Subject.MATH.primaryColor)
                    Text("Volumen V = 1/3 · G · h = ${formatDouble(volumePyramid)} cm³", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                }
            }

            // Slider Pyramide
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Pyramide Maße anpassen:", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)

                    Column {
                        Text("Grundkante a = ${formatFloat(baseA)} cm", style = MaterialTheme.typography.labelMedium)
                        Slider(
                            value = baseA,
                            onValueChange = { baseA = (it * 10).toInt() / 10.0f },
                            valueRange = 2.0f..10.0f,
                            steps = 79,
                            colors = SliderDefaults.colors(thumbColor = Subject.MATH.primaryColor, activeTrackColor = Subject.MATH.primaryColor)
                        )
                    }

                    Column {
                        Text("Körperhöhe h = ${formatFloat(heightPyramid)} cm", style = MaterialTheme.typography.labelMedium)
                        Slider(
                            value = heightPyramid,
                            onValueChange = { heightPyramid = (it * 10).toInt() / 10.0f },
                            valueRange = 2.0f..12.0f,
                            steps = 99,
                            colors = SliderDefaults.colors(thumbColor = Subject.MATH.primaryColor, activeTrackColor = Subject.MATH.primaryColor)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CylinderVisualizerCanvas(radius: Float, height: Float) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1B2E))
    ) {
        Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            val width = size.width
            val canvasHeight = size.height
            val centerX = width / 2f
            val centerY = canvasHeight / 2f

            val rPx = radius * 12f
            val hPx = height * 10f

            // Zylinder Körper
            drawOval(Color(0xFFAB47BC), topLeft = Offset(centerX - rPx, centerY - (hPx / 2f) - 15f), size = Size(rPx * 2, 30f))
            drawRect(Color(0xFFAB47BC).copy(alpha = 0.5f), topLeft = Offset(centerX - rPx, centerY - (hPx / 2f)), size = Size(rPx * 2, hPx))
            drawOval(Color(0xFFAB47BC), topLeft = Offset(centerX - rPx, centerY + (hPx / 2f) - 15f), size = Size(rPx * 2, 30f))

            drawOval(Color.White, topLeft = Offset(centerX - rPx, centerY - (hPx / 2f) - 15f), size = Size(rPx * 2, 30f), style = Stroke(2f))
            drawOval(Color.White, topLeft = Offset(centerX - rPx, centerY + (hPx / 2f) - 15f), size = Size(rPx * 2, 30f), style = Stroke(2f))
            drawLine(Color.White, start = Offset(centerX - rPx, centerY - (hPx / 2f)), end = Offset(centerX - rPx, centerY + (hPx / 2f)), strokeWidth = 2f)
            drawLine(Color.White, start = Offset(centerX + rPx, centerY - (hPx / 2f)), end = Offset(centerX + rPx, centerY + (hPx / 2f)), strokeWidth = 2f)
        }
    }
}

@Composable
private fun PyramidVisualizerCanvas(baseA: Float, height: Float) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1B2E))
    ) {
        Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            val width = size.width
            val canvasHeight = size.height
            val centerX = width / 2f
            val centerY = canvasHeight / 2f

            val aPx = baseA * 15f
            val hPx = height * 10f

            val apexY = centerY - (hPx / 2f)
            val baseY = centerY + (hPx / 2f)

            // Grundfläche Raute (3D Perspektive)
            val p1 = Offset(centerX - aPx, baseY)
            val p2 = Offset(centerX, baseY + 20f)
            val p3 = Offset(centerX + aPx, baseY)
            val p4 = Offset(centerX, baseY - 20f)

            val apex = Offset(centerX, apexY)

            // Pyramiden Kanten
            drawLine(Color.White, p1, p2, strokeWidth = 2f)
            drawLine(Color.White, p2, p3, strokeWidth = 2f)
            drawLine(Color.White, p3, p4, strokeWidth = 2f)
            drawLine(Color.White, p4, p1, strokeWidth = 2f)

            drawLine(Color(0xFFFFD700), p1, apex, strokeWidth = 3f)
            drawLine(Color(0xFFFFD700), p2, apex, strokeWidth = 3f)
            drawLine(Color(0xFFFFD700), p3, apex, strokeWidth = 3f)
            drawLine(Color(0xFFFFD700), p4, apex, strokeWidth = 3f)
        }
    }
}

private fun formatFloat(value: Float): String {
    return ((value * 10).toInt() / 10.0).toString()
}

private fun formatDouble(value: Double): String {
    return ((value * 10).toInt() / 10.0).toString()
}
