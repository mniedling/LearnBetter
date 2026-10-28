package org.learnbetter.project.model

data class ExperimentState(
    val selectedMetal: Metal = Metal.MAGNESIUM,
    val acidConcentrationPercent: Int = 10, // 5% bis 20%
    val isReacting: Boolean = false,
    val reactionProgress: Float = 0.0f, // 0.0 bis 1.0
    val temperatureCelsius: Float = 20.0f,
    val hydrogenGasAmount: Float = 0.0f, // Gesammeltes H2 in ml
    val popTestDone: Boolean = false,
    val popTestSuccess: Boolean = false
)
