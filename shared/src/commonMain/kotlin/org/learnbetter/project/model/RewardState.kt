package org.learnbetter.project.model

enum class RewardExchangeRate(
    val displayName: String,
    val xpPer5Minutes: Int
) {
    EASY("Leicht (25 XP = 5 Min)", 25),
    BALANCED("Ausgewogen (50 XP = 5 Min)", 50),
    STRICT("Anspruchsvoll (100 XP = 5 Min)", 100)
}

data class RewardState(
    val totalXp: Int = 0,
    val redeemedMinutes: Int = 0,
    val exchangeRate: RewardExchangeRate = RewardExchangeRate.BALANCED
) {
    val xpFor5Minutes: Int get() = exchangeRate.xpPer5Minutes
    val xpPerMinute: Float get() = xpFor5Minutes / 5.0f

    val totalEarnedMinutes: Int get() = (totalXp / xpPerMinute).toInt()
    val availableMinutes: Int get() = (totalEarnedMinutes - redeemedMinutes).coerceAtLeast(0)
    val xpForNext5Minutes: Int get() = xpFor5Minutes - (totalXp % xpFor5Minutes)
}
