import kotlin.math.hypot

fun perimeter(
    x1: Double,
    y1: Double,
    x2: Double,
    y2: Double,
    x3: Double,
    y3: Double,
    x4: Double = x1,
    y4: Double = y1
): Double {
    return if (!(x1 == x4 && y1 == y4))
        return hypot(x2 - x1, y2 - y1) +
                hypot(x3 - x2, y3 - y2) +
                hypot(x4 - x3, y4 - y3) +
                hypot(x1 - x4, y1 - y4)
    else Math.hypot(x2 - x1, y2 - y1) +
            hypot(x3 - x2, y3 - y2) +
            hypot(x1 - x3, y1 - y3)
}
