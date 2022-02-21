package com.example.snake;

import java.util.ArrayList;

public class Snake {
    private final Point head;
    private ArrayList<Point> tail;
    private Direction direction;
    private Point lasPos;

    public Snake(Point startingPos) {
        this.head = new Point(startingPos);
        this.tail = new ArrayList<>();
        direction = Direction.DOWN;
        this.tail.add(new Point(startingPos.getX(), startingPos.getY() - 10));
    }

    public Point getHead() {
        return head;
    }

    public ArrayList<Point> getTail() {
        return tail;
    }

    public void move() {
        lasPos = new Point(head.getX(), head.getY());
        switch (direction) {
            case LEFT -> head.setX(head.getX() - 10);
            case UP -> head.setY(head.getY() - 10);
            case RIGHT -> head.setX(head.getX() + 10);
            case DOWN -> head.setY(head.getY() + 10);
        }


    }

    public void setDirection(Direction newDirection) {
        direction = newDirection;
    }

    public void grow() {
        tail.add(lasPos);
    }

    public void shift() {
        tail.remove(0);
        tail.add(lasPos);
    }

    public void die() {
        System.out.println("Your length was " + tail.size());
        GameController.getInstance().setPlaying(false);
    }

    public int score() {
        return tail.size();
    }
    public boolean colliding() {
        for(Point p : tail) {
            if (p.getX() == head.getX() && p.getY() == head.getY())
                return true;
        }
        return false;
    }
    public boolean outOfBounds(SnakeCanvas canvas) {
        return getHead().getX() >= canvas.getWidth() ||
                getHead().getX() < 0 ||
                getHead().getY() >= canvas.getHeight() ||
                getHead().getY() < 0;
    }
    public boolean colliding(Point a) {
        for(Point p : tail) {
            if (p.getX() == a.getX() && p.getY() == a.getY())
                return true;
        }

        return false;
    }

    public Direction[] getWorseDirection() {
        int right = 0, left = 0, up = 0, down = 0;

        for(Point p : tail) {
            if(p.getX() > head.getX()) left++;
            else right++;

            if(p.getY() > head.getY()) down++;
            else up++;
        }
        Direction[] d = new Direction[2];

        d[0] = right > left ? Direction.LEFT : Direction.RIGHT;
        d[1] = up > down ? Direction.DOWN : Direction.UP;

        return d;
    }

    public Direction getDirection() {
        return direction;
    }

    public Snake copy() {
        Point p = new Point(getHead().getX(), getHead().getY());
        Snake s = new Snake(p);
        s.setTail((ArrayList<Point>) getTail().clone());
        return s;
    }
    public SimSnake copySim() {
        Point p = new Point(getHead().getX(), getHead().getY());
        SimSnake s = new SimSnake(p);
        s.setTail((ArrayList<Point>) getTail().clone());
        return s;
    }

    public void setTail(ArrayList<Point> newT) {
        tail = newT;
    }
}
