fun main(args: Array<String>) {
    if (args.size < 3) {
        println("Invalid number of program arguments")
    }
    for ((idx, arg) in args.withIndex())
        println("Argument $idx: $arg It has ${arg.length} characters")
}
