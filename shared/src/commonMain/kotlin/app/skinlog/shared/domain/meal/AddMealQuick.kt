package app.skinlog.shared.domain.meal

interface AddMealQuick {
    suspend fun execute(cmd: AddMealQuickCommand): AddMealQuickResult
}