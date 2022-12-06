

import kotlin.test.Test
import kotlin.test.assertEquals
import day06.*

internal class Day06Test {
    private val impl: Day06 = Day06()

    @Test
    fun testPartOne() {
        var testResult = impl.part1("mjqjpqmgbljsphdztnvjfqwrcgsmlb")
        assertEquals(7, testResult)

        testResult = impl.part1("bvwbjplbgvbhsrlpgdmjqwftvncz")
        assertEquals(5, testResult)

        testResult = impl.part1("nppdvjthqldpwncqszvftbrmjlhg")
        assertEquals(6, testResult)

        testResult = impl.part1("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg")
        assertEquals(10, testResult)

        testResult = impl.part1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")
        assertEquals(11, testResult)

        for (line in impl.input()) {
            val actualResult = impl.part1(line)
            // assertEquals(actualResult, 459)
            println("Result was: " + actualResult)
        }
    }

    @Test
    fun testPartTwo() {
        var testResult = impl.part2("mjqjpqmgbljsphdztnvjfqwrcgsmlb")
        assertEquals(19, testResult)

        testResult = impl.part2("bvwbjplbgvbhsrlpgdmjqwftvncz")
        assertEquals(23, testResult)

        testResult = impl.part2("nppdvjthqldpwncqszvftbrmjlhg")
        assertEquals(23, testResult)

        testResult = impl.part2("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg")
        assertEquals(29, testResult)

        testResult = impl.part2("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")
        assertEquals(26, testResult)

        for (line in impl.input()) {
            val actualResult = impl.part2(line)
            // assertEquals(actualResult, 459)
            println("Result was: " + actualResult)
        }
    }
}

