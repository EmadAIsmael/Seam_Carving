import java.lang.Math.*

fun main() {
    val (a, b) = readLine()!!.split(" ").map { it.trim().toDouble() }
    val (x, y) = readLine()!!.split(" ").map { it.trim().toDouble() }

    val productVectors = a * x + b * y
    val magnitude1 = sqrt(pow(a, 2.0) + pow(b, 2.0))
    val magnitude2 = sqrt(pow(x, 2.0) + pow(y, 2.0))
    val cos = productVectors / (magnitude1 * magnitude2)
    println(toDegrees(acos(cos)))
}
