
package day4
import utils.readInput

class Day04() {

    fun parse(input: String): Pair<Assignment, Assignment> {

        var from = 0
        var to = input.indexOf('-')

        var left = input.substring(from, to).toInt()
        from = to + 1
        to = input.indexOf(',', from)
        var right = input.substring(from, to).toInt()

        val first = Assignment(left, right)

        from = to + 1
        to = input.indexOf('-', from)
        left = input.substring(from, to).toInt()
        from = to + 1
        to = input.length
        right = input.substring(from, to).toInt()

        val second = Assignment(left, right)


        return Pair(first, second)
    }

    fun part1(seq: Sequence<String>): Int {
        // count when all sides overlap
        var score = 0

        for (pair in seq.map({ a -> parse(a) })) {
            // print(pair)
            if (pair.first.overlaps(pair.second) ||
                pair.second.overlaps(pair.first)) {
                // println(" -- match")
                score++
            } else {
                // println(" miss ")
            }
        }

        return score
    }

    fun part2(input: List<String>): Int {
        // count when any records overlap
        var score = 0

        for (pair in input.asSequence().map({ a -> parse(a) })) {
            if (pair.first.partiallyOverlaps(pair.second) ||
                pair.second.partiallyOverlaps(pair.first)) {
                score++
            }
        }
        return score
    }

    val inputName = "Day04"
    val input = readInput(inputName)
    val testName = "Day04_test"
    val testInput = readInput(testName)
}

class Assignment(val from: Int, val to: Int) { 

    fun overlaps(other: Assignment): Boolean {
        return this.from >= other.from && this.to <= other.to
    }

    fun partiallyOverlaps(other: Assignment): Boolean {
        return (
            this.from <= other.from && other.from <= this.to
        ) || (
            this.from <= other.to && other.to <= this.to
        )
    }

    // data class generates the same
    override fun toString(): String {
        return "Assignment(${from}-${to})"
    }
}



