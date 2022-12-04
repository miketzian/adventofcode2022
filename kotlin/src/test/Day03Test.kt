
import kotlin.test.Test
import kotlin.test.assertEquals
import day3.Day03

internal class Day03Test {
    private val impl: Day03 = Day03()

    @Test
    fun testPartOne() {
        val testResult = impl.part1(impl.testInput)
        assertEquals(testResult, 157)

        val actualResult = impl.part1(impl.input)
        assertEquals(actualResult, 8072)
        println("Result was: " + actualResult)
    }

    @Test
    fun check() {
        assertEquals(impl.priority('a'), 1)
        assertEquals(impl.priority('z'), 26)
        assertEquals(impl.priority('A'), 27)
        assertEquals(impl.priority('Z'), 52)
        
        assertEquals(impl.priority('Z'), 52)
    

        
        // ('p', 16), ('L', 38), ('P', 42), ('v', 22), ('t', 20), ('s', 19)]
    
    
    }

    @Test
    fun testPartTwo() {
        val testResult = impl.part2(impl.testInput)
        assertEquals(testResult, 70)

        val actualResult = impl.part2(impl.input)
        assertEquals(actualResult, 2567)
        println("Result was: " + actualResult)
    }
}