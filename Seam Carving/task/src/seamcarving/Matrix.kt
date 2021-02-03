package seamcarving

class Matrix(val rows: Int = 1, val columns: Int = 1) {
    private val mat = Array(rows) { IntArray(columns) }

//    constructor(arr: Array<DoubleArray>) : this(arr.size, arr[0].size) {
//        for (r in 0 until rows)
//            for (c in 0 until columns)
//                mat[r][c] = arr[r][c]
//    }

    constructor(arr: Array<IntArray>) : this(arr.size, arr[0].size) {
        for (r in 0 until rows)
            for (c in 0 until columns)
                mat[r][c] = arr[r][c]
    }

    constructor(arr: MutableList<MutableList<Int>>) : this(arr.size, arr[0].size) {
        for (r in 0 until rows)
            for (c in 0 until columns)
                mat[r][c] = arr[r][c]
    }

    fun transpose(): MutableList<MutableList<Int>> {
        val transpose = MutableList(columns) { MutableList<Int>(rows) { 0 } }
        for (r in 0 until rows)
            for (c in 0 until columns)
                transpose[c][r] = mat[r][c]
        return transpose
    }
}
