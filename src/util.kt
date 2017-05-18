/**
 * Created by chrisb on 18/05/2017.
 */

import java.security.SecureRandom
import java.util.*

var piles = ArrayList<Pile>()
var totalStones = 0

data class Pile(var stones: Int = 0)

data class Player(var name: String = "", var stones: Int = 0) {
    fun choose(): Pair<Int, Int> {
        var pile = 0
        var stonesToTake = 0

        loop@ while (true) {
            try {
                print("It is your turn. Choose pile to take stones from: ")
                pile = readLine()!!.toInt()

                // ugly check
                if (piles[pile-1] !is Pile) throw IndexOutOfBoundsException()
                if (piles[pile-1].stones == 0) throw NullPointerException()
                break
            } catch(e: Exception) {
                when (e) {
                    is NumberFormatException -> {
                        println("I dont'understand that. Can you try again?")
                        continue@loop
                    }
                    is IndexOutOfBoundsException -> {
                        println("I can't seem to find that pile. Can you try again?")
                        continue@loop
                    }
                    is NullPointerException-> {
                        println("There are no stones in that pile. Can you try again?")
                        continue@loop
                    }
                }
            }
        }

        loop2@ while (true) {
            try {
                print("Chose pile $pile. How many stones: ")
                stonesToTake = readLine()!!.toInt()
                //ugly check
                if (!(stoneCheck(stonesToTake,1,piles[pile-1].stones))) throw IndexOutOfBoundsException()
                println("Chose $stonesToTake stones.")
                stones += stonesToTake
                totalStones += stonesToTake
                break
            } catch(e: Exception) {
                when (e) {
                    is NumberFormatException -> {
                        println("I don't understand that. Can you try again?")
                        continue@loop2
                    }
                    is IndexOutOfBoundsException -> {
                        println("I can't take that amount of stones! Can you try again?")
                        continue@loop2
                    }
                }
            }
        }
        return Pair(pile, stonesToTake)
    }
    fun stoneCheck(number: Int, min: Int, max: Int): Boolean {
        return (number >= min && number <= max)
    }
}

class NimController {

    /* Player */

    fun setupPlayers(): List<Player> {
        println("Welcome to Nim! Let's start.")
        val playerList = listOf(Player(), Player())
        for (player in playerList) {
            var nameCheck = true
            loop@ while(nameCheck) {
                try {
                    print("Enter Player ${playerList.indexOf(player) + 1} Name: ")
                    val name = readLine()!!
                    player.name = if (name.isNotEmpty()) name else throw Exception()
                    nameCheck = false
                } catch (e: Exception) {
                    println("Name cannot be empty!")
                    continue@loop
                }
            }
        }
        return playerList
    }

    fun printPlayerDetails(player: Player) {
        println("\nPlayer Name: ${player.name}")
        println("Current Number of Stones: ${player.stones}")
    }

    /* Logic */

    fun generate(min: Int, max: Int): Int {
        val rand: Random = SecureRandom()
        return rand.nextInt(max - min + 1) + min
    }

    /* Board */

    fun printBoard() {
        var x = 1
        piles.forEach {
            println("Pile $x: ${"O ".repeat(it.stones)}")
            x++
        }
    }

    fun updateBoard(pile: Int, stone: Int) {
        val setToMod = piles[pile-1]
        setToMod.stones -= stone
    }

    // generate between 2 to 5 piles, with 1 to 8 stones per pile
    fun buildBoard(): ArrayList<Pile> {
        for (i in 1..generate(2,5)) {
            val startingStones = generate(1,8)
            totalStones += startingStones
            val pile = Pile(stones = startingStones)
            piles.add(pile)
        }
        return piles
    }
}

