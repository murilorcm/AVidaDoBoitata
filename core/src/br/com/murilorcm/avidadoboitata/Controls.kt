package br.com.murilorcm.avidadoboitata

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport

class Controls {
    private var currentDirection = 0 //0,1,2,3 U,R,D,L
    private var nextDirection = 0

    private val touch = Vector2()

    private val upBox = Rectangle(235f, 265f, 130f, 135f)
    private val downBox = Rectangle(235f, 0f, 130f, 135f)
    private val leftBox = Rectangle(65f, 135f, 130f, 130f)
    private val rightBox = Rectangle(365f, 135f, 130f, 130f)

    fun getDirection(): Int {
        currentDirection = nextDirection
        return nextDirection
    }

    fun update(viewport: Viewport) {
        if (Gdx.input.isTouched) {
            touch.x = Gdx.input.x.toFloat()
            touch.y = Gdx.input.y.toFloat()
            viewport.unproject(touch)
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP) && currentDirection != 2) nextDirection = 0
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && currentDirection != 3) nextDirection = 1
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && currentDirection != 0) nextDirection = 2
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && currentDirection != 1) nextDirection = 3
    }
}