package seamcarving


fun main(args: Array<String>) {
    // 1. Read arguments
    if (args.size != 8) {
        println("Invalid number of arguments.")
        return
    }
    val inFile = args[args.indexOf("-in") + 1]
    val outFile = args[args.indexOf("-out") + 1]
    // number of vertical seams to remove
    val widthToRemove = args[args.indexOf("-width") + 1]
    // number of horizontal seams to remove
    val heightToRemove = args[args.indexOf("-height") + 1]

    // 2. save a greyscale version of input picture
    // 3. find seam and color it red.
    val inPicture = Picture(inFile)
//    val seamCarver = SeamCarver(inPicture)
    val seamCarver = Seams(inPicture)
//    seamCarver.saveGreyScale(outFile)
//    seamCarver.drawVerticalSeam(outFile)
//    seamCarver.drawHorizontalSeam(outFile)
    seamCarver.resize(widthToRemove.toInt(), heightToRemove.toInt() , outFile)
}
