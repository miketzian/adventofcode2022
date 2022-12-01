
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day1Test {
    private val impl: Day1 = Day1()

    @Test
    fun testPartOne() {
        val testResult = impl.part1(impl.testInput)
        assertEquals(testResult, 24000)

        val actualResult = impl.part1(impl.input)
        println("Result was: " + actualResult)
    }

    
    @Test
    fun testPartTwo() {
        val testResult = impl.part2(impl.testInput)
        assertEquals(testResult, 45000)

        val actualResult = impl.part2(impl.input)
        println("Result was: " + actualResult)
    }
}