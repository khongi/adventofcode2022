fun main() {
    fun Char.getPriority(): Int {
        return if (isLowerCase()) {
            this - 'a' + 1
        } else {
            this - 'A' + 27
        }
    }

    fun Set<Char>.findCommon(other: String): Set<Char> {
        return toCharArray().intersect(other.asIterable().toSet())
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach { rucksack ->
            val firstHalf = rucksack.substring(0..rucksack.lastIndex/2)
            val secondHalf = rucksack.substring(rucksack.length/2..rucksack.lastIndex)

            sum += firstHalf.asIterable().toSet().findCommon(secondHalf).sumOf { it.getPriority() }
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        val groups = input.chunked(3)
        var sum = 0
        groups.forEach { group ->
            val (first, second, third) = group
            sum += first
                .asIterable()
                .toSet()
                .findCommon(second)
                .findCommon(third)
                .sumOf { it.getPriority() }
        }
        return sum
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
