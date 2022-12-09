package day09

import utils.*

class Day09():BaseDay("Day09") {

    fun part1(input: Sequence<String>): Int {
        
        val head = RopePart(0, 0)
        val tail = RopePart(0, 0)

        val visited : MutableSet<Pair<Int, Int>> = mutableSetOf(tail.location())

        input.map{ 
            line -> 
                parseMoves(line)
        }.forEach{ move: Pair<Direction, Int> ->
            // println("processing move: $move")
            tail.follow(head.move(move)).forEach{ it2 -> 
                visited.add(it2)
                // println("head=${head.location()}, tail=${tail.location()}")
            }
        }

        return visited.size
    }

    fun part2(input: Sequence<String>): Int {
        
        val head = RopePart(0, 0)

        // initialize list entries
        val parts = List(9) {
            RopePart(0, 0)
        }

        // val p1 = RopePart(0, 0)
        // val p2 = RopePart(0, 0)
        // val p3 = RopePart(0, 0)
        // val p4 = RopePart(0, 0)
        // val p5 = RopePart(0, 0)
        // val p6 = RopePart(0, 0)
        // val p7 = RopePart(0, 0)
        // val p8 = RopePart(0, 0)
        // val p9 = RopePart(0, 0)
    
        val visited : MutableSet<Pair<Int, Int>> = mutableSetOf(parts.last().location())

        input.map{ 
            line -> 
                parseMoves(line)
        }.forEach{ move: Pair<Direction, Int> ->
            // println("processing move: $move")

            var last: Sequence<Pair<Int, Int>> = head.move(move)
            for (part in parts) {
                last = part.follow(last)
            }

            last.forEach{
                visited.add(it)
            }

            // equivalent to the above
            // p9.follow(
            //     p8.follow(
            //         p7.follow(
            //             p6.follow(
            //                 p5.follow(
            //                     p4.follow(
            //                         p3.follow(
            //                             p2.follow(
            //                                 p1.follow(head.move(move))
            //                             )
            //                         )
            //                     )
            //                 )
            //             )
            //         )
            //     )
            // ).forEach{ it -> 
            //     visited.add(it)
            // }
        }

        return visited.size
    }

    fun parseMoves(input: String): Pair<Direction, Int> {
        val split = input.split(" ")
        val move = Direction.valueOf(split[0])
        val distance = split[1].toInt()
        return Pair(move, distance)
    }
}

enum class Direction { 
    U, D, L, R
}

class RopePart(xInit: Int, yInit: Int) {
    private var xLoc: Int = xInit
    private var yLoc: Int = yInit

    fun move(where: Pair<Direction, Int>): Sequence<Pair<Int, Int>> {
        return sequence {
            var distance = where.second
            while (distance > 0) { 
                when (where.first) {
                    Direction.U -> {
                        yLoc += 1
                        yield(Pair(xLoc, yLoc))
                    }
                    Direction.D -> {
                        yLoc -= 1
                        yield(Pair(xLoc, yLoc))
                    }
                    Direction.L -> {
                        xLoc -= 1
                        yield(Pair(xLoc, yLoc))
                    }
                    Direction.R -> {
                        xLoc += 1
                        yield(Pair(xLoc, yLoc))
                    }
                }
                distance -= 1
            }
        }
    }

    fun follow(seq: Sequence<Pair<Int, Int>>): Sequence<Pair<Int, Int>> {

        fun followDiff(one: Int, two: Int): Int {
            val d: Int = one - two
            if (d < 0) {
                return d * -1
            } else {
                return d
            }
        }

        return sequence { 
            for (loc in seq) {
                // this is what the tail will do.
                //val pre = "Following: $loc, from ${location()} "
                var moved = false
                val diag = followDiff(loc.first, xLoc) > 1 || followDiff(loc.second, yLoc) > 1
                
                // if we are not moving diagonally, then the axis must be more than one move away
                // if we are moving diagonally, then we have already checked that at least one 
                // of the axes is more than one move away so this check can be removed.
                val check = if (diag) { 0 } else { 1 }

                if (diag || (loc.second == yLoc) || (loc.first == xLoc)){                
                    if (loc.first != xLoc) {
                        if (loc.first > (xLoc + check)) {
                            xLoc = xLoc + 1
                            moved = true
                        } else if (loc.first < (xLoc - check)) {
                            xLoc = xLoc - 1
                            moved = true
                        }
                    }
                    if (loc.second != yLoc) {
                        if (loc.second > (yLoc + check)) {
                            yLoc = yLoc + 1
                            moved = true
                        } else if (loc.second < (yLoc - check)) {
                            yLoc = yLoc - 1
                            moved = true
                        }
                    }
                    if (moved) {
                        //println("$pre now: ${location()}")
                        yield(location())
                    } 
                }
            }
        }
    }

    fun location(): Pair<Int, Int> {
        return Pair(xLoc, yLoc)
    }

}