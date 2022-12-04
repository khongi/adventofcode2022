fun main() {
    fun String.getRange(): IntRange {
        val (start, end) = split('-').map { it.toInt() }
        return start..end
    }

    fun part1(input: List<String>): Int {
        return input.count { line ->
            val (first, second) = line.split(',').map { it.getRange() }
            val intersect = first.intersect(second)
            intersect.size == first.count() || intersect.size == second.count()
        }
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)

    val input = readInput("Day04")
    println(part1(input))
}
