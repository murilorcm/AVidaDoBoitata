package br.com.murilorcm.avidadoboitata.game

import br.com.murilorcm.avidadoboitata.Main
import br.com.murilorcm.avidadoboitata.game.food.Food
import br.com.murilorcm.avidadoboitata.game.snake.Bodypart
import br.com.murilorcm.avidadoboitata.over.GameOverScreen
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.Queue
import com.badlogic.gdx.utils.viewport.FitViewport

class GameState(private val game: Main) {
    private val boardSize = 10 //How many squares in the board
    private val mBody = Queue<Bodypart>()
    private val controls = Controls()
    private val mFood = Food(boardSize)

    private val snakeHead = Texture(Gdx.files.internal("snakeHead-topdown.png"))
    private val snakeBody = Texture(Gdx.files.internal("snakeBody-topdown.png"))
    private val snakeTail = Texture(Gdx.files.internal("snakeTail-topdown.png"))
    private val snakeCurve = Texture(Gdx.files.internal("snakeCurve-topdown.png"))

    private val terrain = Texture(Gdx.files.internal("terrain.png"))

    private val music =  Gdx.audio.newSound(Gdx.files.internal("Sons/Field2.ogg"))

    private var mTimer = 0f

    private var snakeLength = 3
    private var score = 0

    private val sprite = Sprite()

    init {
        initSnake()
        music.loop()
    }

    private fun initSnake() {
        mBody.addLast(Bodypart(15, 15, boardSize, 0, null)) //head
        mBody.addLast(Bodypart(15, 14, boardSize, 0, mBody.last()))
        mBody.addLast(Bodypart(15, 13, boardSize, 0, mBody.last()))
    }

    fun update(delta: Float, viewport: FitViewport) { //update game logic
        mTimer += delta
        controls.update(viewport)
        if (mTimer > 0.13f) {
            mTimer = 0f
            advance()
        }
    }

    private fun advance() {
        val headX = mBody.first().x
        val headY = mBody.first().y

        val direction = controls.getDirection()

        val newBp = when (direction) {
            /**up*/
            0 -> Bodypart(headX, headY + 1, boardSize, direction, null)
            /**right*/
            1 -> Bodypart(headX + 1, headY, boardSize, direction, null)
            /**down*/
            2 -> Bodypart(headX, headY - 1, boardSize, direction, null)
            /**left*/
            3 -> Bodypart(headX - 1, headY, boardSize, direction, null)
            /**should never happen*/
            else -> Bodypart(headX, headY + 1, boardSize, direction, null)
        }

        mBody.first().nextBp = newBp
        mBody.addFirst(newBp)

        if (mBody.first().x == mFood.x && mBody.first().y == mFood.y) {
            mFood.type.sound.play()

            snakeLength++
            score++

            do {
                mFood.randomisePos(boardSize)
                mFood.randomiseFood()
            } while (mBody.firstOrNull { it.x == mFood.x && it.y == mFood.y } != null)
        }

        mBody.filter { it != mBody.first() }.forEach { bp ->
            if (bp.x == mBody.first().x && bp.y == mBody.first().y) {
                music.dispose()
                game.screen = GameOverScreen(game, score)
//                snakeLength = 3
//                mBody.clear()
//                initSnake()
//                score = 0
                return@forEach
            }
        }

        if (mBody.size - 1 == snakeLength) {
            mBody.removeLast()
        }
    }

    fun draw(width: Float, height: Float, camera: OrthographicCamera) { //draw snake, food and board
        game.batch.projectionMatrix = camera.combined
        game.batch.begin()

        game.batch.draw(terrain, 0f, 0f, width, height)

        val scaleSnake = width / boardSize //width of one board square

        /**food*/
        game.batch.draw(mFood.type.img, mFood.x * scaleSnake, mFood.y * scaleSnake, scaleSnake, scaleSnake)

        /**Snake*/
        mBody.forEach { bp ->
            when (bp) {
                mBody.first() -> {
                    drawBodyPart(snakeHead, scaleSnake, bp, null)
                }
                mBody.last() -> {
                    when (bp.direction) {
                        /**up*/
                        0 -> {
                            val nextBp = bp.nextBp
                            if (nextBp != null) {
                                when (nextBp.direction) {
                                    1 -> drawBodyPart(snakeTail, scaleSnake, bp, 270f)
                                    3 -> drawBodyPart(snakeTail, scaleSnake, bp, 90f)
                                    else -> drawBodyPart(snakeTail, scaleSnake, bp, null)
                                }
                            }
                        }
                        /**right*/
                        1 -> {
                            val nextBp = bp.nextBp
                            if (nextBp != null) {
                                when (nextBp.direction) {
                                    0 -> drawBodyPart(snakeTail, scaleSnake, bp, 0f)
                                    2 -> drawBodyPart(snakeTail, scaleSnake, bp, 180f)
                                    else -> drawBodyPart(snakeTail, scaleSnake, bp, null)
                                }
                            }
                        }
                        /**down*/
                        2 -> {
                            val nextBp = bp.nextBp
                            if (nextBp != null) {
                                when (nextBp.direction) {
                                    1 -> drawBodyPart(snakeTail, scaleSnake, bp, 270f)
                                    3 -> drawBodyPart(snakeTail, scaleSnake, bp, 90f)
                                    else -> drawBodyPart(snakeTail, scaleSnake, bp, null)
                                }
                            }
                        }
                        /**left*/
                        3 -> {
                            val nextBp = bp.nextBp
                            if (nextBp != null) {
                                when (nextBp.direction) {
                                    0 -> drawBodyPart(snakeTail, scaleSnake, bp, 0f)
                                    2 -> drawBodyPart(snakeTail, scaleSnake, bp, 180f)
                                    else -> drawBodyPart(snakeTail, scaleSnake, bp, null)
                                }
                            }
                        }
                        else -> drawBodyPart(snakeTail, scaleSnake, bp, null)
                    }
                }
                else -> {
                    when (bp.direction) {
                        /**up*/
                        0 -> {
                            val nextBp = bp.nextBp
                            if (nextBp != null) {
                                when (nextBp.direction) {
                                    1 -> drawBodyPart(snakeCurve, scaleSnake, bp, 90f)
                                    3 -> drawBodyPart(snakeCurve, scaleSnake, bp, 0f)
                                    else -> drawBodyPart(snakeBody, scaleSnake, bp, null)
                                }
                            }
                        }
                        /**right*/
                        1 -> {
                            val nextBp = bp.nextBp
                            if (nextBp != null) {
                                when (nextBp.direction) {
                                    0 -> drawBodyPart(snakeCurve, scaleSnake, bp, 270f)
                                    2 -> drawBodyPart(snakeCurve, scaleSnake, bp, 0f)
                                    else -> drawBodyPart(snakeBody, scaleSnake, bp, null)
                                }
                            }
                        }
                        /**down*/
                        2 -> {
                            val nextBp = bp.nextBp
                            if (nextBp != null) {
                                when (nextBp.direction) {
                                    1 -> drawBodyPart(snakeCurve, scaleSnake, bp, 180f)
                                    3 -> drawBodyPart(snakeCurve, scaleSnake, bp, 270f)
                                    else -> drawBodyPart(snakeBody, scaleSnake, bp, null)
                                }
                            }
                        }
                        /**left*/
                        3 -> {
                            val nextBp = bp.nextBp
                            if (nextBp != null) {
                                when (nextBp.direction) {
                                    0 -> drawBodyPart(snakeCurve, scaleSnake, bp, 180f)
                                    2 -> drawBodyPart(snakeCurve, scaleSnake, bp, 90f)
                                    else -> drawBodyPart(snakeBody, scaleSnake, bp, null)
                                }
                            }
                        }
                        else -> drawBodyPart(snakeBody, scaleSnake, bp, null)
                    }
                }
            }
        }

        /**Score*/
        game.font.draw(game.batch, "Pontos: $score", 100f, 1400f)

        game.batch.end()
    }

    private fun drawBodyPart(snake: Texture, scaleSnake: Float, bp: Bodypart, degrees: Float?) {
        sprite.set(Sprite(snake))
        sprite.setSize(scaleSnake, scaleSnake)
        sprite.setPosition(bp.x * scaleSnake, bp.y * scaleSnake)
        sprite.setOrigin(scaleSnake / 2, scaleSnake / 2)
        sprite.rotation = degrees ?: directionToDegrees(bp)
        sprite.draw(game.batch)
    }

    private fun directionToDegrees(bp: Bodypart): Float {
        return when (bp.direction) {
            /**up*/
            0 -> 0f
            /**right*/
            1 -> 270f
            /**down*/
            2 -> 180f
            /**left*/
            3 -> 90f
            /**should never happen*/
            else -> -1f
        }
    }
}