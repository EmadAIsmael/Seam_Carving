fun main(args: Array<String>) {
    val (p, v) = Array(2) { readLine()!! }

    val f = ::finalAmount
    val parm = f.parameters.find { it.name == p }
    if (parm == null)
        println(finalAmount())
    else
        println(f.callBy(mapOf(parm to v.toInt())))
}

fun finalAmount(amount: Int = 1000, percent: Int = 5, years: Int = 10): Int =
    (amount * Math.pow((1.0 + percent.toDouble() / 100.0), years.toDouble())).toInt()
