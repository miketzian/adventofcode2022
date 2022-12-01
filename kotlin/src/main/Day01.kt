
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

    fun part2(input: List<String>): Int {
        // need the top three elves
        var top3 = IntArray(3) { _ -> 0 }

        fun maybePush(value: Int) {
            println("have elf with calories: " + value)
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

    val testInput = readInput("Day01_test")
    val input = readInput("Day01")
}
