

import kotlin.test.Test
import kotlin.test.assertEquals
import day11.*

internal class Day11Test {
    private val impl: Day11 = Day11()

    @Test
    fun testPartOne() {
        val testResult = impl.part1(impl.testInput())
        assertEquals(10605, testResult)

        val actualResult = impl.part1(impl.input())
        // assertEquals(459, actualResult)
        println("Result was: $actualResult")
    }

    @Test
    fun testPartTwo() {
        val testResult: Long = impl.part2(impl.testInput())
        assertEquals<Long>(2713310158, testResult)

        val actualResult = impl.part2(impl.input())
        // assertEquals(779, actualResult)
        println("Result was: $actualResult")
    }
}

