package seamcarving

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt


class Seams(private val picture: Picture) {

    data class Weight(
        var y: Int = -1,
        var x: Int = -1,
        var weight: Double = 0.0,
        var from: Weight? = null
    )

    var picArray = picture.asMutableList()
    private var maxEnergyValue = 0.0

    // current picture
    fun picture(): Picture = this.picture

    // width of current picture
    private fun width(): Int = this.picture.width()

    // height of current picture
    private fun height(): Int = this.picture.height()

    // energy of pixel at column x and row y
    fun energy(y: Int, x: Int): Double {
        fun deltaXBorderCheck(y: Int, x: Int): Pair<Int, Int> {
            val xd = when (x) {
                0 -> x + 1
//                this.width() - 1 -> this.width() - 2
                picArray[0].size - 1 -> picArray[0].size - 2
                else -> x
            }
            return Pair(y, xd)
        }

        fun deltaYBorderCheck(y: Int, x: Int): Pair<Int, Int> {
            val yd = when (y) {
                0 -> y + 1
//                this.height() - 1 -> this.height() - 2
                picArray.size - 1 -> picArray.size - 2
                else -> y
            }
            return Pair(yd, x)
        }

        fun deltaXSqrd(y: Int, x: Int): Double {
            val (dy, dx) = deltaXBorderCheck(y, x)
//            val left = Color(this.picture.getRGB(dx - 1, dy)).toArray()
//            val right = Color(this.picture.getRGB(dx + 1, dy)).toArray()
            val left = Color(picArray[dy][dx - 1]).toArray()
            val right = Color(picArray[dy][dx + 1]).toArray()
            val diff = (left zip right).map { pair -> abs(pair.first - pair.second) }
            return diff.map { it.toDouble().pow(2.0) }
                .sum()
        }

        fun deltaYSqrd(y: Int, x: Int): Double {
            val (dy, dx) = deltaYBorderCheck(y, x)
//            val up = Color(this.img.getRGB(dx, dy - 1)).toArray()
//            val down = Color(this.img.getRGB(dx, dy + 1)).toArray()
            val up = Color(picArray[dy - 1][dx]).toArray()
            val down = Color(picArray[dy + 1][dx]).toArray()
            val diff = (up zip down).map { pair -> abs(pair.first - pair.second) }
            return diff.map { it.toDouble().pow(2.0) }
                .sum()
        }

        return sqrt(deltaXSqrd(y, x) + deltaYSqrd(y, x))
    }

    private fun findMaxEnergyValue(): Double {
        var maxEnergyValue = 0.0
        for (y in 0 until this.height())
            for (x in 0 until this.width()) {
                val pixelEnergy = energy(y, x)
                if (pixelEnergy > maxEnergyValue)
                    maxEnergyValue = pixelEnergy
            }
        return maxEnergyValue
    }

    private fun intensity(y: Int, x: Int): Int {
        return (255.0 * energy(y, x) / this.maxEnergyValue).toInt()
    }

    fun saveGreyScale(outFile: String) {
        this.maxEnergyValue = findMaxEnergyValue()
        val greyScale = Picture(width(), height())

        for (y in 0 until this.height())
            for (x in 0 until this.width()) {
                val pixelIntensity = intensity(y, x)
                var (a: Int, r: Int, g: Int, b: Int) = Color(picture.getRGB(y, x))

                r = pixelIntensity
                g = pixelIntensity
                b = pixelIntensity
                greyScale.setRGB(y, x, Color(a, r, g, b).value)
            }
        greyScale.save(outFile)
    }

    fun minWeight(w: Array<Weight>): Weight {
        var found = Weight(weight = Double.POSITIVE_INFINITY)
        for (r in w) {
            if (r.weight < found.weight) {
                found = r
            }
        }
        return found
    }

    private fun seamList(): Array<Array<Weight>> {

        val width = picArray[0].size
        val height = picArray.size
        val seamList = Array(height) { Array(width) { Weight() } }

        // intialize first row
        for (c in 0 until width) {
            seamList[0][c].y = 0
            seamList[0][c].x = c
            seamList[0][c].weight = energy(0, c)
        }
        // set next rows
        for (y in 1 until height)
            for (x in 0 until width) {
                val minimalCost = minWeight(
                    arrayOf(
                        if (x == 0) Weight(weight = Double.POSITIVE_INFINITY)
                        else seamList[y - 1][x - 1],
                        seamList[y - 1][x],
                        if (x + 1 == width) Weight(weight = Double.POSITIVE_INFINITY)
                        else seamList[y - 1][x + 1]
                    )
                )

                seamList[y][x] = Weight(
                    y, x,
                    energy(y, x) + minimalCost.weight,
                    minimalCost
                )
            }
        return seamList
    }

    fun findVerticalSeam(): IntArray {
        val seamList = seamList()
        return findSeam(seamList)
    }

    private fun findSeam(seamList: Array<Array<Weight>>): IntArray {
        val height = seamList.size
        val seam = IntArray(height)

        val seamLastRow = seamList[seamList.lastIndex]
        var minimalCost = minWeight(seamLastRow)
        // assign index to seam array lastIndex
        seam[height - 1] = minimalCost.x

        var y = height - 2
        while (y >= 0 && minimalCost != Weight()) {
            seam[y] = minimalCost.x
            minimalCost = minimalCost.from!!
            y--
        }

        return seam
    }

    fun drawVerticalSeam(outFile: String) {
        val seam = findVerticalSeam()

        for ((y, x) in seam.withIndex()) {
            this.picture.setRGB(y, x, Color(255, 255, 0, 0).value)
        }
        picture.save(outFile)
    }

    fun removeVerticalSeam() {
        val seam = findVerticalSeam()

        for ((r, c) in seam.withIndex())
            picArray[r].removeAt(c)
    }

    fun findHorizontalSeam(): IntArray {
        picArray = Matrix(picArray).transpose().toMutableList()
        val seamList = seamList()
        picArray = Matrix(picArray).transpose().toMutableList()
        return findSeam(seamList)
//        val seam = findSeam(seamList)
//        energyMap = Matrix(energyMap).transpose()
//        this.horizontalSeamList = seamList
//        return seam
    }

    fun removeHorizontalSeam() {
        val seam = findHorizontalSeam()
        for ((r, c) in seam.withIndex())
            picArray[r].removeAt(c)
    }

    fun drawHorizontalSeam(outFile: String) {
        val seam = findHorizontalSeam()

        for ((y, x) in seam.withIndex()) {
            // exchange x, y; matrix was transposed.
            this.picture.setRGB(x, y, Color(255, 255, 0, 0).value)
        }
        picture.save(outFile)
    }

    fun resize(
        widthToRemove: Int,
        heightToRemove: Int,
        outFile: String
    ) {

        val w = widthToRemove
        val h = heightToRemove

        repeat(w) {
            removeVerticalSeam()
        }

        val reduced = Picture(
            this.width() - widthToRemove,
            this.height() - heightToRemove
        )
        for (y in 0 until reduced.height())
            for (x in 0 until reduced.width()) {
                val pixel = picArray[y][x]
                reduced.setRGB(y, x, pixel)
            }
        reduced.save(outFile)
    }
}