package org.learnbetter.project.model

data class RewardState(
    val totalXp: Int = 250,
    val redeemedMinutes: Int = 0
) {
    // 25 XP = 5 Minuten PC-Zeit (5 XP = 1 Minute)
    val totalEarnedMinutes: Int get() = totalXp / 5
    val availableMinutes: Int get() = (totalEarnedMinutes - redeemedMinutes).coerceAtLeast(0)
    val xpForNext5Minutes: Int get() = 25 - (totalXp % 25)
}
