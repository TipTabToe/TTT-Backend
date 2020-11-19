package fi.tuni.tamk.tiko.tiptabtoe.service;

import fi.tuni.tamk.tiko.tiptabtoe.model.Game;
import fi.tuni.tamk.tiko.tiptabtoe.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {
    private List<Game> games = new ArrayList<>();

    public String createGame() {
        var game = new Game();
        games.add(game);
        return game.getUUID();
    }

    public Game findByUUID(String uuid) {
        return games.stream()
                .filter(g -> g.getUUID().equals(uuid))
                .findAny()
                .orElse(null);
    }
}
