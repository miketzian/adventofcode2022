package day06

import utils.*

class Day06:BaseDay("Day06") {

    fun part1(input: String): Int {
        for (i in 0 until (input.length - 3)) {
            if (setOf(input[i], input[i+1], input[i+2], input[i+3]).size == 4) {
                // println("ix=${i}, ${input[i]} != ${input[i+1]} != ${input[i+2]} != ${input[i+3]}")
                return i + 4
            }
        }
        throw UnsupportedOperationException("not found!")
    }

    fun part2(input: String): Int {

        val freq: MutableMap<Char, Int> = mutableMapOf()
        for (c in input.substring(0, 14))
        {
            freq.putIfAbsent(c, 0)
            freq[c] = freq[c]!! + 1
        }

        for (i in 0 until (input.length - 14)) {
            
            if (i > 0) {
                val oldHead: Char = input[i-1]
                val newTail: Char = input[i+13]

                // otherwise no-op
                if (oldHead != newTail) {
                    // decrement initial
                    freq[oldHead] = freq[oldHead]!! - 1
                    if (freq[oldHead]!! == 0) {
                        // remove if count=0 as we are counting entries
                        freq.remove(oldHead)
                    }
                    // add char at the end
                    freq.putIfAbsent(newTail, 0)
                    freq[newTail] = freq[newTail]!! + 1
                }
            }
            // how many keys
            if (freq.size == 14) {
                // println("winner: ${freq}")
                return i + 14
            }
        }
        throw UnsupportedOperationException("not found!")
    }
}
