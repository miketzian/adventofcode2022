

import kotlin.test.Test
import kotlin.test.assertEquals
import day07.*

internal class Day07Test {
    private val impl: Day07 = Day07()

    @Test
    fun testPartOne() {
        val testResult = impl.part1(impl.testInput())
        assertEquals(95437, testResult, )

        val actualResult = impl.part1(impl.input())
        // assertEquals(actualResult, 459)
        println("Result was: $actualResult")
    }

    @Test
    fun testPartTwo() {
        val testResult = impl.part2(impl.testInput())
        assertEquals(testResult, 24933642)

        val actualResult = impl.part2(impl.input())
        // assertEquals(actualResult, 779)
        println("Result was: $actualResult")
    }
}

