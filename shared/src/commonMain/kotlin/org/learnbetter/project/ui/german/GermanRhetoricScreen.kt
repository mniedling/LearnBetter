package org.learnbetter.project.ui.german

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.learnbetter.project.model.Subject
import org.learnbetter.project.ui.theme.ChemistryColors

data class RhetoricTask(
    val id: Int,
    val exampleText: String,
    val options: List<String>,
    val correctIndex: Int,
    val definition: String
)

@Composable
fun GermanRhetoricScreen(
    onXpEarned: (Int) -> Unit = {},
    onBackToHub: (() -> Unit)? = null
) {
    val tasks = listOf(
        RhetoricTask(
            id = 1,
            exampleText = "»Milch macht müde Männer munter.«",
            options = listOf("Metapher", "Alliteration", "Anapher", "Personifikation"),
            correctIndex = 1,
            definition = "Alliteration: Wiederholung des gleichen Anflauts bei aufeinanderfolgenden Wörtern (M... m... m... m...)."
        ),
        RhetoricTask(
            id = 2,
            exampleText = "»Rabenmutter« / »Schneckenpost«",
            options = listOf("Metapher", "Hyperbel", "Alliteration", "Inversion"),
            correctIndex = 0,
            definition = "Metapher: Bildhafter Ausdruck, bei dem ein Wort im übertragenen Sinne verwendet wird."
        ),
        RhetoricTask(
            id = 3,
            exampleText = "»Die Sonne lacht uns entgegen.«",
            options = listOf("Anapher", "Personifikation", "Hyperbel", "Metapher"),
            correctIndex = 1,
            definition = "Personifikation: Vermenschlichung von leblosen Dingen oder Naturerscheinungen."
        )
    )

    var currentTaskIndex by remember { mutableIntStateOf(0) }
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }

    val currentTask = tasks[currentTaskIndex]

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
            text = "📖 Stilmittel & Rhetorik-Baukasten",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Subject.GERMAN.primaryColor,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Erkenne die sprachlichen Stilmittel aus Gedichten und Sachtexten!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        // Textbeispiel Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Beispiel-Text:", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
                Text(
                    text = currentTask.exampleText,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        // Optionen
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            currentTask.options.forEachIndexed { index, optionName ->
                val isSelected = selectedOptionIndex == index
                val isCorrect = index == currentTask.correctIndex
                val hasAnswered = selectedOptionIndex != null

                val backgroundColor = when {
                    hasAnswered && isCorrect -> ChemistryColors.NeutralGreen.copy(alpha = 0.25f)
                    hasAnswered && isSelected -> MaterialTheme.colorScheme.errorContainer
                    else -> MaterialTheme.colorScheme.surface
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = !hasAnswered) {
                            selectedOptionIndex = index
                            if (index == currentTask.correctIndex) {
                                onXpEarned(5)
                            }
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = backgroundColor)
                ) {
                    Text(
                        text = optionName,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        // Erklärung & Nächste Aufgabe
        if (selectedOptionIndex != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("💡 Erklärung:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = Subject.GERMAN.primaryColor)
                    Text(currentTask.definition, style = MaterialTheme.typography.bodySmall)
                }
            }

            if (currentTaskIndex + 1 < tasks.size) {
                Button(
                    onClick = {
                        currentTaskIndex++
                        selectedOptionIndex = null
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Subject.GERMAN.primaryColor)
                ) {
                    Text("Nächste Aufgabe ➔")
                }
            }
        }
    }
}
