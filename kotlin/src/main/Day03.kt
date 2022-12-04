
package day3
import utils.readInput

class Day03() {

    fun part1(input: List<String>): Int {

        var total: Int = 0;
        
        // named loops with @
        outer@ for (line in input) {
            val mid : Int = line.length / 2

            // string.toSet -> create set of the unique chars
            val right = line.substring(mid, line.length).toSet()

            for (c in line.substring(0, mid)) {
                if (c in right) {
                    val score = priority(c)
                    total += score
                    // println("c=${c}, ${score}")
                    continue@outer
                }
            }
        }

        println("total=${total}")

        return total
    }

    fun part2(input: List<String>): Int {

        var from = 0
        var total = 0
        val max = input.size
        while (from < max) {

            val two = input[from + 1].toSet()
            val three = input[from + 2].toSet()
            inner@ for (c in input[from]) {
                if (c in two && c in three) {
                    val prio = priority(c)
                    // println("(ix=${from}, c=${c}, prio=${prio}), ")
                    total += prio
                    break@inner
                }
            }
            from += 3
        }
        println("total=${total}")
        return total
    }

    fun priority(input: Char): Int {
        // https://kotlinlang.org/docs/control-flow.html#when-expression
        return when (input.code) {
            in 65..90 -> input.code - 65 + 27
            in 97..122 -> input.code - 97 + 1
            else -> throw Exception("Invalid")
        }
    }

    val inputName = "Day03"
    val input = readInput(inputName)
    val testName = "Day03_test"
    val testInput = readInput(testName)
}
