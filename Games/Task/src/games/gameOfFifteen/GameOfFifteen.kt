package games.gameOfFifteen

import board.Cell
import board.Direction
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game = GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {
    private val board = mutableMapOf<Cell, Int?>()

    override fun initialize() {
        val values = (initializer.initialPermutation + null).chunked(4)
        for ((i, valueList) in values.withIndex()) {
            for ((j, value) in valueList.withIndex()) {
                board[Cell(i + 1, j + 1)] = value
            }
        }
    }

    override fun canMove(): Boolean {
        return true
    }

    override fun hasWon(): Boolean {
        val values = board.values.toList().dropLast(1)
        return values == (1..15).toList()
    }

    override fun processMove(direction: Direction) {
        val blankCell = board.entries.find { (_, value) -> value == null }?.key
        if (blankCell != null) {
            when (direction) {
                Direction.UP -> moveVertically(blankCell) { i -> i + 1 }
                Direction.DOWN -> moveVertically(blankCell) { i -> i - 1 }
                Direction.RIGHT -> moveHorizontally(blankCell) { j -> j - 1 }
                Direction.LEFT -> moveHorizontally(blankCell) { j -> j + 1 }
            }
        }

    }

    private fun move(cell: Cell, rowGetter: (Int) -> Int, columnGetter: (Int) -> Int) {
        val row = rowGetter(cell.i)
        val column = columnGetter(cell.j)
        val targetCellValue = get(row, column)
        board[cell] = targetCellValue
        board[Cell(row, column)] = null
    }

    private fun moveVertically(cell: Cell, rowGetter: (Int) -> Int) {
        move(cell, rowGetter) { j -> j }
    }

    private fun moveHorizontally(cell: Cell, columnGetter: (Int) -> Int) {
        move(cell, { i -> i }, columnGetter)
    }

    override fun get(i: Int, j: Int): Int? {
        return board.entries.find { (cell, _) -> cell.i == i && cell.j == j }?.value
    }
}