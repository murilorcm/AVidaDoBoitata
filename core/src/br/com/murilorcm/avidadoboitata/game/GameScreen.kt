package br.com.murilorcm.avidadoboitata.game

import br.com.murilorcm.avidadoboitata.Main
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.viewport.FitViewport

class GameScreen(game: Main) : Screen {

    private val gameState = GameState(game)

    private val width = 600f
    private val height = 600f

    private val camera = OrthographicCamera(width, height)
    private var viewport: FitViewport

    init {
        camera.setToOrtho(false, width, height)
        viewport = FitViewport(width, height, camera)
        viewport.apply()
    }

    override fun hide() {
    }

    override fun show() {
    }

    override fun render(delta: Float) {
        camera.update()
        viewport.apply()

        gameState.update(delta, viewport)

        gameState.draw(width, height, camera)
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    override fun dispose() {
    }
}