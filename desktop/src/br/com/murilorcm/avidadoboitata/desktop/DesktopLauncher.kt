package br.com.murilorcm.avidadoboitata.desktop

import br.com.murilorcm.avidadoboitata.Main
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import java.awt.Toolkit

fun main(arg: Array<String>) {
    val config = LwjglApplicationConfiguration()
    val dimension = Toolkit.getDefaultToolkit().screenSize
    config.height = dimension.height
    config.width = dimension.width
    LwjglApplication(Main(), config)
}