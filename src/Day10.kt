fun main() {
    fun part1(input: List<String>): Int {
        var x = 1
        var cmd = 0
        var add = false
        var str = 0
        for (cycles in 1..220) {
            when (cycles) {
                20, 60, 100, 140, 180, 220 -> {
                    str += cycles * x
                }
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

    fun part2(input: List<String>) {
        val crt: List<MutableMap<Int, Char>> = buildList(6) { repeat(6) { add(mutableMapOf()) } }
        var x = 1
        var cmd = 0
        var add = false
        var cycle = 0

        for (row in 5 downTo 0) {
            for (col in 0 until 40) {
                cycle++
                val pixel = when (col) {
                    x, x + 1, x - 1 -> '#'
                    else -> '.'
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

                crt[row][col] = pixel
            }
        }

        for (row in 5 downTo 0) {
            for (col in 0 until 40) {
                print(crt[row][col])
            }
            println()
        }
    }

    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
    part2(testInput)

    val input = readInput("Day10")
    println(part1(input))
    part2(input)
}
