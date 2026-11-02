package org.learnbetter.project.ui.driving

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.learnbetter.project.model.Subject

@Composable
fun DrivingFormulaCalculatorScreen(
    onXpEarned: (Int) -> Unit = {},
    onBackToHub: (() -> Unit)? = null
) {
    var speedKmH by remember { mutableFloatStateOf(50.0f) }

    // Formeln
    val vTenths = speedKmH / 10.0f
    val reactionDistanceMeters = vTenths * 3.0f
    val brakingDistanceMeters = vTenths * vTenths
    val stoppingDistanceMeters = reactionDistanceMeters + brakingDistanceMeters
    val emergencyBrakingMeters = brakingDistanceMeters / 2.0f

    val animatedReaction by animateFloatAsState(targetValue = reactionDistanceMeters, animationSpec = tween(durationMillis = 500))
    val animatedBraking by animateFloatAsState(targetValue = brakingDistanceMeters, animationSpec = tween(durationMillis = 500))

    var hasTested by remember { mutableStateOf(false) }

    LaunchedEffect(speedKmH) {
        if (!hasTested && speedKmH != 50f) {
            hasTested = true
            onXpEarned(5)
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
            text = "🛵 Anhalteweg & Bremsweg Rechner",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Subject.DRIVING_SCHOOL.primaryColor,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Bewege den Tacho-Schieberegler für Tempo v (in km/h) und beobachte die Faustformeln für den Führerschein!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        // 1. Visueller Bremsweg Canvas
        StoppingDistanceCanvas(
            reactionMeters = animatedReaction,
            brakingMeters = animatedBraking
        )

        // 2. Tacho Slider Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Geschwindigkeit v:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Subject.DRIVING_SCHOOL.primaryColor
                    ) {
                        Text(
                            text = "${speedKmH.toInt()} km/h",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }

                Slider(
                    value = speedKmH,
                    onValueChange = { speedKmH = (it / 5).toInt() * 5.0f },
                    valueRange = 10.0f..100.0f,
                    steps = 17,
                    colors = SliderDefaults.colors(
                        thumbColor = Subject.DRIVING_SCHOOL.primaryColor,
                        activeTrackColor = Subject.DRIVING_SCHOOL.primaryColor
                    )
                )
            }
        }

        // 3. Formel Erklärungs-Karten
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("📊 Faustformeln der Fahrschule:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("⚡ Reaktionsweg (v ÷ 10) × 3:", style = MaterialTheme.typography.bodyMedium)
                    Text("${formatFloat(reactionDistanceMeters)} Meter", fontWeight = FontWeight.Bold, color = Color(0xFFE65100))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("🛑 Bremsweg (v ÷ 10) × (v ÷ 10):", style = MaterialTheme.typography.bodyMedium)
                    Text("${formatFloat(brakingDistanceMeters)} Meter", fontWeight = FontWeight.Bold, color = Color(0xFFC62828))
                }

                HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("🚗 Anhalteweg = Reaktion + Bremse:", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Text("${formatFloat(stoppingDistanceMeters)} Meter", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = Subject.DRIVING_SCHOOL.primaryColor)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("⚠️ Gefahrenbremsung (Bremsweg ÷ 2):", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                    Text("${formatFloat(emergencyBrakingMeters)} Meter", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun StoppingDistanceCanvas(
    reactionMeters: Float,
    brakingMeters: Float
) {
    val totalMeters = reactionMeters + brakingMeters

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
    ) {
        Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            val width = size.width
            val height = size.height
            val startX = 20f
            val roadY = height * 0.65f
            val maxMetersScale = 130f // Maximum scale for 130 meters

            val pixelsPerMeter = (width - 40f) / maxMetersScale

            // Straße
            drawRect(Color(0xFF334155), topLeft = Offset(10f, roadY - 15f), size = Size(width - 20f, 30f))

            // Reaktionsweg (Orange)
            val rxWidth = reactionMeters * pixelsPerMeter
            drawRect(Color(0xFFF97316), topLeft = Offset(startX, roadY - 10f), size = Size(rxWidth, 20f))

            // Bremsweg (Rot)
            val brWidth = brakingMeters * pixelsPerMeter
            drawRect(Color(0xFFEF4444), topLeft = Offset(startX + rxWidth, roadY - 10f), size = Size(brWidth, 20f))

            // Auto Marker am Ende
            val carX = startX + (totalMeters * pixelsPerMeter)
            drawCircle(Color(0xFFFFD700), radius = 12f, center = Offset(carX, roadY))

            // Beschriftung Meter
            drawRect(Color.White.copy(alpha = 0.8f), topLeft = Offset(startX, roadY + 25f), size = Size(totalMeters * pixelsPerMeter, 3f))
        }
    }
}

private fun formatFloat(value: Float): String {
    return ((value * 10).toInt() / 10.0).toString()
}
