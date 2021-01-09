package seamcarving

import java.awt.Color
//import java.awt.Graphics2D
//import java.awt.image.BufferedImage
import java.io.File
//import java.util.*
import javax.imageio.ImageIO


fun main(args: Array<String>) {

    // 1. Read arguments
    if (args.size != 4) {
        println("Invalid number of arguments.")
        return
    }
    val inFile = args[args.indexOf("-in") + 1]
    val outFile = args[args.indexOf("-out") + 1]

    // 2. read input file; get image width & height
    val theFile = File(inFile)
    val inImg = ImageIO.read(theFile)
    val width = inImg.width
    val height = inImg.height

    // 3. manipulate pixels
    for (x in 0 until width)
        for (y in 0 until height) {
            val color = inImg.getRGB(x, y)
            // unpack color int
            val a = (color shr 24) and 0xff
            val r = (color shr 16) and 0xff
            val g = (color shr 8) and 0xff
            val b = (color) and 0xff
            // pack new color int
            val newColor =
                (((255 - a) and 0xff) shl 24) or
                        (((255 - r) and 0xff) shl 16) or
                        (((255 - g) and 0xff) shl 8) or
                        (((255 - b) and 0xff)
                                )
            inImg.setRGB(x, y, newColor)
        }

    // 4. save to ouput image file
    val f = File(outFile)
    ImageIO.write(inImg, "PNG", f)

}
