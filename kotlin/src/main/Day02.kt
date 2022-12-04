
package day2

import utils.readInput
import kotlin.collections.ArrayList

enum class Result(val score: Int) {
    WIN(6), LOSE(0), DRAW(3)
}

enum class Move {
    ROCK {
        override val score = 1
        override val losesAgainst by lazy { Move.valueOf("PAPER") } // PAPER does not yet exist
        override val winsAgainst by lazy { Move.valueOf("SCISSORS") }
    }, PAPER {
        override val score = 2
        override val losesAgainst by lazy { Move.valueOf("SCISSORS") }
        override val winsAgainst = Move.ROCK
    }, SCISSORS {
        override val score = 3
        override val losesAgainst = Move.ROCK
        override val winsAgainst = Move.PAPER
    };

    // what move should be played in order to achieve
    // the desired result against this move.
    final fun forResult(result: Result): Move {
        return when (result) {
            Result.WIN -> losesAgainst
            Result.LOSE -> winsAgainst
            // Result.DRAW -> this
            else -> this
        }
        // if (result == Result.WIN) {
        //     // find the record that will win when
        //     // this Move is played
        //     return losesAgainst
        // } else if (result == Result.LOSE) {
        //     return winsAgainst
        // } else {
        //     return this
        // }
    }
    final fun forMove(move: Move): Result {
        if (move == winsAgainst) {
            return Result.WIN
        }
        if (move == losesAgainst) {
            return Result.LOSE
        }
        return Result.DRAW
    }
    abstract val score: Int
    abstract val winsAgainst: Move
    abstract val losesAgainst: Move
}

class Day02() {

    fun part1(input: List<String>): Int {
        val lmoves = mapOf("A" to Move.ROCK, "B" to Move.PAPER, "C" to Move.SCISSORS)
        val rmoves = mapOf("X" to Move.ROCK, "Y" to Move.PAPER, "Z" to Move.SCISSORS)
        
        var scores: ArrayList<Int> = ArrayList()

        for (move in input) {
            val lr = move.split(" ")
            val lmove: Move = lmoves[lr[0]]!!  // array get is nullable
            val rmove: Move = rmoves[lr[1]]!!

            scores.add(rmove.score)

            val result = rmove.forMove(lmove)
            scores.add(result.score)
        }

        // println(scores.joinToString(","))
        return scores.sum()
    }

    fun part2(input: List<String>): Int {
        val lmoves = mapOf("A" to Move.ROCK, "B" to Move.PAPER, "C" to Move.SCISSORS)
        val rresult = mapOf("X" to Result.LOSE, "Y" to Result.DRAW, "Z" to Result.WIN)

        var scores: ArrayList<Int> = ArrayList()

        for (move in input) {
            val lr = move.split(" ")
            val lmove: Move = lmoves[lr[0]]!!
            val result: Result = rresult[lr[1]]!!

            val rmove = lmove.forResult(result)

            scores.add(rmove.score)
            scores.add(result.score)
        }
        // println(scores.joinToString(","))
        return scores.sum()
    }

    val inputName = "Day02"
    val input = readInput(inputName)
    val testName = "Day02_test"
    val testInput = readInput(testName)
}
