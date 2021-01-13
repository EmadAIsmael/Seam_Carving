class EspressoMachine(val costPerServing: Float) {

    constructor(
        coffeeCapsulesCount: Int,
        totalCost: Float,
        costPerServing: Float = totalCost / coffeeCapsulesCount
    ) : this(costPerServing)

    constructor(
        coffeeBeansWeight: Float,
        totalCost: Float,
        costPerServing: Float = totalCost / (coffeeBeansWeight /  10.6f).toInt()
    ) : this(costPerServing)
}
