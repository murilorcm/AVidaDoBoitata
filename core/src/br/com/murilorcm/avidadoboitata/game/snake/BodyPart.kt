package br.com.murilorcm.avidadoboitata.game.snake

class Bodypart(x: Int, y: Int, boardSize: Int, internal val direction: Int, internal var nextBp : Bodypart?) {
    var x: Int = 0
        private set
    var y: Int = 0
        private set

    init {
        this.x = x % boardSize
        if (this.x < 0) this.x += boardSize

        this.y = y % boardSize
        if (this.y < 0) this.y += boardSize
    }
}