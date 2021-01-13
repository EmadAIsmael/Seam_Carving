import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.math.min
import kotlin.math.max


fun main(args: Array<String>) {
    val (a, b, c) = Array(3) { readLine()!!.trim().toDouble() }

    val x1 = (-b - sqrt(b.pow(2) - 4.0 * a * c)) / (2.0 * a)
    val x2 = (-b + sqrt(b.pow(2) - 4.0 * a * c)) / (2.0 * a)

    println("${min(x1, x2)} ${max(x1, x2)}")
}
