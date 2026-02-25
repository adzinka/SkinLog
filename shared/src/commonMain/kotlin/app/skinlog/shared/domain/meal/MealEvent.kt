package app.skinlog.shared.domain.meal

data class MealEvent(
    val id: Long,
    val dayDate: String,
    val timestamp: Long,
    val mealType: MealType,
    val note: String?,
    val exposures: List<MealExposure>
)

data class MealExposure(
    val key: ExposureKey,
    val intensity: Intensity
)