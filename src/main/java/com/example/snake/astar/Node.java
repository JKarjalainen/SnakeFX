package com.example.snake.astar;

import java.util.ArrayList;

public class Node {
    private Position pos;
    private int g = Integer.MAX_VALUE - 100;
    private int h = 0;
    private int f = 0;
    private Node parent = null;

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node(Position pos) {
        this.pos = pos;
    }

    public Node(Position pos, Node parent) {
        this.pos = pos;
        this.parent = parent;

    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getH(Node endPoint) {
        int xDiff = (pos.getX() - endPoint.getPos().getX());
        int yDiff = (pos.getY() - endPoint.getPos().getY());
        xDiff = (int)Math.sqrt(xDiff * xDiff);
        yDiff = (int)Math.sqrt(yDiff * yDiff);

        return (xDiff + yDiff);
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public ArrayList<Node> getNeighbours(int maxSizeY, int maxSizeX)  {
        ArrayList<Node> returns = new ArrayList<>();
        returns.add(new Node(new Position(pos.getX() - 1, pos.getY())));
        returns.add(new Node(new Position(pos.getX() + 1, pos.getY())));
        returns.add(new Node(new Position(pos.getX(), pos.getY() - 1)));
        returns.add(new Node(new Position(pos.getX(), pos.getY() + 1)));
        returns.removeIf(x -> x.getPos().getX() < 0 || x.getPos().getY() < 0 || x.getPos().getX() >= maxSizeX || x.getPos().getY() >= maxSizeY);
        return returns;
    }

    @Override
    public boolean equals(Object o) {
        return pos.equals(((Node) o).getPos());
    }
}
