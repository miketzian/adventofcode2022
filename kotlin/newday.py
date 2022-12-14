#!/usr/bin/env python3.10
import click
import os

def template(day):
    return """

package {1}
import utils.*

class {0}:BaseDay("{0}") {{

    fun part1(input: Sequence<String>): Int {{
        return 0
    }}

    fun part2(input: Sequence<String>): Int {{
        return 0
    }}
}}
""".format(day, day.lower())

def test_template(day):
    return """

import kotlin.test.Test
import kotlin.test.assertEquals
import {1}.*

internal class {0}Test {{
    private val impl: {0} = {0}()

    @Test
    fun testPartOne() {{
        val testResult = impl.part1(impl.testInput())
        assertEquals(2, testResult)

        val actualResult = impl.part1(impl.input())
        // assertEquals(459, actualResult)
        println("Result was: $actualResult")
    }}

    @Test
    fun testPartTwo() {{
        val testResult = impl.part2(impl.testInput())
        assertEquals(4, testResult)

        val actualResult = impl.part2(impl.input())
        // assertEquals(779, actualResult)
        println("Result was: $actualResult")
    }}
}}

""".format(day, day.lower())

@click.command()
@click.argument('day', type=int)
def cli(day):
    day = 'Day{}'.format(str(day).rjust(2, '0'))
    for f in (
        f'src/main/{day}.kt',
        f'src/test/{day}Test.kt',
        f'src/resources/{day}.txt',
        f'src/resources/{day}_test.txt',
        ):
        if not os.path.exists(f):
            with open(f, 'wt') as fh:
                if f.startswith('src/main'):
                    fh.write(template(day))
                elif f.startswith('src/test'):
                    fh.write(test_template(day))
                else:
                    fh.write('\n')

if __name__ == '__main__':
    cli()