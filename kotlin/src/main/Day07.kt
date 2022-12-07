

package day07
import day5.pop
import utils.*

class Day07():BaseDay("Day07") {

    fun part1(input: Sequence<String>): Int {

        val root: PathDir = readEntries(input)

        // do calculations
        var total = 0
        fun checkPath(base: PathDir) {
            for (entry in base.getEntries().filter { it is PathDir }.map{ it as PathDir }) {
                val name = entry.path
                val size = entry.size()
                if (size < 100000) {
                    // println("name=${name}, size=${size}")
                    total += size
                // } else {
                //     println("have name: ${name}, size=${size} (too big)")
                }
                // smart cast didn't pick this
                checkPath(entry)
            }
        }

        checkPath(root)
        return total
    }

    private fun readEntries(input: Sequence<String>): PathDir {

        val root = PathDir("/")
        val context: MutableList<Pair<String, PathDir>> = mutableListOf()
        var inLs = false

        for (line in input) {
            if (context.isEmpty()) {
                assert(line == "\$ cd /")
                context.add(Pair("/", root))
            }
            else if (line.startsWith("\$")) {
                if (line.startsWith("\$ cd ")) {
                    val fragment = line.substring(5)
                    if (fragment == "..") {
                        context.removeLast()
                    } else {
                        val dir = context.last().second.getDir(fragment)
                        context.add(Pair(dir.path, dir))
                    }
                    inLs = false
                } else if (line == "\$ ls") {
                    inLs = true
                } else {
                    throw UnsupportedOperationException("command not configured: ${line}")
                }
            } else if (inLs) {
                val spl = line.split(" ")
                if (spl[0] == "dir") {
                    context.last().second.addEntry(PathDir(spl[1]))
                } else {
                    val size: Int = spl[0].toInt()
                    context.last().second.addEntry(PathFile(spl[1], size))
                }
            } else {
                throw UnsupportedOperationException("unreachable?")
            }
        }
        return root
    }


    fun part2(input: Sequence<String>): Int {

        val root: PathDir = readEntries(input)

        // do calculations
        var minDir: Pair<PathDir, Int>? = null

        fun checkPath(base: PathDir, atLeast: Int) {
            for (entry in base.getEntries().filter { it is PathDir }.map{ it as PathDir}) {

                // actually somewhat inefficient, since the dirs will get re-calcualted
                // maybe need a cache with a small ttl
                val size = entry.size()

                if (size >= atLeast) {
                    // println("name=${name}, size=${size}")
                    if (minDir != null) {
                        if (minDir!!.second > size) {
                            minDir = Pair(entry, size)
                        }
                    } else {
                        minDir = Pair(entry, size)
                    }
                //} else {
                //    println("name=${name}, size=${size} (too small)")
                }
                checkPath(entry, atLeast)
            }
        }

        val freeSpace = 70000000 - root.size()
        val needSpace = 30000000 - freeSpace
        println("have ${freeSpace}, need at least ${needSpace}")
        checkPath(root, needSpace)
        println("minDir ${minDir!!.first.path} / ${minDir!!.second}")

        // 17665203 is too high
        //   528671 need at minimum

        return minDir!!.second
    }
}

enum class PathType { FILE, DIR }
abstract class PathEntry(val pathType: PathType){
    abstract val path: String
    abstract fun size(): Int
}
class PathDir(override val path: String): PathEntry(PathType.DIR){
    private val entries: MutableMap<String, PathEntry> = mutableMapOf()
    override fun size(): Int {
        return entries.values.asSequence().map{ it.size() }.sum()
    }
    fun addEntry(entry: PathEntry) {
        if (entries.containsKey(entry.path)) {
            println("skipping, contains already!: ${entry.path}")
            return
        } 
        entries[entry.path] = entry
    }
    fun getDir(path: String): PathDir {
        val entry = entries[path]!!
        if (entry is PathDir) {
            return entry
        }
        throw UnsupportedOperationException("should be dir")
    }
    fun getEntries(): Sequence<PathEntry> {
        return entries.values.asSequence()
    }
}
class PathFile(override val path: String, private val fileSize: Int): PathEntry(PathType.FILE){
    override fun size(): Int {
        return fileSize
    }
}

