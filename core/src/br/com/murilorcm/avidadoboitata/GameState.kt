package br.com.murilorcm.avidadoboitata

import br.com.murilorcm.avidadoboitata.food.Food
import br.com.murilorcm.avidadoboitata.snake.Bodypart
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Queue
import com.badlogic.gdx.utils.viewport.FitViewport

class GameState {
    private val boardSize = 30 //How many squares in the board
    private val yOffset = 400f //How high the board is off the bottom
    private val shapeRenderer = ShapeRenderer()
    private val mBody = Queue<Bodypart>()
    private val controls = Controls()
    private val mFood = Food(boardSize)

    private var mTimer = 0f
    private var snakeLength = 3
    private var colourCounter = 0f

    init {
        mBody.addLast(Bodypart(15, 15, boardSize)) //head
        mBody.addLast(Bodypart(15, 14, boardSize))
        mBody.addLast(Bodypart(15, 13, boardSize))
    }

    fun update(delta: Float, viewport: FitViewport) { //update game logic
        mTimer += delta
        colourCounter += delta
        controls.update(viewport)
        if (mTimer > 0.13f) {
            mTimer = 0f
            advance()
        }
    }

    private fun advance() {
        val headX = mBody.first().x
        val headY = mBody.first().y

        when (controls.getDirection()) {
            /**up*/
            0 -> mBody.addFirst(Bodypart(headX, headY + 1, boardSize))
            /**right*/
            1 -> mBody.addFirst(Bodypart(headX + 1, headY, boardSize))
            /**down*/
            2 -> mBody.addFirst(Bodypart(headX, headY - 1, boardSize))
            /**left*/
            3 -> mBody.addFirst(Bodypart(headX - 1, headY, boardSize))
            /**should never happen*/
            else -> mBody.addFirst(Bodypart(headX, headY + 1, boardSize))
        }

        if (mBody.first().x == mFood.x && mBody.first().y == mFood.y) {
            snakeLength++
            mFood.randomisePos(boardSize) //TODO check it's not in body
        }

        for (i in 1 until mBody.size) { // TODO  usar for each
            if (mBody.get(i).x == mBody.first().x && mBody.get(i).y == mBody.first().y) {
                snakeLength = 3
            }
        }

        if (mBody.size - 1 == snakeLength) {
            mBody.removeLast()
        }
    }

    fun draw(width: Float, height: Float, camera: OrthographicCamera) { //draw snake and board
        shapeRenderer.projectionMatrix = camera.combined
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)

        shapeRenderer.setColor(1f, 1f, 1f, 1f)
        shapeRenderer.rect(0f, yOffset, width, width)

        shapeRenderer.setColor(0f, 0f, 0f, 1f)
        shapeRenderer.rect((0f + 5f), (yOffset + 5), (width - 5 * 2), (width - 5 * 2))

        shapeRenderer.setColor(MathUtils.sin(colourCounter),-MathUtils.sin(colourCounter),1f,1f)

        /**buttons*/
        shapeRenderer.rect(235f, 265f, 130f, 135f)
        shapeRenderer.rect(235f, 0f, 130f, 135f)
        shapeRenderer.rect(105f,135f,130f,130f)
        shapeRenderer.rect(365f,135f,130f,130f)

        val scaleSnake = width / boardSize //width of one board square

        /**food*/
        shapeRenderer.rect(mFood.x * scaleSnake, mFood.y * scaleSnake + yOffset, scaleSnake, scaleSnake)

        mBody.forEach { bp ->
            shapeRenderer.rect(bp.x * scaleSnake, bp.y * scaleSnake + yOffset, scaleSnake, scaleSnake)
        }

        shapeRenderer.end()
    }
}
