fun main() {
    fun part1(input: List<String>): Int {
        var acc = 0
        var max = 0
        input.forEach { number ->
            if (number.isNotEmpty()) {
                val value = number.toInt()
                acc += value
            } else {
                if (max < acc) {
                    max = acc
                }
                acc = 0
            }
        }
        return max
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
