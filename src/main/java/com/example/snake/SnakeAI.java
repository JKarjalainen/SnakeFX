package com.example.snake;

import java.util.ArrayList;

public class SnakeAI {
    private Snake snake;
    private SnakeCanvas canvas;

    public SnakeAI(Snake snake, SnakeCanvas canvas) {
        this.snake = snake;
        this.canvas = canvas;
    }
    public Direction getNextMove(Point food) {
        if(snake.getHead().getX() > food.getX() && snake.getDirection() != Direction.RIGHT) return checkAndMove(Direction.LEFT);
        else if(snake.getHead().getX() < food.getX() && snake.getDirection() != Direction.LEFT) return checkAndMove(Direction.RIGHT);

        if(snake.getHead().getY() < food.getY() && snake.getDirection() != Direction.UP) return checkAndMove(Direction.DOWN);
        else if(snake.getHead().getY() > food.getY() && snake.getDirection() != Direction.DOWN) return checkAndMove(Direction.UP);

        else return panic();
    }

    private Direction intToDir(int i) {
        return Direction.RIGHT;
    }

    private Direction panic() {
        for(int i = 0; i < Direction.values().length; i++) {
            if(!notSafeMove(Direction.values()[i])) {
                return Direction.values()[i];
            }
        }
        return Direction.RIGHT;
    }

    private Direction checkAndMove(Direction d) {

        if(!notSafeMove(d)) {
            return d;
        }
        for(int i = 0; i < Direction.values().length; i++) {
            if(!notSafeMove(Direction.values()[i])) {
                return Direction.values()[i];
            }
        }
        return Direction.RIGHT;

       /* Point s = snake.getHead();
        Direction newDir = d;
        for(Point p : snake.getTail()) {
            switch (d) {
                case RIGHT -> {
                    if(s.getX() + 10 == p.getX() && s.getY() == p.getY())
                        newDir = Direction.UP;
                }
                case UP -> {
                    if(s.getX() == p.getX() && s.getY() - 10 == p.getY())
                        newDir = Direction.RIGHT;
                }
                case LEFT -> {
                    if(s.getX() - 10 == p.getX() && s.getY() == p.getY())
                        newDir = Direction.UP;
                }
                case DOWN -> {
                    if(s.getX() == p.getX() && s.getY() + 10 == p.getY())
                        newDir = Direction.RIGHT;
                }
            }
        }


        return newDir;*/
    }
    private boolean notSafeMove(Direction d) {
        Point s = snake.getHead();
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

}
