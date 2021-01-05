fun compose(g: (Int) -> Int, h: (Int) -> Int): (Int) -> Int {
    return { it -> g(h(it)) }
}
