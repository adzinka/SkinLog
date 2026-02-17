package app.skinlog.shared.domain.meal

sealed class AddMealQuickResult {
    data class Success(val eventId: Long) : AddMealQuickResult()

    data class ValidationError(val reason: ValidationReason) : AddMealQuickResult()

    data class Failure(val reason: FailureReason) : AddMealQuickResult()
}

enum class ValidationReason {
    NO_EXPOSURES_SELECTED,
    INVALID_TIMESTAMP
}

enum class FailureReason {
    PERSISTENCE_ERROR
}