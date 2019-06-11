package br.com.murilorcm.avidadoboitata.game

import br.com.murilorcm.avidadoboitata.Main
import br.com.murilorcm.avidadoboitata.game.food.Food
import br.com.murilorcm.avidadoboitata.game.snake.Bodypart
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.Queue
import com.badlogic.gdx.utils.viewport.FitViewport

class GameState(val game: Main) {
    private val boardSize = 10 //How many squares in the board
    private val mBody = Queue<Bodypart>()
    private val controls = Controls()
    private val mFood = Food(boardSize)

    private var snakeHead = Texture(Gdx.files.internal("snakeHead-topdown.png"))
    private var snakeBody = Texture(Gdx.files.internal("snakeBody-topdown.png"))
    private var snakeTail = Texture(Gdx.files.internal("snakeTail-topdown.png"))

    private val terrain = Texture(Gdx.files.internal("grama.png"))

    private val food = Texture(Gdx.files.internal("badlogic.jpg"))

    private var mTimer = 0f
    private var snakeLength = 3
    private var direction = -1
    private var lastDirection = -1
    private var score = 0

    init {
        initSnake()
    }

    private fun initSnake() {
        mBody.addLast(Bodypart(15, 15, boardSize)) //head
        mBody.addLast(Bodypart(15, 14, boardSize))
        mBody.addLast(Bodypart(15, 13, boardSize))
    }

    fun update(delta: Float, viewport: FitViewport) { //update game logic
        mTimer += delta
        controls.update(viewport)
        if (mTimer > 0.30f) {
            mTimer = 0f
            advance()
        }
    }

    private fun advance() {
        val headX = mBody.first().x
        val headY = mBody.first().y
        direction = controls.getDirection()

        when (direction) {
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

            do {
                mFood.randomisePos(boardSize)
            } while(mBody.firstOrNull { it.x == mFood.x && it.y == mFood.y } != null)
        }

        mBody.filter { it != mBody.first()}.forEach { bp ->
            if (bp.x == mBody.first().x && bp.y == mBody.first().y) {
                snakeLength = 3
                mBody.clear()
                initSnake()
                score = 0
                return@forEach
            }
        }

        score++

        if (mBody.size - 1 == snakeLength) {
            mBody.removeLast()
        }
    }

    fun draw(width: Float, height: Float, camera: OrthographicCamera) { //draw snake, food and board
        game.batch.projectionMatrix = camera.combined
        game.batch.begin()

        game.font.draw(game.batch, "Pontos: " + score, 0f, 480f)

        game.batch.draw(terrain, 0f, 0f, width, height)

        val scaleSnake = width / boardSize //width of one board square

        /**food*/
        game.batch.draw(food, mFood.x * scaleSnake, mFood.y * scaleSnake, scaleSnake, scaleSnake)

        mBody.forEach { bp ->
            when (bp) {
                mBody.first() -> {
                    game.batch.draw(snakeHead, bp.x * scaleSnake, bp.y * scaleSnake, scaleSnake, scaleSnake)
                }
                mBody.last() -> {
                    game.batch.draw(snakeTail, bp.x * scaleSnake, bp.y * scaleSnake, scaleSnake, scaleSnake)
                }
                else -> {
                    game.batch.draw(snakeBody, bp.x * scaleSnake, bp.y * scaleSnake, scaleSnake, scaleSnake)
                }
            }
        }

        game.batch.end()
    }
}
