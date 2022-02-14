package com.example.snake;

public class GameController {
    private static GameController instance = null;
    private boolean playing = true;

    private GameController() {}

    public static GameController getInstance() {
        if(instance == null)
            instance = new GameController();
        return instance;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
}
