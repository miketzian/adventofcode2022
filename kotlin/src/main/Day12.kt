package day12

import utils.*

class Day12:BaseDay("Day12") {

    fun part1(input: Sequence<String>): Int {

        var pos: Position? = null
        val map: List<List<Char>> = buildList {
            input.forEachIndexed{
                ixX, str ->
                    val ixY = str.indexOf('S')
                    if (ixY != -1) {
                        pos = Position(ixX, ixY)
                    }
                    add(str.toList())
            }
        }
        // input.map{ it.toList() }.toList()
        if (pos == null) {
            throw RuntimeException("did not find S")
        }

        val min = search(Follower(0, current=pos!!), map)
        return min.depth
    }

    fun part2(input: Sequence<String>): Int {

        val map: List<List<Char>> = input.map{ it.toList() }.toList()
        return sequence {
            map.forEachIndexed{
                ixX, it ->
                    it.forEachIndexed{
                        ixY, c ->
                            if (c == 'S' || c == 'a') {
                                yield(Position(ixX, ixY))
                            }
                    }
            }
        }.map{
            try {
                search(Follower(0, current = it), map).depth
            } catch (nfe: NoSuchElementException) {
                // we throw when not found for part 1
                Int.MAX_VALUE
            }
        }.min()
    }

    private fun search(start: Follower, map: List<List<Char>>): Follower {

        val maxX = map.size
        val maxY = map[0].size
        fun isValid(p: Position): Boolean {
            return p.first in 0 until maxX && p.second in 0 until maxY
        }
        fun isDone(candidate: Position): Boolean {
            return map[candidate.first][candidate.second] == 'E'
        }
        // val queue = LinkedTransferQueue<Follower>(listOf(start))
        val queue = Queue()
        queue.add(start)
        val checked : MutableSet<Position> = mutableSetOf(start.current)

        while (!queue.isEmpty()) {
            val f1 = queue.remove()

            val candidates = sequence {
                yield(f1.current.copy(first = f1.current.first - 1))
                yield(f1.current.copy(first = f1.current.first + 1))
                yield(f1.current.copy(second = f1.current.second - 1))
                yield(f1.current.copy(second = f1.current.second + 1))
            }.filter {
                isValid(it)
                && f1.canWalk(it, map) // can we go this way?
                && !checked.contains(it)
            }.map {
                // moves that we can potentially take.
                Follower(f1.depth + 1, it)
            }.toList()

            val maybe = candidates.firstOrNull { isDone(it.current) }
            if (maybe != null) {
                // println("found $f1")
                return maybe
            } else {
                // only if we didn't find the end, check the other paths.
                checked.addAll(candidates.map{ it.current })
                queue.addAll(candidates)
            }
        }
        throw NoSuchElementException("not found!")
    }
}
typealias Position = Pair<Int, Int>
data class Follower(val depth: Int, val current: Position) {
    fun canWalk(candidate: Position, map: List<List<Char>>): Boolean {
        val self : Char = map[current.first][current.second]
        if (self == 'S') {
            // start can walk anywhere
            return true
        }
        val other: Char = map[candidate.first][candidate.second]
        if (other == 'E') {
            // can only walk there from z
            return self == 'z'
        }
        // println("can walk from $self to $other ? ${self.code} ${other.code}")
        // return okDiff.contains(self.code - other.code)
        if (other.code - 1 == self.code) {
            // at most one higher
            return true
        }
        return other.code <= self.code
    }
}

class Queue {

    private var head: Node? = null
    private var tail: Node? = null

    fun isEmpty(): Boolean {
        return head == null
    }
    fun add(input: Follower) {
        // println("add ${input}")
        if (head == null) {
            head = Node(input)
            tail = head
        } else {
            // head is not null, so tail cannot be null
            tail!!.next = Node(input)
            tail = tail!!.next
        }
    }
    fun remove(): Follower {
        if (head != null) {
            val r = head!!.value
            head = head!!.next
            // if there is no next, the tail must be null
            if (head == null) {
                tail = null
            }
            return r
        } else {
            throw NoSuchElementException("empty")
        }
    }
    fun addAll(input: Iterable<Follower>) {
        input.forEach{
            add(it)
        }
    }

    inner class Node(val value: Follower) {
        var next : Node? = null
    }
}

