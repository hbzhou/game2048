package board

class GameBoardImpl<T>(private val squareBoard: SquareBoard) : GameBoard<T>, SquareBoard by squareBoard {
    private val cellValues = HashMap<Cell, T?>(width * width)

    init {
        squareBoard.getAllCells().forEach { cellValues[it] = null }
    }

    override fun get(cell: Cell): T? {
        return cellValues[cell]
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return cellValues.all { predicate.invoke(it.value) }
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return cellValues.any { predicate.invoke(it.value) }
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return cellValues.filterValues(predicate).keys.firstOrNull()
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return cellValues.filterValues(predicate).keys
    }

    override fun set(cell: Cell, value: T?) {
        cellValues[cell] = value
    }
}
