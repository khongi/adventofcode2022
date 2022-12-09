sealed class Node(
    val name: String,
    val parent: Dir?,
    val level: Int,
) {
    abstract fun calculateSize(): Int
    abstract fun print()
}

class File(
    name: String,
    parent: Dir,
    level: Int,
    val size: Int,
) : Node(name, parent, level) {
    override fun calculateSize() = size
    override fun print() = println("- $name (file, size=$size)".prependIndent("  ".repeat(level)))
}

class Dir(
    name: String,
    parent: Dir?,
    level: Int,
    val nodes: MutableList<Node> = mutableListOf(),
) : Node(name, parent, level) {
    override fun calculateSize() = nodes.sumOf { it.calculateSize() }
    override fun print() {
        println("- $name (dir, size=${calculateSize()})".prependIndent("  ".repeat(level)))
        nodes.forEach { it.print() }
    }
}

fun main() {
    fun buildTree(input: List<String>): Dir {
        val root = Dir("/", parent = null, level = 0)
        var currentNode = root
        input.drop(1).forEach { line ->
            val elements = line.split(' ')
            when (elements[0]) {
                "$" -> {
                    when (elements[1]) {
                        "cd" -> {
                            val destination = elements[2]
                            currentNode = if (destination == "..") {
                                currentNode.parent ?: error("no parent to cd to")
                            } else {
                                currentNode.nodes.first { it.name == destination } as? Dir ?: error("no such dir")
                            }
                        }

                        "ls" -> {
                            // no-op
                        }
                    }
                }

                "dir" -> {
                    if (currentNode.nodes.firstOrNull { it.name == elements[1] } == null) {
                        currentNode.nodes.add(
                            Dir(
                                name = elements[1],
                                parent = currentNode,
                                level = currentNode.level + 1,
                            )
                        )
                    }
                }

                else -> {
                    if (currentNode.nodes.firstOrNull { it.name == elements[1] } == null) {
                        currentNode.nodes.add(
                            File(
                                name = elements[1],
                                parent = currentNode,
                                level = currentNode.level + 1,
                                size = elements[0].toInt(),
                            )
                        )
                    }
                }
            }
        }
        return root
    }

    fun Dir.findDirectories(compare: (Int) -> Boolean): List<Dir> {
        val children = nodes.filterIsInstance<Dir>().flatMap { it.findDirectories(compare) }
        return if (compare(calculateSize())) {
            children + this
        } else {
            children
        }
    }

    fun part1(input: List<String>): Int {
        val root = buildTree(input)
        return root.findDirectories { it <= 100_000 }.sumOf { it.calculateSize() }
    }


    fun part2(input: List<String>): Int {
        val total = 70_000_000
        val unusedNeeded = 30_000_000

        val root = buildTree(input)
        val unusedSpace = total - root.calculateSize()
        val spaceNeeded = unusedNeeded - unusedSpace

        return root.findDirectories { it >= spaceNeeded }.minOf { it.calculateSize() }
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
