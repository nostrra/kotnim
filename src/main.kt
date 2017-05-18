/* Rudimentary Nim game in Kotlin
mechanics based from tk15's Nim-Game

TODO: Apply best-case scenario to win
*/

val game = NimController()

fun main(args: Array<String>) {
    val players = game.setupPlayers()
    var _currentPlayer = 0

    game.buildBoard()

    while(true) {
        println("\nDEBUG Total Stones: $totalStones")
        game.printPlayerDetails(players[_currentPlayer])
        game.printBoard()

        val (pPile,pStone) = players[_currentPlayer].choose()
        game.updateBoard(pPile, pStone)

        if (totalStones == 0) break
        else _currentPlayer = (_currentPlayer + 1) % players.count()
    }
    println("${players[_currentPlayer].name} [Player ${_currentPlayer+1}] took the last stone/s and wins the game.")
}

