fun main() {
    val parm = readLine()!!
    val parmVal = readLine()!!.toInt()

    println(
        when (parm) {
            "old" -> secondaryMarketPrice(old = parmVal)
            "passed" -> secondaryMarketPrice(passed = parmVal)
            "speed" -> secondaryMarketPrice(speed = parmVal)
            "auto" -> secondaryMarketPrice(auto = parmVal)
            else -> secondaryMarketPrice()
        }
    )
}

fun secondaryMarketPrice(old: Int = 5, passed: Int = 100_000, speed: Int = 120, auto: Int = 0): Int {
    var marketPrice = 20_000
    marketPrice -= (2_000 * old)
    marketPrice += if (speed < 120) -(120 - speed) * 100 else (speed - 120) * 100
    marketPrice -= 200 * (passed / 10_000)
    marketPrice += if (auto == 1) 1_500 else 0

    return marketPrice
}
