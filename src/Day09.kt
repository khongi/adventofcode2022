import kotlin.math.abs

data class Position(val x: Int, val y: Int)

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

fun Char.toDirection(): Direction = when (this) {
    'U' -> Direction.UP
    'D' -> Direction.DOWN
    'L' -> Direction.LEFT
    'R' -> Direction.RIGHT
    else -> error("$this is not a valid direction")
}

fun Position.move(direction: Direction, by: Int): Position {
    return when (direction) {
        Direction.UP -> copy(y = y + by)
        Direction.DOWN -> copy(y = y - by)
        Direction.LEFT -> copy(x = x - by)
        Direction.RIGHT -> copy(x = x + by)
    }
}

fun Position.move(byX: Int = 0, byY: Int = 0): Position = copy(x = x + byX, y = y + byY)

fun Position.moveTowards(towards: Position): Position {
    if (isTouching(towards)) {
        return this
    }
    val isSameX = x == towards.x
    val isSameY = y == towards.y

    val differenceX = towards.x - x
    val differenceY = towards.y - y

    val (byX, byY) = if (!isSameX && !isSameY) {
        if (abs(differenceX) > abs(differenceY)) {
            differenceX.towardsZero() to differenceY
        } else if (abs(differenceY) > abs(differenceX)) {
            differenceX to differenceY.towardsZero()
        } else {
            differenceX.towardsZero() to differenceY.towardsZero()
        }
    } else if (isSameX) {
        0 to differenceY.towardsZero()
    } else {
        differenceX.towardsZero() to 0
    }

    return move(byX = byX, byY = byY)
}

fun Position.isTouching(other: Position): Boolean {
    val distanceX = abs(other.x - x)
    val distanceY = abs(other.y - y)
    return distanceX <= 1 && distanceY <= 1
}

fun Int.towardsZero(): Int {
    return if (this > 0) {
        this - 1
    } else if (this < 0) {
        this + 1
    } else {
        0
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        var head = Position(0, 0)
        var tail = Position(0, 0)
        val visited = mutableSetOf<Position>()
        input.map { it.split(" ") }.map { it[0][0].toDirection() to it[1].toInt() }.forEach { (dir, by) ->
            repeat(by) {
                head = head.move(dir, 1)
                tail = tail.moveTowards(head)
                visited.add(tail.copy())
            }
        }
        return visited.size
    }

    fun part2(input: List<String>): Int {
        val rope = buildList(10) { repeat(10) { add(Position(0, 0)) } }.toMutableList()
        val visited = mutableSetOf(Position(0, 0))
        input.map { it.split(" ") }.map { it[0][0].toDirection() to it[1].toInt() }.forEach { (dir, by) ->
            repeat(by) {
                rope[0] = rope[0].move(dir, 1)
                for (i in 1 until rope.size) {
                    rope[i] = rope[i].moveTowards(rope[i - 1])
                }
                visited.add(rope.last())
            }
        }
        return visited.size
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 1)
    val testInput2 = readInput("Day09_test2")
    check(part2(testInput2) == 36)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
