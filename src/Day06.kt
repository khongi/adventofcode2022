fun main() {
    fun detectMarker(stream: String, uniqueChars: Int): Int {
        val readChars = mutableListOf<Char>()
        stream.forEachIndexed { index, c ->
            readChars.add(c)
            if (readChars.takeLast(uniqueChars).toSet().count() == uniqueChars) {
                return index + 1
            }
        }
        return -1
    }

    fun part1(input: List<String>): Int = detectMarker(input[0], 4)

    fun part2(input: List<String>): Int = detectMarker(input[0], 14)

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
