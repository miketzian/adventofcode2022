package day08
import utils.*

class Day08():BaseDay("Day08") {

    fun part1(input: Sequence<String>): Int {
        
        var count = 0
        // grid is height of each tree 
        val grid: List<List<Int>> = input.map { 
            i: String-> i.asSequence().map{ 
                i2: Char -> i2.digitToInt()
            }.toList() 
        }.toList()

        for (i in 1 until (grid.size - 1)) {
            
            inner@for (j in 1 until (grid[i].size - 1)) {
                val v = grid[i][j]
                // val debug : Boolean = i == 3 && j == 3
                // if (debug) {
                //     println("v=$v")
                // }
                
                // println("$i -> $j - test")

                var ok = false
                for (it in 0 until i) {
                    if (grid[it][j] >= v) { 
                        // if (debug) {
                        //     println("grid[$it][$j] = ${grid[it][j]}")
                        // }
                        // break
                        ok = true
                        break
                    }
                }
                if (!ok) {
                    continue@inner
                }
                ok = false
                for (it in (i + 1) until (grid.size)) {
                    if (grid[it][j] >= v) { 
                        // if (debug) {
                        //     println("grid[$it][$j] = ${grid[it][j]}")
                        // }
                        // break
                        ok = true
                        break
                    }
                }
                if (!ok) {
                    continue@inner
                }
                ok = false
                for (jt in 0 until j) {
                    if (grid[i][jt] >= v) { 
                        // if (debug) {
                        //     println("grid[$i][$jt] = ${grid[i][jt]}")
                        // }
                        // break
                        ok = true
                        break
                    }
                }
                if (!ok) {
                    continue@inner
                }
                ok = false
                for (jt in (j + 1) until (grid[i].size)) {
                    if (grid[i][jt] >= v) { 
                        // if (debug) {
                        //     println("grid[$i][$jt] = ${grid[i][jt]}")
                        // }
                        // break
                        ok = true
                        break
                    }
                }
                if (!ok) {
                    continue@inner
                }
                //println("**")
                count += 1
                
            }
        }
        // count = how many visible 

        return (grid.size * grid.size) - count
    }

    // calculate the scenic score of the best tree
    fun part2(input: Sequence<String>): Int {
        
        // grid is height of each tree 
        val grid: List<List<Int>> = input.map { 
            i: String-> i.asSequence().map{ 
                i2: Char -> i2.digitToInt()
            }.toList() 
        }.toList()

        var bestScore: List<Int> = listOf()
        var bestScoreInt: Int = 0

        for (i in 1 until (grid.size - 1)) {
            
            inner@for (j in 1 until (grid[i].size - 1)) {
                val v = grid[i][j]
                // val debug : Boolean = i == 3 && j == 2
                // if (debug) {
                //     println("v=$v")
                //     println("$i -> $j") 
                // }

                var ok = false
                var viewingDiff: MutableList<Int> = mutableListOf(0,0,0,0)
                for (it in (i-1) downTo 0) {
                    viewingDiff[0] += 1
                    if (grid[it][j] >= v) { 
                        // if (debug) {
                        //     println("grid[$it][$j] = ${grid[it][j]}")
                        // }
                        break
                    }
                }
                if (viewingDiff[0] == 0) {
                    continue@inner
                }
                ok = false
                for (it in (i + 1) until (grid.size)) {
                    viewingDiff[1] += 1
                    if (grid[it][j] >= v) { 
                        // if (debug) {
                        //     println("grid[$it][$j] = ${grid[it][j]}")
                        // }
                        break
                    }
                }
                if (viewingDiff[1] == 0) {
                    continue@inner
                }
                ok = false
                for (jt in (j-1) downTo 0) {
                    viewingDiff[2] += 1
                    if (grid[i][jt] >= v) { 
                        // if (debug) {
                        //     println("grid[$i][$jt] = ${grid[i][jt]}")
                        // }
                        break
                    }
                }
                if (viewingDiff[2] == 0) {
                    continue@inner
                }
                ok = false
                for (jt in (j + 1) until (grid[i].size)) {
                    viewingDiff[3] += 1
                    if (grid[i][jt] >= v) { 
                        // if (debug) {
                        //     println("grid[$i][$jt] = ${grid[i][jt]}")
                        // }
                        break
                    }
                }
                if (viewingDiff[3] == 0) {
                    continue@inner
                }
                val score = viewingDiff.fold(1, {
                    acc: Int, el: Int -> acc * el
                })
                // println("** Score: $viewingDiff -> $score")
                if (score > bestScoreInt) {
                    bestScore = viewingDiff
                    bestScoreInt = score
                }
            }
        }
        // count = how many visible 
        println("Best Score: $bestScore -> $bestScoreInt")
        return bestScoreInt
    }
}