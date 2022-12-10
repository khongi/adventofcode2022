fun main() {
    fun buildMatrix(input: List<String>): List<List<Int>> {
        val mtx: MutableList<MutableList<Int>> = mutableListOf()
        input.forEach { line ->
            val row = mutableListOf<Int>()
            line.forEach { row.add(it.digitToInt()) }
            mtx.add(row)
        }
        return mtx
    }

    fun List<List<Int>>.buildCol(colIndex: Int): List<Int> {
        val col = mutableListOf<Int>()
        for (element in this) {
            col.add(element[colIndex])
        }
        return col
    }

    fun List<List<Int>>.isVisible(rowIndex: Int, colIndex: Int): Boolean {
        val height = this[rowIndex][colIndex]
        val row = this[rowIndex]
        val col = buildCol(colIndex)

        val leftCheck = row.subList(0, colIndex + 1).count { it >= height } == 1
        val rightCheck = row.subList(colIndex, row.size).count { it >= height } == 1
        val topCheck = col.subList(0, rowIndex + 1).count { it >= height } == 1
        val bottomCheck = col.subList(rowIndex, col.size).count { it >= height } == 1

        return leftCheck || rightCheck || topCheck || bottomCheck
    }

    fun calculateScore(subList: List<Int>, height: Int, edgeValue: Int): Int {
        val closestTall = subList.indexOfFirst { it >= height }
        return if (closestTall == -1) {
            edgeValue
        } else {
            closestTall + 1
        }
    }

    fun List<List<Int>>.scenicScore(rowIndex: Int, colIndex: Int): Int {
        val height = this[rowIndex][colIndex]
        val row = this[rowIndex]
        val col = buildCol(colIndex)

        val leftScore = calculateScore(row.subList(0, colIndex).reversed(), height, colIndex)
        val rightScore = calculateScore(row.subList(colIndex + 1, row.size), height, row.size - colIndex - 1)
        val topScore = calculateScore(col.subList(0, rowIndex).reversed(), height, rowIndex)
        val bottomScore = calculateScore(col.subList(rowIndex + 1, col.size), height, col.size - rowIndex - 1)

        return leftScore * rightScore * topScore * bottomScore
    }

    fun part1(input: List<String>): Int {
        val rows = input[0].length
        val cols = input.size
        val mtx = buildMatrix(input)

        var visibleCount = 0
        repeat(rows) { row ->
            repeat(cols) { col ->
                if (mtx.isVisible(row, col)) { visibleCount++ }
            }
        }
        return visibleCount
    }

    fun part2(input: List<String>): Int {
        val rows = input[0].length
        val cols = input.size
        val mtx = buildMatrix(input)

        var max = 0
        for (r in 1 until rows - 1) {
            for (c in 1 until cols - 1) {
                val score = mtx.scenicScore(r, c)
                if (score > max) {
                    max = score
                }
            }
        }
        return max
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
