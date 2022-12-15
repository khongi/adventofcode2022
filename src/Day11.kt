class Monkey(chunk: List<String>) {
    val items = mutableListOf<Long>()
    val testNumber: Int
    var inspectCount = 0
        private set
    private val left: String
    private val right: String
    private val operation: String
    private val testTrueMonkey: Int
    private val testFalseMonkey: Int

    init {
        items.addAll(chunk[1].substringAfter(": ").split(", ").map { it.toLong() })
        val (l, o, r) = chunk[2].substringAfter("= ").split(" ")
        left = l
        right = r
        operation = o
        testNumber = chunk[3].substringAfter("by ").toInt()
        testTrueMonkey = chunk[4].substringAfter("monkey ").toInt()
        testFalseMonkey = chunk[5].substringAfter("monkey ").toInt()
    }

    fun operate(old: Long): Long {
        val l = if (left == "old") old else left.toLong()
        val r = if (right == "old") old else right.toLong()
        return when (operation) {
            "+" -> l + r
            "*" -> l * r
            else -> error("illegal operation: $operation")
        }
    }

    fun test(item: Long): Int {
        inspectCount++
        return if (item % testNumber == 0L) {
            testTrueMonkey
        } else {
            testFalseMonkey
        }
    }
}

fun main() {
    fun List<Int>.findLcm(): Int {
        var lcm = max()
        while (true) {
            if (count { lcm % it == 0 } != size) {
                lcm++
            } else {
                return lcm
            }
        }
    }

    fun round(monkeys: List<Monkey>, lower: (Long) -> Long) {
        monkeys.forEach { monkey ->
            val iterator = monkey.items.iterator()
            while (iterator.hasNext()) {
                val item = iterator.next()
                val newItem = monkey.operate(item)
                val loweredItem = lower(newItem)
                val monkeyToThrowTo = monkey.test(loweredItem)
                iterator.remove()
                monkeys[monkeyToThrowTo].items.add(loweredItem)
            }
        }
    }

    fun part1(input: List<String>): Int {
        val monkeys = mutableListOf<Monkey>()
        input.chunked(7).forEach { monkeys.add(Monkey(it)) }
        repeat(20) { round(monkeys) { it / 3 } }
        monkeys.sortByDescending { it.inspectCount }

        return monkeys[0].inspectCount * monkeys[1].inspectCount
    }

    fun part2(input: List<String>): Long {
        val monkeys = mutableListOf<Monkey>()
        input.chunked(7).forEach { monkeys.add(Monkey(it)) }
        val lcm = monkeys.map { it.testNumber }.findLcm()
        repeat(10000) { round(monkeys) { it % lcm } }
        monkeys.sortByDescending { it.inspectCount }
        return monkeys[0].inspectCount.toLong() * monkeys[1].inspectCount
    }

    val testInput = readInput("Day11_test")
    check(part1(testInput) == 10605)
    check(part2(testInput) == 2713310158L)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
