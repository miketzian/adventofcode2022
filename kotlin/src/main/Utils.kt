import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src/resources", "$name.txt")
    .readLines()

fun readBuffered(name: String) = 
        BufferedReader(FileReader(File("src/resources", "$name.txt")))

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
