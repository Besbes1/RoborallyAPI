package com.example.roborallyapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class controller {

    private int players;
    private String board;

    public int newGame;

    public int join;
    public Game game;

    List<Game> running_Game;

    // load game
    @GetMapping("/loadGame/{id}")
    public ResponseEntity<String> loadGame(@PathVariable String id) {

        String fileName = "src/main/resources/templates/" + id;

        try {
            String fileContent = Files.readString(Paths.get(fileName), StandardCharsets.UTF_8);
            return ResponseEntity.status(HttpStatus.OK).body(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
    }

    //join game

    @PostMapping("/join/{gameId}")
    public ResponseEntity<String> joinGame(@PathVariable int gameId) {
        // Check if the game exists and is available for joining
        if (game != null && game.getGameId() == gameId && !game.isStarted()) {
            // Add the logic to join the game
            // You can update the game state to include the new player or perform any necessary operations

            // Return a success response
            return ResponseEntity.status(HttpStatus.OK).body("Successfully joined the game.");
        } else {
            // Return an error response if the game doesn't exist or is not available for joining
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to join the game.");
        }
    }




@GetMapping("/sendList/{path}")
 public ResponseEntity<String> sendList(@PathVariable String path) {

    List<String> gameFiles = new ArrayList<>();

    File resources = new File("src/main/resources/" + path);
    File[] listOfFiles = resources.listFiles();
    for(int i = 0 ; i < listOfFiles.length ; i++) {
        if (listOfFiles[i].isFile()) {
            //String filename = Files.readString(listOfFiles[i].getName());
            String filename = listOfFiles[i].getName();
            gameFiles.add(filename);
        }
    }
    return ResponseEntity.status(HttpStatus.OK).body(String.join(",", gameFiles));
}


    // initialize game
    @GetMapping("/new/{players}/{boardNum}")
    public ResponseEntity<String> newGame (@PathVariable int players, @PathVariable String boardNum) {
        System.out.println(players + "  players" + " , board  " + boardNum);
        game = new Game(players, boardNum);
        String filePath = "src/main/resources/boardOptions/"+ boardNum + ".json";
        try {
            String fileContent = Files.readString(Paths.get(filePath), StandardCharsets.UTF_8);
            return ResponseEntity.status(HttpStatus.OK).body(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //add players and board number into game to create a game

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
    }


    // send gameID
    @GetMapping("/gameID")
    public ResponseEntity<String> gameID() {
        try {
            int GameId = game.getGameId();
            return (ResponseEntity.status(HttpStatus.OK).body(String.valueOf(GameId)));
        } catch (NullPointerException e) // if game is null
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game is null");
        }

    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {

        return ResponseEntity.status(HttpStatus.OK).body("serverisup");
    }


    // save game
    @PostMapping("/saveGame/{id}")
    public void saveGame(@RequestBody String game, @PathVariable String id) {

        String filename = "src/main/resources/templates/"+ id;

        FileWriter fileWriter = null;
        try{
            fileWriter = new FileWriter(filename);
            fileWriter.write(game);
        } catch (IOException e1) {
            System.out.println(e1);
        }

        if (fileWriter != null) {
            try {
                fileWriter.close();
            } catch (IOException e2) {}
        }

        System.out.println(game + id);
    }

}
