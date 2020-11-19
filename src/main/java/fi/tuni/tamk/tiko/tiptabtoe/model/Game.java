package fi.tuni.tamk.tiko.tiptabtoe.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game {
    UUID uuid = UUID.randomUUID();
    List<User> players = new ArrayList<>();
    public Game() {
        System.out.println("Game created");
        System.out.println("UUID: " + uuid);
    }

    public String getUUID() {
        return uuid.toString();
    }
}
