package seamcarving

import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import javax.imageio.ImageIO


fun main() {
    val scanner = Scanner(System.`in`)

    print("Enter rectangle width:")
    val width = scanner.nextInt()

    print("Enter rectangle height:")
    val height = scanner.nextInt()

    print("Enter output image name:")
    val fileName = scanner.next()

    // 1. create image
    val img = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)

    // 2. draw shapes
    val g: Graphics2D = img.createGraphics()
    g.color = Color.black
    g.fillRect(0, 0, width - 1, height - 1)

    // drawLine(int x1, int y1, int x2, int y2)
    g.color = Color.red
    val (x1, y1, x2, y2) = listOf(0, 0, width - 1, height - 1)
    g.drawLine(x1, y1, x2, y2)
    g.drawLine(x2, y1, x1, y2)

    // 3. save image
    val f = File(fileName)
    ImageIO.write(img, "PNG", f)

    // 4. clean up
    g.dispose()

}
