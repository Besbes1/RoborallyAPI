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

    final  private List<String> gameFiles = new ArrayList<>();


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

@PostMapping("savedGame")
 public ResponseEntity<List<String>> savedGame() {

    File resources = new File("src/main/resources/templates/");
    File[] listOfFiles = resources.listFiles();
    for(int i = 0 ; i < listOfFiles.length ; i++) {
        if (listOfFiles[i].isFile()) {
            //String filename = Files.readString(listOfFiles[i].getName());
            String filename = listOfFiles[i].getName();
            gameFiles.add(filename);
        }
    }
    return ResponseEntity.status(HttpStatus.OK).body(gameFiles);
}




    // initialize game
    @GetMapping("/new/{players}/{board}")
    public String newGame(@PathVariable String players, @PathVariable String board) {



        this.players = Integer.parseInt(players);
        this.board = board;

        System.out.println(players + "  players" + " , board  " + board);

        return "OK";
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
