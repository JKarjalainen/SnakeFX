package com.example.snake;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.concurrent.ThreadLocalRandom;

public class GameLoop extends Thread {
    private final Snake snake;
    private final SnakeCanvas canvas;
    private Point food;
    private SnakeAI2 snakeAI;

    public GameLoop(Snake snake, SnakeCanvas canvas) {
        this.snake = snake;
        this.canvas = canvas;
        this.food = new Point(snake.getHead().getX(), snake.getHead().getY() + 100);
        //Player controlled
        //setKeyListener();
        snakeAI = new SnakeAI2(snake, canvas);

    }

    private void setKeyListener() {
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();

            switch (keyCode) {
                case LEFT -> snake.setDirection(Direction.LEFT);
                case RIGHT -> snake.setDirection(Direction.RIGHT);
                case UP -> snake.setDirection(Direction.UP);
                case DOWN -> snake.setDirection(Direction.DOWN);
            }
        });
    }

    public void run() {
        int i = 0;
        while(GameController.getInstance().isPlaying()) {

            float time = System.currentTimeMillis();


            if(snakeAI != null) {
                snake.setDirection(snakeAI.getNextMove(food));
            }

            snake.move();

            if(checkFood()) {
                snake.grow();
                moveFood();
                System.out.println(snake.getTail().size());
            } else {
                snake.shift();
            }
            if(snake.colliding() || snake.outOfBounds(canvas))
                snake.die();
            canvas.clearScreen();
            canvas.drawSnake(snake);
            canvas.drawPoints(food, Color.RED);

            time = System.currentTimeMillis() - time;
            long interval = 1000 / 50;
            if(time < interval) {
                try {
                    Thread.sleep((long)(interval - time));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        canvas.clearScreen();
        canvas.writeEndGameText(snake.score());

    }

    public boolean checkFood() {
        return (snake.getHead().getX() == food.getX() && snake.getHead().getY() == food.getY());
    }

    public void moveFood() {
        Point newPoint;
        do {
            int randomX = ThreadLocalRandom.current().nextInt(0, (int)canvas.getWidth() / 10 - 1) * 10;
            int randomY = ThreadLocalRandom.current().nextInt(0, (int)canvas.getHeight() / 10 - 1) * 10;
            newPoint = new Point(randomX, randomY);
        }
        while(snake.colliding(newPoint));
        food = newPoint;
    }
}
