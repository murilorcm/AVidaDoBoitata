package br.com.murilorcm.avidadoboitata

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class Main : Game() {

    lateinit var batch: SpriteBatch
    lateinit var img: Texture

    override fun create() {
        batch = SpriteBatch()
        this.setScreen(GameScreen(this))
//        img = Texture("badlogic.jpg")
    }

//    override fun render() {
//        Gdx.gl.glClearColor(1.0f, 0.0f, 0.0f, 1.0f)
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
//
//        batch.begin()
//        batch.draw(img, 0.0f, 0.0f)
//        batch.end()
//    }

    override fun dispose() {
        batch.dispose()
//        img.dispose()
    }
}