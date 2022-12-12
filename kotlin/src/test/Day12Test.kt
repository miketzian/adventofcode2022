import kotlin.test.Test
import kotlin.test.assertEquals
import day12.*

internal class Day12Test {
    private val impl: Day12 = Day12()

    @Test
    fun testPartOne() {
        val testResult = impl.part1(impl.testInput())
        assertEquals(31, testResult)

        val actualResult = impl.part1(impl.input())
        println("Result was: $actualResult")
    }

    @Test
    fun testPartTwo() {
        val testResult = impl.part2(impl.testInput())
        assertEquals(29, testResult)

        val actualResult = impl.part2(impl.input())
        println("Result was: $actualResult")
    }
}

