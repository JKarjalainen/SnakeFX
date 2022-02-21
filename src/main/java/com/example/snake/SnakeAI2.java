package com.example.snake;

import javafx.scene.paint.Color;

import java.security.DrbgParameters;
import java.util.ArrayList;
import java.util.HashMap;

public class SnakeAI2 {
    private final Snake snake;
    private final SnakeCanvas canvas;
    private ArrayList<Direction> movesToApple = new ArrayList<>();
    private Direction ogDir;
    private final boolean drawCanvas = false; // For debugging
    private int simNumb = 0;
    public HashMap<Point, ArrayList<Direction>> triedDirections = new HashMap<>();

    //Mahdollisesti joku ongelma collision jutussa simuloinnissa

    public SnakeAI2(Snake snake, SnakeCanvas canvas) {
        this.snake = snake;
        this.canvas = canvas;
    }

    public void calculateMovesToApple(Point apple) {
        SimSnake s = snake.copySim();
        ogDir = s.getDirection();
        canvas.clearScreen();
        ArrayList<Direction> directions = new ArrayList<>();
        ArrayList<SimSnake> simulationPoints = new ArrayList<>();
        float time = System.currentTimeMillis();
        while(true) {

            if(drawCanvas)
                canvas.clearScreen();
            //sleepAndDrawCanvas(s, apple);

            Direction d = getMoveCloserToApple(s, apple);



            if(s.getTriedDirections().contains(d) || !checkIfSafeMove(s, d)) {
                d = getSafeMove(s);
            }
            if(d == null) {
                //sleepAndDrawCanvas(s);
                if(simulationPoints.isEmpty()) {
                    d = getMoveCloserToApple(s, apple);
                } else {

                    //sleepAndDrawCanvas(s, apple);
                    //No work :(
                    simulationPoints.remove(simulationPoints.size() - 1);
                    if(simulationPoints.isEmpty()) {
                        movesToApple = panic(snake, apple);
                        return;
                    }
                    s = simulationPoints.get(simulationPoints.size() - 1);
                    s.addToTriedDirections(directions.remove(simulationPoints.size()));

                    time = System.currentTimeMillis() - time;

                    continue;
                }
            }
            s.setDirection(d);
            s.move();
           //sleepAndDrawCanvas(s, apple);

            ogDir = s.getDirection();
            if((s.getHead().getX() == apple.getX() && s.getHead().getY() == apple.getY())) {
                directions.add(d);
                SimSnake sc = s.copySim();
                sc.addToTriedDirections(s.getDirection());
                simulationPoints.add(sc);
                //simNumb = simulationPoints.size() - 1;
                break;
            } else {
                s.shift();
                directions.add(d);
                SimSnake sc = s.copySim();
                sc.addToTriedDirections(s.getDirection());
                simulationPoints.add(sc);
               // simNumb = simulationPoints.size() - 1;
            }


        }


        movesToApple = directions;
    }

    private Direction getSafeMove(SimSnake s) {
        ArrayList<Direction> possibleDirections = new ArrayList<>();
        for (int i = 0; i < Direction.values().length; i++) {
            Direction d = Direction.values()[i];
            if(checkIfSafeMove(s, d) && !s.getTriedDirections().contains(d)) possibleDirections.add(d);
        }

        return possibleDirections.isEmpty() ? null : possibleDirections.get(0);
    }

    private Direction getSafeMove(Snake s) {
        ArrayList<Direction> possibleDirections = new ArrayList<>();
        for (int i = 0; i < Direction.values().length; i++) {
            Direction d = Direction.values()[i];
            if(checkIfSafeMove(s, d)) possibleDirections.add(d);
        }

        return possibleDirections.isEmpty() ? null : possibleDirections.get(0);
    }

    private ArrayList<Direction> panic(Snake s, Point food) {
        ArrayList<Direction> directionArrayList = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            Direction d = getMoveCloserToApple(s, food);

            if (!checkIfSafeMove(s, d)) {
                d = getSafeMove(s);
            }
            directionArrayList.add(d);
        }

        return directionArrayList;
    }

    private boolean checkIfSafeMoveDebug(Snake s, Direction d) {
        if(drawCanvas) sleepAndDrawCanvas(s);
        s.setDirection(d);
        s.move();
        if(drawCanvas)
            sleepAndDrawCanvas(s);

        if(s.colliding() || s.outOfBounds(canvas)) {
            s.setDirection(getOpposite(d));
            s.move();
            if(drawCanvas)
                sleepAndDrawCanvas(s);

            return false;
        }
        s.setDirection(getOpposite(d));
        s.move();
        if(drawCanvas) sleepAndDrawCanvas(s);
        s.setDirection(ogDir);
        return true;
    }

    private Direction getMoveCloserToApple(Snake s, Point a) {
        if(s.getHead().getX() > a.getX() && s.getDirection() != Direction.RIGHT) return Direction.LEFT;
        else if(s.getHead().getX() < a.getX() && s.getDirection() != Direction.LEFT) return Direction.RIGHT;

        if(s.getHead().getY() < a.getY() && s.getDirection() != Direction.UP) return Direction.DOWN;
        else if(s.getHead().getY() > a.getY() && s.getDirection() != Direction.DOWN) return Direction.UP;
        return s.getDirection();
    }

    public Direction getNextMove(Point food) {
        if(movesToApple.isEmpty()) calculateMovesToApple(food);
        if(movesToApple.isEmpty()) movesToApple.add(snake.getDirection());
        return movesToApple.remove(0);
    }

    public ArrayList<Direction> getMovesToApple() {
        return movesToApple;
    }

    public boolean checkIfSafeMove(Snake s, Direction d) {
        s.setDirection(d);
        s.move();

        if(s.colliding() || s.outOfBounds(canvas)) {
            s.setDirection(getOpposite(d));
            s.move();
            return false;
        }
        s.setDirection(getOpposite(d));
        s.move();
        s.setDirection(ogDir);
        return true;
    }

    public Direction getOpposite(Direction d) {
        switch (d) {
            case LEFT -> {
                return Direction.RIGHT;
            }
            case UP -> {
                return Direction.DOWN;
            }
            case RIGHT -> {
                return Direction.LEFT;
            }
            default -> {
                return Direction.UP;
            }
        }
    }

    private void sleepAndDrawCanvas(Snake s) {
        //if(snake.getTail().size() < 20) return;
        try {
            canvas.clearScreen();
            canvas.drawSnakeDebug(s);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void sleepAndDrawCanvas(Snake s, Point a) {
        if(snake.getTail().size() < 50) return;
        try {
            canvas.clearScreen();
            canvas.drawSnakeDebug(s);
            canvas.drawPoints(a, Color.GREEN);
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
