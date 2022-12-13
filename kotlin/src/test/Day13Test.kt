

import kotlin.test.Test
import kotlin.test.assertEquals
import day13.*

internal class Day13Test {
    private val impl: Day13 = Day13()

    @Test
    fun testPartOne() {
        val testResult = impl.part1(impl.testInput())
        assertEquals(13, testResult)


        val actualResult = impl.part1(impl.input())
        // assertEquals(459, actualResult)
        println("Result was: $actualResult")
    }

    @Test
    fun testPartTwo() {
        val testResult = impl.part2(impl.testInput())
        assertEquals(140, testResult)

        val actualResult = impl.part2(impl.input())
        // assertEquals(779, actualResult)
        println("Result was: $actualResult")
    }

}


