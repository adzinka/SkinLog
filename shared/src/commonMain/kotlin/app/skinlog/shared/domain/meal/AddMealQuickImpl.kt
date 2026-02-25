package app.skinlog.shared.domain.meal

class AddMealQuickImpl(
    private val mealRepo: MealRepo,
    private val nowProvider: () -> Long

) : AddMealQuick {

    override suspend fun execute(cmd: AddMealQuickCommand): AddMealQuickResult {
        if (cmd.exposures.isEmpty()) {
            return AddMealQuickResult.ValidationError(
                ValidationReason.NO_EXPOSURES_SELECTED
            )
        }

        if (!isValidTimestamp(cmd.timestamp)) {
            return AddMealQuickResult.ValidationError(
                ValidationReason.INVALID_TIMESTAMP
            )
        }

        val id = mealRepo.addQuickMeal(cmd)
        return AddMealQuickResult.Success(id)
    }

    private fun isValidTimestamp(ts: Long): Boolean {
        if (ts <= 0L) return false

        val now = nowProvider()
        val maxFutureMs = 24L * 60L * 60L * 1000L
        return ts <= now + maxFutureMs
    }
}