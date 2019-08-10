package com.amazurok.swingy.controller;

import com.amazurok.swingy.exceptions.IllegalInputException;
import com.amazurok.swingy.model.Game;
import com.amazurok.swingy.model.characters.Person;
import com.amazurok.swingy.view.ConsoleView;
import com.amazurok.swingy.view.WindowManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GamePlayController {
    Logger log = LoggerFactory.getLogger(GamePlayController.class);

    private enum GameStage {START, SELECTION, CREATION, ERRORS, PLAY, FIGHT, RUN, GAME_OVER, QUIT}

    private DBController db = new DBController();
    private CharacterController characterController = new CharacterController(db);

    private WindowManager windowManager;
    private GameStage stage = GameStage.START;

    public GamePlayController(String mode) {
        if (mode.toLowerCase().equals("console") || mode.toLowerCase().equals("c")) {
            windowManager = new ConsoleView(characterController);
        } else if (mode.toLowerCase().equals("gui") || mode.toLowerCase().equals("g")) {
            //TODO: add graphic mode
        } else {
            log.error("Invalid game mode");
            System.exit(1);
        }
    }

    public void game() {
        while (true) {
            switch (stage) {
                case START:
                    Integer startView = windowManager.displayStartView();
                    if (startView.equals(1)) {
                        stage = GameStage.CREATION;
                    } else if (startView.equals(2)) {
                        stage = GameStage.SELECTION;
                    } else {
                        stage = GameStage.QUIT;
                    }
                    break;
                case SELECTION:
                    List<Person> heroes = db.getHeroes();
                    String res = windowManager.displayPlayerSelectionView(heroes);
                    if (res.equals("q")) {
                        stage = GameStage.QUIT;
                    } else if (res.equals("b")) {
                        stage = GameStage.START;
                    } else {
                        characterController.getPerson(db.getHeroById(res));
                        stage = GameStage.PLAY;
                    }
                    break;
                case CREATION:
                    try {
                        windowManager.displayCreatePlayerView();
                        stage = GameStage.PLAY;
                    } catch (IllegalInputException e) {
                        log.error("Error with creating hero\n" + e.getMessage());
                    }
                    break;
                case PLAY:
                    break;
                case FIGHT:
                    break;
                case RUN:
                    break;
                case ERRORS:
                    break;
                case GAME_OVER:
                    break;
                case QUIT:
                    System.exit(0);
            }
        }
    }
}
