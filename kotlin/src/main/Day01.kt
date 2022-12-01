
class Day1() {

    fun part1(input: List<String>): Int {

        var most = -1;
        var next = 0;

        for (line: String in input) {

            if (line == "") {
                // check previous
                if (next > most) {
                    most = next
                }
                next = 0
                continue
            }
            next += Integer.parseInt(line)
        }

        return most
    }

    fun part1v2(input: List<String>): Int {

        var most = -1;

        for (next: Int in Day01Parser(input.iterator())) {
            if (next > most) {
                most = next
            }
        }

        return most
    }

    fun part1v3(resource: String): Int {

        var most = -1;

        readBuffered(resource).use { 
            br ->
            val iterator = readBufferedIter(br)
            for (next: Int in Day01Parser(iterator)) {
                if (next > most) {
                    most = next
                }
            }
        }

        return most
    }

    fun part2(input: List<String>): Int {
        // need the top three elves
        var top3 = IntArray(3) { _ -> 0 }

        fun maybePush(value: Int) {
            var min = 0

            for (ix in 1..2) {
                if (top3[ix] < top3[min]) {
                    min = ix;
                }
            }
            if (value > top3[min]) { 
                top3[min] = value
            }
        }

        var next = 0;

        for (line: String in input) {

            if (line == "") {
                maybePush(next)
                next = 0
                continue
            }
            next += Integer.parseInt(line)
        }

        if (next > 0) {
            maybePush(next)
        }

        println("top3 were: " + top3.joinToString(", "))
        
        return top3.sum()
    }

    fun part2v2(resource: String): Int {

        val counter = Day01Top3()

        readBuffered(resource).use { 
            br ->
            val iterator = readBufferedIter(br)
            for (next: Int in Day01Parser(iterator)) {
                counter.offer(next)
            }
        }
        return counter.result()
    }

    val inputName = "Day01"
    val testInputName = "${inputName}_test"
    val input = readInput(inputName)
    val testInput = readInput(testInputName)
}

class Day01Top3() {

    var top3 = IntArray(3) { _ -> 0 }

    fun offer(value: Int) {

        // keep the array sorted, so fewer comparisons

        // 50 -> 0, 0, 50
        // 75 -> 0, 50, 75
        // 20 -> 20, 50, 75
        // 80 -> 50, 75, 80

        for (ix in 2 downTo 0) {
            if (value > top3[ix]) {
                when(ix) {
                    2 -> {
                        top3[0] = top3[1]
                        top3[1] = top3[2]
                    }
                    1 -> {
                        top3[0] = top3[1]
                    }
                }
                top3[ix] = value
                break
            }
        }
    }

    fun result(): Int {
        println("top3 were: " + top3.joinToString(", "))
        return top3.sum()
    }

}



class Day01Parser(source: Iterator<String>): Iterator<Int> {

    val wrapped = source

    override fun hasNext(): Boolean {
        return wrapped.hasNext()
    }

    override fun next(): Int {
        var next: Int = 0
        while (wrapped.hasNext()) {
            val value = wrapped.next()
            if (value == "") {
                return next
            }
            next += Integer.parseInt(value)
        }
        // last iteration
        if (next == 0) {
            throw NoSuchElementException()
        }
        return next;
    }

}