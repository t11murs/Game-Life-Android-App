package com.example.test3
import androidx.lifecycle.ViewModel

class ViewModelGame(): ViewModel() {

    object data{
        var size: Int = 10
        var cells: Array<Array<Int>> = Array(size){Array(size){0} }
        var neighbors: Array<Array<Int>> = Array(size){Array(size){0} }
        var speed: Int = 1
        var status: Boolean = false
    }

    // Подсчет живых соседей для каждой клетки
    fun countNeighbors(cells: Array<Array<Int>>, neighborCounts: Array<Array<Int>>) {
        val size = cells.size
        for (i in 0 until size) {
            for (j in 0 until size) {
                var count = 0
                for (x in -1..1) {
                    for (y in -1..1) {
                        if (x == 0 && y == 0) continue
                        val ni = i + x
                        val nj = j + y
                        if (ni in 0 until size && nj in 0 until size) {
                            count += cells[ni][nj]
                        }
                    }
                }
                neighborCounts[i][j] = count
            }
        }
    }

    // Обновление сетки на основе правил игры "Жизнь"
    fun updateGrid(cells: Array<Array<Int>>, neighborCounts: Array<Array<Int>>) {
        val size = cells.size
        for (i in 0 until size) {
            for (j in 0 until size) {
                cells[i][j] = when {
                    cells[i][j] == 1 && (neighborCounts[i][j] == 2 || neighborCounts[i][j] == 3) -> 1
                    cells[i][j] == 0 && neighborCounts[i][j] == 3 -> 1
                    else -> 0
                }
            }
        }
    }


}