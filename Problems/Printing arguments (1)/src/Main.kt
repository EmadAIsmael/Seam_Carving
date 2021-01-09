fun main(args: Array<String>) {
    if (args.size != 3) {
        println("Invalid number of program arguments")
    } else {
        args.forEachIndexed { idx, arg -> println("Argument ${idx + 1}: ${arg}. It has ${arg.length} characters") }
    }
}
