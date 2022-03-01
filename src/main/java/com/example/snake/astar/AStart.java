package com.example.snake.astar;

import com.example.snake.Direction;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class AStart {
    private ArrayList<Node> openList = new ArrayList<>();
    private ArrayList<Node> closedList = new ArrayList<>();



    public ArrayList<Node> run(int[][] maze, Position start, Position end) {
        Node startNode = new Node(start);
        Node endNode = new Node(end);
        startNode.setG(0);
        openList.add(startNode);

        while(!openList.isEmpty()) {

            Node currentNode = openList.get(0);

            for(Node n : openList) {
                currentNode = currentNode.getF() < n.getF() ? currentNode : n;
            }


            openList.remove(currentNode);
            closedList.add(currentNode);

            if(maze[currentNode.getPos().getY()][currentNode.getPos().getX()] != 0) continue;

            if(currentNode.getPos().equals(endNode.getPos())) {
                ArrayList<Node> path = new ArrayList<>();
                path = getPath(currentNode);
                Collections.reverse(path);

                return path;
            }


            for(Node neighbour : currentNode.getNeighbours(maze.length, maze[0].length)) {
                int si = currentNode.getNeighbours(maze.length, maze[0].length).size();
                int tenativeG = currentNode.getG() + 1;
                if(openList.contains(neighbour)) {
                    neighbour = openList.get(openList.indexOf(neighbour));
                }
                if(closedList.contains(neighbour)) continue;
                if(neighbour.getG() > tenativeG) {
                    neighbour.setG(tenativeG);
                    neighbour.setF(tenativeG + neighbour.getH(endNode));
                    neighbour.setParent(currentNode);
                    if(!openList.contains(neighbour)) {
                        openList.add(neighbour);
                    }
                }
            }

        }
        // No route found
        return null;
    }

    public ArrayList<Node> getPath(Node current) {
        ArrayList<Node> path = new ArrayList<>();
        path.add(current);
        while(current != null) {
            current = current.getParent();
            if(current == null) break;
            path.add(current);
        }
        return path;
    }

    public ArrayList<Node> getOpenList() {
        return openList;
    }

    public void setOpenList(ArrayList<Node> openList) {
        this.openList = openList;
    }

    public ArrayList<Node> getClosedList() {
        return closedList;
    }

    public void setClosedList(ArrayList<Node> closedList) {
        this.closedList = closedList;
    }

    public static ArrayList<Direction> pathToMoves(ArrayList<Node> path) {
        if(path == null) return null;
        ArrayList<Direction> moves = new ArrayList<>();
        for(int i = 1; i < path.size(); i++) {
            Node prev = path.get(i - 1);
            Node current = path.get(i);
            if(prev.getPos().getX() < current.getPos().getX()) moves.add(Direction.RIGHT);
            else if(prev.getPos().getX() > current.getPos().getX()) moves.add(Direction.LEFT);
            else if(prev.getPos().getY() < current.getPos().getY()) moves.add(Direction.DOWN);
            else if(prev.getPos().getY() > current.getPos().getY()) moves.add(Direction.UP);
        }
        return moves;
    }

}
