class CinemaManager(
    val rows: Int,
    val seats: Int
) {


    companion object {
        val EXIT_STATUS = 0
        val INCORRECT_INPUT = 0
    }

    private val hallInfo = Array(rows) { Array(seats) { 'S' } }

    private var purchasedCount = 0
    private var currentIncome = 0

    fun printSeatsInfo() {
        println()
        println("Cinema:")
        println("  " + (1..seats).joinToString(" "))
        for (row in 1..rows) {
            println("$row " + hallInfo[row - 1].joinToString(" "))
        }
        println()
    }

    fun geTicketPriceOfASeat(row: Int): Int {
        val total = rows * seats
        if (total <= 60) return 10
        val front = rows / 2
        return if (row <= front) 10 else 8
    }

    fun getTotalIncome(): Int {
        val total = rows * seats
        return if (total <= 60) {
            total * 10
        } else {
            val frontSeats = rows / 2
            val backSeats = rows - frontSeats
            frontSeats * seats * 10 + backSeats * seats * 8
        }
    }

    fun printStats() {
        val totalSeats = rows * seats
        val percentage = if (totalSeats == 0) 0.0 else purchasedCount * 100.0 / totalSeats
        println()
        println("Number of purchased tickets: $purchasedCount")
        println("Percentage: ${"%.2f".format(percentage)}%")
        println("Current income: $$currentIncome")
        println("Total income: $${getTotalIncome()}")
        println()
    }

    fun buyTicket() {
        while (true) {
            println()
            println("Enter a row number:")
            val requiredRow = readln().toIntOrNull()?.takeIf { it >= 1 && it <= rows } ?: run {
                println("incorrect input")
                return
            }
            println("Enter a seat number in that row:")
            val requiredSeat = readln().toIntOrNull()?.takeIf { it >= 1 && it <= seats } ?: run {
                println("incorrect input")
                return
            }

            // Проверка занятости
            if (hallInfo[requiredRow - 1][requiredSeat - 1] == 'B') {
                println()
                println("That ticket has already been purchased!")
                continue
            }

            val price = geTicketPriceOfASeat(requiredRow)
            hallInfo[requiredRow - 1][requiredSeat - 1] = 'B'
            purchasedCount++
            currentIncome += price

            println()
            println("Ticket price: $$price")
            println()
            break
        }
    }


    fun tickCinemaManager(): Int {
        println("1. Show the seats")
        println("2. Buy a ticket")
        println("3. Statistics")
        println("0. Exit")

        val result = readln().toIntOrNull()?.takeIf { it >= 0 && it <= 3 } ?: run {
            println("incorrect input")
            return INCORRECT_INPUT
        }

        when (result) {
            1 -> printSeatsInfo()
            2 -> buyTicket()
            3 -> printStats()
            0 -> {}
        }

        return result
    }

}

fun main() {
    println("Enter the number of rows:")
    val rows = readln().toIntOrNull()?.takeIf { it >= 0 } ?: return println("incorrect input")

    println("Enter the number of seats in each row:")
    val seats = readln().toIntOrNull()?.takeIf { it >= 0 } ?: return println("incorrect input")


    val cinemaManager = CinemaManager(rows, seats)
    while (true) {
        val statusCode = cinemaManager.tickCinemaManager()
        if (statusCode == CinemaManager.EXIT_STATUS) {
            return
        }
    }

}
