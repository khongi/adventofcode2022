fun main() {
    fun part1(input: List<String>): Int {
        var x = 1
        var cmd = 0
        var add = false
        var str = 0
        for (cycles in 1..220) {
            when (cycles) {
                20, 60, 100, 140, 180, 220 -> { str += cycles * x }
            }

            input[cmd].split(" ").run {
                if (size == 1) {
                    cmd++
                } else {
                    if (add) {
                        x += last().toInt()
                        cmd++
                        add = false
                    } else {
                        add = true
                    }
                }
            }
        }

        return str
    }

    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)

    val input = readInput("Day10")
    println(part1(input))
}
