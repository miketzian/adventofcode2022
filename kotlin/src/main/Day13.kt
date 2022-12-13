package day13

//import com.fasterxml.jackson.databind.ObjectMapper
//import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
//import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import utils.*

class Day13:BaseDay("Day13") {

    // private val mapper by lazy { ObjectMapper(YAMLFactory()).registerKotlinModule() }

    // 10ms faster to declare it, even if it's not used
    // private val mapper = ObjectMapper(YAMLFactory()).registerKotlinModule()

    fun part1(input: Sequence<String>): Int {

        return read(input).mapIndexed() {
            ix, it ->
                Pair(ix + 1, correct(it))
        }.filter {
            it.second
        }.map {
            // println("OK: ${it.first}")
            it.first
        }.sum()
    }

    fun part2(input: Sequence<String>): Int {

        val div2 = listOf(listOf(2))
        val div6 = listOf(listOf(6))

        return sequence {
            read(input).forEach{
                yield(it.first)
                yield(it.second)
            }
            yield(div2)
            yield(div6)
        }.sortedWith { a, b ->
            // sort the list
            listCompare(a, b)
        }.mapIndexed{
            // add the indexes,
            // so that we can filter and keep the original indexes
            ix, v -> Pair(ix + 1, v)
        }.filter{
            // filter for the items
            it.second == div2 || it.second == div6
        }.map{
            // then keep only the indexes they were in
            it.first
        }.fold(1) { acc, i -> acc * i }
    }

    private fun listCompare(first: List<Any?>, second: List<Any?>): Int {
        // println("comparing: \n$first\n$second")

        val one = first.iterator()
        val two = second.iterator()

        while (one.hasNext()) {
            if (!two.hasNext()) {
                // println("$first vs $second = right side ran out of items - BAD")
                return 1
            }
            // first two values returned
            var oneV = one.next()
            var twoV = two.next()

            if (oneV is Packet && twoV is Int) {
                twoV = listOf(twoV)
            } else if (oneV is Int && twoV is Packet) {
                oneV = listOf(oneV)
            }

            if (oneV is Int && twoV is Int) {
                // println("comparing:\n$oneV\n$twoV")
                if (oneV < twoV) {
                    // println("$first vs $second = $oneV < $twoV = OK")
                    return -1
                } else if (oneV > twoV) {
                    // println("$first vs $second = $oneV > $twoV = BAD")
                    return 1
                }
            } else if (oneV is Packet && twoV is Packet) {
                val c = listCompare(oneV, twoV)
                if (c < 0) {
                    // println("$first vs $second = $oneV < $twoV = OK")
                    return -1
                } else if (c > 0) {
                    // println("$first vs $second = $oneV > $twoV = BAD")
                    return 1
                } // else continue
            } else {
                throw UnsupportedOperationException("")
            }
        }
        if (two.hasNext()) {
            // println("$first vs $second = left side ran out of items - OK")
            return -1
        }
        // println("$first vs $second = equal number is ok")
        return 0
    }

    private fun correct(pair: Pair<Packet,Packet>): Boolean {
        return listCompare(pair.first, pair.second) < 0
    }

    private fun read(input: Sequence<String>): Sequence<Pair<Packet,Packet>> {
        val it = input.iterator()

        fun parse(value: String): Packet {
            assert(value.startsWith("[") && value.endsWith("]"))
            // return mapper.readValue(value, List::class.java)
            return readPacket(value)
        }

        return sequence {
            try {
                while(it.hasNext()) {
                    val left = parse(it.next())
                    val right = parse(it.next())
                    yield(Pair(left, right))
                    // will throw on last iteration
                    it.next()
                }
            } catch (ex: NoSuchElementException) {
                // no more packets
            }
        }
    }

    /**
     * 94, 88, 90, 100ms - jackson yaml
     * 30, 34, 29, 34ms - this one
     */
    private fun readPacket(input: String): Packet {

        fun readSingle(i: PeekableIterator<Char>): Packet {
            assert(i.hasNext())
            assert(i.next() == '[')

            return buildList {
                while (i.hasNext()) {
                    when(i.peek()) {
                        '[' -> add(readSingle(i))
                        ']' -> break // end of list
                        else -> {
                            // we have a number, but it might have more than one digit.
                            add(i.asSequence().takeWhile{
                                    c -> c != ',' && c != ']'
                            }.joinToString("").toInt())
                            if (i.hasNext() && i.peek() == ',') {
                                i.next()
                            }
                        }
                    }
                }
            }
        }
        return readSingle(PeekableIterator(input.iterator()))
    }
}
typealias Packet = List<*>