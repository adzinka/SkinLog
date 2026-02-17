package app.skinlog.shared.domain.meal

data class AddMealQuickCommand(
    val timestamp: Long,
    val mealType: MealType,
    val note: String?,
    val exposures: Map<ExposureKey, Intensity>
)