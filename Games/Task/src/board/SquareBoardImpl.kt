package board

import board.Direction.*

class SquareBoardImpl(override val width: Int): SquareBoard {
    private var cells: Array<Array<Cell>> = arrayOf()

    init {
        cells = (1..width).map { i -> (1..width).map { Cell(i, it) }.toTypedArray() }.toTypedArray()
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        if (i <= 0 || j <= 0) return null
        if (i > this.width || j > this.width) return null
        return cells[i - 1][j - 1]
    }

    override fun getCell(i: Int, j: Int): Cell {
        if (i > this.width || j > this.width) throw IllegalArgumentException()
        return cells[i - 1][j - 1]
    }

    override fun getAllCells(): Collection<Cell> {
        return cells.flatten()
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        return jRange.filter { it <= width }.map { j -> cells[i - 1][j - 1] }
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        return iRange.filter { it <= width }.map { i -> cells[i - 1][j - 1] }
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when (direction) {
            UP -> getCellOrNull(i - 1, j)
            LEFT -> getCellOrNull(i, j - 1)
            DOWN -> getCellOrNull(i + 1, j)
            RIGHT -> getCellOrNull(i, j + 1)
        }
    }
}