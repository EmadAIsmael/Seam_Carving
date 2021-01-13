class IceCreamOrder(val price: Int) {

    constructor(popsicles: Int, price: Int = 7 * popsicles) : this(price)

    constructor(scoops: Int, toppings: Int, price: Int = 5 * scoops + 2 * toppings) : this(price)
}
