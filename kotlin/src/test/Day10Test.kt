

import kotlin.test.Test
import kotlin.test.assertEquals
import day10.*

internal class Day10Test {
    private val impl: Day10 = Day10()

    @Test
    fun testPartOne() {
        val testResult = impl.part1(impl.testInput())
        assertEquals(13140, testResult)

        val actualResult = impl.part1(impl.input())
        // assertEquals(actualResult, 459)
        println("Result was: $actualResult")
    }

    @Test
    fun testPartTwo() {
        val testResult = impl.part2(impl.testInput())
        println(testResult)

        val expected = """##..##..##..##..##..##..##..##..##..##..
###...###...###...###...###...###...###.
####....####....####....####....####....
#####.....#####.....#####.....#####.....
######......######......######......####
#######.......#######.......#######.....""".split("\n")

        val testResultLines = testResult.split("\n")
        for (i in 0 until 6) {
            println(expected[i])
            println(testResultLines[i])
            assertEquals(expected[i], testResultLines[i])
        }

        val actualResult = impl.part2(impl.input())
        println("Result was: \n$actualResult")
    }
}

