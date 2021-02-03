package seamcarving

import java.awt.image.BufferedImage
import java.awt.image.RenderedImage
import java.io.File
import java.io.IOException
import java.lang.StrictMath.abs
import javax.imageio.ImageIO
import kotlin.math.pow
import kotlin.math.sqrt

class Picture {

    private var width: Int = 0
    private var height: Int = 0
    private var img: BufferedImage

    constructor(width: Int, height: Int) {
        // create a blank image with width by height
        this.img = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
        this.width = this.img.width
        this.height = this.img.height
    }

    constructor(fileName: String) {
        // read image from file
        val imgFile = File(fileName)
        this.img = ImageIO.read(imgFile)
        this.width = this.img.width
        this.height = this.img.height
    }

    constructor(img: RenderedImage) {
        this.img = img as BufferedImage
        this.width = img.width
        this.height = img.height
    }

    fun save(fileName: String) {
        val f = File(fileName)
        try {
            if (!ImageIO.write(this.img, "PNG", f)) {
                throw RunTimeException("Unexpected error writing image")
            }
        } catch (e: IOException) {
            println(e.message)
        } catch (e: RunTimeException) {
            println(e.message)
        }
    }

    class RunTimeException(message: String) : Throwable()

    fun width() = this.width
    fun height() = this.height

    // getRGB(col, row)
    fun getRGB(y: Int, x: Int): Int {
        // returns an Int representing color value at x, y.
        return this.img.getRGB(x, y)
    }

    // setRGB(col, row, value)
    fun setRGB(y: Int, x: Int, color: Int) {
        // sets color value at x, y to value of parameter color.
        this.img.setRGB(x, y, color)
    }

    fun asMutableList(): MutableList<MutableList<Int>> {
        val p = MutableList(this.height) { MutableList(this.width) { 0 }  }
        for (y in 0 until this.height)
            for (x in 0 until this.width)
                p[y][x] = this.img.getRGB(x, y)
        return p
    }

    fun asArray(): Array<IntArray> {
        val p = Array(this.height) { IntArray(this.width) { 0 }  }
        for (y in 0 until this.height)
            for (x in 0 until this.width)
                p[y][x] = this.img.getRGB(x, y)
        return p
    }

    // energy of pixel at column x and row y
    fun energy(y: Int, x: Int): Double {
        fun deltaXBorderCheck(y: Int, x: Int): Pair<Int, Int> {
            val xd = when (x) {
                0 -> x + 1
                this.width() - 1 -> this.width() - 2
                else -> x
            }
            return Pair(y, xd)
        }

        fun deltaYBorderCheck(y: Int, x: Int): Pair<Int, Int> {
            val yd = when (y) {
                0 -> y + 1
                this.height() - 1 -> this.height() - 2
                else -> y
            }
            return Pair(yd, x)
        }

        fun deltaXSqrd(y: Int, x: Int): Double {
            val (dy, dx) = deltaXBorderCheck(y, x)
            val left = Color(this.img.getRGB(dx - 1, dy )).toArray()
            val right = Color(this.img.getRGB(dx + 1, dy)).toArray()
            val diff = (left zip right).map { pair -> abs(pair.first - pair.second) }
            return diff.map { it.toDouble().pow(2.0) }
                .sum()
        }

        fun deltaYSqrd(y: Int, x: Int): Double {
            val (dy, dx) = deltaYBorderCheck(y, x)
            val up = Color(this.img.getRGB(dx, dy - 1)).toArray()
            val down = Color(this.img.getRGB(dx, dy + 1)).toArray()
            val diff = (up zip down).map { pair -> abs(pair.first - pair.second) }
            return diff.map { it.toDouble().pow(2.0) }
                .sum()
        }

        return sqrt(deltaXSqrd(y, x) + deltaYSqrd(y, x))
    }

    fun pixels(): IntArray {
        return img.getRGB(0,0, img.width, img.height, null, 0, img.width)
    }

    companion object {
        fun getImageFromArray(pixels: IntArray, width: Int, height: Int): RenderedImage {
            val image = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
            image.setRGB(0,0, width, height, pixels, 0, width)
            return image
        }
    }
}
