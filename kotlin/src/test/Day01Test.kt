
import day1.*
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day1Test {
    private val impl: Day1 = Day1()

    @Test
    fun testPartOne() {
        val testResult = impl.part1(impl.testInput)
        assertEquals(testResult, 24000)

        val actualResult = impl.part1(impl.input)
        assertEquals(actualResult, 68292)
        println("Result was: " + actualResult)
    }

    @Test
    fun testPartOnev2() {
        val testResult = impl.part1v2(impl.testInput)
        assertEquals(testResult, 24000)

        val actualResult = impl.part1v2(impl.input)
        assertEquals(actualResult, 68292)
        println("Result was: " + actualResult)
    }

    
    @Test
    fun testPartOnev3() {
        val testResult = impl.part1v3(impl.testInputName)
        assertEquals(testResult, 24000)

        val actualResult = impl.part1v3(impl.inputName)
        assertEquals(actualResult, 68292)
        println("Result was: " + actualResult)
    }

    @Test
    fun testParser() {
        val expected = listOf(6000, 4000, 11000, 24000, 10000)
        val testResult = Day01Parser(impl.testInput.iterator()).asSequence().toList()

        assertEquals(expected, testResult)
        println("Parser OK")
    }
    
    @Test
    fun testPartTwo() {
        val testResult = impl.part2(impl.testInput)
        assertEquals(testResult, 45000)

        val actualResult = impl.part2(impl.input)
        assertEquals(actualResult, 203203)
        println("Result was: " + actualResult)
    }

    @Test
    fun testPartTwov2() {
        val testResult = impl.part2v2(impl.testInputName)
        assertEquals(testResult, 45000)

        val actualResult = impl.part2v2(impl.inputName)
        assertEquals(actualResult, 203203)
        println("Result was: " + actualResult)
    }

}