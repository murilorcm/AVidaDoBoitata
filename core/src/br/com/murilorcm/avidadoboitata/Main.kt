package br.com.murilorcm.avidadoboitata

import br.com.murilorcm.avidadoboitata.menu.MenuScreen
import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class Main : Game() {

    lateinit var batch: SpriteBatch
    lateinit var font: BitmapFont

    override fun create() {
        batch = SpriteBatch()
        font = BitmapFont()
        font.region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        font.data.scale(2f)
        this.setScreen(MenuScreen(this))
    }

    override fun dispose() {
        batch.dispose()
    }
}