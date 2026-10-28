package org.learnbetter.project.model

data class ExperimentState(
    val selectedAcid: Acid = Acid.HYDROCHLORIC,
    val selectedMetal: Metal = Metal.MAGNESIUM,
    val acidConcentrationPercent: Int = 10,
    val isReacting: Boolean = false,
    val reactionProgress: Float = 0.0f,
    val temperatureCelsius: Float = 20.0f,
    val hydrogenGasAmount: Float = 0.0f,
    val popTestDone: Boolean = false,
    val popTestSuccess: Boolean = false
)
