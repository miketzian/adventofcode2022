

import kotlin.test.Test
import kotlin.test.assertEquals
import day5.*

internal class Day05Test {
    private val impl: Day05 = Day05()

    @Test
    fun testPartOne() {
        val testResult = impl.part1(
            impl.testInput()
        )
        assertEquals(testResult, "CMZ")

        val actualResult = impl.part1(impl.input())
        assertEquals(actualResult, "TGWSMRBPN")
        println("Result was: " + actualResult)
    }

    @Test
    fun testPartTwo() {
        val testResult = impl.part2(impl.testInput())
        assertEquals(testResult, "MCD")

        val actualResult = impl.part2(impl.input())
        assertEquals(actualResult, "TZLTLWRNF")
        println("Result was: " + actualResult)
    }
}

