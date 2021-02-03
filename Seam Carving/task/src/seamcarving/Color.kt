package seamcarving

class Color(val value: Int) {
    val a: Int get() = (value shr 24) and 0xff
    val r: Int get() = (value shr 16) and 0xff
    val g: Int get() = (value shr 8) and 0xff
    val b: Int get() = (value shr 0) and 0xff

    constructor(a: Int, r: Int, g: Int, b: Int) : this(
        ((a and 0xff) shl 24) or
                ((r and 0xff) shl 16) or
                ((g and 0xff) shl 8) or
                ((b and 0xff) shl 0)
    )

    fun toArray(): IntArray {
        return intArrayOf(a, r, g, b)
    }

    operator fun component1(): Int {
        return (value shr 24) and 0xff
    }

    operator fun component2(): Int {
        return (value shr 16) and 0xff
    }

    operator fun component3(): Int {
        return (value shr 8) and 0xff
    }

    operator fun component4(): Int {
        return (value shr 0) and 0xff
    }
}
