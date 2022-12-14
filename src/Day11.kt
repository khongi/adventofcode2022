class Monkey(chunk: List<String>) {
    val items = mutableListOf<Int>()
    var inspectCount = 0
        private set
    private val monkeyNumber: Int
    private val left: String
    private val right: String
    private val operation: String
    private val testNumber: Int
    private val testTrueMonkey: Int
    private val testFalseMonkey: Int

    init {
        monkeyNumber = chunk[0].substringBefore(":").split(" ")[1].toInt()
        items.addAll(chunk[1].substringAfter(": ").split(", ").map { it.toInt() })
        val (l, o, r) = chunk[2].substringAfter("= ").split(" ")
        left = l
        right = r
        operation = o
        testNumber = chunk[3].substringAfter("by ").toInt()
        testTrueMonkey = chunk[4].substringAfter("monkey ").toInt()
        testFalseMonkey = chunk[5].substringAfter("monkey ").toInt()
    }

    fun operate(old: Int): Int {
        val l = if (left == "old") old else left.toInt()
        val r = if (right == "old") old else right.toInt()
        return when (operation) {
            "+" -> l + r
            "*" -> l * r
            else -> error("illegal operation: $operation")
        }
    }

    fun test(item: Int): Int {
        inspectCount++
        return if (item % testNumber == 0) {
            testTrueMonkey
        } else {
            testFalseMonkey
        }
    }
}

fun main() {
    fun Int.calm(): Int = this / 3

    fun round(monkeys: List<Monkey>) {
        monkeys.forEach { monkey ->
            val iterator = monkey.items.iterator()
            while (iterator.hasNext()) {
                val item = iterator.next()
                val newItem = monkey.operate(item)
                val calmItem = newItem.calm()
                val monkeyToThrowTo = monkey.test(calmItem)
                iterator.remove()
                monkeys[monkeyToThrowTo].items.add(calmItem)
            }
        }
    }

    fun part1(input: List<String>): Int {
        val monkeys = mutableListOf<Monkey>()
        input.chunked(7).forEach { monkeys.add(Monkey(it)) }
        repeat(20) { round(monkeys) }
        monkeys.sortByDescending { it.inspectCount }

        return monkeys[0].inspectCount * monkeys[1].inspectCount
    }

    val testInput = readInput("Day11_test")
    check(part1(testInput) == 10605)

    val input = readInput("Day11")
    println(part1(input))
}
