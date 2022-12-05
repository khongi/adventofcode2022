import java.util.Stack

fun main() {
    operator fun <E> List<E>.component2(): Int = this[1].toString().toInt()
    operator fun <E> List<E>.component4(): Int = this[3].toString().toInt()
    operator fun <E> List<E>.component6(): Int = this[5].toString().toInt()

    fun buildStacks(
        input: List<String>,
        numberedLineIndex: Int
    ): List<Stack<Char>> {
        val cols = input[numberedLineIndex].split(' ').last().toInt()
        val stacks: MutableList<Stack<Char>> = mutableListOf()
        repeat(cols) { stacks.add(Stack()) }

        input.subList(0, numberedLineIndex).forEach { crateLevel ->
            crateLevel.chunked(4).map { it[1] }.forEachIndexed { index, c ->
                if (c.isLetter()) {
                    stacks[index].add(c)
                }
            }
        }
        stacks.forEach { it.reverse() }
        return stacks
    }

    fun getTopCrates(stacks: List<Stack<Char>>) =
        stacks.map { it.pop() }.joinToString(separator = "")

    fun moveSingleCrates(
        moveCount: Int,
        stacks: List<Stack<Char>>,
        to: Int,
        from: Int
    ) {
        repeat(moveCount) { stacks[to - 1].add(stacks[from - 1].pop()) }
    }

    fun getNumberedLineIndex(input: List<String>) = input.indexOfFirst { it.contains('1') }

    fun operateCrateMover(
        input: List<String>,
        move: (
            moveCount: Int,
            stacks: List<Stack<Char>>,
            to: Int,
            from: Int
        ) -> Unit
    ): String {
        val numberedLineIndex = getNumberedLineIndex(input)
        val stacks: List<Stack<Char>> = buildStacks(input, numberedLineIndex)

        input.subList(numberedLineIndex + 2, input.size).forEach { command ->
            val (_, moveCount, _, from, _, to) = command.split(' ')
            move(moveCount, stacks, to, from)
        }

        return getTopCrates(stacks)
    }

    fun part1(input: List<String>): String {
        return operateCrateMover(input, ::moveSingleCrates)
    }

//    fun part2(input: List<String>): Int {
//        return input.size
//    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
//    check(part2(testInput) == 1)

    val input = readInput("Day05")
    println(part1(input))
//    println(part2(input))
}

