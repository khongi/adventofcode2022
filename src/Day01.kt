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
        var acc = 0
        val maxes = mutableListOf(0, 0, 0)
        input.forEachIndexed { index, number ->
            val calcMax = number.isEmpty() || index == input.lastIndex
            if (number.isNotEmpty()) {
                val value = number.toInt()
                acc += value
            }
            if (calcMax) {
                val maxToReplace = maxes.indexOfFirst { it < acc }
                if (maxToReplace != -1) {
                    maxes[maxToReplace] = acc
                    maxes.sort()
                }
                acc = 0
            }
        }
        return maxes.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
