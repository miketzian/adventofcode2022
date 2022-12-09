

import kotlin.test.Test
import kotlin.test.assertEquals
import day09.*

internal class Day09Test {
    private val impl: Day09 = Day09()

    @Test
    fun testPartOne() {
        val testResult = impl.part1(impl.testInput())
        println("TestResult was: $testResult")
        assertEquals(testResult, 13)

        val actualResult = impl.part1(impl.input())
        // assertEquals(actualResult, 459)
        println("Result was: $actualResult")
    }

    @Test
    fun testPartTwo() {
        val testResult = impl.part2(impl.testInput())
        println("TestResult was: $testResult")
        assertEquals(testResult, 1)

        val testResult2 = impl.part2("""R 5
U 8
L 8
D 3
R 17
D 10
L 25
U 20""".split("\n").asSequence())
        println("TestResult2 was: $testResult2")
        assertEquals(testResult2, 36)

        val actualResult = impl.part2(impl.input())
        // assertEquals(actualResult, 779)
        println("Result was: $actualResult")
    }
}

