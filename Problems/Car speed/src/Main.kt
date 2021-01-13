fun main(args: Array<String>) {
    val speed = readLine()!!.toInt()
    val x = readLine()!!
    val limit = if (x == "no limit") 60 else x.toInt()

    if (speed > limit)
        println("Exceeds the limit by ${speed - limit} kilometers per hour")
    else
        println("Within the limit")
}