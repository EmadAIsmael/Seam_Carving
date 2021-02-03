// Do not initialize the backFromTheWall and returnedWatchman! It is already done.
val list = backFromTheWall.toMutableList()
list.add(returnedWatchman)
backFromTheWall = list.toTypedArray()
println(backFromTheWall.joinToString(", "))