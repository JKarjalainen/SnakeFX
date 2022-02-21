/*
package com.example.snake;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class SnakeAI {
    private Snake snake;
    private SnakeCanvas canvas;

    public SnakeAI(Snake snake, SnakeCanvas canvas) {
        this.snake = snake;
        this.canvas = canvas;
    }

   */
/* public Direction getNextMove(Point food) {

        if(snake.getHead().getX() > food.getX() && snake.getDirection() != Direction.RIGHT) return checkAndMove(Direction.LEFT);
        else if(snake.getHead().getX() < food.getX() && snake.getDirection() != Direction.LEFT) return checkAndMove(Direction.RIGHT);

        if(snake.getHead().getY() < food.getY() && snake.getDirection() != Direction.UP) return checkAndMove(Direction.DOWN);
        else if(snake.getHead().getY() > food.getY() && snake.getDirection() != Direction.DOWN) return checkAndMove(Direction.UP);

        else return panic();
    }*//*

    public Direction getNextMoveWithCalc(Point food) {
        if(diesInFuture10Moves(food)) {
            System.out.println("dies");
        }
        if(snake.getHead().getX() > food.getX() && snake.getDirection() != Direction.RIGHT) return checkAndMove(Direction.LEFT, snake);
        else if(snake.getHead().getX() < food.getX() && snake.getDirection() != Direction.LEFT) return checkAndMove(Direction.RIGHT, snake);

        if(snake.getHead().getY() < food.getY() && snake.getDirection() != Direction.UP) return checkAndMove(Direction.DOWN, snake);
        else if(snake.getHead().getY() > food.getY() && snake.getDirection() != Direction.DOWN) return checkAndMove(Direction.UP, snake);

        else return panic(snake);
    }
    public Direction getNextMove(Point food, Snake s) {


        if(s.getHead().getX() > food.getX() && s.getDirection() != Direction.RIGHT) return checkAndMove(Direction.LEFT, s);
        else if(s.getHead().getX() < food.getX() && s.getDirection() != Direction.LEFT) return checkAndMove(Direction.RIGHT, s);

        if(s.getHead().getY() < food.getY() && s.getDirection() != Direction.UP) return checkAndMove(Direction.DOWN, s);
        else if(s.getHead().getY() > food.getY() && s.getDirection() != Direction.DOWN) return checkAndMove(Direction.UP, s);

        else return panic(s);
    }

    private Direction intToDir(int i) {
        return Direction.RIGHT;
    }

    private Direction panic(Snake s) {
        for(int i = 0; i < Direction.values().length; i++) {
            if(!notSafeMove(Direction.values()[i], s)) {
                return Direction.values()[i];
            }
        }
        return Direction.RIGHT;
    }

    private Direction checkAndMove(Direction d, Snake s) {

        if(!notSafeMove(d, s)) {
            return d;
        }
        ArrayList<Direction> possibleDirections = new ArrayList<>();
        for(int i = 0; i < Direction.values().length; i++) {
            if(!notSafeMove(Direction.values()[i], s)) {
                possibleDirections.add(Direction.values()[i]);
            }
        }

        return !possibleDirections.isEmpty()  ? possibleDirections.get(0) : Direction.RIGHT;
    }
    private boolean notSafeMove(Direction d, Snake sn) {
        Point s = sn.getHead();
        Direction newDir = d;
        for(Point p : snake.getTail()) {
            switch (d) {
                case RIGHT -> {
                    if(s.getX() + 10 == p.getX() && s.getY() == p.getY() || s.getX() + 10 > canvas.getWidth())
                        return true;
                }
                case UP -> {
                    if(s.getX() == p.getX() && s.getY() - 10 == p.getY() || s.getY() - 10 < 0)
                        return true;
                }
                case LEFT -> {
                    if(s.getX() - 10 == p.getX() && s.getY() == p.getY() || s.getX() - 10 < 0)
                        return true;
                }
                case DOWN -> {
                    if(s.getX() == p.getX() && s.getY() + 10 == p.getY() || s.getY() + 10 > canvas.getHeight())
                        return true;
                }
            }
        }


        return false;
    }

    private boolean notSafeMove(Point s,Direction d) {
        Direction newDir = d;
        for(Point p : snake.getTail()) {
            switch (d) {
                case RIGHT -> {
                    if(s.getX() + 10 == p.getX() && s.getY() == p.getY() || s.getX() + 10 > canvas.getWidth())
                        return true;
                }
                case UP -> {
                    if(s.getX() == p.getX() && s.getY() - 10 == p.getY() || s.getY() - 10 < 0)
                        return true;
                }
                case LEFT -> {
                    if(s.getX() - 10 == p.getX() && s.getY() == p.getY() || s.getX() - 10 < 0)
                        return true;
                }
                case DOWN -> {
                    if(s.getX() == p.getX() && s.getY() + 10 == p.getY() || s.getY() + 10 > canvas.getHeight())
                        return true;
                }
            }
        }


        return false;
    }

    private Point getNextPoint(Direction d) {
        Point s = snake.getHead();
        switch (d) {
            case RIGHT -> {
                return new Point(s.getX() + 10, s.getY());
            }
            case UP -> {
                return new Point(s.getX(), s.getY() - 10);
            }
            case LEFT -> {
                return new Point(s.getX() - 10, s.getY());
            }
            case DOWN -> {
                return new Point(s.getX(), s.getY() + 10);
            }
            default -> {
                return new Point(s);
            }
        }
    }

    private Point getNextPoint(Point start, Direction d) {
        Point s = start;
        switch (d) {
            case RIGHT -> {
                return new Point(s.getX() + 10, s.getY());
            }
            case UP -> {
                return new Point(s.getX(), s.getY() - 10);
            }
            case LEFT -> {
                return new Point(s.getX() - 10, s.getY());
            }
            case DOWN -> {
                return new Point(s.getX(), s.getY() + 10);
            }
            default -> {
                return new Point(s);
            }
        }
    }

    private boolean diesInFuture10Moves(Point food) {
        Snake s = snake.copy();
        boolean returnValue = false;
        for(int i = 0; i < 25; i++) {
            float time = System.currentTimeMillis();
            canvas.clearScreen();
            s.setDirection(getNextMove(food, s));
            s.move();

            if((s.getHead().getX() == food.getX() && s.getHead().getY() == food.getY())) {
                returnValue = false;//Sai ruoan
                break;
            } else {
                s.shift();
            }
            if(s.colliding())
                returnValue = true; // dont use this direction
            if(
                    s.getHead().getX() >= canvas.getWidth() ||
                            s.getHead().getX() < 0 ||
                            s.getHead().getY() >= canvas.getHeight() ||
                            s.getHead().getY() < 0
            )
                returnValue = true; // dont use this direction
            canvas.drawSnake(s);
            canvas.drawPoints(food, Color.RED);

            time = System.currentTimeMillis() - time;
            long interval = 1000 / 10;
            if(time < interval) {
                try {
                    Thread.sleep((long)(interval - time));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return returnValue;
    }

}
*/
