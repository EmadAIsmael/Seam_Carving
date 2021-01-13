package seamcarving

import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class Picture {

    private var width: Int = 0
    private var height = 0
    private var img: BufferedImage

    constructor(width: Int, height: Int) {
        // create a blank image with width by height
        this.img = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
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

    fun getRGB(x: Int, y: Int): IntArray {
        // returns an Int representing color value at x, y.
        return unpackColor(this.img.getRGB(x, y))
    }

    fun setRGB(x: Int, y: Int, color: Int) {
        // sets color value at x, y to value of parameter color.
        this.img.setRGB(x, y, color)
    }

    fun unpackColor(color: Int): IntArray {
        // returns 4 (32 bit Int) numbers
        // representing the components of an RGB color;
        // alpha, red, green, and blue
        return intArrayOf(
            (color shr 24) and 0xff,            // a
            (color shr 16) and 0xff,            // r
            (color shr 8) and 0xff,             // g
            (color shr 0) and 0xff              // b
        )
    }

    // packs 4 Ints into one Int value representing
    // an RGB color.
    fun packColor(a: Int, r: Int, g: Int, b: Int): Int =
        ((a and 0xff) shl 24) or
                ((r and 0xff) shl 16) or
                ((g and 0xff) shl 8) or
                ((b and 0xff) shl 0)
}


class SeamCarver(val picture: Picture) {

    private var outFile: String = ""
    private var maxEnergyValue = 0.0

    constructor(picture: Picture, outFile: String) : this(picture) {
        this.outFile = outFile
    }

    // current picture
    fun picture(): Picture = this.picture

    // width of current picture
    fun width(): Int = this.picture.width()

    // height of current picture
    fun height(): Int = this.picture.height()

    // energy of pixel at column x and row y
    fun energy(x: Int, y: Int): Double {
        fun deltaXBorderCheck(x: Int, y: Int): Pair<Int, Int> {
            val xd = when (x) {
                0 -> x + 1
                this.width() - 1 -> this.width() - 2
                else -> x
            }
            return Pair(xd, y)
        }

        fun deltaYBorderCheck(x: Int, y: Int): Pair<Int, Int> {
            val yd = when (y) {
                0 -> y + 1
                this.height() - 1 -> this.height() - 2
                else -> y
            }
            return Pair(x, yd)
        }

        fun deltaXSqrd(x: Int, y: Int): Double {
            val (dx, dy) = deltaXBorderCheck(x, y)
            val left = this.picture.getRGB(dx - 1, dy)
            val right = this.picture.getRGB(dx + 1, dy)
            val diff = (left zip right).map { pair -> abs(pair.first - pair.second) }
            return diff.map { it.toDouble().pow(2.0) }
                .sum()
        }

        fun deltaYSqrd(x: Int, y: Int): Double {
            val (dx, dy) = deltaYBorderCheck(x, y)
            val up = this.picture.getRGB(dx, dy - 1)
            val down = this.picture.getRGB(dx, dy + 1)
            val diff = (up zip down).map { pair -> abs(pair.first - pair.second) }
            return diff.map { it.toDouble().pow(2.0) }
                .sum()
        }

        return sqrt(deltaXSqrd(x, y) + deltaYSqrd(x, y))
    }

    fun findMaxEnergyValue(): Double {
        var maxEnergyValue = 0.0
        for (x in 0 until this.width())
            for (y in 0 until this.height()) {
                val pixelEnergy = energy(x, y)
                if (pixelEnergy > maxEnergyValue)
                    maxEnergyValue = pixelEnergy
            }
        return maxEnergyValue
    }

    fun intensity(x: Int, y: Int): Int {
        return (255.0 * energy(x, y) / this.maxEnergyValue).toInt()
    }

    fun saveGreyScale() {
        this.maxEnergyValue = findMaxEnergyValue()
        val greyScale = Picture(width(), height())

        for (x in 0 until this.width())
            for (y in 0 until this.height()) {
                val pixelIntensity = intensity(x, y)
                val clr = picture.getRGB(x, y)
                clr[1] = pixelIntensity
                clr[2] = pixelIntensity
                clr[3] = pixelIntensity
                val (a, r, g, b) = clr
                greyScale.setRGB(x, y, greyScale.packColor(a, r, g, b))
            }
        greyScale.save(outFile)
    }

}


fun main(args: Array<String>) {
    // 1. Read arguments
    if (args.size != 4) {
        println("Invalid number of arguments.")
        return
    }
    val inFile = args[args.indexOf("-in") + 1]
    val outFile = args[args.indexOf("-out") + 1]

    // 2. save a greyscale version of input picture
    val inPicture = Picture(inFile)
    val seamCarver = SeamCarver(inPicture, outFile)
    seamCarver.saveGreyScale()

}
