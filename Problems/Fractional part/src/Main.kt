fun main() {
    val num = readLine()!!.trim().toDouble().toString()
    println(num[num.indexOf('.') + 1])
}
