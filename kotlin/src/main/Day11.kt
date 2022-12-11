package day11

import utils.*

class Day11():BaseDay("Day11") {

    fun part1(input: Sequence<String>): Int {

        val mm = monkeyMap(input)
        for (round in 0 until 20) {
            for (monkey in mm.values) {
                monkey.takeTurn(mm){
                    it / 3L
                }
            }
        }

        // println(mm)
        // we want the top two records
        val top2 = mm.values.map{ it.inspections }.sortedDescending().take(2)
        return top2.first() * top2.last()
    }

    fun part2(input: Sequence<String>): Long {

        // Monkey# -> Monkey()
        val mm = monkeyMap(input)

        // use the least common multiple of all the checks
        val lcm: Long = mm.values.map{ it.divBy }.fold(1L){ acc, i -> acc * i }

        for (round in 1 until 10001) {
            // this actually relies on the internal order of the map
            // perhaps this should be iterating through monkey#
            // but it works as is
            mm.values.forEach{
                it.takeTurn(mm){
                    value ->
                        value % lcm
                }
            }
        }

        // still want the top two.
        val top2 = mm.values.map{ it.inspections }.sortedDescending().take(2)
        return top2.first().toLong() * top2.last().toLong()
    }

    private fun monkeyMap(input: Sequence<String>): Map<Int, Monkey> {
        return buildMap<Int, Monkey> {

            // in retrospect, could have split by \n\n
            val it = input.iterator()
            while (it.hasNext()) {
                // Monkey 2:
                var line: String = it.next()
                assert(line.startsWith("Monkey "))
                val monkeyId = line.substring(7, line.indexOf(":")).toInt()

                //   Starting items: 79, 60, 97
                line = it.next()
                assert(line.contains("Starting items: "))
                val monkeyItems = line.split("Starting items: ")[1].split(", ")
                    .map { it.toLong() }.toMutableList()

                //   Operation: new = old * old
                line = it.next()
                assert(line.contains("Operation: "))
                val operation = line.split("Operation: ")[1]

                //   Test: divisible by 13
                line = it.next()
                assert(line.contains("Test: divisible by "))
                val divBy = line.split(" ").last().toLong()

                //   If true: throw to monkey 1
                line = it.next()
                assert(line.contains("If true"))
                val ifTrue = line.split(" ").last().toInt()

                //            If false: throw to monkey 3
                line = it.next()
                assert(line.contains("If false"))
                val ifFalse = line.split(" ").last().toInt()

                // buildMap
                put(
                    monkeyId, Monkey(
                        monkeyItems, operation, divBy, ifTrue, ifFalse
                    )
                )
                // println("found monkey=${monkeyId}")

                if (it.hasNext()) {
                    assert(it.next() == "")
                }
            }
        }
    }
}
data class Monkey(val items: MutableList<Long>, val op: String, val divBy: Long, val ifTrue: Int, val ifFalse: Int) {

    var inspections: Int = 0

    // apparenly nashorn removed in jdk17, how rude
    private fun eval(old: Long): Long {
        val parts: List<String> = op.split(" = ").last().split(" ")
        val p1: Long = when(parts.first()) {
            "old" -> old
            else -> parts.first().toLong()
        }
        val p2: Long = when(parts.last()) {
            "old" -> old
            else -> parts.last().toLong()
        }
        return when(parts[1]) {
            "+" -> p1 + p2
            "-" -> p1 - p2
            "*" -> p1 * p2
            "/" -> p1 / p2
            else -> throw UnsupportedOperationException(parts[1])
        }
    }

    fun takeTurn(mm: Map<Int, Monkey>, worryFunc: (new: Long) -> Long) {
        // items = worryLevel (for that item)
        items.forEach{
            inspections ++
            // calculate the operation result
            val new = eval(it)
            // calculate the reduction due to relief
            val newRounded = worryFunc(new)
            if (newRounded % divBy == 0L) {
                // println("throw=${newRounded} to ${ifTrue}")
                mm[ifTrue]!!.items.add(newRounded)
            } else {
                // println("throw=${newRounded} to ${ifFalse}")
                mm[ifFalse]!!.items.add(newRounded)
            }
        }
        // monkey always throws everything
        items.clear()
    }
}
