
import kotlin.test.Test
import kotlin.test.assertEquals
import day4.*
import utils.readSequence

internal class Day04Test {
    private val impl: Day04 = Day04()

    @Test
    fun testPartOne() {
        val testResult = impl.part1(readSequence(impl.testName))
        assertEquals(testResult, 2)

        val actualResult = impl.part1(readSequence(impl.inputName))
        assertEquals(actualResult, 459)
        println("Result was: " + actualResult)
    }

    @Test
    fun testPartTwo() {
        val testResult = impl.part2(impl.testInput)
        assertEquals(testResult, 4)

        val actualResult = impl.part2(impl.input)
        assertEquals(actualResult, 779)
        println("Result was: " + actualResult)
    }
}