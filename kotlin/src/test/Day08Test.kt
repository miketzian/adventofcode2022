

import kotlin.test.Test
import kotlin.test.assertEquals
import day08.*

internal class Day08Test {
    private val impl: Day08 = Day08()

    @Test
    fun testPartOne() {
        val testResult = impl.part1(impl.testInput())
        println("Test Result was: $testResult")
        assertEquals(21, testResult, )

        val actualResult = impl.part1(impl.input())
        assertEquals(actualResult, 1679)
        println("Result was: $actualResult")
    }

    @Test
    fun testPartTwo() {
        val testResult = impl.part2(impl.testInput())
        assertEquals(8, testResult)

        val actualResult = impl.part2(impl.input())
        // assertEquals(actualResult, 779)
        println("Result was: $actualResult")
    }
}

