package com.amazurok.swingy.controller;

import com.amazurok.swingy.exceptions.IllegalInputException;
import com.amazurok.swingy.model.Game;
import com.amazurok.swingy.model.artifacts.Artifact;
import com.amazurok.swingy.model.characters.Person;
import com.amazurok.swingy.view.ConsoleView;
import com.amazurok.swingy.view.WindowManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

public class GamePlayController {
    Logger log = LoggerFactory.getLogger(GamePlayController.class);

    private enum GameStage {START, SELECTION, CREATION, ERRORS, PLAY, FIGHT, RUN, WIN, GAME_OVER, QUIT}

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
        String res;
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
                    res = windowManager.displayPlayerSelectionView(heroes);
                    if (res.equals("q")) {
                        stage = GameStage.QUIT;
                    } else if (res.equals("b")) {
                        stage = GameStage.START;
                    } else {
                        characterController.setPerson(db.getHeroById(res));
                        characterController.createEnemy();
                        stage = GameStage.PLAY;
                    }
                    break;
                case CREATION:
                    windowManager.displayCreatePlayerView();
                    characterController.createEnemy();
                    stage = GameStage.PLAY;
                    break;
                case PLAY:
                    res = windowManager.displayPlayView();
                    if (characterController.move(res)) {
                        stage = GameStage.WIN;
                    }
                    if (characterController.isItTimeToFight()) {
                        if (windowManager.displayFightOrRun().equals("f")) {
                            stage = GameStage.FIGHT;
                        } else {
                            stage = GameStage.RUN;
                        }
                    }
                    break;
                case FIGHT:
                    Person person = characterController.fight();
                    windowManager.displayFightReport(person);
                    if (person.getType().equals("Enemy")) {
                        stage = GameStage.GAME_OVER;
                    } else {
                        Artifact artifact = characterController.getArtifact();
                        if (artifact != null) {
                            if (windowManager.displayArtifact(artifact).equals("y")) {
                                characterController.setArtifact(artifact);
                            }
                        }
                        characterController.createMap();
                        stage = GameStage.PLAY;
                    }
                    break;
                case RUN:
                    if (characterController.run()) {

                        stage = GameStage.PLAY;
                    } else {
                        windowManager.displayForcedFightNotice();
                        stage = GameStage.FIGHT;
                    }
                    break;
                case ERRORS:
                    break;
                case WIN:
                    if (windowManager.displayWinView()) {
                        stage = GameStage.PLAY;
                        characterController.getPerson().incrementExperience(2000);
                        characterController.setPersonToCentre();
                        characterController.createEnemy();
                    } else {
                        stage = GameStage.QUIT;
                    }
                    break;
                case GAME_OVER:
                    if (windowManager.displayGameOver()) {
                        stage = GameStage.START;
                    } else {
                        stage = GameStage.QUIT;
                    }
                    break;
                case QUIT:
                    windowManager.displayQuitDialogue();
                    System.exit(0);
                    break;
            }
        }
    }
}
