package com.example.roborallyapi;

import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;

@RestController
public class controller {

    private int players;
    private String board;


    // load game
    @GetMapping("/game/{id}")
    public String loadGame(@PathVariable String game) {





        // Return the loaded game data
        return "String";
    }

    @GetMapping("/new/{players}/{board}")
    public String newGame(@PathVariable String players, @PathVariable String board) {



        this.players = Integer.parseInt(players);
        this.board = board;

        System.out.println(players + "  players" + " , board  " + board);

        return "OK";
    }


    // save game
    @PutMapping("/game/{id}")
    public void saveGame(@RequestBody String game, @PathVariable String id) {

        String filename = "src/main/resources/templates/"+ id;

        FileWriter fileWriter = null;
        try{
            fileWriter = new FileWriter(filename);
            fileWriter.write(game);
        } catch (IOException e1) {}

        if (fileWriter != null) {
            try {
                fileWriter.close();
            } catch (IOException e2) {}
        }
    }
}
