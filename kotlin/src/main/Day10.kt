package day10


import utils.*
import kotlin.text.StringBuilder

class Day10():BaseDay("Day10") {

    private fun opSequence(input: Sequence<String>): Sequence<Pair<Int, Int>> {
        return sequence {
            var cycle = 0
            var registerX = 1
            for (op in input.map{parse(it)}) {
                cycle++
                // should yield what is the value during the cycle
                yield(Pair(cycle, registerX))
                when(op) {
                    is NoOp -> {
                        // nothing
                    }
                    is AddX -> {
                        // addX goes for two cycles
                        cycle++
                        yield(Pair(cycle, registerX))
                        // the register is only set after the 2nd cycle is complete
                        registerX += op.value
                    }
                    else -> throw UnsupportedOperationException("odd=${op}")
                }
            }
        }
    }

    fun part1(input: Sequence<String>): Int {

        return opSequence(input).filter{ it ->
            it.first == 20 || (it.first >= 60 && (it.first - 20) % 40 == 0)
        }.fold(0) { acc, pair ->
            val signal = pair.first * pair.second
            // println("acc=${acc}, pair=${pair}, sig=${signal}")
            if (acc == 0) signal else acc +signal
        }
    }

    fun part2(input: Sequence<String>): String {
        return opSequence(input).map{
            // pixels are zero based, need to subtract one.
            val v = (it.first - 1) % 40
            val ret = if (it.second <= (v + 1) && it.second >= (v - 1)) {
                "#"
            } else {
                "."
            }
//            if (it.first < 20) {
//                println("cycle=${it.first}, x=${it.second}, ret=${ret}")
//            }
            if (v == 39) { // it.first % 40 == 0
                "$ret\n"
            } else {
                ret
            }
        }.fold(StringBuilder(220)) { acc, v -> acc.append(v) }
            .toString()
    }

    private fun parse(input: String): Op {
        return if ("noop" == input) {
            NoOp()
        } else {
            assert(input.startsWith("addx"))
            val value = input.split(" ")[1].toInt()
            AddX(value)
        }
    }
}
open class Op{}
class NoOp(): Op(){}
class AddX(val value: Int): Op(){}

