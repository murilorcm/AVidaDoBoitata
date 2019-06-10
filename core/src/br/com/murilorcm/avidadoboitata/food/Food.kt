package br.com.murilorcm.avidadoboitata.food

import com.badlogic.gdx.math.MathUtils

class Food(boardSize: Int) {

    init {
        randomisePos(boardSize)
    }

    var x: Int = 0
        private set
    var y: Int = 0
        private set

    internal fun randomisePos(boardSize: Int) {
        x = MathUtils.random(boardSize - 1)
        y = MathUtils.random(boardSize - 1)
    }
}
