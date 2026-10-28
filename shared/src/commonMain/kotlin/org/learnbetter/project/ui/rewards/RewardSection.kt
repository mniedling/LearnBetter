package org.learnbetter.project.ui.rewards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.learnbetter.project.model.RewardExchangeRate
import org.learnbetter.project.model.RewardState
import org.learnbetter.project.ui.theme.ChemistryColors

@Composable
fun RewardCard(
    rewardState: RewardState,
    onRateChanged: (RewardExchangeRate) -> Unit,
    onRedeemMinutes: (Int) -> Unit
) {
    var showRedeemDialog by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("🎮", fontSize = 28.sp)
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "PC-Zeit Belohnung",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                            IconButton(
                                onClick = { showSettingsDialog = true },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Text("⚙️", fontSize = 14.sp)
                            }
                        }
                        Text(
                            text = "${rewardState.exchangeRate.xpPer5Minutes} XP = 5 Min. PC-Zeit",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.8f)
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = ChemistryColors.AchievementGold
                ) {
                    Text(
                        text = "💻 ${rewardState.availableMinutes} Min",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }

            // Fortschritt bis zu den nächsten 5 Minuten
            val currentProgressInPackage = (rewardState.totalXp % rewardState.xpFor5Minutes) / rewardState.xpFor5Minutes.toFloat()
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Fortschritt zu den nächsten +5 Min:",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.85f)
                    )
                    Text(
                        text = "Noch ${rewardState.xpForNext5Minutes} XP",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }

                LinearProgressIndicator(
                    progress = { currentProgressInPackage },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(CircleShape),
                    color = ChemistryColors.AchievementGold,
                    trackColor = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.2f)
                )
            }

            Button(
                onClick = { showRedeemDialog = true },
                enabled = rewardState.availableMinutes >= 5,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = if (rewardState.availableMinutes >= 5) "💻 PC-Zeit jetzt einlösen!" else "Erspiele mind. 5 Min (${rewardState.xpFor5Minutes} XP)",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    if (showSettingsDialog) {
        RewardSettingsDialog(
            currentRate = rewardState.exchangeRate,
            onDismiss = { showSettingsDialog = false },
            onRateSelected = { rate ->
                onRateChanged(rate)
                showSettingsDialog = false
            }
        )
    }

    if (showRedeemDialog) {
        RedeemDialog(
            availableMinutes = rewardState.availableMinutes,
            xpPer5Minutes = rewardState.xpFor5Minutes,
            onDismiss = { showRedeemDialog = false },
            onConfirmRedeem = { minutes ->
                onRedeemMinutes(minutes)
                showRedeemDialog = false
            }
        )
    }
}

@Composable
private fun RewardSettingsDialog(
    currentRate: RewardExchangeRate,
    onDismiss: () -> Unit,
    onRateSelected: (RewardExchangeRate) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("⚙️ Belohnungskurs anpassen (Eltern)", fontWeight = FontWeight.Bold)
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Wähle aus, wie viele XP für 5 Minuten Computerzeit erforderlich sind:", style = MaterialTheme.typography.bodySmall)

                RewardExchangeRate.entries.forEach { rate ->
                    val isSelected = rate == currentRate
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                        onClick = { onRateSelected(rate) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = rate.displayName,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Schließen")
            }
        }
    )
}

@Composable
private fun RedeemDialog(
    availableMinutes: Int,
    xpPer5Minutes: Int,
    onDismiss: () -> Unit,
    onConfirmRedeem: (Int) -> Unit
) {
    var selectedMinutes by remember { mutableIntStateOf(if (availableMinutes >= 15) 15 else 5) }
    var redeemSuccess by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (redeemSuccess) "🎉 Erfolgreich eingelöst!" else "💻 Computerzeit einlösen",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            if (redeemSuccess) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("⏰", fontSize = 48.sp)
                    Text(
                        text = "Du hast $selectedMinutes Minuten Computerzeit eingelöst!",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Text(
                        text = "Zeige diesen Bildschirm deinen Eltern, um dir deine PC-Zeit abzuholen!",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Wähle aus, wie viele Minuten du jetzt einlösen möchtest:")

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(5, 15, 30).forEach { mins ->
                            val canAfford = availableMinutes >= mins
                            val isSelected = selectedMinutes == mins

                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(vertical = 4.dp),
                                shape = RoundedCornerShape(12.dp),
                                color = when {
                                    isSelected -> MaterialTheme.colorScheme.primary
                                    canAfford -> MaterialTheme.colorScheme.surfaceVariant
                                    else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                                },
                                onClick = {
                                    if (canAfford) selectedMinutes = mins
                                }
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "$mins Min",
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "${mins / 5 * xpPer5Minutes} XP",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            if (redeemSuccess) {
                Button(
                    onClick = {
                        onConfirmRedeem(selectedMinutes)
                    }
                ) {
                    Text("Fertig")
                }
            } else {
                Button(
                    onClick = {
                        redeemSuccess = true
                    },
                    enabled = availableMinutes >= selectedMinutes
                ) {
                    Text("Jetzt $selectedMinutes Min einlösen")
                }
            }
        },
        dismissButton = {
            if (!redeemSuccess) {
                TextButton(onClick = onDismiss) {
                    Text("Abbrechen")
                }
            }
        }
    )
}
