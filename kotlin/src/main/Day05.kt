

package day5

import utils.*
import kotlin.collections.MutableList

fun <V> MutableList<V>.pop(): V {
    return removeAt(this.size - 1)
}

class Day05(): BaseDay("Day05") {

    private fun process(input: Sequence<String>,
                        performMove: (action: MoveAction, data: MutableMap<Int, MutableList<Char>>) -> Unit): String {
        
        // this should be a linked list
        val data: MutableMap<Int, MutableList<Char>> = mutableMapOf()

        for (message in this.parser(input)) {
            // println(message)
            when(message) {
                is InitAction -> {
                    for (entry in message.data.entries) {
                        // println("putting entry ${entry.key}=${entry.value}")
                        data[entry.key] = entry.value.asReversed()
                    }
                }
                is MoveAction -> {
                    assert(data.isNotEmpty())
                    performMove(message, data)
                }
                is CompleteAction -> {
                    return data.entries.asSequence().map{
                            e -> e.value.pop()
                    }.joinToString("")
                }
            }
        }
        throw UnsupportedOperationException("unreachable")
    }

    fun part1(input: Sequence<String>): String {       
        val processor = fun (action: MoveAction, data: MutableMap<Int, MutableList<Char>>): Unit {
            for (i in 1..action.count) {
                val v = data[action.from]!!.pop()
                data[action.to]!!.add(v)
            }
        }
        return process(input, processor)
    }
    
    fun part2(input: Sequence<String>): String {       
        val processor = fun (action: MoveAction, data: MutableMap<Int, MutableList<Char>>): Unit {
            // println(action)
            val fromList = data[action.from]!!
            val toList = data[action.to]!!
            val fromIx = fromList.size-action.count
            
            // take the elements in order
            for (i in fromIx until fromList.size) {
                toList.add(fromList[i])
            }
            // remove the elements
            for (i in 1..action.count) {
                fromList.pop()
            }
        }
        return process(input, processor)
    }

    private fun parser(input: Sequence<String>): Sequence<BaseAction> {

        return sequence {
            var mode = Event.INITIAL;
            val data: MutableMap<Int, MutableList<Char>> = mutableMapOf()

            for (line in input) {
                assert(!line.endsWith("\n"))

                // parse until we get a blank line
                if (line == "") {
                    assert(data.isNotEmpty())
                    yield(InitAction(data))
                    // start to read the data
                    mode = Event.MOVES
                    continue
                } else {
                    // println("checking mode")
                    when(mode) { 
                        Event.INITIAL -> {
                            // we are collecting data
                            if (data.isEmpty()) {
                                // we need to populate the index
                                val entries : Int = (line.length / 4) + 1
                                for (i in 1..entries) {
                                    data[i] = mutableListOf()
                                }
                            }
                            val lineChars = line.toCharArray()
                            for (entry in data.entries) {
                                // do we have a value for this index ?
                                val entryChar = lineChars[
                                    1 + (4 * (entry.key - 1))
                                ]
                                if (entryChar != ' ' && entryChar.toString() != entry.key.toString()) {
                                    entry.value.add(entryChar)
                                }
                            }
                        }
                        Event.MOVES -> {
                            // println("collecting move")
                            // we are collecting moves
                            val tmp = line.split(" ")
                            yield(MoveAction(
                                Integer.parseInt(tmp[1]),
                                Integer.parseInt(tmp[3]),
                                Integer.parseInt(tmp[5])
                            ))
                        }                        
                        else -> {
                            throw UnsupportedOperationException("unreachable")
                        }
                    }
                }
            }
            // at the end
            yield(CompleteAction())
        }
    }
}

enum class Event {
    INITIAL, MOVES, COMPLETE
}
open class BaseAction(val event: Event) {}
data class InitAction(val data: Map<Int, MutableList<Char>>): BaseAction(Event.INITIAL){}
data class MoveAction(val count: Int, val from: Int, val to: Int): BaseAction(Event.MOVES){}
class CompleteAction(): BaseAction(Event.COMPLETE){}
