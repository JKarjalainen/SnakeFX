package com.example.snake;

import java.util.ArrayList;

public class SimSnake extends Snake {

    private ArrayList<Direction> triedDirections = new ArrayList<>();

    public SimSnake(Point start) {
        super(start);
    }

    public ArrayList<Direction> getTriedDirections() {
        return triedDirections;
    }

    public void addToTriedDirections(Direction d) {
        triedDirections.add(d);
    }


}
