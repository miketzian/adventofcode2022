package day15

import utils.*
import kotlin.math.absoluteValue
import kotlin.math.min
import kotlin.math.max

class Day15:BaseDay("Day15") {

    fun part1(input: Sequence<String>, row: Int): Int {
        val tun = Tunnel(input.map{ parse(it) })
        return tun.countFree(row)
    }

    fun part2(input: Sequence<String>, maxValues: Int): Long {
        // 4000000
        val tun = Tunnel(input.map{ parse(it) })
        val p = tun.check(maxValues)
        println(p)
        return (p.first.toLong() * 4000000L) + p.second.toLong()
    }

    private val eventRegex = "Sensor at x=(?<x>\\-?\\d+), y=(?<y>\\-?\\d+): closest beacon is at x=(?<bx>\\-?\\d+), y=(?<by>\\-?\\d+)".toRegex()

    fun parse(input: String): Event {
        // Sensor at x=2, y=18: closest beacon is at x=-2, y=15
        return eventRegex.matchEntire(input)!!.let {
            Event(Position(
                    it.groups["x"]!!.value.toInt(),
                    it.groups["y"]!!.value.toInt())
                ,Position(
                    it.groups["bx"]!!.value.toInt(),
                    it.groups["by"]!!.value.toInt()
                )
            )
        }
    }
}
data class Position(val first: Int, val second: Int) {
    // calculate the taxi distance from the other
    fun distance(other: Position): Int {
        val tdX = (first - other.first).absoluteValue
        val tdY = (second - other.second).absoluteValue
        return tdX + tdY
    }
}
data class Event(val pos: Position, val beacon: Position) {

    private val distance = pos.distance(beacon)
    val minX: Int = pos.first - distance
    val maxX: Int = pos.first + distance
    val minY: Int = pos.second - distance
    val maxY: Int = pos.second + distance

    fun isKnownFree(q: Position): Boolean {
        return if (q == pos || q == beacon) {
            // no
            false
        } else q.distance(pos) <= distance
    }

    fun isKnown(q: Position): Boolean {
        return q.distance(pos) <= distance
    }

    // get a sequence of positions that are not known to this sensor
    // just outside the beacon position's distance
    fun outside(): Sequence<Position> {
        return sequence {
            // up from left
            var x = pos.first - distance - 1
            var y = pos.second
            // up
            while (y < (pos.second + distance + 1)) {
                // goes up diagonally
                yield(Position(x, y))
                x++
                y++
            }
            // down right from top
            while (x < (pos.first + distance + 1)) {
                yield(Position(x, y))
                x++
                y--
            }
            // down from right
            while (y > (pos.second - distance - 1)) {
                yield(Position(x, y))
                x--
                y--
            }
            // up from bottom to the left
            while (x < (pos.first - distance - 1)) {
                yield(Position(x, y))
                x--
                y++
            }
            // left-middle was the starting point
            // no need to yield
        }
    }
}
enum class State(val value: Char){
    Empty('.'),
    Beacon('B'),
    Sensor('S')
}

class Tunnel(sensors: Sequence<Event>) {

    private val readings = sensors.toList()
    private var minX = readings[0].minX
    private var maxX = readings[0].maxX
    private var minY = readings[0].minY
    private var maxY = readings[0].maxY

    init {
        // initial min/max
        readings.asSequence().filterIndexed{ ix, _ -> ix != 0 }.forEach{
            minX = min(minX, it.minX)
            maxX = max(maxX, it.maxX)
            minY = min(minY, it.minY)
            maxY = max(maxY, it.minY)
        }
    }

    fun countFree(row: Int): Int {
        return (minX until maxX + 1).count {
            readings.any { evt ->
                evt.isKnownFree(Position(it, row))
            }
        }
    }

    fun check(size: Int): Position {
        return readings.firstNotNullOf {
            it.outside().filter {
                // is in range
                p ->
                    p.first in 0 until size && p.second in 0 until size
            }.firstOrNull {
                // position on outside that isn't known to any
                p ->
                    readings.all { r -> !r.isKnown(p) }
            }
        }
    }
}
