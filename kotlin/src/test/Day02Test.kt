
import kotlin.test.Test
import kotlin.test.assertEquals
import day2.Day02

internal class Day2Test {
    private val impl: Day02 = Day02()

    @Test
    fun testPartOne() {
        val testResult = impl.part1(impl.testInput)
        assertEquals(testResult, 15)

        val actualResult = impl.part1(impl.input)
        assertEquals(actualResult, 14531)
        println("Result was: " + actualResult)
    }

        
    @Test
    fun testPartTwo() {
        val testResult = impl.part2(impl.testInput)
        assertEquals(testResult, 12)

        val actualResult = impl.part2(impl.input)
        assertEquals(actualResult, 11258)
        println("Result was: " + actualResult)
    }
}