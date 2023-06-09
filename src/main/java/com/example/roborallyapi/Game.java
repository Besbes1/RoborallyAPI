package com.example.roborallyapi;

import java.util.Random;

public class Game {

    int numPlayers;
    String boardOption;

    int gameId;

    public boolean started;
    public boolean isStarted() {
        return started;
    }

    public Game(int numPlayers, String boardOption) {
        this.numPlayers = numPlayers;
        this.boardOption = boardOption;
        this.gameId = gameId;

        Random random = new Random();

        gameId = random.nextInt(200);

        System.out.println(numPlayers + " players" + "," + " board " + boardOption + "," + " GameID " + gameId);
    }

    public int  getGameId() {
        return gameId;
    }

}
