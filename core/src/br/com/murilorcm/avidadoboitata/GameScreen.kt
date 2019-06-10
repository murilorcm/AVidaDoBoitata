package br.com.murilorcm.avidadoboitata

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.viewport.FitViewport

class GameScreen(private val game: Main) : Screen {

    private val gameState = GameState()

    private val width = 600f
    private val height = 1000f

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

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
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