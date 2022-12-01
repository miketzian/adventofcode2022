#!/usr/bin/env python3.10
import click
import os

def template(day):
    return """
class {0}() {{

    fun part1(input: List<String>): Int {{
        return 0
    }}

    fun part2(input: List<String>): Int {{
        return 0
    }}

    val inputName = "{0}"
    val input = readInput(inputName)
    val testName = "{0}_test"
    val testInput = readInput(testName)
}}
""".format(day)

@click.command()
@click.argument('day', type=int)
def cli(day):
    day = 'Day{}'.format(str(day).rjust(2, '0'))
    for f in (f'src/main/{day}.kt', f'src/resources/{day}.txt', f'src/resources/{day}_test.txt'):
        if not os.path.exists(f):
            with open(f, 'wt') as fh:
                if f.startswith('src/main'):
                    fh.write(template(day))
                else:
                    fh.write('\n')

if __name__ == '__main__':
    cli()