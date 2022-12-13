package utils

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.math.BigInteger
import java.security.MessageDigest

public open class BaseDay(val inputName: String) {

    val testName = "${inputName}_test"

    fun input(): Sequence<String> {
        return readSequence(inputName)
    }

    val input = readInput(inputName)
    fun testInput(): Sequence<String> {
        return readSequence(testName)
    }

}

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src/resources", "$name.txt")
    .readLines()

fun readBuffered(name: String) = 
        BufferedReader(FileReader(File("src/resources", "$name.txt")))

// read file as sequence of lines
fun readSequence(name: String) = readBuffered(name).lineSequence()

// read file as sequence using sequence generator
fun readSequenceManual(name: String) : Sequence<String> =
    sequence {
        val reader = readBuffered(name)
        with (reader) {
            var nextline = reader.readLine()
            while (nextline != null) {
                yield(nextline)
                nextline = reader.readLine()
            }
        }
    }

// from BufferedReader.lines() but returns the Iterator<String>
// this was faster than java.util.Scanner with delimiter \n
class readBufferedIter(br: BufferedReader): Iterator<String> {
    val br = br
    var nextLine : String ?= null

    override fun hasNext(): Boolean {
        if (nextLine != null) {
            return true
        }
        nextLine = br.readLine() // UncheckedIOException
        return nextLine != null
    }

    override fun next(): String {
        if (hasNext()) {
            val r: String = nextLine.orEmpty()
            nextLine = null
            return r
        }
        throw NoSuchElementException();
    }
}

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

class PeekableIterator<T>(private val wrapped: Iterator<T>): Iterator<T> {

    var next: T? = null

    fun peek(): T {
        if (next == null) {
            next = wrapped.next()
        }
        return next!!
    }

    override fun hasNext(): Boolean {
        return next != null || wrapped.hasNext()
    }

    override fun next(): T {
        if (next != null) {
            val r = next!!
            next = null;
            return r
        }
        return wrapped.next()
    }
}

