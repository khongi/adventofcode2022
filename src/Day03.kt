fun main() {
    fun Char.getPriority(): Int {
        return if (isLowerCase()) {
            this - 'a' + 1
        } else {
            this - 'A' + 27
        }
    }

    fun String.findCommon(other: String): Set<Char> {
        return toCharArray().intersect(other.asIterable().toSet())
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach { rucksack ->
            val firstHalf = rucksack.substring(0..rucksack.lastIndex/2)
            val secondHalf = rucksack.substring(rucksack.length/2..rucksack.lastIndex)

            sum += firstHalf.findCommon(secondHalf).sumOf { it.getPriority() }
        }

        return sum
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)

    val input = readInput("Day03")
    println(part1(input))
}
