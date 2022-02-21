package com.example.snake;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class SnakeCanvas extends Canvas {
    private GraphicsContext gc;
    private Color backroundColor = Color.GRAY;

    public SnakeCanvas(int w, int h) {
        super(w, h);
        gc = this.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1.2);
        clearScreen();
    }

    public void clearScreen() {
        gc.setFill(backroundColor);
        gc.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    public void drawPoints(ArrayList<Point> points) {
        gc.setFill(Color.WHITE);
        points.forEach(point -> {
            gc.fillRect(point.getX(), point.getY(), 10, 10);
            //gc.strokeRect(point.getX(), point.getY(), 10, 10);
        });
    }
    public void drawPoints(Point point, Color color) {
        gc.setFill(color);
        gc.fillRect(point.getX(), point.getY(), 10, 10);
    }
    public void drawPoints(ArrayList<Point> points, Color color) {
        gc.setFill(color);
        points.forEach(point -> {
            gc.fillRect(point.getX(), point.getY(), 10, 10);

        });
    }
    public void drawPoints(Point point) {
        gc.setFill(Color.WHITE);
        gc.fillRect(point.getX(), point.getY(), 10, 10);
        //gc.strokeRect(point.getX(), point.getY(), 10, 10);
    }

    public void drawSnake(Snake snake) {
        drawPointsSnake(snake);
    }
    public void drawSnakeDebug(Snake snake) {
        drawPointsSnake(snake);
    }
    public void drawPointsSnake(Snake snake) {
        for(int i = 0; i < snake.getTail().size(); i++) {
            ArrayList<Direction> directionOfSnake;
            if(i == 0) {
                drawPoints(snake.getTail().get(0));
                directionOfSnake = checkDirections(snake.getTail().get(0), snake.getHead(), new Point(0, 0));
                drawLines(directionOfSnake, snake.getTail().get(0));
                continue;
            } else if(i == snake.getTail().size() - 1) {
                drawPoints(snake.getTail().get(snake.getTail().size() - 1));
                directionOfSnake = checkDirections(snake.getTail().get(snake.getTail().size() - 1), new Point(0, 0), snake.getTail().get(snake.getTail().size() - 2));
                drawLines(directionOfSnake, snake.getTail().get(snake.getTail().size() - 1));
                continue;
            }

            Point p = snake.getTail().get(i);
            Point pn = snake.getTail().get(i + 1);
            Point pp = snake.getTail().get(i - 1);
            // left has snake
            directionOfSnake = checkDirections(p, pn , pp);
            drawPoints(p);
            drawLines(directionOfSnake, p);
        }
    }

    private ArrayList<Direction> checkDirections(Point p, Point pn, Point pp) {
        ArrayList<Direction> directionOfSnake = new ArrayList<>();
        if((p.getX() - 10 == pp.getX() && p.getY() == pp.getY()) || (p.getX() - 10 == pn.getX() && p.getY() == pn.getY())) {
            directionOfSnake.add(Direction.LEFT);
        }
        if((p.getX() + 10 == pp.getX() && p.getY() == pp.getY()) || (p.getX() + 10 == pn.getX() && p.getY() == pn.getY())) {
            directionOfSnake.add(Direction.RIGHT);
        }
        if((p.getY() - 10 == pp.getY() && p.getX() == pp.getX()) || (p.getY() - 10 == pn.getY() && p.getX() == pn.getX())) {
            directionOfSnake.add(Direction.UP);
        }
        if((p.getY() + 10 == pp.getY() && p.getX() == pp.getX()) || (p.getY() + 10 == pn.getY() && p.getX() == pn.getX())) {
            directionOfSnake.add(Direction.DOWN);
        }
        return directionOfSnake;
    }

    public void drawLines(ArrayList<Direction> d, Point p) {
        if(!d.contains(Direction.LEFT)) {
            gc.strokeLine(p.getX(), p.getY(), p.getX(), p.getY() + 10);
        }
        if(!d.contains(Direction.RIGHT)) {
            gc.strokeLine(p.getX() + 10, p.getY(), p.getX() + 10, p.getY() + 10);
        }
        if(!d.contains(Direction.UP)) {
            gc.strokeLine(p.getX(), p.getY(), p.getX() + 10, p.getY());
        }
        if(!d.contains(Direction.DOWN)) {
            gc.strokeLine(p.getX(), p.getY() + 10, p.getX() + 10, p.getY() + 10);
        }
    }


    public void writeEndGameText(int score) {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font(20));
        gc.fillText("Score: " + score, 20, 200);
    }
}
