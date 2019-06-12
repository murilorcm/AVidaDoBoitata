package br.com.murilorcm.avidadoboitata.over

import br.com.murilorcm.avidadoboitata.Main
import br.com.murilorcm.avidadoboitata.game.GameScreen
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture

class GameOverScreen(private val game: Main, private val score: Int) : Screen {
    private val width = 1500f
    private val height = 1500f

    private val camera = OrthographicCamera(width, height)
    private val icon = Texture(Gdx.files.internal("snake.png"))

    private val music =  Gdx.audio.newSound(Gdx.files.internal("Sons/Field4.ogg"))

    init {
        music.loop()
        camera.setToOrtho(false, width, height)
    }

    override fun hide() {
    }

    override fun show() {
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        camera.update()
        game.batch.projectionMatrix = camera.combined

        game.batch.begin()
        game.batch.draw(icon, 900f, 200f, 400f, 400f)
        game.font.draw(game.batch, "Fim de Jogo", 600f, 1050f)
        game.font.draw(game.batch, "Pontuação: $score", 600f, 950f)
        game.font.draw(game.batch, "Pressione qualquer tecla para recomeçar!", 600f, 850f)

        game.batch.end()

        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
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
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        music.dispose()
    }
}