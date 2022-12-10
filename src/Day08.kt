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

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)

    val input = readInput("Day08")
    println(part1(input))
}
