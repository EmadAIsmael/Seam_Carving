fun main(args: Array<String>) {
    val (a, b, c) = Array(3) { readLine()!!.toInt() }

    // a, b, c: sides of the triangle
    // p ::= half perimeter
    // s = area of the triangle
    val p = (a + b + c) / 2.0
    val s = Math.sqrt(p * (p - a) * (p - b) * (p - c))
    println(s)
}
