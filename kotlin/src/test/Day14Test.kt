

import kotlin.test.Test
import kotlin.test.assertEquals
import day14.*

internal class Day14Test {
    private val impl: Day14 = Day14()

    @Test
    fun testPartOne() {
        val testResult = impl.part1(impl.testInput())
        assertEquals(24, testResult)

        val actualResult = impl.part1(impl.input())
        // assertEquals(459, actualResult)
        println("Result was: $actualResult")
    }

    @Test
    fun testPartTwo() {
        val testResult = impl.part2(impl.testInput())
        assertEquals(93, testResult)

        val actualResult = impl.part2(impl.input())
        // assertEquals(779, actualResult)
        println("Result was: $actualResult")
    }
}

