fun main() {
    println("Enter the number of rows:")
    val rows = readln().toInt()

    println("Enter the number of seats in each row:")
    val seats = readln().toInt()

    val hall = MutableList(rows) { MutableList(seats) { 'S' } }

    var purchasedCount = 0
    var currentIncome = 0

    fun printHall() {
        println()
        println("Cinema:")
        println("  " + (1..seats).joinToString(" "))
        for (row in 1..rows) {
            println("$row " + hall[row - 1].joinToString(" "))
        }
        println()
    }

    fun ticketPrice(row: Int): Int {
        val total = rows * seats
        if (total <= 60) return 10
        val front = rows / 2
        return if (row <= front) 10 else 8
    }

    fun totalIncome(): Int {
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
        println("Total income: $${totalIncome()}")
        println()
    }

    fun buyTicket() {
        while (true) {
            println()
            println("Enter a row number:")
            val r = readln().toInt()
            println("Enter a seat number in that row:")
            val c = readln().toInt()

            // Проверка границ
            if (r !in 1..rows || c !in 1..seats) {
                println()
                println("Wrong input!")
                continue
            }

            // Проверка занятости
            if (hall[r - 1][c - 1] == 'B') {
                println()
                println("That ticket has already been purchased!")
                continue
            }

            val price = ticketPrice(r)
            hall[r - 1][c - 1] = 'B'
            purchasedCount++
            currentIncome += price

            println()
            println("Ticket price: $$price")
            println()
            break
        }
    }

    while (true) {
        println("1. Show the seats")
        println("2. Buy a ticket")
        println("3. Statistics")
        println("0. Exit")

        when (readln().toInt()) {
            1 -> printHall()
            2 -> buyTicket()
            3 -> printStats()
            0 -> return
        }
    }
}
