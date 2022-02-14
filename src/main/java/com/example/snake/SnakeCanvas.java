package com.example.snake;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class SnakeCanvas extends Canvas {
    private GraphicsContext gc;

    public SnakeCanvas(int w, int h) {
        super(w, h);
        gc = this.getGraphicsContext2D();
        clearScreen();
    }

    public void clearScreen() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    public void drawPoints(ArrayList<Point> points) {
        gc.setFill(Color.WHITE);
        points.forEach(point -> {
            gc.fillRect(point.getX(), point.getY(), 10, 10);
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
    }

    public void drawSnake(Snake snake) {
        drawPoints(snake.getHead());
        drawPoints(snake.getTail());
    }

    public void writeEndGameText(int score) {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font(20));
        gc.fillText("Score: " + score, 20, 200);
    }
}
