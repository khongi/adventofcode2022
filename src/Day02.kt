sealed class HandGesture {
    abstract val score: Int
    abstract val defeats: HandGesture
    abstract val losesTo: HandGesture
}

object Rock : HandGesture() {
    override val score = 1
    override val defeats = Scissors
    override val losesTo = Paper
}

object Paper : HandGesture() {
    override val score: Int = 2
    override val defeats = Rock
    override val losesTo = Scissors
}

object Scissors : HandGesture() {
    override val score: Int = 3
    override val defeats = Paper
    override val losesTo = Rock
}

fun main() {
    fun Char.getHandGesture(): HandGesture {
        return when (this) {
            'A', 'X' -> Rock
            'B', 'Y' -> Paper
            'C', 'Z' -> Scissors
            else -> throw IllegalArgumentException("Input can't be $this")
        }
    }

    fun HandGesture.calculateRound(enemy: HandGesture): Int {
        val combatScore =  if (this.defeats == enemy) {
            6
        } else if (this.losesTo == enemy) {
            0
        } else {
            3
        }
        return combatScore + this.score
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach { line ->
            val them = line[0].getHandGesture()
            val us = line[2].getHandGesture()
            sum += us.calculateRound(them)
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
