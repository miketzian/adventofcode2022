package day14

import utils.*

class Day14:BaseDay("Day14") {

    fun part1(input: Sequence<String>): Int {
        val t = parse(input)
        // t.render()
        val maxY = t.maxY()
        var pieces = 0
        while(pieces < 2048) {
            pieces++
            val p = t.sand(maxY)
            if (p.second == maxY) {
                t.add(p.first, p.second, State.LAST)
                break
            }
        }
        // t.render()
        // the last one is falling
        return pieces - 1
    }

    fun part2(input: Sequence<String>): Int {
        val t = parse(input)
        // t.render()
        val maxY = t.addFloor()
        // t.render()
        var p = t.sand(maxY)
        while (p != t.entry) {
            t.add(p.first, p.second, State.SAND)
            p = t.sand(maxY)
        }
        // t.render()
        // the last one is falling
        return t.count()
    }

    private fun parse(input: Sequence<String>): Tunnel {
        val t = Tunnel()
        input.forEach {
            line ->
            val items = line.split(" -> ").map{
                posStr ->
                    posStr.split(",").map{
                        it.toInt()
                    }.toList()
                }.toList()
            // [[1,2],[2,3],...]

            // load the cracks.
            items.forEachIndexed{
                i, value ->
                    if (i > 0) {
                        val prevValue = items[i-1]
                        if (prevValue[0] == value[0]) {
                            // the different axis is y
                            val range = if (prevValue[1] > value[1]) {
                                // go from v -> vp
                                value[1] until prevValue[1] + 1 // inclusive
                            } else {
                                prevValue[1] until value[1] + 1
                            }
                            for (y in range) {
                                t.add(value[0], y, State.CRACK)
                            }
                        } else {
                            assert(prevValue[1] == value[1])
                            // the different axis is x
                            val range = if (prevValue[0] > value[0]) {
                                // go from v -> vp
                                value[0] until prevValue[0] + 1 // inclusive
                            } else {
                                prevValue[0] until value[0] + 1
                            }
                            for (x in range) {
                                t.add(x, value[1], State.CRACK)
                            }
                        }
                    }
            }
        }
        return t
    }
}

enum class State(val value: Char) {
    EMPTY('.'),
    CRACK('#'),
    ENTRY('+'),
    SAND('o'),
    LAST('X')
}
typealias Position = Pair<Int, Int>
class Tunnel {
    val entry = Position(500, 0)
    private val data: MutableMap<Position, State> = mutableMapOf(Pair(entry, State.ENTRY))
    private var maxX = entry.first
    private var maxY = entry.second
    private var minX = entry.first
    private var minY = entry.second

    fun maxY(): Int {
        return maxY
    }

    fun add(x: Int, y: Int, state: State) {
        data[Position(x, y)] = state
        if (maxX < x)
            maxX = x
        if (minX > x)
            minX = x
        if (minY > y)
            minY = y
        if (maxY < y)
            maxY = y
    }

    fun addFloor(): Int {

        val yFloor = maxY + 2
        val ySide = (maxY - minY) + 5
        // println("ySide = ${ySide}")

        for (x in listOf(minX, 500-ySide).min() until listOf(maxX + 1, 500+ySide).max()) {
            add(x, yFloor, State.CRACK)
        }
        return yFloor
    }

    fun count(): Int {
        return data.filterValues {
            it == State.SAND
        }.count()
    }

    // produce one unit of sand, continue until it comes to rest
    fun sand(maxY: Int): Position {
        var x = 500
        var y = 0

        fun move(): Boolean {
            if (y == maxY) {
                // we have our winner
                return false
            }
            if (!data.contains(Position(x, y+1))) {
                // drop
                y++
                return true
            } else if (!data.contains(Position(x-1, y+1))) {
                // diagonal left
                x--
                y++
                return true
            } else if (!data.contains(Position(x+1, y+1))) {
                // diagonal right
                x++
                y++
                return true
            } else {
                // no more moves
                return false
            }
        }
        while(move()) {
            // do nothing
            // render()
        }
        val pos = Position(x, y)
        assert(data[pos] == null || data[pos] == State.ENTRY)
        data[pos] = State.SAND
        return pos
    }

    @Suppress("unused")
    fun render() {
        println("$minX < x < $maxX -> $minY < y < $maxY")
        for (j in minY until maxY + 1) {
            inner@
            for (i in minX until maxX + 1) {
                print((data[Position(i, j)]?.value ?: State.EMPTY.value))
            }
            println()
        }
    }
}