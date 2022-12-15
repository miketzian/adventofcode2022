

import kotlin.test.Test
import kotlin.test.assertEquals
import day15.*

internal class Day15Test {
    private val impl: Day15 = Day15()

    @Test
    fun testPartOne() {
        val testResult = impl.part1(impl.testInput(), 10)
        assertEquals(26, testResult)

        val actualResult = impl.part1(impl.input(), 2000000)
        // assertEquals(459, actualResult)
        println("Result was: $actualResult")
    }

    @Test
    fun testPartTwo() {
        val testResult = impl.part2(impl.testInput(), 20)
        assertEquals(56000011, testResult)

        val actualResult = impl.part2(impl.input(), 4000000)
        println("Result was: $actualResult")
    }

    @Test
    fun testParse() {
        val event = impl.parse("Sensor at x=2, y=18: closest beacon is at x=-2, y=15")
        assertEquals(Position(2, 18), event.pos)
        assertEquals(Position(-2, 15), event.beacon)

        val event2 = impl.parse("Sensor at x=-2, y=-18: closest beacon is at x=-2, y=-15")
        assertEquals(Position(-2, -18), event2.pos)
        assertEquals(Position(-2, -15), event2.beacon)

    }
}

