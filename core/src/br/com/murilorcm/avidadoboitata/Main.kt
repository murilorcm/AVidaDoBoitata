package br.com.murilorcm.avidadoboitata

import br.com.murilorcm.avidadoboitata.game.GameScreen
import br.com.murilorcm.avidadoboitata.menu.MenuScreen
import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.BitmapFont



class Main : Game() {

    lateinit var batch: SpriteBatch
    lateinit var font: BitmapFont

    override fun create() {
        batch = SpriteBatch()
        font = BitmapFont()
        this.setScreen(MenuScreen(this))
    }

    override fun dispose() {
        batch.dispose()
    }
}