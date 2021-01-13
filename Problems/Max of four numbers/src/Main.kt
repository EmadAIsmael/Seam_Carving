fun main(args: Array<String>) {
    val (a, b, c, d) = readLine()!!.split(" ").map { it.toInt() }

    println(Math.max(Math.max(a, b), Math.max(c, d)))
}
