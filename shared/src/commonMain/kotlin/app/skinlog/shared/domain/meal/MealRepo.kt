package app.skinlog.shared.domain.meal

interface MealRepo {
    suspend fun addQuickMeal(cmd: AddMealQuickCommand): Long
    suspend fun listMealsForDay(dayDate: String): List<MealEvent>
}