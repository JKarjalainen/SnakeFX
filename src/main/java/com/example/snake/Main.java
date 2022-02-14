package com.example.snake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    SnakeCanvas snakeCanvas;


    Snake snake;
    @Override
    public void start(Stage stage) throws IOException {
        try {
            BorderPane root = new BorderPane();
            snake = new Snake(new Point(40, 40));
            snakeCanvas = getContent();
            root.setCenter(snakeCanvas);

            snakeCanvas.drawPoints(snake.getHead());

            Scene scene = new Scene(root,400,400);
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }

        GameLoop gameLoop = new GameLoop(snake, snakeCanvas);

        gameLoop.start();
    }

    private SnakeCanvas getContent() {
        return new SnakeCanvas(400, 400);
    }

    public static void main(String[] args) {
        launch();
    }
}