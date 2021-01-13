import kotlin.math.pow


fun main() {
    val (a, b) = readLine()!!.split(" ").map { it.toDouble() }

    println(a.pow(b))
}
