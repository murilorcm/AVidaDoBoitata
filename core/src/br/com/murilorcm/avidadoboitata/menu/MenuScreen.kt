package br.com.murilorcm.avidadoboitata.menu

import br.com.murilorcm.avidadoboitata.Main
import br.com.murilorcm.avidadoboitata.game.GameScreen
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera

class MenuScreen(private val game: Main) : Screen {
    private val width = 600f
    private val height = 600f

    private val camera = OrthographicCamera(width, height)

    init {
        camera.setToOrtho(false, width, height)
    }

    override fun hide() {
    }

    override fun show() {
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        camera.update()
        game.batch.projectionMatrix = camera.combined;

        game.batch.begin()
        game.font.draw(game.batch, "Bem-Vindo a Vida do Boitatá", 100f, 150f)
        game.font.draw(game.batch, "Toque para começar!", 100f, 100f)
        game.batch.end()

        if (Gdx.input.isTouched) {
            game.screen = GameScreen(game)
            dispose()
        }
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun dispose() {
    }

}