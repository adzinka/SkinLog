package app.skinlog.shared

import app.skinlog.shared.domain.meal.AddMealQuickCommand
import app.skinlog.shared.domain.meal.AddMealQuickImpl
import app.skinlog.shared.domain.meal.AddMealQuickResult
import app.skinlog.shared.domain.meal.ExposureKey
import app.skinlog.shared.domain.meal.Intensity
import app.skinlog.shared.domain.meal.MealEvent
import app.skinlog.shared.domain.meal.MealExposure
import app.skinlog.shared.domain.meal.MealRepo
import app.skinlog.shared.domain.meal.MealType
import app.skinlog.shared.domain.meal.ValidationReason
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking

class AddMealQuickImplTest {

    @Test
    fun `happy path returns Success and calls repo`() = runBlocking {
        val repo = FakeMealRepo()
        val now = 1_000_000L
        val useCase = AddMealQuickImpl(repo, nowProvider = { now })

        val cmd = AddMealQuickCommand(
            timestamp = now,
            mealType = MealType.LUNCH,
            note = "Test",
            exposures = listOf(
                MealExposure(ExposureKey.DAIRY, Intensity.M)
            )
        )

        val result = useCase.execute(cmd)

        assertTrue(result is AddMealQuickResult.Success)
        assertEquals(cmd, repo.lastCmd)
    }

    @Test
    fun `empty exposures returns ValidationError`() = runBlocking {
        val repo = FakeMealRepo()
        val now = 1_000_000L
        val useCase = AddMealQuickImpl(repo, nowProvider = { now })

        val cmd = AddMealQuickCommand(
            timestamp = now,
            mealType = MealType.LUNCH,
            note = null,
            exposures = emptyList()
        )

        val result = useCase.execute(cmd)

        assertTrue(result is AddMealQuickResult.ValidationError)
        val err = result as AddMealQuickResult.ValidationError
        assertEquals(ValidationReason.NO_EXPOSURES_SELECTED, err.reason)
        assertEquals(null, repo.lastCmd)
    }
}

private class FakeMealRepo : MealRepo {
    var lastCmd: AddMealQuickCommand? = null
    private var nextId = 1L

    override suspend fun addQuickMeal(cmd: AddMealQuickCommand): Long {
        lastCmd = cmd
        return nextId++
    }

    override suspend fun listMealsForDay(dayDate: String): List<MealEvent> = emptyList()
}