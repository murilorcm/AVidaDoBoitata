package br.com.murilorcm.avidadoboitata.game.food

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils


enum class TypeToTexture(val img: Texture, val sound: Sound) {
    ARARA(Texture(Gdx.files.internal("arara.png")), Gdx.audio.newSound(Gdx.files.internal("Sons/Attack3.ogg"))),
    CAPIVARA(Texture(Gdx.files.internal("capivara.png")), Gdx.audio.newSound(Gdx.files.internal("Sons/Attack3.ogg"))),
    LENHADOR(Texture(Gdx.files.internal("lenhador.png")), Gdx.audio.newSound(Gdx.files.internal("Sons/Attack1.ogg"))),
    CACADOR(Texture(Gdx.files.internal("cacador.png")), Gdx.audio.newSound(Gdx.files.internal("Sons/Attack1.ogg")))
}

class Food(boardSize: Int) {

    lateinit var type: TypeToTexture

    init {
        randomisePos(boardSize)
        randomiseFood()
    }

    var x: Int = 0
        private set
    var y: Int = 0
        private set

    internal fun randomisePos(boardSize: Int) {
        x = MathUtils.random(boardSize - 1)
        y = MathUtils.random(boardSize - 1)
    }

    internal fun randomiseFood() {
        val rand = MathUtils.random(0, 3)
        type = TypeToTexture.values()[rand]
    }
}
